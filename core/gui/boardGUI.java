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
import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

import core.tile;

public class boardGUI extends GUI implements guiSegment{
	
	JLayeredPane boardContainer;
	JLabel boardLabel;
	BufferedImage boardBase;
	private JLabel boardLetters[];
	private int n;
	
	public boardGUI() {
		//Create and set up the window.
		
		//load board image
		try {                
			boardBase = ImageIO.read(this.getClass().getResource("mainBoard.jpg"));
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
		boardContainer = new JLayeredPane();
		//boardContainer.setLayout(new BorderLayout());
		boardContainer.setLayout(new FlowLayout(FlowLayout.CENTER,5,5));
		boardContainer.setOpaque(false);
		boardLabel = new JLabel(new ImageIcon( boardBase )); //draw board background
		boardLetters = new JLabel[7];
		n = 0;
	}
	
	public void addComponents(javax.swing.JLayeredPane pane) {
		boardContainer.add(boardLabel, JLayeredPane.DEFAULT_LAYER);
		boardContainer.setSize(boardContainer.getPreferredSize());
		boardContainer.setLocation(0,0);
		
		pane.add(boardContainer, BorderLayout.WEST, javax.swing.JLayeredPane.DEFAULT_LAYER);
		addTile(null, 0, 0);
	}
	
	public void addTile(tile t, int x, int y) {

		BufferedImage blankTile = null;
		try {
			blankTile = ImageIO.read(this.getClass().getResource("singleTile_large.png"));
		} catch (IOException ex) {
			ex.printStackTrace();
		}

			boardLetters[n] = new JLabel(new ImageIcon(blankTile));
			boardLetters[n].setOpaque(false);
			boardLetters[n].setLocation(0,0);
			boardContainer.add(boardLetters[n],JLayeredPane.DEFAULT_LAYER);
			boardContainer.setSize(boardContainer.getPreferredSize());
			//boardLetters[n].setIcon();
	}

    public JLayeredPane getContainer() {
	   return boardContainer;
    }
}
