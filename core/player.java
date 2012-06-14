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

public class player
{
	private rack playerRack;
	private int score,  difficulty;
	private boolean sentient;
	private String name;

	public player(boolean sentient)
	{
		playerRack = new rack();
		score = 0;
		this.sentient = sentient;
	}
	
	public player(boolean sentient, String name, int difficulty) {
		playerRack = new rack();
		score = 0;
		this.sentient = sentient;
		this.name = name;
		this.difficulty = difficulty;
	}

	public int getDifficulty() {
    	return difficulty;
    }

	public String getName() {
    	return name;
    }

	/**
	 * Draws a new tile from the bag for each empty space in the player's rack
	 */
	public void draw()
	{
		playerRack.draw();
	}

	public boolean isSentient() {
    	return sentient;
    }

	/**
	 * Adds the parameter to the player's score
	 * 
	 * @param score		the score to add
	 */
	public void updateScore(int score)
	{
		this.score += score;
	}
	
	/**
	 * Returns the player's current score
	 * @return	the player's current score
	 */
	public int getScore()
	{
		return score;
	}
	
	public int getAdjustedScore() {
		int subtract = 0;
		rack playerRack = getRack();
		for(int i=0; i<7;i++)
			if(playerRack.get(i) != null)
				subtract += playerRack.get(i).getValue();
		
		return score - subtract;
	}

	/**
	 * Returns the player's rack
	 * @return	a reference to the player's rack
	 */
	public rack getRack()
	{
		return playerRack;
	}

}