package core;

import java.util.ArrayList;
import java.util.Scanner;

public class indexedDictionary {
	private static ArrayList<String> dict = new ArrayList<String>();
	private static int[] charIndexes;
	private static int low,high,size;
	private static Scanner dictFile;

	public indexedDictionary()
	{
		dictFile = new Scanner(getClass().getResourceAsStream("dictionary.txt"));
		charIndexes = new int[26];
		loadDict();
	}

	private void loadDict() {
		char previousChar = 'a' - 1;
		String nextLine;
		int lineNum = 0;
		while(dictFile.hasNextLine()) {
			nextLine = dictFile.nextLine();
			dict.add(nextLine);
			if(previousChar != nextLine.charAt(0))
			{
				charIndexes[nextLine.charAt(0)-'a'] = lineNum;
				previousChar = nextLine.charAt(0);
			}
			lineNum++;
		}
		size = dict.size();
		high = size;
		low = -1;
	}

	public static int getSize()
	{
		return size;
	}

	public static boolean checkWord(String needle) {
		boolean result = false;
		low = getFirstIndex(needle.charAt(0))-1;
		high = getLastIndex(needle.charAt(0));		
		int place, test = 0;
			//needle = needle.toLowerCase(); //use only if needed
		while(!result && Math.abs(high-low)!=1)
		{
			place = (high+low)/2;
			//System.out.println(dict.get(place)+" "+place+" hi:"+high+" lo:"+low+ " dist:"+test);
			test = needle.compareTo(dict.get(place));
			if(test > 0) //needle word is after test point alphabetically
				low = place;
			else if(test < 0)
				high = place;
			else
				result = true;
		}
		return result;
	}
	
	// returns the index of the first line starting with char needle
	public static int getFirstIndex(char needle)
	{
		//if(needle == 'a') return 0;
		//return charIndexes[needle-'b']+1;
		return charIndexes[needle - 'a'];
	}

	// returns the index of the first line starting with the next char after needle
	public static int getLastIndex(char needle)
	{
		return needle == 'z' ? size : charIndexes[(needle - 'a') + 1]; // >:)
	}

	public void close()
	{
		dictFile = null;
		dict = null;
	}
}