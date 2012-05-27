package core;

public class player
{
	rack playerRack;
	int score;
	
	public player()
	{
		playerRack = new rack();
		score = 0;
	}
	
	public boolean submit()
	{
		boolean result = indexedDictionary.checkWord("hello");
		if (result)
		{
			playerRack.remove(/*tiles to be removed*/4);
			playerRack.remove(5);
			playerRack.draw();
		}
		return result;
	}
	
	public rack getRack()
	{
		return playerRack;
	}
}
