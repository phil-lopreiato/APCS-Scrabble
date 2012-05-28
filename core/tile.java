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

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class tile
{
	private char letter;
	private int value;
	private int special;
	private static int[] letterValues = {1,3,3,2,1,4,2,4,1,8,5,1,3,1,1,3,10,1,1,1,1,4,4,8,4,10,0};

	public tile(char letter)
	{
		this.letter = letter;
		value = lookupValue(letter);
		special = 1; //init to 1! (we don't want to multiply scores by 0 now, do we?
	}

	public tile(int special)
	{
		letter = '['; //this is ASCII 91, which immediately follows 'Z'. this will be considered, for all purposes within the program, a blank tile. for the array indexing, a
		//blank will be index 26 (immediately follows 'Z'), so it's important that this matches
		value = 0;
		this.special = special;
	}

	/**
	 * Returns the letter which the tile represents (char)
	 * 
	 * @return	the letter of this tile (char)
	 */
	public char getLetter()
	{
		return letter;
	}

	/**
	 * Returns the point value of this tile
	 * 
	 * @return	the number of points this tile is worth
	 */
	public int getValue()
	{
		return value;
	}

	/**
	 * Returns the letter multiplier for this tile. Used for multiplier spaces and in the virtual board.
	 * 
	 * Since we're using a blank tile to represent double/triple letter/word squares on the board, this method will return the multiplier for tile placed on top of it
	 * To differentiate between multiplying on a single letter or an entire word, a negative value will be used to represent a letter multiplier and a positive value for a word
	 * multiplier. Places that call this method will have to test that and act accordingly (use a Math.abs() ). Once a tile has been placed on top (or as part of a word that covers)
	 * a multiplier square, the multiplier will always be positive and will be multiplied by the point value of the letter to calculate the points for the tile.
	 * For example: if a tile represents a double letter square, this will return a -2. if it represents a triple word, it will return +3. If the tile represents a letter 'A' that
	 * has been placed on a double letter square and is part of a word that covers a triple word square, then this will return 2*3 = multiplier of 6
	 * 
	 * @return	the multiplier value of this tile (see long description paragraph)
	 */
	public int getSpecial()
	{
		return special;
	}

	/**
	 * Looks up a point value for a letter
	 * 
	 * @param letter	the letter to lookup the value for
	 * @return			the point value for that letter
	 */
	private static int lookupValue(char letter) {
		return letterValues[letter-65];
	}

	/**
	 * Draws a letter and score overlay on a blank tile
	 * 
	 * @param large		true if you want to draw a large sized tile (for use in rack display) or false if you want a smaller image (like to have on the board)
	 * @return			a BufferedImage with the overlay drawn on it
	 */
	public BufferedImage paint(boolean large) {
		BufferedImage baseTile = null, output;
		try {
			baseTile = ImageIO.read(this.getClass().getResource(large?"gui/singleTile_large.png":"gui/singleTile.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		output = new BufferedImage(baseTile.getWidth(), baseTile.getHeight(), BufferedImage.TYPE_INT_ARGB);

		Graphics2D g2d = output.createGraphics();
		g2d.drawImage(baseTile, 0, 0, null);
		
		//check if this is a blank tile
		if(getLetter() != '[')
		{
			g2d.setPaint(Color.black);
			g2d.setFont(new Font("Serif", Font.BOLD, 60));
			String s = Character.toString(getLetter());
			FontMetrics fm = g2d.getFontMetrics();

			int x = output.getWidth()/2 - fm.stringWidth(s)/2;
			int y = fm.getHeight();
			g2d.drawString(s, x, y);

			g2d.setFont(new Font("Serif", Font.BOLD, 20));
			fm = g2d.getFontMetrics();
			s = Integer.toString(this.getValue());
			x = output.getWidth() - fm.stringWidth(s);
			y = output.getHeight() - fm.getHeight();
			g2d.drawString(s, x, y);

		}
		g2d.dispose();
		return output;
	}
}