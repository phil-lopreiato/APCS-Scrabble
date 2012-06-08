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

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;

import core.tile;

public class boardGUI extends GUI implements guiSegment{

	JLayeredPane boardContainer, tileContainer;

	JLabel boardLabel;
	BufferedImage boardBase;
	private JLabel boardLetters[][];
	private String[] letters = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};

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
		tileContainer.getInsets();

		boardLabel = new JLabel(new ImageIcon( boardBase )); //draw board background
		boardLabel.setPreferredSize(new java.awt.Dimension(652,691));
		boardLabel.setLocation(0,110);
		boardLetters = new JLabel[15][15];
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
		for(int x=0;x<15;x++) {
			for(int y=0;y<15;y++) {
				if(boardLetters[x][y] != null && boardLetters[x][y].getIcon()!= null) {
					if(boardLetters[x][y].getMouseMotionListeners().length > 0) boardLetters[x][y].removeMouseMotionListener(boardLetters[x][y].getMouseMotionListeners()[0]);
					if(boardLetters[x][y].getMouseListeners().length > 0) boardLetters[x][y].removeMouseListener(boardLetters[x][y].getMouseListeners()[0]);
				}
			}
		}
	}
	
	public void submitBlanks() {
		gameRef.submitBlanks(blanks,blankLocs);
	}

	public void addVirtualBoard(tile[][] virtualBoard) {
		//virtualBoardLetters = new ArrayList<JLabel>();
		//JLabel label;
		boolean blank = false;
		char s;
		for(int x=0;x<15;x++) {
			for(int y=0;y<15;y++) {
				if(virtualBoard[x][y] != null) {
					blank = true;
					Integer[] arr = {x,y};
					if(virtualBoard[x][y].getLetter() == '[' && blankLocs.indexOf(arr) == -1) {
						s =((String)JOptionPane.showInputDialog(
				                null,
				                "Select a letter for the blank tile to represent.",
				                "Wildcard Tile Selection",
				                JOptionPane.PLAIN_MESSAGE,null,
				                letters, letters[0])).charAt(0);
						blanks.add(s);
						blankLocs.add(arr);
					}else
						s = virtualBoard[x][y].getLetter();
					
					boardLetters[x][y].setIcon(new ImageIcon(blank?new tile(s).paint(false):virtualBoard[x][y].paint(false)));
					boardLetters[x][y].setBounds((int)(x*42.5)+13,(int)(y*45.5)+6,46,43);
					boardLetters[x][y].setVisible(true);
				}
			}
		}
		repaint();
	}

	public JLayeredPane getContainer() {
		return boardContainer;
	}
	
	public void removeTile(Component c) {
		int x =  (int) ((c.getLocation().getX())/42.5), y =  (int) (c.getLocation().getY()/45.25);
		gameRef.replaceTile(-1,x,y);
    	Integer[] arr = {x,y};
    	if(blankLocs.indexOf(arr)!= -1) {
    		blankLocs.remove(blankLocs.indexOf(arr));
    		blanks.remove(blankLocs.indexOf(arr));
    	}
    	c.setVisible(false);
    	gameRef.drawCurrentRack();
	}
	
	public void hideVB() {
	    Component[] comps = tileContainer.getComponents();
	    for(Component c:comps) {
	    	//(int) ((c.getLocation().getX())/42.5), (int) (c.getLocation().getY()/45.25)
	    	if(c.getMouseListeners().length > 0)
	    		c.setVisible(false);
	    }
    }
	
	class onBoardTileClick implements MouseListener{

		private int startX, startY, endX, endY;
		private Point start;
		
		public onBoardTileClick() {
			startX = startY = endX = endY = -1;
		}
		
		@Override
        public void mouseClicked(MouseEvent arg0) {
	      if(arg0.getClickCount() == 2) {
	    	Component c = arg0.getComponent();
	    	removeTile(c);
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
			start = c.getLocation();
        }

		@Override
        public void mouseReleased(MouseEvent arg0) {
	        Component c = arg0.getComponent();

	        endX = (int) ((c.getX())/42.5);
			endY = (int) ((c.getY())/45.25);
			
			if(endX > 14)
				endX = 14;
			if(endY > 14)
				endY = 14;
			
			if((c.getX() < 0 || c.getX() > 652 || c.getY() < 0 || c.getY() > 691 || ((JLabel)c).getIcon() == null)) { //tile not on game board
				c.setLocation(start);
			}else {
				if(gameRef.isEmpty(endX, endY)) {
					c.setVisible(false);
					gameRef.swap(startX, startY, endX, endY);	
				}else
					c.setLocation(start);
			}
			repaint();
        }
		
	}
}
