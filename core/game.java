package core;

public class game {
	private board board;
	private static player players[];
	private bag bag;
	private static tile displayRack[];
	
	public game()
	{
		board = new board();
		bag = new bag();
		displayRack = new tile[7];
	}
	
	public void setNumPlayers(int num)
	{
		players = new player[num];
	}
	
	public int getNumPlayers() {
		return players.length;
	}
	
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
	
	public static void setRack(tile[] tiles)
	{
		for(int i=0; i<7; i++)
			displayRack[i] = tiles[i]; //to avoid aliasing which i think is bad here...
	}
}