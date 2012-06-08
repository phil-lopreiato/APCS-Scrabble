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

import java.util.ArrayList;

import core.gui.GUI;

public class game {
	private static player players[];
	private static int playersTurn;
	private static GUI gui;
	private int winner;

	/**
	 * @param in	input reference to the GUI
	 */
	public game(GUI in)
	{
		new board();
		new virtualBoard();
		new bag();
		new indexedDictionary();
		setGUI(in);
		winner = -1;
		playersTurn = -1; //start with -1 so when newTurn() is called, the first player (player 0) actually goes
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
	public void start(int num)
	{
		players = new player[num];
		for (int i=0; i<num; i++)
			players[i] = new player();
		gui.setNumPlayers(num);
		gui.loadGameDisplay();
		gui.updateBagTiles(bag.getSize());
		newTurn();
	}

	public boolean isEmpty(int x, int y) {
		return board.isEmpty(x, y);
	}

	public void loadDict() {
		indexedDictionary.loadDict();
	}

	/**
	 * Ends the current turn and moves on to the next player
	 */
	private void newTurn()
	{
		playersTurn++;
		playersTurn %= getNumPlayers();
		gui.setTurn(playersTurn);
		virtualBoard.reset(players[playersTurn]);
		gui.waitForTurn();
		drawCurrentRack();
	}

	/**
	 * Returns the number of players in this game
	 * 
	 * @return	number of players playing this game
	 */
	public int getNumPlayers()
	{
		return players.length;
	}

	/**
	 * Returns the number of the player whose turn it currently is (0 thru n-1, where n in the number of players)
	 * 
	 * @return	the number of the player whose turn it currently is
	 */
	public int getCurrentPlayerIndex()
	{
		return playersTurn;
	}

	/**
	 * Used to check if the game has been completed
	 * 
	 * @return	true if the game has been completed, false if not
	 */
	public static int gameOver()
	{
		boolean result = false;
		int i = 0;
		while (!result && i<players.length)
		{
			if(players[i].getRack().isEmpty())
				result = true;
			i++;
		}
		return result?i--:-1;
	}

	/**
	 * Submits the current turn
	 * 
	 * Submits the current virtual board, which is then validated and scored. If the tiles placed are valid, the turn is played and the game moves to the next player.
	 */
	public void submit()
	{
		if(virtualBoard.submit())
		{
			gui.updateScore(playersTurn, players[playersTurn].getScore());
			board.paint(gui); //update the board's display
			players[playersTurn].getRack().draw();
			gui.updateBagTiles(bag.getSize());
			winner = gameOver();
			if(winner >= 0)
			{
				gui.gameOver(winner);
				System.exit(0);
			}
			else
				newTurn();
		}
	}

	/**
	 * Passes the current turn. Play proceeds to the next player
	 */
	public void pass()
	{
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
		boolean canPlace = virtualBoard.place(rackIndex, x, y);
		if(canPlace)
			virtualBoard.paint(gui); //update the VB in GUI
		return canPlace;
	}

	public boolean swap(int firstX, int firstY, int secondX, int secondY) {
		boolean canSwap = virtualBoard.swap(firstX, firstY, secondX, secondY);
		if(canSwap)
			virtualBoard.paint(gui);
		return canSwap;
	}

	/**
	 * Draws the current player's rack onto the GUI
	 */
	public void drawCurrentRack() {
		players[playersTurn].getRack().paint(gui);
	}

	/**
	 * @param rackIndex 	the location in the player's rack of the tile to place
	 * @param x				x-coordinate on the board to locate the tile
	 * @param y				y-coordinate on the board to locate the tile
	 * @return				true if the tile was successfully returned to the player's rack, false otherwise
	 */
	public boolean replaceTile(int rackIndex, int x, int y)
	{
		boolean canReplace = virtualBoard.replace(rackIndex, x, y);
		if(canReplace){
			virtualBoard.paint(gui); //update VB in GUI
		}
		return canReplace;
	}

	public String checkWord(String text) {
		return indexedDictionary.checkWord(text)?text+" is a valid word and is worth "+indexedDictionary.scoreWord(text)+" points":text+" is not a valid word";
	}

	public int scoreVB() {
		return virtualBoard.validate();
	}

	public void submitBlanks(ArrayList<Character> blanks, ArrayList<Integer[]> blankLocs) {
		for(int i = 0;i<blanks.size();i++) {
			virtualBoard.replaceTile(blanks.get(i), blankLocs.get(i)[0], blankLocs.get(i)[1]);
		}
	}

	public void removeVB() {
		virtualBoard.clear();
		gui.hideVB();
		virtualBoard.paint(gui);
		drawCurrentRack();
	}

}