/**
 * @author Sandie,Lincoln,James
 * @version 1.0
 * Represents a Diamond item.
 * This class inherits from Loot.
 */
public class Diamond extends Loot{
    /**
     * This constructs a Diamond Item by taking in coordinates and passing
     * relevant information to the Loot class.
     * @param x The coordinate X
     * @param y The coordinate Y
     */
    public Diamond (int x, int y){
        super(x,y,"images/diamond.png",50);
    }
}