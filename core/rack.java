package core;

public class rack
{
	private tile[] tiles;

	public rack()
	{
		tiles = new tile[7];
		for(int i=0; i<7; i++)
			tiles[i] = bag.drawTile();
	}

	public tile get(int index)
	{
		return tiles[index];
	}
	
	public void draw()
	{
		for(int i=0; i<7; i++)
			if(tiles[i] == null)
				tiles[i] = bag.drawTile();
	}

	public void remove(int index)
	{
		tiles[index] = null;
	}

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
}