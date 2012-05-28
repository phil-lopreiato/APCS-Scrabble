package core;

//class to control a player's turn???
public class virtualBoard
{
	private static tile[][] virtualBoard;
	private static player currentPlayer;

	public virtualBoard()
	{
		virtualBoard = new tile[15][15];
	}

	public static void reset(player Player)
	{
		virtualBoard = new tile[15][15];
		currentPlayer = Player;
	}

	public static boolean place(int rackIndex, int x, int y)
	{
		boolean placed;
		if(virtualBoard[x][y] == null && board.isEmpty(x,y))
		{
			tile Tile = currentPlayer.getRack().get(rackIndex);
			virtualBoard[x][y] = Tile;
			currentPlayer.getRack().remove(rackIndex);
			placed = true;
		}
		else
			placed = false;
		return placed;
	}

	public static boolean submit()
	{
		boolean valid;
		if(validate())
		{
			currentPlayer.updateScore(scoreTurn());
			board.addVB(virtualBoard);
			valid = true;
		}
		else
			valid = false;
		return valid;
	}

	public static boolean validate()
	{
		//check all words and placements and stuff - will need to load master board
		return true;
	}

	public static int scoreTurn()
	{
		return 23;
	}
}