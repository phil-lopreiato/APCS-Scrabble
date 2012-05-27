import core.game;
import core.gui.boardFrame;


public class Scrabble{	
	public static void main(String[] args){	
		System.out.println("hi phil!");
		game theGame = new game(); //reference to the game
		
		boardFrame f = new boardFrame(theGame); //init new GUI
		f.gameInit(); //draw the game init stuff (select number of players)
					  //this will eventually also ask for computer players
		f.show(); //show the main content panel
		
		//wait for button to be pressed
		do {
			try {
	            Thread.sleep(100); //wait 100 ms
            } catch (InterruptedException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
            }
		}while(!f.numPlayersSet()); 
		
		f.clear(); //clear everything from frame, now we draw with the board
		
		f.loadGameDisplay(theGame.getNumPlayers()); //draw the board
		
		f.repaint(); //repaint the display
	}
}