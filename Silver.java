/**
 * @author Sandie,James,Lincoln
 * @version 1.0
 * Represents a Silver Coin Item.
 * This class inherits from Loot.
 */
public class Silver extends Loot{
    /**
     * This constructs a Silver Item by taking in coordinates and passing
     * relevant information to the Loot class.
     * @param x The coordinate X
     * @param y The coordinate Y
     */
    public Silver (int x, int y){
        super(x,y,"images/silver.png",5);
    }
}