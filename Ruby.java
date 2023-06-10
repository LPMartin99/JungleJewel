/**
 * @author Sandie,Lincoln,James
 * @version 1.0
 * Represents a Ruby Item.
 * This class inherits from Loot.
 */
public class Ruby extends Loot{
    /**
     * This constructs a Ruby Item by taking in coordinates and passing
     * relevant information to the Loot class.
     * @param x The coordinate X
     * @param y The coordinate Y
     */
    public Ruby (int x, int y){
        super(x,y,"images/ruby.png",25);
    }
}
