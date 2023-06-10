import javafx.scene.image.Image;
import javafx.animation.Timeline;
/**
 * @author Sandie,James
 * @version 1.0
 * Represents a Bomb Item.
 * This class stores the bomb behaviours and inherits from the Item class.
 */

public class Bomb extends Item{

    /** Constructs a bomb object taking the coordinates of its position,
     * setting the image
     * @param x Coordinate X of the Bomb.
     * @param y Coordinate Y of the Bomb
     */
    public Bomb(int x, int y){
        super(x, y, false,"images/bomb.png");
    }

    /**
     * Checks if a bomb is near a specified coordinate, if so, set it to explode.
     * @param x X coordinate
     * @param y Y coordinate
     * @param bombTimeLine TimeLine of the Bomb so we can start it
     * @param board Board So we can draw when a Bomb explodes.
     */
    public static void bombCheck(int x, int y, Timeline bombTimeLine,
                                 Board board){
        for (Tile t : Tile.getSurroundingTiles(x,y)){
            Item i = Item.getItem(t.getX(), t.getY());
            if (i instanceof Bomb){
                i.setImage(new Image("images/bombExplode.png"));
                bombTimeLine.play();
                bombTimeLine.setOnFinished(explode -> {
                    i.setImage(new Image("images/explosion.png"));
                    bombExplode(t, board);
                });
            }
        }
    }

    /**
     * When Bomb explodes, destroys items horizontal and vertical to the Bomb.
     * If a Bomb is in this path, explodes it immediately,
     * and if an NPC is in the blast, removes that NPC
     * @param originOfExplosion The Tile the Bomb is exploding from.
     * @param board The Board the Bomb is on.
     */
    private static void bombExplode(Tile originOfExplosion,Board board){
        Item.removeItem(Item.getItem(originOfExplosion.getX(),
                originOfExplosion.getY()));
        for (Tile t2 : Tile.getAllLevelTiles()){
            if ((t2.getX() == originOfExplosion.getX()
                    || t2.getY() == originOfExplosion.getY())
                    && (Item.getItem(t2.getX(),t2.getY()) != null)){
                if ((Item.getItem(t2.getX(), t2.getY()).isCollectible())){
                    Item.removeItem(Item.getItem(t2.getX(),t2.getY()));
                }else if (Item.getItem(t2.getX(),t2.getY()) instanceof Bomb){
                    bombExplode(t2,board);
                }
                if (NPC.getNPC(t2.getX(),t2.getY()) != null){
                    NPC.removeNPC(NPC.getNPC(t2.getX(),t2.getY()));
                }
            }
        }
        board.drawItemLayer();
        // Redraw game as the player may have moved.
        board.drawPlayerLayer();
    }



}
