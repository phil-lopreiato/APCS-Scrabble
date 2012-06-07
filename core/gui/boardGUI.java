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
import java.awt.FlowLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

import core.tile;

public class boardGUI extends GUI implements guiSegment{

	JLayeredPane boardContainer, tileContainer;

	JLabel boardLabel;
	BufferedImage boardBase;
	private JLabel boardLetters[][];
	private ArrayList<JLabel> virtualBoardLetters;
	private java.awt.Insets insets;

	public boardGUI() {
		//Create and set up the window.

		//load board image
		try {
			boardBase = ImageIO.read(this.getClass().getResource("mainBoard.jpg"));
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		boardContainer = new JLayeredPane();
		boardContainer.setLayout(null);
		boardContainer.setLayout(new FlowLayout(FlowLayout.CENTER,5,5));
		boardContainer.setOpaque(false);

		tileContainer = new JLayeredPane();
		tileContainer.setPreferredSize(new java.awt.Dimension(652,691));
		tileContainer.setLocation(0, 125);
		//tileContainer.setLayout(new FlowLayout(FlowLayout.CENTER,3,3));
		tileContainer.setOpaque(false);
		tileContainer.setLayout(null);
		insets = tileContainer.getInsets();

		boardLabel = new JLabel(new ImageIcon( boardBase )); //draw board background
		boardLabel.setPreferredSize(new java.awt.Dimension(652,691));
		boardLabel.setLocation(0,110);
		boardLetters = new JLabel[15][15];
		virtualBoardLetters = new ArrayList<JLabel>();
	}

	public void addComponents(javax.swing.JLayeredPane pane) {
		boardContainer.add(boardLabel, 0);
		boardContainer.setSize(boardContainer.getPreferredSize());
		boardContainer.setLocation(0,120);

		tileContainer.setSize(tileContainer.getPreferredSize());
		tileContainer.setLocation(0, 125);
		
		for(int x=0; x<15; x++)
			for(int y=0; y<15; y++) { //set default tiles
				boardLetters[x][y] = new JLabel();
				boardLetters[x][y].setOpaque(false);
				//boardLetters[x][y].setLocation((40*x)+(5*x),(40*y)+(5*y));
				//boardLetters[x][y].setLocation(10,200);
				boardLetters[x][y].setBounds(10,200,40,43);
				boardLetters[x][y].setIcon(null);
				boardLetters[x][y].setPreferredSize(new java.awt.Dimension(43,46));
				boardLetters[x][y].addMouseMotionListener(new tileDnD());
				boardLetters[x][y].addMouseListener(new onBoardTileClick());
				tileContainer.add(boardLetters[x][y],JLayeredPane.DEFAULT_LAYER);
			}

		pane.add(boardContainer, 0);
		pane.add(tileContainer, 0);
	}

	public void addTile(tile t, int x, int y) {
		BufferedImage icon = null;
		JLabel label = null;
		if(t != null)
			icon = t.paint(false);
		else {
			try {
				icon = ImageIO.read(this.getClass().getResource("gui/singleTile.png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		label = new JLabel(new ImageIcon(icon));
		label.setPreferredSize(new java.awt.Dimension(40,43));
		label.setLocation((x*43)+10,(y*46)+126);
		boardContainer.add(label,4);
		boardLetters[x][y] = label;
	}
	
	public void submitVB() {
		System.out.println("submit!");
		for(int x=0;x<15;x++) {
			for(int y=0;y<15;y++) {
				if(boardLetters[x][y] != null && boardLetters[x][y].getIcon()!= null) {
					if(boardLetters[x][y].getMouseMotionListeners().length > 0) boardLetters[x][y].removeMouseMotionListener(boardLetters[x][y].getMouseMotionListeners()[0]);
					if(boardLetters[x][y].getMouseListeners().length > 0) boardLetters[x][y].removeMouseListener(boardLetters[x][y].getMouseListeners()[0]);
				}
			}
		}
	}

	public void addVirtualBoard(tile[][] virtualBoard) {
		//virtualBoardLetters = new ArrayList<JLabel>();
		//JLabel label;
		for(int x=0;x<15;x++) {
			for(int y=0;y<15;y++) {
				if(virtualBoard[x][y] != null) {
					boardLetters[x][y].setIcon(new ImageIcon(virtualBoard[x][y].paint(false)));
					boardLetters[x][y].setBounds((int)(x*42.5)+13,(int)(y*45.5)+6,46,43);
					boardLetters[x][y].setVisible(true);
					/*for(int i=0;i<15;i++)
						for(int j=0;j<15;j++)
							boardLetters[i][j].setIcon(new ImageIcon(virtualBoard[x][y].paint(false)));*/
					
					//label = new JLabel(new ImageIcon(virtualBoard[x][y].paint(false)));
					//boardLetters[x][y].setLocation(200,200);
					//label.setPreferredSize(new java.awt.Dimension(40,43));
					//label.setOpaque(false);
					//tileContainer.add(label,4);
					//tileContainer.moveToFront(label);
				}
			}
		}
		repaint();
	}

	public JLayeredPane getContainer() {
		return boardContainer;
	}
	
	class onBoardTileClick implements MouseListener{

		private int startX, startY, endX, endY;
		
		public onBoardTileClick() {
			startX = startY = endX = endY = -1;
		}
		
		@Override
        public void mouseClicked(MouseEvent arg0) {
	      if(arg0.getClickCount() == 2) {
	    	System.out.println("double click!");
	    	Component c = arg0.getComponent();
	    	gameRef.replaceTile(-1, (int) ((c.getX())/42.5), (int) ((c.getY())/45.25));
	    	c.setVisible(false);
	    	gameRef.drawCurrentRack();
	      }
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
	        Component c = arg0.getComponent();
	        startX = (int) ((c.getX())/42.5);
			startY = (int) ((c.getY())/45.25);
			System.out.println("down! ("+c.getX()+","+c.getY()+"): "+startX+","+startY);
        }

		@Override
        public void mouseReleased(MouseEvent arg0) {
	        Component c = arg0.getComponent();

	        endX = (int) ((c.getX())/42.5);
			endY = (int) ((c.getY())/45.25); 
			System.out.println("up! ("+c.getX()+","+c.getY()+"): "+endX+","+endY);
			
			c.setVisible(false);
			gameRef.swap(startX, startY, endX, endY);			
        }
		
	}
}
