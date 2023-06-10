/**
 * @author Jason, Sandie, Mai
 * @version 2.0
 * Represents an individual High Score.
 * Stores both a username and score.
 */
public class HighScore {
    private String username;
    private final int SCORE;

    /**
     * Constructs a High Score.
     * @param username The username of the User who achieved the score.
     * @param score The score they got.
     */
    public HighScore(String username, int score){
        this.username = username;
        this.SCORE = score;
    }

    /**
     * Returns the User's score.
     * @return int score
     */
    public int getScore() {
        return SCORE;
    }

    /**
     * Returns the User's username.
     * @return String username
     */
    public String getUsername() {
        return username;
    }
}
