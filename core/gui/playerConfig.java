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
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

import core.game;

public class playerConfig extends GUI implements ActionListener, FocusListener{
	private int numPlayers, turnSeconds;
	private JComboBox numPlayersSelect, p1Type, p2Type, p3Type, p4Type, skynetLevel;
	private JButton goButton; 
	private JTextField turnTime, p1Name, p2Name, p3Name, p4Name;
	
	public playerConfig(game gameRef, GUI in){
		numPlayers = 0;
		String[] numOptions = {"1","2","3","4"};
		String[] playerOptions = {"Human", "Computer"};
		String[] computerLevel = {"Easy", "Medium", "Hard", "Insane", "Singularity"};
		
		numPlayersSelect = new JComboBox(numOptions);
		numPlayersSelect.setSelectedIndex(0);
		goButton = new JButton("Continue");
		goButton.addActionListener(this);
		
		p1Name = new JTextField("Player 1",8);
		p2Name = new JTextField("Player 2",8);
		p3Name = new JTextField("Player 3",8);
		p4Name = new JTextField("Player 4",8);
		
		p1Name.addFocusListener(this);
		p2Name.addFocusListener(this);
		p3Name.addFocusListener(this);
		p4Name.addFocusListener(this);
		
		p1Type = new JComboBox(playerOptions);
		p2Type = new JComboBox(playerOptions);
		p3Type = new JComboBox(playerOptions);
		p4Type = new JComboBox(playerOptions);
		
		p1Type.setSelectedIndex(0);
		p2Type.setSelectedIndex(0);
		p3Type.setSelectedIndex(0);
		p4Type.setSelectedIndex(0);
		
		skynetLevel = new JComboBox(computerLevel);
		skynetLevel.setSelectedIndex(4);
		
		turnTime = new JTextField("0",5);
	}
	
