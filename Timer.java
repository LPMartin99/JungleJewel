/**
 * @author Sandie
 * @version 1.0
 * A class to represent a Timer
 */
public class Timer {
    private int secondsLeft;

    /**
     * Constructs a Timer
     * @param seconds The number of seconds to start with
     */
    public Timer(int seconds){
        this.secondsLeft = seconds;
    }

    /**
     * Returns the number of seconds left
     * @return seconds left
     */
    public int getSecondsLeft() {
        return secondsLeft;
    }

    /**
     * Sets the seconds left to a given value
     * @param secondsLeft Seconds to set Timer to.
     */
    public void setSecondsLeft(int secondsLeft) {
        this.secondsLeft = secondsLeft;
    }

    /**
     * Boosts the timer by 10 seconds
     */
    public void boostTimer(){
        this.secondsLeft = this.secondsLeft + 10;
    }

    /**
     * Reduces the timer by 10 seconds
     */
    public void reduceTimer(){
        this.secondsLeft = this.secondsLeft - 10;
    }
}
