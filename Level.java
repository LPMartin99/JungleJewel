import java.util.ArrayList;

/**
 * @author James,Sandie, Lincoln
 * @version 1.3
 * This class represents a Level.
 */
public class Level {
    private final int LEVEL_NUMBER;
    private ArrayList<HighScore> highScores;
    private final int MAX_WIDTH;
    private final int MAX_HEIGHT;
    private final Timer GAME_TIMER;
    private final Player PLAYER;

    /**
     * This constructs a Level object
     * @param levelNumber The number of the Level.
     * @param gameTimer The game Timer in the Level.
     * @param maxWidth The max width of the Level.
     * @param maxHeight The max height of the Level.
     * @param highScores The Level's high-scores.
     * @param player The player that is within the level
     */
    public Level(int levelNumber, Timer gameTimer, int maxWidth,
                 int maxHeight, ArrayList<HighScore> highScores, Player player) {
        this.LEVEL_NUMBER = levelNumber;
        this.GAME_TIMER = gameTimer;
        this.MAX_WIDTH = maxWidth;
        this.MAX_HEIGHT = maxHeight;
        this.highScores = highScores;
        this.PLAYER = player;
    }

    /**
     * Returns the width of the level
     * @return maxWidth
     */
    public int getMaxWidth() {
        return MAX_WIDTH;
    }

    /**
     * Returns the height of the level
     * @return maxHeight
     */
    public int getMaxHeight() {
        return MAX_HEIGHT;
    }

    /**
     * Returns the game timer
     * @return gameTimer
     */
    public Timer getGameTimer() {
        return GAME_TIMER;
    }

    /**
     * Returns the level number
     * @return levelNumber
     */
    public int getLevelNumber() {
        return LEVEL_NUMBER;
    }

    /**
     * Returns the array list of high-scores
     * @return highScores
     */
    public ArrayList<HighScore> getHighScores() {
        return highScores;
    }

    /**
     * Returns the player
     * @return player
     */
    public Player getPlayer() {
        return PLAYER;
    }
}



