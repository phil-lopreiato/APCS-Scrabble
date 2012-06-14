/* Copyright (C) 2012 Justin Yost, Phil Lopreiato
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
 * @author 	Justin Yost
 * @author 	Phil Lopreiato
 * @version 1.0
 */

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