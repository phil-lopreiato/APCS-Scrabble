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
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import core.tile;

public class rackGUI extends GUI implements guiSegment {
	
	private JPanel rackContainer;
	private JLabel rackLetters[];
	
	public rackGUI() {
		rackContainer  = new JPanel();
		rackContainer.setPreferredSize(new Dimension(7* 100/*large tile width*/ + 7* 5/*border*/,110 /*large tile height*/));
		rackLetters = new JLabel[7];
		rackContainer.setLayout(new FlowLayout(FlowLayout.CENTER,5,5));
	}
	
	public void addComponents(java.awt.Container pane) {
		//draw rack
		BufferedImage blankTile = null;
		try {                
			blankTile = ImageIO.read(this.getClass().getResource("singleTile_large.png"));
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
		for(int i = 0;i<7;i++) { //set default tiles
			rackLetters[i] = new JLabel();
			rackLetters[i].addMouseMotionListener(new tileDnD());
			rackLetters[i].addMouseListener(new tileClick());
			rackContainer.add(rackLetters[i]);
			updateRack(i,blankTile);
		}
		
		pane.add(rackContainer, BorderLayout.SOUTH);
	}
	
	public void updateRack(int pos /*0-6*/,BufferedImage tile) {
		rackLetters[pos].setIcon(new ImageIcon(tile));
	}
	
	public void updateRack(BufferedImage[] tiles) {
		for(int i = 0;i<tiles.length;i++)
			rackLetters[i].setIcon(new ImageIcon(tiles[i]));
	}
	
	public void updateRack(tile[] tiles) {
		for(int i = 0;i<tiles.length;i++)
			rackLetters[i].setIcon(new ImageIcon(tiles[i].paint(true)));
	}
	
	class tileDnD extends MouseMotionAdapter{
		public void mouseDragged(MouseEvent e) {
			Component c = e.getComponent();
			c.setLocation(c.getX()+e.getX(),c.getY()+e.getY());
			repaint();
		}
	}
	
	class tileClick implements MouseListener{

		@Override
        public void mouseClicked(MouseEvent arg0) {
	        
        }

		@Override
        public void mouseEntered(MouseEvent arg0) {
	        // TODO Auto-generated method stub
	        
        }

		@Override
        public void mouseExited(MouseEvent arg0) {
	        // TODO Auto-generated method stub
	        
        }

		@Override
        public void mousePressed(MouseEvent arg0) {
			JLabel tile = (JLabel) arg0.getSource();
			//rackContainer.remove(tile);
			//mainFrame.add(tile);
        }

		@Override
        public void mouseReleased(MouseEvent arg0) {
			//System.out.print("release!");
			Component c = arg0.getComponent();
	        System.out.println(c.getY());
	        //x: 5<650
	        //y: -685<-7
		}
		
	}


    public JPanel getContainer() {
	   return rackContainer;
    }
}
