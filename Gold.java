/**
 * @author Sandie,James,Lincoln
 * @version 1.0
 * Represents a Gold Coin Item.
 * This class inherits from Loot.
 */
public class Gold extends Loot{
    /**
     * This constructs a Gold Item by taking in coordinates and passing
     * relevant information to the Loot class.
     * @param x The coordinate X
     * @param y The coordinate Y
     */
    public Gold (int x, int y){
        super(x,y,"images/gold.png",10);
    }
}
