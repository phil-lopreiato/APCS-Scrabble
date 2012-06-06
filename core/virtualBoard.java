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

//class to control a player's turn???
public class virtualBoard
{
	private static tile[][] virtualBoard;
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
			System.out.println(Tile);
			virtualBoard[x][y] = Tile;
			currentPlayer.getRack().remove(rackIndex);
			placed = true;
			System.out.println("placed");
		}
		return placed;
	}

	public static boolean replace(int rackIndex, int x, int y)
	{
		boolean replaced = false;
		if(virtualBoard[x][y] != null)
		{
			replaced = currentPlayer.getRack().replace(rackIndex, virtualBoard[x][y]);
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
		boolean valid = false;
		if(validate())
		{
			currentPlayer.updateScore(scoreTurn());
			board.addVB(virtualBoard);
			valid = true;
		}
		return valid;
	}

	/**
	 * Validates the placement of tiles and checks all words made
	 * 
	 * @return	true if all words and placements are valid, false otherwise
	 */


	public static boolean validate()
	{
		//check all words and placements and stuff - will need to load master board
		//check that all tiles are touching and in a row
		//find first tiles and check all create words
		clearChecks();
		return true;
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
		//TODO Make work
		int[] position = new int[2];
		position[0] = 0;
		position[1] = 0;		
		return position;
	}

	/**
	 * Scores the current turn
	 * 
	 * @return	the score from this turn
	 */
	public static int scoreTurn()
	{
		clearScoredProperties();
		return 23;
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

	private static void clearScoredProperties()
	{
		for(int i=0; i<15; i++)
			for(int j=0; j<15; j++)
			{
				properties[i][j].setScoredHorizontal(false);
				properties[i][j].setScoredVertical(false);
			}
	}
	
	public static void paint(core.gui.GUI gui) {
		gui.addVirtualBoard(virtualBoard);
	}
}