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
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import core.tile;

public class boardGUI extends GUI implements guiSegment{
	
	JPanel boardContainer;
	JLabel boardLabel;
	BufferedImage boardBase;
	
	public boardGUI() {
		//Create and set up the window.
		
		//load board image
		try {                
			boardBase = ImageIO.read(this.getClass().getResource("mainBoard.jpg"));
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
		boardContainer = new JPanel(new BorderLayout());
		boardLabel = new JLabel(new ImageIcon( boardBase )); //draw board background
		
	}
	
	public void addComponents(java.awt.Container pane) {
		boardContainer.add(boardLabel);
		
		pane.add(boardContainer, BorderLayout.WEST);
	}
	
	public void addTile(tile t,int x, int y) {
		//to be implemented
	}


    public JPanel getContainer() {
	   return boardContainer;
    }
}
