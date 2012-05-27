package core;

import java.util.ArrayList;

public class bag
{
	private static ArrayList<tile> tiles;
	
	public bag()
	{
		//initialize all tiles
		tiles = new ArrayList<tile>(100);
	}
	
	public static tile drawTile()
	{
		//guaranteed to return a random tile from bag
		tile temp = tiles.get(4);
		tiles.remove(4);
		return temp;
	}
	
}
