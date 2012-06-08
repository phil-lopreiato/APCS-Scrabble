/* Copyright (C) 2012 Justin Yost, Phil Lopreiato
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

/**
 * @author 	Justin Yost
 * @author 	Phil Lopreiato
 * @version 1.0
 */

package core;

public class virtualBoard
{
	private static tile[][] virtualBoard, masterBoard;
	private static tileProperties[][] properties;
	private static player currentPlayer;

	public virtualBoard()
	{
		virtualBoard = new tile[15][15];
		properties = new tileProperties[15][15];
		for(int i=0; i<15; i++)
			for(int j=0; j<15; j++)
			{
				properties[i][j] = new tileProperties();
			}
	}

	/**
	 * Resets the virtual board for use during a new turn
	 * 
	 * @param Player	player whose turn it is
	 */
	public static void reset(player Player)
	{
		virtualBoard = new tile[15][15];
		currentPlayer = Player;
	}

	/**
	 * Place a tile onto the virtual board
	 * 
	 * @param rackIndex		location on the rack where we can find the tile to place (0<n<7)
	 * @param x				x-coordinate on the board where we want to place the tile
	 * @param y				y-coordinate on the board where we want to place the tile
	 * @return				true if the tile can be placed in the location (i.e. no tile already exists in that location), false othewise
	 */
	public static boolean place(int rackIndex, int x, int y)
	{
		boolean placed = false;
		if(virtualBoard[x][y] == null && board.isEmpty(x,y))
		{
			tile Tile = currentPlayer.getRack().get(rackIndex);
			virtualBoard[x][y] = Tile;
			currentPlayer.getRack().remove(rackIndex);
			placed = true;
		}
		return placed;
	}

	public static boolean swap(int firstX, int firstY, int secondX, int secondY) {
		boolean swapped = false;
		tile temp;
		if(virtualBoard[firstX][firstY] != null || virtualBoard[secondX][secondY] != null) {
			temp = virtualBoard[firstX][firstY];
			virtualBoard[firstX][firstY] = virtualBoard[secondX][secondY];
			virtualBoard[secondX][secondY] = temp;
			swapped = true;
		}
		return swapped;
	}

	public static void replaceTile(char c,int x, int y) {
		virtualBoard[x][y] = new tile(c,0);
	}

	public static boolean replace(int rackIndex, int x, int y)
	{
		boolean replaced = false;
		if(virtualBoard[x][y] != null){
			if(rackIndex == -1) {
				for(int i=6;i>=0;i--) {
					if(currentPlayer.getRack().get(i) == null) {
						rackIndex = i;
						break;
					}
				}
			}
			replaced = currentPlayer.getRack().replace(rackIndex, virtualBoard[x][y]);
			virtualBoard[x][y] = null;
		}
		return replaced;
	}

	/**
	 * Submits a turn to the main board (also validates and scores)
	 * 
	 * @return	true if the VB has valid words and tile placement, false otherwise
	 */
	public static boolean submit()
	{
		masterBoard = board.getBoard();
		boolean valid = false;
		int score = validate();
		if(score != -1)
		{
			currentPlayer.updateScore(score);
			board.addVB(virtualBoard);
			valid = true;
		}
		return valid;
	}

	/**
	 * Validates the placement of tiles and checks all words made are valid.  Also scores the tile placement (see return value)
	 * 
	 * @return	The points the turn is worth or -2 for invalid tile placement, -1 for invalid word
	 */
	public static int validate()
	{
		int score = -2;
		if(checkPlacement())
			score = scoreTurn();

		clearChecks();
		return score;
	}

