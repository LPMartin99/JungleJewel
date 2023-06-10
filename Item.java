import javafx.scene.image.Image;
import java.util.ArrayList;
/**
 * @author Sandie, Mai, Lincoln
 * @version 1.3
 * Represents an abstract superclass for all Items.
 * This stores all the items on the current level and their information.
 */

public abstract class Item {
    private static ArrayList<Item> allLevelItems;
    protected int positionX;
    protected int positionY;
    protected boolean isCollectible;
    protected Image image;
    private final int X_OFFSET = 3;
    private final int Y_OFFSET = 3;

    /**
     * Constructs an Item using information passed from subclass constructors.
     * @param x X coordinates
     * @param y Y coordinates
     * @param collectible True if Item is collectable, False if not.
     * @param image URL of the Item image.
     */
    public Item(int x, int y,boolean collectible,String image){
        this.positionX = x;
        this.positionY = y;
        this.isCollectible = collectible;
        this.image = new Image(image);
    }

    /**
     * Returns the Item found at a given x,y coordinate.
     * @param x X coordinate
     * @param y Y coordinate
     * @return item
     */
    public static Item getItem(int x, int y){
        Item item = null;
        for (Item i : allLevelItems){
            if (i.positionX == x && i.positionY == y){
                item = i;
            }
        }
        return item;
    }

    /**
     * Removes a given Item from static list of all Items on the current Level.
     * @param item Item to remove.
     */
    public static void removeItem(Item item){
        allLevelItems.remove(item);
    }

    /**
     * Returns the static list of all Items on the current Level.
     * @return allLevelItems
     */
    public static ArrayList<Item> getAllLevelItems() {
        return allLevelItems;
    }

    /**
     * Sets the static list of all Items on the current Level.
     * @param allLevelItems Updated list of all Items.
     */
    public static void setAllLevelItems(ArrayList<Item> allLevelItems) {
        Item.allLevelItems = new ArrayList<>(allLevelItems);
    }

    /**
     * Returns the xOffset.
     * @return xOffset
     */
    public int getXOffset() {
        return X_OFFSET;
    }

    /**
     * Returns the yOffset.
     * @return getYOffset
     */
    public int getYOffset() {
        return Y_OFFSET;
    }


    /**
     * Returns the x position of an Item.
     * @return positionX
     */
    public int getPositionX() {
        return positionX;
    }

    /**
     * Returns the y position of an Item.
     * @return positionY
     */
    public int getPositionY() {
        return positionY;
    }

    /**
     * Returns whether the Item is collectible or not.
     * True if collectible.
     * @return isCollectible
     */
    public boolean isCollectible() {
        return isCollectible;
    }

    /**
     * This method returns the image of the Item
     * @return image
     */
    public Image getItemImage() {
        return image;
    }

    /**
     * Defines a method to be overwritten in Lever and Gate,
     * which will return the colour of said instances.
     * @return (Never accessed in this class. See Gate and Lever.)
     */
    public char getColour() {
        return ' ';
    }

    /**
     * Sets the image of the items.
     * @param image Image to replace the old image with.
     */
    public void setImage(Image image) {
        this.image = image;
    }
}