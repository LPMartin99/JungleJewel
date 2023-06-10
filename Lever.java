import java.util.ArrayList;

/**
 * @author Sandie, James
 * @version 1.0
 * Represents a Lever Item.
 * This class stores the colour of a Lever object, behaviours on Lever objects,
 * marks the Lever items as collectible, and inherits from item.
 */
public class Lever extends Item {
    private final char LEVER_COLOUR;

    /**
     * Constructs a Lever Item.
     * Stores a colour and passes on relevant information to the Item parent class.
     * @param x The X coordinate of the Item.
     * @param y The Y coordinate of the Item.
     * @param colour The colour of the Lever.
     */
    public Lever(int x, int y,char colour){
        super(x, y, true,getImageForColour(colour));
        this.LEVER_COLOUR = colour;
    }

    /**
     * Decides the image of the Lever depending on its colour.
     * @param leverColour The colour of the Lever.
     * @return The image URL
     */
    public static String getImageForColour(char leverColour){
        return switch (leverColour) {
            case ('B') -> "images/keyc.png";
            case ('R') -> "images/keyr.png";
            case ('G') -> "images/keyg.png";
            default -> "images/default.png";
        };
    }

    /**
     * Returns the colour of the lever
     * Overrides Item method
     * @return colour
     */
    @Override
    public char getColour() {
        return LEVER_COLOUR;
    }

    /**
     * Removes Gate Items that are the same colour as the given Lever
     * @param tiles The list of Tiles on the Board
     */
    public void removeGates(ArrayList<Tile> tiles){
        for (Tile t: tiles){
            Item item = Item.getItem(t.getX(),t.getY());
            if (item instanceof Gate){
                if (this.LEVER_COLOUR == ((Gate) item).getColour()){
                    Item.removeItem(item);
                }
            }
        }
    }
}