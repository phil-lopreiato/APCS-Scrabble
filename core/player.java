package core;

public class player
{
	private rack playerRack;
	private int score;
	private int[] tileIndices;

	public player()
	{
		playerRack = new rack();
		score = 0;
		tileIndices = new int[7];
	}

	public void draw()
	{
		playerRack.draw();
	}
	
	public tile remove(int index)
	{
		tile selected = playerRack.get(index);
		playerRack.remove(index);
		return selected;
	}

	public void updateScore(int score)
	{
		this.score += score;
	}

	public rack getRack()
	{
		return playerRack;
	}

}