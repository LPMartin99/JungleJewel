/**
 * @author Lincoln, Sandie
 * @version 1.0
 * This class represents Loot.
 * It stores the number of points it awards the player
 * when collected and inherits from Item.
 */
public class Loot extends Item {
    protected int pointsValue;
    /**
     * This constructs a loot item using values passed in from loot subclasses,
     * storing the value of each loot.
     * @param x X coordinate
     * @param y Y coordinate
     * @param image Image to display
     * @param points Value of the loot
     */
    public Loot(int x, int y,String image, int points){
        super(x, y, true,image);
        this.pointsValue = points;
    }

    /**
     * Returns the value of the loot item
     * @return pointsValue
     */
    public int getPointsValue() {
        return pointsValue;
    }

}
