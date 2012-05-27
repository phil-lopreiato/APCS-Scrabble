package core;

//could be called masterBoard
public class board
{
	private static tile[][] board;

	public board()
	{
		board = new tile[15][15];
	}

	public static void addVB(tile[][] VB)
	{
		for(int row=0; row<15; row++)
			for(int col=0; col<15; col++)
				board[row][col] = VB[row][col];
	}

	public static boolean isEmpty(int x, int y)
	{
		return board[x][y] == null;
	}
	//include methods such as find first tile in word... (i think this should be in virtual board instead)
}