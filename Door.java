import java.util.ArrayList;

/**
 * @author Sandie, Khushi, James, Lincoln
 * @version 1.0
 * Represents a Door Item.
 * This class inherits from Item.
 */
public class Door extends Item{
    /**
     * Constructs a door object taking the coordinates of its position,
     * and setting the image.
     * @param x X coordinate
     * @param y Y coordinate
     */
    public Door(int x, int y){
        super(x, y, false,"images/door.png");
    }

    /**
     * This method returns a boolean indicating whether all levers and loot are collected.
     * If true then players/thieves can interact with the door, ending the level.
     * @param items The list of all Items on the Board
     * @return isOpen
     */
    public boolean isOpen (ArrayList<Item> items){
        boolean isOpen = true;
        for (Item i : items){
            if (i instanceof Loot || i instanceof Lever) {
                isOpen = false;
            }
        }
        return isOpen;
    }
}
