/**
 * @author Sandie, Lincoln, James
 * @version 1.2
 * This class represents a Thief NPC.
 * This class holds the position of the thieves, and the direction they are facing.
 * Inherits from NPC.
 */
public class Thief extends NPC{
    /**
     * Constructs a Thief object.
     * Used for FloorFollowingThief.
     * @param x The X coordinate of the Thief.
     * @param y The Y coordinate of the Thief.
     * @param imageURL The URL for the thief's image.
     * @param directionFacing The direction the Thief is facing.
     */
    public Thief(int x, int y,String imageURL,String directionFacing){
        super(x,y,imageURL,directionFacing);
    }
    /**
     * Constructs a Thief object.
     * @param x The X coordinate of the Thief.
     * @param y The Y coordinate of the Thief.
     * @param imageURL The URL for the thief's image.
     */
    public Thief(int x, int y,String imageURL){
        super(x,y,imageURL);
    }

    /**
     * Details actions taken when a thief is on the same Tile as certain Items.
     * @param timer The timer for the Level - used when Item is a clock.
     * @param board The Board currently running - used to end the game.
     */
    public void thiefItemInteractions(Timer timer,Board board){
        Item itemOnTile = Item.getItem(positionX, positionY);
        if (itemOnTile != null) {
            if (itemOnTile instanceof Loot) {
                Item.removeItem(itemOnTile);
            } else if (itemOnTile instanceof Clock) {
                ((Clock) itemOnTile).collected(timer,this);
                Item.removeItem(itemOnTile);
            } else if (itemOnTile instanceof Lever) {
                ((Lever) itemOnTile).removeGates(Tile.getAllLevelTiles());
                Item.removeItem(itemOnTile);
            } else if(itemOnTile instanceof Door) {
                if (((Door) itemOnTile).isOpen(Item.getAllLevelItems())) {
                    board.gameOver();
                }
            }
        }
    }

}

