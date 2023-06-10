/**
 * @author Sandie,Khushi
 * @version 1.0
 * Represents a Clock Item.
 * This class stores the clock behaviours and inherits from Item.
 */
public class Clock extends Item {
    /**
     * Constructs a bomb object taking the coordinates of its position,
     * setting the image.
     * @param x The X coordinate
     * @param y The Y coordinate
     */
    public Clock(int x, int y){
        super(x, y, true,"images/clock.png");
    }

    /**
     * A method that takes the game timer and notes it was collected by a thief
     * NPC so that we can reduce the amount of time remaining.
     * @param gameTimer The Timer tracking the time in the game.
     * @param thief Signals a Thief collected the Item.
     */
    public void collected(Timer gameTimer,Thief thief){
        gameTimer.reduceTimer();
    }

    /**
     * A method that takes the game timer and notes it was collected by a player
     * so that we can boost the amount of time remaining.
     * @param gameTimer The Timer tracking the time in the game.
     * @param player Signals a Player collected the Item.
     */
    public void collected(Timer gameTimer, Player player){
        gameTimer.boostTimer();
    }
}