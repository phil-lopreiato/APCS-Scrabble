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

public class rack
{
	private tile[] tiles;

	public rack()
	{
		tiles = new tile[7];
		for(int i=0; i<7; i++)
			tiles[i] = bag.drawTile();
	}

	/**
	 * Returns the tile at a given location in the rack
	 * 
	 * @param index		the location in the rack (0<n<7)
	 * @return			the tile at that location
	 */
	public tile get(int index)
	{
		return tiles[index];
	}

	/**
	 * Fills each blank location in the player's rack with a new tile drawn from the bag
	 */
	public void draw()
	{
		for(int i=0; i<7; i++)
			if(tiles[i] == null)
				tiles[i] = bag.drawTile();
	}

	/**
	 * Removes a given tile from the player's rack
	 * 
	 * @param index 	index denoting the location to remove a tile (0<n<7)
	 */
	public void remove(int index)
	{
		tiles[index] = null;
	}

	public boolean replace(int index, tile Tile)
	{
		boolean replaced = false;
		if(tiles[index] == null)
		{
			tiles[index] = Tile;
			replaced = true;
		}
		return replaced;
	}

	/**
	 * Tests is the rack contains 0 tiles
	 * 
	 * @return	true if the rack is empty, false otherwise
	 */
	public boolean isEmpty()
	{
		boolean result = true;
		int i = 0;
		while (result && i<7)
		{
			if(tiles[i] != null)
				result = false;
		}
		return result;
	}

	/**
	 * Draws the player's rack onto the GUI
	 * 
	 * @param gui	a reference to the GUI on which to draw the rack
	 */
	public void paint(GUI gui) {
		gui.updateRack(tiles);
	}
}