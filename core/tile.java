package core;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class tile
{
	private char letter;
	private int value;
	private int special;
	private static int[] letterValues = {1,3,3,2,1,4,2,4,1,8,5,1,3,1,1,3,10,1,1,1,1,4,4,8,4,10,0};
	
	public tile(char letter)
	{
		this.letter = letter;
		value = lookupValue(letter);
		special = 0;
	}
	
	public tile(int special)
	{
		letter = '['; //this is ASCII 91, which immediately follows 'Z'. this will be considered, for all purposes within the program, a blank tile. for the array indexing, a 
					  //blank will be index 26 (immediately follows 'Z'), so it's important that this matches
		value = 0;
		this.special = special;
	}
	
	public char getLetter()
	{
		return letter;
	}
	
	public int getValue()
	{
		return value;
	}
	
	public int getSpecial()
	{
		return special;
	}
	
	private static int lookupValue(char letter) {
		return letterValues[letter-65];
	}
	
	public BufferedImage paint(boolean large) {
		BufferedImage baseTile = null, output;
        try {
	        baseTile = ImageIO.read(this.getClass().getResource(large?"gui/singleTile_large.png":"gui/singleTile.png"));
        } catch (IOException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
        }
        output = new BufferedImage(baseTile.getWidth(), baseTile.getHeight(), BufferedImage.TYPE_INT_ARGB);
        
		Graphics2D g2d = output.createGraphics();
		g2d.drawImage(baseTile, 0, 0, null);
		g2d.setPaint(Color.black);
		g2d.setFont(new Font("Serif", Font.BOLD, 60));	
		String s = Character.toString(getLetter());
        FontMetrics fm = g2d.getFontMetrics();
        
        int x = output.getWidth()/2 - fm.stringWidth(s)/2;
        int y = fm.getHeight();
        g2d.drawString(s, x, y);
        
        g2d.setFont(new Font("Serif", Font.BOLD, 20));	
        fm = g2d.getFontMetrics();
        s = Integer.toString(this.getValue());
        x = output.getWidth() - fm.stringWidth(s);
        y = output.getHeight() - fm.getHeight();
        g2d.drawString(s, x, y);
        
        g2d.dispose();
        return output;
	}
}