package core;

public class tile
{
	private char letter;
	private int value;
	private int special;
	private static int[] letterValues = {1,3,3,2,1,4,2,4,1,8,5,1,3,1,1,3,10,1,1,1,1,4,4,8,4,10,0};
	
	public tile(char letter)
	{
		this.letter = letter;
		value = lookupValue(letter);
		special = 0;
	}
	
	public tile(int special)
	{
		letter = '['; //this is ASCII 91, which immediately follows 'Z'. this will be considered, for all purposes within the program, a blank tile. for the array indexing, a 
					  //blank will be index 26 (immediately follows 'Z'), so it's important that this matches
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
	
	private static int lookupValue(char letter) {
		return letterValues[letter-65];
	}
}