package core;

public class wordMaker
{
	
	public static void main(String[] args)
	{
		char[] letters = {'a','b','c','d','e','f','g'};
		char[] start = new char[0];
		scramble(7,0,start,letters);
	}
	
	private static void scramble(int length, int level, char[] prefix, char[] letters)
	{	
		if(prefix.length==length)
		{
			for(int i=0; i<prefix.length; i++)
				System.out.print(prefix[i]);
			System.out.println();
			return;
		}
		for(int i=0; i<letters.length; i++)
		{
			char[] next = new char[letters.length-1];
			char[] nextPrefix = new char[prefix.length+1];
			for(int j=0, k=0; j<next.length+1; j++,k++)
			{
				if(j!=i)
					next[k] = letters[j];
				else
					k--;
			}
			
			for(int j=0; j<prefix.length;j++)
				nextPrefix[j] = prefix[j];
			nextPrefix[nextPrefix.length-1] = letters[i];
			
			scramble(length, level+1, nextPrefix, next);
		}
	}
}