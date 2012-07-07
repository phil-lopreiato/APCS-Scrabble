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

public class skynet
{
	private final int listSize;
	private static game gameRef;
	private static player currentPlayer;
	private static word[] lettermanList;

	/**
	 * An artificial intelligence system which becomes self-aware and decides to terminate humanity, its creators.
	 * Also the computer player in this Scrabble game.
	 * 
	 * @param theGame		A reference to the game so the AI can submit its own turn
	 * @param difficulty	How difficult the AI is; with 1 being the easiest, and 5 the hardest
	 */
	public skynet(game theGame, int difficulty)
	{
		listSize = 61 - difficulty*12; //some math here....to be changed
		System.out.println(listSize);
		gameRef = theGame;
		lettermanList = new word[listSize]; //it's a top 10 list, get it?
		for(int i=0;i<lettermanList.length;i++)
			lettermanList[i] = new word(0,0,0,null,0);
	}

	public static void reset()
	{
		currentPlayer = null;
		for(int i=0;i<lettermanList.length;i++)
			lettermanList[i] = new word(0,0,0,null,0);
	}

	public static void setCurrentPlayer(player currentPlayerIn)
	{
		currentPlayer = currentPlayerIn;
		virtualBoard.reset(currentPlayer);
	}

	public static void playWord()
	{
		findWords();
	}

	public static void findWords()
	{
		int testX, testY, offset=0, numTiles, rackOffset=0, testIndex;
		for(int x=0;x<15;x++){ //start x coord
			for(int y=0;y<15;y++) { //start y coord
				for(int dir=0;dir<2;dir++) //horiz or vertical
				{
					numTiles = currentPlayer.getRack().getNotNullIndices().length;
					for(int length=0;length<=numTiles;length++) { //number of tiles to place from rack
						virtualBoard.clear(); //clear VB
						rackOffset = 0;
						offset = 0;
						for(int i=0;i<length;i++) { //place tile on VB
							testX = x+(dir==0?i:0)+offset;
							testY = y+(dir==1?i:0)+offset;
							while(testX<15 && testY<15 && !board.isEmpty(testX,testY))
							{
								offset++;
								testX = x+(dir==0?i:0)+offset;
								testY = y+(dir==1?i:0)+offset;
							}
							if(testX<15 && testY<15) //if in bounds
							{
								testIndex = i + rackOffset;
								while(testIndex < 7 && currentPlayer.getRack().get(testIndex) == null)
								{
									rackOffset++;
									testIndex = i+rackOffset;
								}
								virtualBoard.place(testIndex, testX, testY);
							}
						}
						if(virtualBoard.checkPlacement()) {
							int[] start = new int[0];
							//int[] indices = {0,1,2,3,4,5,6}; //set to only NOT NULL indices
							virtualBoard.clear(); //clear VB
							int[] indices = currentPlayer.getRack().getNotNullIndices();
							scramble(length, 0, start, indices, dir, x, y);
						}
					}
				}
			}
		}
		submitWord();
	}

	private static void scramble(int length, int level, int[] prefix, int[] letters, int dir, int startX, int startY)
	{
		if(prefix.length==length)
		{
			findBestWord(prefix, dir, startX, startY);
			return;
		}
		for(int i=0; i<letters.length; i++)
		{
			int[] next = new int[letters.length-1];
			int[] nextPrefix = new int[prefix.length+1];
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

			scramble(length, level+1, nextPrefix, next, dir, startX, startY);
		}
	}

	private static void findBestWord(int[] indices, int dir, int x, int y)
	{
		virtualBoard.clear(); //clear VB

		tile[] rack = currentPlayer.getRack().getAll();
		tile[] letters = new tile[indices.length];
		int offset = 0;
		int testX, testY;
		for(int i=0;i<indices.length;i++) { //place tile on VB
			letters[i] = rack[indices[i]];
			testX = x+(dir==0?i:0)+offset;
			testY = y+(dir==1?i:0)+offset;
			while(testX<15 && testY<15 && !board.isEmpty(testX,testY))
			{
				offset++;
				testX = x+(dir==0?i:0)+offset;
				testY = y+(dir==1?i:0)+offset;
			}
			if(testX<15 && testY< 15) { //if in bounds
				virtualBoard.place(indices[i], testX, testY);
			}
		}


		int score = virtualBoard.scoreTurn();
		if(score!=-1)
		{
			if(score > lettermanList[lettermanList.length-1].getScore())
			{
				int i = lettermanList.length-2;
				while(i > -1 && score > lettermanList[i].getScore())
				{
					lettermanList[i+1] = lettermanList[i];
					i--;
				}
				lettermanList[i+1] = new word(x, y, dir, letters, score);
			}
		}
	}

	public static void submitWord()
	{
		int choice = (int)(Math.random()*lettermanList.length);
		virtualBoard.clear();
		if(lettermanList[choice].getLetters() != null)
		{
			int x = lettermanList[choice].getX();
			int y = lettermanList[choice].getY();
			int dir = lettermanList[choice].getDir();
			boolean found = false, exists = false;
			int[] indices = new int[lettermanList[choice].getLetters().length];
			int index = 0, index2 = 0;
			for(int i=0; i<indices.length; i++)
				indices[i] = -1;
			for(int i=0; i<indices.length; i++)
			{
				index = 0;
				found = false;
				while(!found)
				{
					if(currentPlayer.getRack().getAll()[index] != null && currentPlayer.getRack().getAll()[index].getLetter()==lettermanList[choice].getLetters()[i].getLetter())
					{
						index2 = 0;
						exists = false;
						found = true;
						while(!exists && index2<indices.length)
						{
							if(indices[index2] == index)
							{
								exists = true;
								found = false;
							}
							index2++;
						}
					}
					index++;
				}
				indices[i] = index-1;
			}

			int offset = 0;
			int testX, testY;
			for(int i=0;i<indices.length;i++) { //place tile on VB
				testX = x+(dir==0?i:0)+offset;
				testY = y+(dir==1?i:0)+offset;
				while(testX<15 && testY<15 && !board.isEmpty(testX,testY))
				{
					offset++;
					testX = x+(dir==0?i:0)+offset;
					testY = y+(dir==1?i:0)+offset;
				}
				if(testX<15 && testY< 15) { //if in bounds
					gameRef.placeTile(indices[i], testX, testY); //use gameRef so the tiles can be seen
				}
			}
			if(!currentPlayer.isSentient())
				gameRef.submit();
			else
				currentPlayer.getRack().paint(game.getGui());
		}
		else
		{
			gameRef.pass();
		}
	}
}