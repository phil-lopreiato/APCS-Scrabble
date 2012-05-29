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

import core.gui.GUI;

public class game {
	//private board board;
	private static player players[];
	//private bag bag;
	private static tile displayRack[];
	private static int playersTurn;
	private static GUI gui;

	public game(int numPlayers)
	{
		//board = new board();
		//bag = new bag();
		new board();
		new bag();
		displayRack = new tile[7];
		setNumPlayers(numPlayers);
		playersTurn = getNumPlayers() - 1; //start with last player so when newTurn() is called, the first player actually goes
		newTurn();
	}
	
	/**
	 * Give the game a reference to the GUI. 
	 * 
	 * By giving the game a reference to the parent GUI, it allows the gameplay to draw parts of the board (like racks and words before they're played) by 
	 * calling on a method from the GUI
	 * 
	 * @param in	input reference to the GUI
	 */
	public void setGUI(GUI in) {
		gui = in;
	}
	
	/**
	 * Set the number of players in this game
	 * 
	 * @param num	number of players who will be playing
	 */
	public void setNumPlayers(int num)
	{
		players = new player[num];
		for (int i=0; i<num; i++)
			players[i] = new player();
	}

	/**
	 * Returns the number of players in this game
	 * 
	 * @return	number of players playing this game
	 */
	public int getNumPlayers() {
		return players.length;
	}
	
	/**
	 * Returns the number of the player whose turn it currently is (0 thru n-1, where n in the number of players)
	 * 
	 * @return	the number of the player whose turn it currently is
	 */
	public int getCurrentPlayerNumber()
	{
		return playersTurn;
	}

	/**
	 * Used to check if the game has been completed
	 * 
	 * @return	true if the game has been completed, false if not
	 */
	public static boolean gameOver()
	{
		boolean result = false;
		int i = 0;
		while (!result && i<players.length)
		{
			if(players[i].getRack().isEmpty())
				result = true;
		}
		return result;
	}
	
	/**
	 * Submits the current turn
	 * 
	 * Submits the current virtual board, which is then validated and scored. If the tiles placed are valid, the turn is played and the game moves to the next player.
	 */
	public void submit()
	{
		if(virtualBoard.submit())
			newTurn();
	}

	/**
	 * Places a tile into the virtual board at a given location
	 * 
	 * @param rackIndex		the location in the player's rack of the tile to place
	 * @param x				x-coordinate on the board to place the tile
	 * @param y				y-coordinate on the board to place the tile
	 * @return				true if valid tile placement, false if not
	 */
	public boolean placeTile(int rackIndex, int x, int y)
	{
		return virtualBoard.place(rackIndex, x, y);
	}
	
	public boolean replaceTile(int rackIndex, int x, int y)
	{
		return virtualBoard.replace(rackIndex, x, y);
	}

	/**
	 * Ends the current turn and moves on to the next player
	 */
	private void newTurn()
	{
		playersTurn++;
		playersTurn %= getNumPlayers();
		virtualBoard.reset(players[playersTurn]);
		setRackToDisplay();
	}

	/**
	 * Draws the current player's rack onto the GUI
	 */
	private static void setRackToDisplay()
	{
		rack current = players[playersTurn].getRack();
		for(int i=0; i<7; i++)
		{
			displayRack[i] = current.get(i); //to avoid aliasing which i think is bad here...
			System.out.print(current.get(i).getLetter());
		}
		current.paint(gui);
		System.out.println();
	}
}