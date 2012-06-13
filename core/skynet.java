package core;

public class skynet{

	private final int listSize;
	private static game gameRef;
	private static player currentPlayer;
	private static word[] lettermanList;

	public skynet(game theGame, int difficulty) {
		listSize = difficulty; //some math here....to be changed
		gameRef = theGame;
		lettermanList = new word[10]; //it's a top 10 list, get it?
		for(int i=0;i<lettermanList.length;i++)
			lettermanList[i] = new word(0,0,0,null,0);
	}

	public static void reset() {
		currentPlayer = null;
		for(int i=0;i<lettermanList.length;i++)
			lettermanList[i] = new word(0,0,0,null,0);
	}

	public static void setCurrentPlayer(player currentPlayerIn) {
		currentPlayer = currentPlayerIn;
		virtualBoard.reset(currentPlayer);
	}

	public static void playWord() {
		System.out.println("skynet!");
		findWords();
	}

	public static void findWords() {
		tile[] rack = currentPlayer.getRack().getAll();
		int testX, testY, offset=0;
		for(int x=0;x<15;x++){ //start x coord
			//System.out.println("x: "+x);
			for(int y=0;y<15;y++) { //start y coord
				//System.out.println("y:"+y);
				for(int dir=0;dir<2;dir++){ //horiz or vertical
					//System.out.println("dir: "+dir);
					for(int length=0;length<=7;length++) { //number of tiles to place from rack
						virtualBoard.clear(); //clear VB
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
							if(testX<15 && testY< 15) { //if in bounds
								virtualBoard.place(i, testX, testY);
							}
						}
						if(virtualBoard.checkPlacement()) {
							//System.out.println("food: ("+x+","+y+","+dir+","+length+")");
							int[] start = new int[0];
							int[] indices = {0,1,2,3,4,5,6};
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


		//char[] hi = new char[indices.length];
		//for(int i=0; i<indices.length; i++)
			//hi[i] = currentPlayer.getRack().getAll()[indices[i]].getLetter();

		
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
			//for(int i=0; i<indices.length; i++)
				//System.out.print(hi[i]);
			//System.out.println(" score: " + score);

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
		virtualBoard.clear();
		int x = lettermanList[0].getX();
		int y = lettermanList[0].getY();
		int dir = lettermanList[0].getDir();
		boolean found = false;
		int[] indices = new int[lettermanList[0].getLetters().length];
		int index = 0;
		for(int i=0; i<indices.length; i++)
		{
			index = 0;
			System.out.println(currentPlayer.getRack().getAll()[index].getLetter());
			System.out.print(lettermanList[0].getLetters()[i].getLetter());
			found = false;
			while(!found)
			{
				if(currentPlayer.getRack().getAll()[index].getLetter()==lettermanList[0].getLetters()[i].getLetter())
					found = true;
				index++;
			}
			indices[i] = index-1;
		}
		System.out.println();
				
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
				System.out.println(indices[i]);
				gameRef.placeTile(indices[i], testX, testY);
			}
		}
		
		gameRef.submit();
		System.out.println(board.isEmpty(7, 7));
	}
}
