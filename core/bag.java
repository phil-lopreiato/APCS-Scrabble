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

import java.util.ArrayList;

public class bag
{
	private static ArrayList<tile> tiles;
	private static int[] letterDistributions = {9,2,2,4,12,2,3,2,9,1,1,4,2,6,8,2,1,6,4,6,4,2,2,1,2,1,2};

	public bag()
	{
		//initialize all tiles
		tiles = new ArrayList<tile>(100);
		fillBag();
		//randomizeBag(); //see below
	}
	
	/**
	 *  Fills the bag with the correct number of each letter tile
	 *  
	 *  Iterates through each letter and places the correct number into the bag. Letter distributions are taken from the array letterDistributions and can be modified there.
	 *  Default values are: {9,2,2,4,12,2,3,2,9,1,1,4,2,6,8,2,1,6,4,6,4,2,2,1,2,1,2}. This corresponds to {A,B,C,...,Z, }
	 */
	public static void fillBag() {
		for(int i=0; i<27; i++) { //for each type of tile (A-<blank>)
			for(int j=0; j<letterDistributions[i]; j++) { //number of each tile to add to the bag
				tiles.add(new tile((char)(i+65)));
			}
		}

	}
	
	/**
	 * Returns the number of tiles remaining in the bag
	 * 
	 * @return	an integer of the number of tiles remaining in the bag
	 */
	public static int getSize()
	{
		return tiles.size();
	}

	//This method is not needed. The bag's array can be arranged in order since the tile is still chosen randomly by the drawTile method.
	/*public void randomizeBag() {
                tile temp;
                int randIndex;
                for(int i=0;i<tiles.size();i++) {
                        temp = tiles.get(i);
                        randIndex = (int)(Math.random()*(tiles.size()));
                        tiles.set(i, tiles.get(randIndex));
                        tiles.set(randIndex,temp);
                }
        }*/
	
	/**
	 * Removes a tile from the bag and returns it
	 *
	 * @return	a tile object representing the drawn tile or null if the bag is empty
	 */
	public static tile drawTile()
	{
		tile result;
		if(getSize() > 0)
			result = tiles.remove((int)(Math.random()*tiles.size()));
		else
			result = null;
		return result;
	}
}