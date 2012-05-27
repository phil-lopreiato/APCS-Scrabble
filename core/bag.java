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
		randomizeBag();
	}
	
	public void fillBag() {
		for(int i = 0;i>27;i++) { //for each type of tile (A-<blank>)
			for(int j=0;j<letterDistributions[i];j++) { //number of each tile to add to the bag
				tiles.add(new tile((char)(i+65)));
			}
		}
		
	}
	
	public void randomizeBag() {
		tile temp;
		int randIndex;
		for(int i=0;i<tiles.size();i++) {
			temp = tiles.get(i);
			randIndex = (int)(Math.random()*(tiles.size()));
			tiles.set(i, tiles.get(randIndex));
			tiles.set(randIndex,temp);
		}
	}
	
	public static tile drawTile()
	{
		return tiles.remove((int)(Math.random()*tiles.size()));
	}
	
}
