package core;

public class player
{
	private rack playerRack;
	private int score;

	public player()
	{
		playerRack = new rack();
		score = 0;
	}

	public void draw()
	{
		playerRack.draw();
	}

	public void updateScore(int score)
	{
		this.score += score;
	}
	
	public int getScore()
	{
		return score;
	}

	public rack getRack()
	{
		return playerRack;
	}

}