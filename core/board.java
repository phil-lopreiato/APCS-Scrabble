/* Copyright (C) 2012 Phil Lopreiato, Justin Yost
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
 * @author 	Phil Lopreiato
 * @author 	Justin Yost
 * @version 1.0
 */

package core;

//could be called masterBoard
public class board
{
	private static tile[][] board;

	public board()
	{
		board = new tile[15][15];
		int[][] specialLayout = {
				{3,0,0,-2,0,0,0,3,0,0,0,-2,0,0,3},
				{0,2,0,0,0,-3,0,0,0,-3,0,0,0,2,0},
				{0,0,2,0,0,0,-2,0,-2,0,0,0,2,0,0},
				{-2,0,0,2,0,0,0,-2,0,0,0,2,0,0,-2},
				{0,0,0,0,2,0,0,0,0,0,2,0,0,0,0},
				{0,-3,0,0,0,-3,0,0,0,-3,0,0,0,-3,0},
				{0,0,-2,0,0,0,-2,0,-2,0,0,0,-2,0,0},
				{3,0,0,-2,0,0,0,0,0,0,0,-2,0,0,3},
				{0,0,-2,0,0,0,-2,0,-2,0,0,0,-2,0,0},
				{0,-3,0,0,0,-3,0,0,0,-3,0,0,0,-3,0},
				{0,0,0,0,2,0,0,0,0,0,2,0,0,0,0},
				{-2,0,0,2,0,0,0,-2,0,0,0,2,0,0,-2},
				{0,0,2,0,0,0,-2,0,-2,0,0,0,2,0,0},
				{0,2,0,0,0,-3,0,0,0,-3,0,0,0,2,0},
				{3,0,0,-2,0,0,0,3,0,0,0,-2,0,0,3},
		};

		for(int i=0; i<15; i++)
			for(int j=0; j<15; j++)
				if(specialLayout[i][j] != 0)
					board[i][j] = new tile(specialLayout[i][j]);
	}

	/**
	 * Returns a 2D array of tiles currently in the board
	 * 
	 * @return a 2D tile[][]
	 */
	public static tile[][] getBoard()
	{
		return board;
	}

	/**
	 * Adds a virtual board to the final board
	 * 
	 * Takes a 2 dimensional tile array (a virtual board) which contains the tiles placed in the current turn. This has already been validated and scored.
	 * The virtual board is then added to the main board when the move has been played.
	 * 
	 * @param VB 2D tile[] containing this turn's virtual board
	 */
	public static void addVB(tile[][] VB)
	{
		for(int row=0; row<15; row++)
			for(int col=0; col<15; col++)
				board[row][col] = VB[row][col];
	}

	/**
	 * Determines is a location on the board is empty
	 * 
	 * @param x		x-coordinate of the location to test
	 * @param y		y-coordinate of the location to test
	 * @return		true if the location is empty or contains a multiplier space, false if it is occupied
	 */
	public static boolean isEmpty(int x, int y)
	{
		return board[x][y] == null || board[x][y].getSpecial() != 0;
	}

	//include methods such as find first tile in word... (i think this should be in virtual board instead)
	
	public static void paint(core.gui.GUI gui) {
				
	}
}