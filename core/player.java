package core;

public class player
{
        private rack playerRack;
        private int score;
        private int[] tileIndices;
        
        public player()
        {
                playerRack = new rack();
                score = 0;
                tileIndices = new int[7];
        }
        
        public boolean submit()
        {
                boolean result = indexedDictionary.checkWord("hello");
                if (result)
                {
                        playerRack.remove(/*tiles to be removed*/4);
                        playerRack.remove(5);
                        playerRack.draw();
                }
                return result;
        }
        
        public void draw()
        {
                playerRack.draw();
        }
        
        public void removeUsedTiles()
        {
                for(int index : tileIndices)
                        playerRack.remove(index);
                draw();
        }
        
        
        public void updateScore(int score)
        {
                this.score += score;
        }
        
        public rack getRack()
        {
                return playerRack;
        }

}
