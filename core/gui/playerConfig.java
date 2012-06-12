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

package core.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

import core.game;

public class playerConfig extends GUI implements ActionListener{
	private int numPlayers, turnSeconds;
	private JComboBox numPlayersSelect;
	private JButton goButton; 
	private JTextField turnTime;
	
	public playerConfig(game gameRef, GUI in){
		numPlayers = 0;
		String[] numOptions = {"1","2","3","4"};
		numPlayersSelect = new JComboBox(numOptions);
		numPlayersSelect.setSelectedIndex(0);
		goButton = new JButton("Continue");
		goButton.addActionListener(this);
		
		turnTime = new JTextField("0",5);
	}
	
	public void addComponents(javax.swing.JLayeredPane pane) {
		JLabel header = new JLabel("Select the Number of Players");
		header.setLocation(0,0);
		header.setSize(header.getPreferredSize());
		
		numPlayersSelect.setLocation(0,20);
		numPlayersSelect.setSize(numPlayersSelect.getPreferredSize());
		
		JLabel timerHeader = new JLabel("Turn Timer ( > 10 seconds): ");
		timerHeader.setLocation(0,60);
		timerHeader.setSize(timerHeader.getPreferredSize());
		
		
		turnTime.setLocation(160,60);
		//turnTime.setMaximumSize(new java.awt.Dimension(50,25));
		turnTime.setSize(new java.awt.Dimension(40,20));
		
		goButton.setLocation(0,110);
		goButton.setSize(goButton.getPreferredSize());
		
		pane.add(header, BorderLayout.NORTH);
		pane.add(numPlayersSelect, BorderLayout.CENTER, new Integer(0));
		pane.add(goButton /*, BorderLayout.SOUTH*/, javax.swing.JLayeredPane.DEFAULT_LAYER);
		pane.add(timerHeader, javax.swing.JLayeredPane.DEFAULT_LAYER);
		pane.add(turnTime, javax.swing.JLayeredPane.DEFAULT_LAYER);
	}
	
	public int getNumPlayers() {
		return numPlayers;
	}
	
	@Override
    public void actionPerformed(ActionEvent e) {
		numPlayers = numPlayersSelect.getSelectedIndex()+1;
		try {
			turnSeconds = Integer.parseInt(turnTime.getText());
			if(turnSeconds < 10 && turnSeconds != 0) return;
			startGame(numPlayers, turnSeconds);
		}catch(java.lang.NumberFormatException ex) {
			//ex.printStackTrace();
		}
    }
}
