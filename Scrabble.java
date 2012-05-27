import core.*;
import core.gui.boardFrame;

public class Scrabble{
        public static void main(String[] args){
                System.out.println("Begin");
                game theGame;
                boardFrame f = new boardFrame(); //init new GUI
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
                
                theGame = new game(f.getNumPlayers()); //instantiate game with correct # players
                f.setParent(theGame); //tell the GUI the game's reference to make talking between them easier
                
                f.clear(); //clear everything from frame, now we draw with the board

                f.loadGameDisplay(theGame.getNumPlayers()); //draw the board

                f.repaint(); //repaint the display


                //thought process:
                while(!game.gameOver())
                {
                        //player places tiles in virtual board
                        //wait for submit
                        //verify -- for now, let's just assume this is good until the rest of the game works
                        //add tiles from virtual board to board
                        //update score
                        //remove tiles from playerRack
                        //draw new tiles
                        //next player's turn
                }
        }
}
