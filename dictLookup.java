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
 * @version 1.0x
 */

import core.indexedDictionary;
import java.util.Scanner;

public class dictLookup
{
	public static void main(String[] args)
	{	
		long startTime = System.currentTimeMillis();
		indexedDictionary dict = new indexedDictionary();
		long endTime = System.currentTimeMillis();
		
		System.out.println("Dictionary Loaded in " + (endTime - startTime) + " milliseconds");
		System.out.println(indexedDictionary.getSize() + " Words in dictionary");
		
		Scanner in = new Scanner(System.in);
		System.out.print("Enter a word to check: ");
		String out = in.nextLine();
		
		startTime = System.currentTimeMillis();
		if(indexedDictionary.checkWord(out)){
			out += " is a valid word";
		}else {
			out += " is not a valid word";
		}
		endTime = System.currentTimeMillis();
		
		System.out.println("\n"+out);
		System.out.println("Word Checked in " + (endTime - startTime) + " milliseconds");
		dict.close();
	}
}