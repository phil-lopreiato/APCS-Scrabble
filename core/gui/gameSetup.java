package core.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

import core.game;

public class gameSetup implements ActionListener{

	JComboBox src;
	game parentGame;
	boolean numPlayersSet;
	
	public gameSetup(JComboBox selecter, game parent) {
		src = selecter;
		parentGame = parent;
		numPlayersSet = false;
	}
	
    public void actionPerformed(ActionEvent ex) {
	   parentGame.setNumPlayers(src.getSelectedIndex()+1);
	   numPlayersSet = true;
    }
    
    public boolean buttonPressed() {
    	return numPlayersSet;
    }   	
}