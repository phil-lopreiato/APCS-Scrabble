package core;

public class game {
	//private board board;
	private static player players[];
	//private bag bag;
	private static tile displayRack[];
	private static int playersTurn;

	public game()
	{
		//board = new board();
		//bag = new bag();
		new board();
		new bag();
		displayRack = new tile[7];
		playersTurn = getNumPlayers();
		newTurn();
	}

	public void setNumPlayers(int num)
	{
		players = new player[num];
		for (int i=0; i<num; i++)
			players[i] = new player();
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

	public void submit()
	{
		if(virtualBoard.submit())
			newTurn();
	}

	public void newTurn()
	{
		playersTurn++;
		playersTurn %= getNumPlayers();
		virtualBoard.reset(players[playersTurn]);
	}

	public static void setRackToDisplay(tile[] tiles)
	{
		for(int i=0; i<7; i++)
			displayRack[i] = tiles[i]; //to avoid aliasing which i think is bad here...
	}

	public void drawStartingRacks() {
		for(player p:players) {
			//p.getRack().draw(); draw in this case means draw a tile... probably should rename that one.
		}
	}
}