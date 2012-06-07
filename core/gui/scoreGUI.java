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
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class scoreGUI extends GUI implements guiSegment, ActionListener{
	private JLayeredPane scoreContainer;
	private JLabel scoreLabels[], currentTurnScore, checkResult;
	private JButton turnSubmit,pass, checkWord;
	private JTextField wordToCheck;
	private int[] playerScores;
	private int numPlayers;

	public scoreGUI(int numPlayers) {
		this.numPlayers = numPlayers;
		scoreLabels = new JLabel[numPlayers];
		playerScores = new int[numPlayers];
		
		turnSubmit = new JButton("Submit Current Turn");
		pass = new JButton("Pass");
		checkWord = new JButton("Check Word");
		wordToCheck = new JTextField("",5);
		currentTurnScore = new JLabel();
		checkResult = new JLabel();

		scoreContainer = new JLayeredPane();
		scoreContainer.setOpaque(false);
		scoreContainer.setLayout(new BoxLayout(scoreContainer,BoxLayout.Y_AXIS));
		scoreContainer.setPreferredSize(new Dimension(300,300));
	}

	public void addComponents(javax.swing.JLayeredPane pane) {
		//draw score area
		JLabel scoreHeader = new JLabel("Scores:"); //header label
		scoreHeader.setAlignmentX(Component.CENTER_ALIGNMENT);
		scoreHeader.setSize(scoreHeader.getPreferredSize());
		scoreHeader.setLocation(680,0);
		scoreContainer.add(scoreHeader,JLayeredPane.DEFAULT_LAYER);

		for(int i = 0;i<numPlayers;i++) {
			playerScores[i] = 0;
			scoreLabels[i] = new JLabel("Player "+i+": "+playerScores[i]);
			scoreLabels[i].setAlignmentX(Component.CENTER_ALIGNMENT);
			scoreLabels[i].setSize(scoreLabels[i].getPreferredSize());
			scoreLabels[i].setLocation(675,10*i);
			scoreContainer.add(scoreLabels[i], JLayeredPane.DEFAULT_LAYER);
		}
		
		turnSubmit.setAlignmentX(Component.CENTER_ALIGNMENT);
		turnSubmit.addActionListener(this);
		turnSubmit.setActionCommand("submit");
		
		pass.setAlignmentX(Component.CENTER_ALIGNMENT);
		pass.addActionListener(this);
		pass.setActionCommand("pass");
		
		currentTurnScore.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		wordToCheck.setAlignmentX(Component.CENTER_ALIGNMENT);
		wordToCheck.setMaximumSize(new Dimension(150,25));
		
		checkResult.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		checkWord.setAlignmentX(Component.CENTER_ALIGNMENT);
		checkWord.addActionListener(this);
		checkWord.setActionCommand("check");
		
		scoreContainer.add(Box.createVerticalStrut(10));
		scoreContainer.add(turnSubmit,JLayeredPane.DEFAULT_LAYER);
		scoreContainer.add(Box.createVerticalStrut(10));
		scoreContainer.add(pass,JLayeredPane.DEFAULT_LAYER);
		scoreContainer.add(Box.createVerticalStrut(10));
		scoreContainer.add(currentTurnScore,JLayeredPane.DEFAULT_LAYER);
		scoreContainer.add(Box.createVerticalStrut(10));
		scoreContainer.add(wordToCheck,JLayeredPane.DEFAULT_LAYER);
		scoreContainer.add(checkWord,JLayeredPane.DEFAULT_LAYER);
		scoreContainer.add(checkResult,JLayeredPane.DEFAULT_LAYER);
		
		scoreContainer.setSize(scoreContainer.getPreferredSize());
		scoreContainer.setLocation(675,0);

		pane.add(scoreContainer, BorderLayout.EAST, JLayeredPane.DEFAULT_LAYER);
	}

	public void updateScore(int player,int score) {
		scoreLabels[player].setText("Player "+player+": "+score);
	}

	public void setTurn(int player)
	{
		for(int i=0; i<numPlayers; i++)
			scoreLabels[i].setForeground(Color.BLACK);
		scoreLabels[player].setForeground(Color.RED);
	}

	public JLayeredPane getContainer() {
		return scoreContainer;
	}
	

    public void actionPerformed(ActionEvent arg0) {
    	if(arg0.getActionCommand().equals("submit"))
		   gameRef.submit();
    	else if(arg0.getActionCommand().equals("pass"))
    		gameRef.pass();
    	else if(arg0.getActionCommand().equals("check"))
    		checkResult.setText(gameRef.checkWord(wordToCheck.getText()));
    }

}