	public void addComponents(javax.swing.JLayeredPane pane) {
		JLabel header = new JLabel("Select the Number of Players");
		JLabel compHeader = new JLabel("Set the difficulty of computer players:");
		
		header.setLocation(0,0);
		header.setSize(header.getPreferredSize());
		
		compHeader.setLocation(200,0);
		compHeader.setSize(compHeader.getPreferredSize());
		
		skynetLevel.setLocation(250,20);
		skynetLevel.setSize(new java.awt.Dimension(120,20));
		
		numPlayersSelect.setLocation(50,20);
		numPlayersSelect.setSize(new java.awt.Dimension(40,20));
		
		JLabel p1Header, p2Header, p3Header, p4Header;
		p1Header = new JLabel("Player 1 Info: ");
		p2Header = new JLabel("Player 2 Info: ");
		p3Header = new JLabel("Player 3 Info: ");
		p4Header = new JLabel("Player 4 Info: ");
		
		p1Header.setSize(p1Header.getPreferredSize());
		p2Header.setSize(p2Header.getPreferredSize());
		p3Header.setSize(p3Header.getPreferredSize());
		p4Header.setSize(p4Header.getPreferredSize());
		
		p1Header.setLocation(0,50);
		p2Header.setLocation(0,70);
		p3Header.setLocation(0,90);
		p4Header.setLocation(0,110);
		
		java.awt.Dimension d = new java.awt.Dimension(80,20);
		p1Name.setSize(d);
		p2Name.setSize(d);
		p3Name.setSize(d);
		p4Name.setSize(d);
		
		p1Name.setLocation(90,50);
		p2Name.setLocation(90,70);
		p3Name.setLocation(90,90);
		p4Name.setLocation(90,110);
		
		d = new java.awt.Dimension(120,20);
		p1Type.setSize(d);
		p2Type.setSize(d);
		p3Type.setSize(d);
		p4Type.setSize(d);
		
		p1Type.setLocation(180,50);
		p2Type.setLocation(180,70);
		p3Type.setLocation(180,90);
		p4Type.setLocation(180,110);
		
		JLabel timerHeader = new JLabel("Turn Timer ( > 10 seconds): ");
		timerHeader.setLocation(0,140);
		timerHeader.setSize(timerHeader.getPreferredSize());
		
		
		turnTime.setLocation(160,140);
		//turnTime.setMaximumSize(new java.awt.Dimension(50,25));
		turnTime.setSize(new java.awt.Dimension(40,20));
		
		goButton.setLocation(0,160);
		goButton.setSize(goButton.getPreferredSize());
		
		pane.add(header, BorderLayout.NORTH);
		pane.add(numPlayersSelect, javax.swing.JLayeredPane.DEFAULT_LAYER);
		
		pane.add(compHeader, javax.swing.JLayeredPane.DEFAULT_LAYER);
		pane.add(skynetLevel, javax.swing.JLayeredPane.DEFAULT_LAYER);
		
		pane.add(p1Header, javax.swing.JLayeredPane.DEFAULT_LAYER);
		pane.add(p2Header, javax.swing.JLayeredPane.DEFAULT_LAYER);
		pane.add(p3Header, javax.swing.JLayeredPane.DEFAULT_LAYER);
		pane.add(p4Header, javax.swing.JLayeredPane.DEFAULT_LAYER);
		
		pane.add(p1Name, javax.swing.JLayeredPane.DEFAULT_LAYER);
		pane.add(p2Name, javax.swing.JLayeredPane.DEFAULT_LAYER);
		pane.add(p3Name, javax.swing.JLayeredPane.DEFAULT_LAYER);
		pane.add(p4Name, javax.swing.JLayeredPane.DEFAULT_LAYER);
		
		pane.add(p1Type, javax.swing.JLayeredPane.DEFAULT_LAYER);
		pane.add(p2Type, javax.swing.JLayeredPane.DEFAULT_LAYER);
		pane.add(p3Type, javax.swing.JLayeredPane.DEFAULT_LAYER);
		pane.add(p4Type, javax.swing.JLayeredPane.DEFAULT_LAYER);
		
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
		String[][] playerInfo;
		try {
			turnSeconds = Integer.parseInt(turnTime.getText());
			if(turnSeconds < 10 && turnSeconds != 0) return;
			
			playerInfo = new String[numPlayers][2];
			if(numPlayers >= 1) {
				if(p1Name.getText().equals("")) return;
				playerInfo[0][0] = p1Name.getText();
				playerInfo[0][1] = Integer.toString(p1Type.getSelectedIndex());
			}
			if(numPlayers >= 2) {
				if(p2Name.getText().equals("")) return;
				playerInfo[1][0] = p2Name.getText();
				playerInfo[1][1] = Integer.toString(p2Type.getSelectedIndex());
			}
			if(numPlayers >= 3) {
				if(p3Name.getText().equals("")) return;
				playerInfo[2][0] = p3Name.getText();
				playerInfo[2][1] = Integer.toString(p3Type.getSelectedIndex());
			}
			if(numPlayers >= 4) {
				if(p4Name.getText().equals("")) return;
				playerInfo[3][0] = p4Name.getText();
				playerInfo[3][1] = Integer.toString(p4Type.getSelectedIndex());
			}
			startGame(playerInfo, skynetLevel.getSelectedIndex()+1, turnSeconds);
		}catch(java.lang.NumberFormatException ex) {
			//ex.printStackTrace();
		}
    }

	@Override
    public void focusGained(FocusEvent arg0) {
	    JTextField c = (JTextField) arg0.getComponent();
	    if(c.getText().equals("Player 1") || c.getText().equals("Player 2") || c.getText().equals("Player 3") || c.getText().equals("Player 4"))
	    	c.setText("");
    }

	@Override
    public void focusLost(FocusEvent arg0) {
		JTextField c = (JTextField) arg0.getComponent();
		if(c.getText().equals(""))
	    	c.setText("Name");
    }
}
