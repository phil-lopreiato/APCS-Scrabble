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
 * @author Phil Lopreiato
 * @author Justin Yost
 * @version 1.0
 */

package core.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;

import core.tile;

public class GUI {

	protected JFrame mainFrame;
	private java.awt.Container contentPane;
	protected JLayeredPane layeredPane;
	
	
	private playerConfig pc;
	protected scoreGUI sg;
	protected rackGUI rg;
	protected boardGUI bg;
	
	protected Dimension screenSize;

	private int numPlayers;
	private int playersTurn;

	public GUI() {
		screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		
		mainFrame = new JFrame("Scrabble");
		mainFrame.setLayout(new BorderLayout());
		
		layeredPane = new JLayeredPane();
		mainFrame.add(layeredPane,BorderLayout.CENTER);
		contentPane = mainFrame.getContentPane();
		
		layeredPane.setPreferredSize(new Dimension(200,100));
		layeredPane.setOpaque(false);
		mainFrame.setLayout(new java.awt.BorderLayout());
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.add(layeredPane,BorderLayout.CENTER);
		numPlayers = 0;
	}

	public void gameInit() {
		pc = new playerConfig();
		pc.addComponents(layeredPane);
	}

	public boolean numPlayersSet() {
		numPlayers = pc.getNumPlayers();
		return numPlayers != 0;
	}

	public int getNumPlayers() {
		return numPlayers;
	}

	public void loadGameDisplay(int numPlayers) {

		clear();
		pc = null; // deallocate config stuf
		
		layeredPane.setPreferredSize(new Dimension((int)screenSize.getWidth(),(int)screenSize.getHeight()-75));

		bg = new boardGUI();
		bg.addComponents(layeredPane);

		sg = new scoreGUI(numPlayers);
		sg.addComponents(layeredPane);

		rg = new rackGUI();
		rg.addComponents(layeredPane);

	}

	public void updateScore(int player, int score) {
		if (sg != null)
			sg.updateScore(player, score);
		repaint();
	}

	public void setTurn(int player)
	{
		if (sg != null)
			sg.setTurn(player);
		repaint();
	}

	public void updateRack(int pos /* 0-6 */, BufferedImage tile) {
		if (rg != null)
			rg.updateRack(pos, tile);
		repaint();
	}

	public void updateRack(BufferedImage[] tiles) {
		if (rg != null)
			rg.updateRack(tiles);
		repaint();
	}

	public void updateRack(tile[] tiles) {
		if (rg != null)
			rg.updateRack(tiles);
		repaint();
	}

	public void show() {
		mainFrame.pack();
		mainFrame.setVisible(true);
		mainFrame.repaint();
	}

	public void repaint() {
		layeredPane.repaint();
		mainFrame.pack();
		mainFrame.repaint();
	}

	public void clear() {
		layeredPane.removeAll();
	}

	public void setCurrentTurn(int turn) {

	}

}