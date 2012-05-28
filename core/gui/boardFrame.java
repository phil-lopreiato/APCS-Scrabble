package core.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import core.game;

public class boardFrame extends JFrame implements ActionListener{
	private JFrame mainFrame;
	private JPanel boardContainer, scoreContainer, rackContainer;
	private JLabel boardLabel, scoreLabels[], rackLetters[];
	private JComboBox numPlayersSelect;
	private BufferedImage boardBase;
	private game gameRef; //may not be needed
	private int numPlayers, playerScores[];
	
	public boardFrame() {
		mainFrame = new JFrame("Scrabble");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        numPlayers = 0;
	}
	
	public void setParent(game parentGame) {
		gameRef = parentGame;
	}
	
	public void gameInit() {
		String[] numOptions = {"1","2","3","4"};
		numPlayersSelect = new JComboBox(numOptions);
		numPlayersSelect.setSelectedIndex(0);
		JButton goButton = new JButton("Continue");
		goButton.addActionListener(this);
		
		mainFrame.getContentPane().add(new JLabel("Select the Number of Players"), BorderLayout.NORTH);
		mainFrame.getContentPane().add(numPlayersSelect, BorderLayout.CENTER);
		mainFrame.getContentPane().add(goButton, BorderLayout.SOUTH);
	}
	
	public boolean numPlayersSet() {
		return numPlayers != 0;
	}
	
	public int getNumPlayers() {
		return numPlayers;
	}
	
	
	public void loadGameDisplay(int numPlayers) {
		
		scoreLabels = new JLabel[numPlayers];
		playerScores = new int[numPlayers];
		
		//Create and set up the window.
		boardContainer = new JPanel(new BorderLayout());
		
        scoreContainer = new JPanel();    
        scoreContainer.setLayout(new BoxLayout(scoreContainer,(int) BoxLayout.Y_AXIS));
        scoreContainer.setSize(new Dimension(10,10));
        
        rackContainer  = new JPanel();
        rackContainer.setPreferredSize(new Dimension(7* 100/*large tile width*/ + 7* 5/*border*/,110 /*large tile height*/));
        rackLetters = new JLabel[7];
        rackContainer.setLayout(new FlowLayout(FlowLayout.CENTER,5,5));
        
        //load board image
        try {                
            boardBase = ImageIO.read(this.getClass().getResource("mainBoard.jpg"));
        } catch (IOException ex) {
              ex.printStackTrace();
        }
       
       boardLabel = new JLabel(new ImageIcon( boardBase )); //draw board background
       boardContainer.add(boardLabel);
       
       //draw score area 
       JLabel scoreHeader = new JLabel("Scores:"); //header label
       scoreHeader.setAlignmentX(scoreContainer.CENTER_ALIGNMENT);
       scoreContainer.add(scoreHeader);
      
       for(int i = 1;i<=playerScores.length;i++) {
    	   playerScores[i-1] = 0;
    	   scoreLabels[i-1] = new JLabel("Player "+i+": "+playerScores[i-1]);
    	   scoreLabels[i-1].setAlignmentX(scoreContainer.CENTER_ALIGNMENT);
    	   scoreContainer.add(scoreLabels[i-1]);
       }
       
       
       //draw rack
       BufferedImage blankTile = null;
       try {                
           blankTile = ImageIO.read(this.getClass().getResource("singleTile_large.png"));
       } catch (IOException ex) {
             ex.printStackTrace();
       }
       
       for(int i = 0;i<7;i++) { //set default tiles
    	   rackLetters[i] = new JLabel();
    	   rackContainer.add(rackLetters[i]);
    	   updateRack(i,blankTile);
       }
       
       //add components to main frame
       mainFrame.getContentPane().add(boardContainer, BorderLayout.WEST);
       mainFrame.getContentPane().add(scoreContainer, BorderLayout.EAST); 
       mainFrame.getContentPane().add(rackContainer, BorderLayout.SOUTH);
	}
	
	public void updateScore(int player,int score) {
		scoreLabels[player-1].setText("Player "+player+": "+playerScores[player-1]);
		repaint();
	}
	
	public void updateRack(int pos /*0-6*/,BufferedImage tile) {
		rackLetters[pos].setIcon(new ImageIcon(tile));
	}
	
	public void updateRack(BufferedImage[] tiles) {
		for(int i = 0;i<tiles.length;i++)
			rackLetters[i].setIcon(new ImageIcon(tiles[i]));
	}
	
	public void show() {	
        mainFrame.pack();
        mainFrame.setVisible(true);
        mainFrame.repaint();
	}
	
	public void repaint() {
		mainFrame.pack();
		mainFrame.repaint();
	}
	
	public void clear() {
		mainFrame.getContentPane().removeAll();
	}

	@Override
    public void actionPerformed(ActionEvent e) {
		numPlayers = numPlayersSelect.getSelectedIndex()+1;
    }
}