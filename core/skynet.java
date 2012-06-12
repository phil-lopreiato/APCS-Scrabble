package core;

public class skynet{
	
	private final int listSize;
	private static game gameRef;
	private static player currentPlayer;
	private word[] lettermanList;
	
	public skynet(game theGame, int difficulty) {
		listSize = difficulty; //some math here....to be changed
		gameRef = theGame;
		lettermanList = new word[10]; //it's a top 10 list, get it?
		for(int i=0;i<lettermanList.length;i++)
			lettermanList[i] = new word(0,0,null,0);
	}
	
	public static void reset() {
		currentPlayer = null;
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
		int testX, testY, offset=0;
		for(int x=0;x<15;x++) { //start x coord
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
							if(testX<15 && testY< 15) { //if in bounds
								if(!board.isEmpty(testX,testY))
									offset++;
								virtualBoard.place(i, testX, testY);
							}
						}
						if(virtualBoard.checkPlacement()) {
							System.out.println("food: ("+x+","+y+","+dir+","+length+")");
							//for(int startT=0;startT<7;startT++){
							//	for(int otherT=0;otherT<7;otherT++) {
									//if(startT != otherT)
										//add
							//	}
							//}
						}
					}
				}
			}
		}
	}
	
	public static void submitWord() {
		
	}
}
