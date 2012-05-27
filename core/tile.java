package core;

public class tile
{
	private char letter;
	private int value;
	private int special;
	
	public tile(char letter)
	{
		this.letter = letter;
		value = 4; //lookup real value used for this letter
		special = 0;
	}
	
	public tile(int special)
	{
		letter = 'a';
		value = 0;
		this.special = special;
	}
	
	public char getLetter()
	{
		return letter;
	}
	
	public int getValue()
	{
		return value;
	}
	
	public int getSpecial()
	{
		return special;
	}
}