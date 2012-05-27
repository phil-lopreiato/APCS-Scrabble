package core.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import core.game;

public class boardFrame extends JFrame implements ActionListener{
	private JFrame mainFrame;
	private JPanel boardContainer, scoreContainer;
	private JLabel boardLabel, playerScores[];
	private JComboBox numPlayersSelect;
	private BufferedImage boardBase;
	private game gameRef;
	private int numPlayers;
	
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
		
		playerScores = new JLabel[numPlayers];
		
		//Create and set up the window.
		boardContainer = new JPanel(new BorderLayout());
        scoreContainer = new JPanel(new GridLayout(0,2));
        
        //load image
        try {                
            boardBase = ImageIO.read(this.getClass().getResource("mainBoard.jpg"));
        } catch (IOException ex) {
              ex.printStackTrace();
        }
       
       boardLabel = new JLabel(new ImageIcon( boardBase ));
       boardContainer.add(boardLabel);
       
       JLabel scoreHeader = new JLabel("Scores:");
       scoreContainer.add(scoreHeader);
       scoreContainer.add(new JLabel()); //add blank label, keep the grid set
       for(int i = 1;i<=playerScores.length;i++) {
    	   scoreContainer.add(new JLabel("Player "+i+": "));
    	   playerScores[i-1] = new JLabel("0");
    	   scoreContainer.add(playerScores[i-1]);
       }
       
       mainFrame.getContentPane().add(boardContainer, BorderLayout.WEST);
       mainFrame.getContentPane().add(scoreContainer, BorderLayout.EAST); 
        
	}
	
	public void updateScore(int player,int score) {
		playerScores[player-1].setText(Integer.toString(score));
		repaint();
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