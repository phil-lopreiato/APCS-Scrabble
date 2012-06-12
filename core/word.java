package core;

public class word {
	private int x,y, score;
	private tile[] letters;
	
	public word(int x, int y, tile[] letters, int score) {
		this.x = x;
		this.y = y;
		this.letters = letters;
		this.score = score;
	}
	
	/*public word(tile[] letters) {
		
	}
	
	public void setLoc(int x, int y) {
		this.x = x;
		this.y = y;
	}*/
	
	public int getX() {
    	return x;
    }

	public int getY() {
    	return y;
    }

	public tile[] getLetters() {
    	return letters;
    }
	
	public int getScore() {
		return score;
	}
}