	/**
	 * Determines if the tiles currently placed on the board are in a valid arrangement.  They must be in a continuous line while touching a tile
	 * that's already been played to be considered valid
	 * 
	 * @return true if the current tile placement is valid
	 */
	private static boolean checkPlacement()
	{
		int row = -1, col = -1, count = 0;
		boolean rowCheck = true, colCheck = true, firstCall = true, touching = false, continuous = false, VBContinuous = true;
		for(int x=0; x<15; x++)
		{
			for(int y=0; y<15; y++)
			{
				if(virtualBoard[x][y] != null)
				{
					continuous = false;
					count++;
					if(firstCall)
					{
						firstCall = false;
						row = x;
						col = y;
					}
					else
					{
						rowCheck = x==row && rowCheck;
						colCheck = y==col && colCheck;
					}

					if(x>0)
						continuous = virtualBoard[x-1][y] != null || !board.isEmpty(x-1, y) || continuous;
					if(x<14)
						continuous = virtualBoard[x+1][y] != null || !board.isEmpty(x+1, y) || continuous;
					if(y>0)
						continuous = virtualBoard[x][y-1] != null || !board.isEmpty(x, y-1) || continuous;
					if(y<14)
						continuous = virtualBoard[x][y+1] != null || !board.isEmpty(x, y+1) || continuous;
					VBContinuous = continuous && VBContinuous;

					if(x>0)
						touching = !board.isEmpty(x-1, y) || touching;
					if(x<14)
						touching = !board.isEmpty(x+1, y) || touching;
					if(y>0)
						touching = !board.isEmpty(x, y-1) || touching;
					if(y<14)
						touching = !board.isEmpty(x, y+1) || touching;
				}
			}
		}
		if(board.isEmpty(7,7) && virtualBoard[7][7] != null)
			touching = true;
		if(count == 1 && !board.isEmpty(7,7))
			VBContinuous = true;
		return touching && VBContinuous && (rowCheck || colCheck);
	}

	/**
	 * Given a position on the board and a direction to search, this method finds the location of the first tile in the word.
	 * 
	 * @param	startX The X position of the tile to check
	 * @param	startY The Y position of the tile to check
	 * @param	direction True for horizontal, False for vertical
	 * @return	a 1D array of ints where the first value is the X position of the first tile, and the second is the Y position
	 */
	private static int[] findFirst(int startX, int startY, boolean direction)
	{
		int position[] = new int[2];
		if(virtualBoard[startX][startY] != null) {
			if(direction) { //horiz
				while(virtualBoard[startX][startY] != null || !board.isEmpty(startX, startY))
					--startX;
				position[0] = ++startX;
				position[1] = startY;
			}else { //vert
				while(virtualBoard[startX][startY] != null || !board.isEmpty(startX, startY))
					--startY;
				position[1] = ++startY;
				position[0] = startX;
			}
		}
		return position;
	}

	/**
	 * Scores the current turn
	 * 
	 * @return	the score from this turn
	 */
	public static int scoreTurn(){
		int first[], wordScore = 0, totalScore = 0, wordMultiplier = 1, letterMultiplier = 1;
		boolean valid = true;
		String word = "";
		for(int x=0; x<15; x++)
			for(int y=0; y<15; y++)
				if(virtualBoard[x][y] != null) {
					for(int i=0; i<2; i++) //check and score horizontal, then vertical
					{
						if(!(i==0?properties[x][y].isCheckedHorizontal():properties[x][y].isCheckedVertical()))
						{
							word = "";
							wordScore = 0;
							wordMultiplier = 1;
							letterMultiplier = 1;
							first = findFirst(x,y,i==0);
							while(virtualBoard[first[0]][first[1]] != null || !board.isEmpty(first[0], first[1]) && valid)
							{
								letterMultiplier = 1;
								if(virtualBoard[first[0]][first[1]] == null)
									word += masterBoard[first[0]][first[1]].getLetter();
								else
									word += virtualBoard[first[0]][first[1]].getLetter();
								if(board.getBoard()[first[0]][first[1]] != null && board.getBoard()[first[0]][first[1]].getSpecial() > 0)
								{ //word multiplier
									wordMultiplier *= board.getBoard()[first[0]][first[1]].getSpecial();
								}
								else if(board.getBoard()[first[0]][first[1]] != null)
								{
									letterMultiplier *= -board.getBoard()[first[0]][first[1]].getSpecial();
								}
								if(virtualBoard[first[0]][first[1]] == null)
									wordScore += masterBoard[first[0]][first[1]].getValue() * letterMultiplier;
								else
									wordScore += virtualBoard[first[0]][first[1]].getValue() * letterMultiplier;
								if(i==0)
									properties[first[0]][first[1]].setCheckedHorizontal(true);
								else
									properties[first[0]][first[1]].setCheckedVertical(true);
								first[i]++;
							}
							wordScore *= wordMultiplier;
							if(word.length() > 1 && valid)
							{
								valid = indexedDictionary.checkWord(word) && valid;
								totalScore += wordScore;
							}
						}
					}
				}
		return valid?totalScore:-1;
	}

	private static void clearChecks()
	{
		for(int i=0; i<15; i++)
			for(int j=0; j<15; j++)
			{
				properties[i][j].setCheckedHorizontal(false);
				properties[i][j].setCheckedVertical(false);
			}
	}

	public static void paint(core.gui.GUI gui) {
		gui.addVirtualBoard(virtualBoard);
	}
}