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