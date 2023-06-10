import javafx.scene.image.Image;
import java.util.ArrayList;

/**
 * @author Sandie
 * @version 1.3
 * Represents NPCs on the Level.
 * Holds static list of all NPCs on the level.
 */
public abstract class NPC {
    private static ArrayList<NPC> allLevelNPCs;
    protected int positionX;
    protected int positionY;
    protected Image image;
    Image tileExample = new Image("images/yellow.png");
    protected int xOffset;
    protected int yOffset;
    protected String directionFacing;

    /**
     * Constructs an NPC Object using information passed from superclasses.
     * This version is for FloorFollowingThief as it relies on the direction heavily.
     * @param x The X position of the NPC.
     * @param y The Y position of the NPC.
     * @param imageURL The URL pointing toward the correct image file.
     * @param directionFacing The direction the NPC is facing.
     */
    public NPC (int x, int y,String imageURL,String directionFacing){
        this.positionX = x;
        this.positionY = y;
        this.directionFacing = directionFacing;
        this.image = new Image(imageURL);
        xOffset = (int)tileExample.getWidth() - (int)image.getWidth()/2;
        yOffset = (int)tileExample.getHeight() - (int)image.getHeight()/2;
    }

    /**
     * Constructs an NPC Object using information passed from superclasses.
     * @param x The X position of the NPC.
     * @param y The Y position of the NPC.
     * @param imageURL The URL pointing toward the correct image file.
     */
    public NPC (int x, int y,String imageURL){
        this.positionX = x;
        this.positionY = y;
        this.image = new Image(imageURL);
        xOffset = (int)tileExample.getWidth() - (int)image.getWidth()/2;
        yOffset = (int)tileExample.getHeight() - (int)image.getHeight()/2;
    }

    /**
     * Sets all the static list of all NPCs as the given ArrayList.
     * @param allLevelNPCs The updated ArrayList of NPCs.
     */
    public static void setAllLevelNPCs(ArrayList<NPC> allLevelNPCs) {
        NPC.allLevelNPCs = new ArrayList<>(allLevelNPCs);

    }

    /**
     * Returns the NPC found at x,y if no NPC is found returns null.
     * @param x The X coordinate.
     * @param y The Y coordinate.
     * @return npc The NPC found at the coordinate/null.
     */
    public static NPC getNPC(int x, int y){
        NPC npc = null;
        for(NPC i: allLevelNPCs){
            if(i.positionX == x && i.positionY == y){
                npc = i;
            }
        }
        return npc;
    }

    /**
     * Returns the static list of all NPCs currently in the level.
     * @return allLevelNPCs
     */
    public static ArrayList<NPC> getAllLevelNPCs() {
        return allLevelNPCs;
    }

    /**
     * Removes a given NPC from the static list.
     * @param npc The NPC Object to be removed.
     */
    public static void removeNPC(NPC npc){
        allLevelNPCs.remove(npc);
    }

    /**
     * Returns the x position of the NPC.
     * @return positionX
     */
    public int getPosX() {
        return positionX;
    }

    /**
     * Returns the y position of the NPC.
     * @return positionY
     */
    public int getPosY() {
        return positionY;
    }

    /**
     * Returns the X offset.
     * @return xOffset
     */
    public int getXOffset() {
        return xOffset;
    }

    /**
     * Sets the X offset
     * @param xOffset the X offset
     */
    public void setXOffset(int xOffset) {
        this.xOffset = xOffset;
    }

    /**
     * Returns the y offset.
     * @return yOffset
     */
    public int getYOffset() {
        return yOffset;
    }

    /**
     * Returns the current image of the NPC.
     * @return image
     */
    public Image getImage() {
        return image;
    }

    /**
     * Returns the direction that the NPC is currently facing.
     * @return directionFacing
     */
    public String getDirectionFacing() {
        return directionFacing;
    }

    /**
     * Checks if a Flying Assassin has collided with another NPC, if so deletes the other NPC
     */
    public static void checkNPCCollisions(){
        NPC collidedWith = null;
        for (NPC n: allLevelNPCs){
            if (n instanceof FlyingAssassin){
                for(NPC n2 : allLevelNPCs){
                    if((n2 != n) && n2.getPosX() == n.getPosX()
                            && n2.getPosY() == n.getPosY()){
                        collidedWith = n2;
                    }
                }
            }
        }
        if(collidedWith != null){
            NPC.removeNPC(collidedWith);
        }
    }
}

