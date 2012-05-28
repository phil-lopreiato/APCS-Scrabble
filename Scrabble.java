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
 * @author 	Justin Yost
 * @author 	Phil Lopreiato
 * @version 1.0
 */

import core.*;
import core.gui.GUI;

public class Scrabble{
	public static void main(String[] args){
		System.out.println("Begin");
		game theGame;
		GUI f = new GUI(); //init new GUI
		f.gameInit(); //draw the game init stuff (select number of players)
		//this will eventually also ask for computer players
		f.show(); //show the main content panel

		//wait for button to be pressed
		do {
			try {
				Thread.sleep(100); //wait 100 ms
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}while(!f.numPlayersSet());

		theGame = new game(f.getNumPlayers()); //instantiate game with correct # players
		theGame.setGUI(f);
		
		f.clear(); //clear everything from frame, now we draw with the board

		f.loadGameDisplay(theGame.getNumPlayers()); //draw the board

		f.repaint(); //repaint the display



		//simulating game below.
		System.out.println(theGame.getNumPlayers() + " players");
		System.out.println("Player #" + theGame.getCurrentPlayerNumber());
		game.setRackToDisplay();
		theGame.submit();
		game.setRackToDisplay();
		f.repaint();
		//end simulation

		//thought process:
		while(!game.gameOver())
		{
			//player places tiles in virtual board
			//wait for submit
			//verify -- for now, let's just assume this is good until the rest of the game works
			//add tiles from virtual board to board
			//update score
			//remove tiles from playerRack
			//draw new tiles
			//next player's turn
		}
	}
}