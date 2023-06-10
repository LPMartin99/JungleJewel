/**
 * @author Sandie, James
 * @version 1.0
 * Represents a Gate Item.
 * This class stores the colour of a Gate object, and inherits from item.
 */
public class Gate extends Item{
    private final char GATE_COLOUR;

    /**
     * This constructs a Gate Item.
     * Stores a colour and passes on relevant information to the Item parent class.
     * @param x X Coordinate.
     * @param y Y Coordinate.
     * @param colour The colour of the gate.
     */
    public Gate(int x, int y, char colour){
        super(x, y, false,getImageForColour(colour));
        this.GATE_COLOUR = colour;
    }

    /**
     * Takes in a colour and sets the image for the gate accordingly.
     * @param gateColour Is the colour of the Gate
     * @return the URL for the correct Gate image.
     */
    public static String getImageForColour(char gateColour){
        return switch (gateColour) {
            case ('B') -> "images/gatec.png";
            case ('R') -> "images/gater.png";
            case ('G') -> "images/gateg.png";
            default -> "images/default.png";
        };
    }

    /**
     * Returns the colour of the gate.
     * @return gateColour
     */
    @Override
    public char getColour() {
        return GATE_COLOUR;
    }
}
