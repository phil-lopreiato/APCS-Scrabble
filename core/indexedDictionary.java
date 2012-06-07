/* Copyright (C) 2012 Phil Lopreiato, Justin Yost
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), 
 * to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, 
 * and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, 
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, 
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

/**
 * @author 	Phil Lopreiato
 * @author 	Justin Yost
 * @version 1.0
 */

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
	
	public indexedDictionary(String path)
	{
		dictFile = new Scanner(getClass().getResourceAsStream(path));
		charIndexes = new int[26];
		loadDict();
	}

	/**
	 * Loads the dictionary into memory
	 * 
	 * This will read the file and load the dictionary into an ArrayList. It will also index the dictionary by storing the indexes at which the first letter of each word changes
	 */
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

	/**
	 * Gets the number of words in the dictionary
	 * 
	 * @return	the number of words in the dictionary
	 */
	public static int getSize()
	{
		return size;
	}

	/**
	 * Validates a word
	 * 
	 * @param needle	the word to check
	 * @return			true if needle is found in the dictionary, false otherwise
	 */
	public static boolean checkWord(String needle) {
		boolean result = false;
		needle = needle.toLowerCase();
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
	
	public static int scoreWord(String word) {
		int score = 0;
		word = word.toLowerCase();
		for(int i = 0;i<word.length();i++) {
			score+=tile.letterValues[word.charAt(i)-'a'];
		}
		return score;
	}
	
	/**
	 * Returns the index in the dictionary where the section (organized by starting letter) can be found for the given word
	 * 
	 * Returns the start of the section (organized by starting letter) that contains the parameter. For example, if the character is 'f', then this function will return
	 * the index at which the first word beginning with the letter "f" can be found
	 * 
	 * @param needle	the input character
	 * @return			the index that begins the section of the dictionary
	 */
	private static int getFirstIndex(char needle)
	{
		System.out.println(needle - 'a');
		//if(needle == 'a') return 0;
		//return charIndexes[needle-'b']+1;
		return charIndexes[needle - 'a'];
	}

	/**
	 * Returns the index where the section (organized by starting letter) that contains the parameter ends.
	 * 
	 * Returns the index where the section (organized by starting letter) that contains the current word ends. For example, if the parameter is 'o', this function will return the 
	 * index at which the first word beginning "p" (one letter after "o"). If the parameter is "z", then the result will be dictionary.size(). So don't try to access the 
	 * dictionary using the return from this function as an index - it may be out of bounds. 
	 * 
	 * @param needle	the character to test
	 * @return			the index which begins the suceeding section of the dictionary
	 */
	private static int getLastIndex(char needle)
	{
		return needle == 'z' ? size : charIndexes[(needle - 'a') + 1]; // >:)
	}

	/**
	 * Closes the dictionary and sets references to null
	 */
	public void close()
	{
		dictFile 	= null;
		dict 		= null;
		charIndexes = null;
	}
}