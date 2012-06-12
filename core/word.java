package core;

public class word {
	private int x,y, score, dir;
	private tile[] letters;
	
	public word(int x, int y, int dir, tile[] letters, int score) {
		this.x = x;
		this.y = y;
		this.dir = dir;
		this.letters = letters;
		this.score = score;
	}
	
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
	
	public int getDir() {
		return dir;
	}
}
