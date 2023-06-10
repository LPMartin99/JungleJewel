import javafx.scene.image.Image;

import java.util.ArrayList;

/**
 * @author Mai, James, Sandie, Lincoln
 * @version 1.4
 * Represents a Floor Following Thief NPC.
 * This class stores the colour of the tiles the FFT will move on and the
 * move behaviour, whilst inheriting from the Thief class.
 */
public class FloorFollowingThief extends Thief {
    private final char COLOUR;

    /**
     * This constructs a Floor Following Thief NPC,
     * storing the specific colour for movement.
     * @param movementDirection The initial direction the FFT moves in
     * @param x The X coordinate
     * @param y The Y coordinate
     * @param colour The colour the FFT follows
     */
    public FloorFollowingThief(String movementDirection, int x, int y, char colour) {
        super(x, y, "images/snakex.png", movementDirection);
        this.COLOUR = colour;
    }

    /**
     * Returns the colour that the Floor Following Thief moves along.
     * @return colour
     */
    public char getColour() {
        return COLOUR;
    }

    /**
     * This finds a valid move for the FFT, so it follows the left wall of it's colour block.
     * @param maxHeight The max height of the Game Board
     * @param maxWidth The max width of the Game Board
     * @param player The Player Item on the Board.
     */
    public void ffMove(int maxHeight, int maxWidth,Player player) {
        ArrayList<Character> tileColours;
        Tile tileToMoveTo = null;
        String directionToFace = "";
        String[] checkOrder;
        switch (directionFacing) {
            case "x":
                checkOrder = new String[]{"-y", "x", "y", "-x"};
                if (positionX < maxWidth) {
                    for (String i : checkOrder) {
                        if (i.equalsIgnoreCase("-y")) {
                            for (Tile t : Tile.getAllLevelTiles()) {
                                if ((t.getX() == positionX) && (t.getY() == positionY - 1)) {
                                    tileColours = t.getColours();
                                    if (tileColours.contains(COLOUR)) {
                                        if (tileToMoveTo == null){
                                            tileToMoveTo = Tile.getTile(positionX,positionY-1);
                                            directionToFace = "-y";
                                            this.image = new Image("images/snakey.png");
                                        }
                                    }
                                }
                            }
                        }else if (i.equalsIgnoreCase("x")) {
                            for (Tile t : Tile.getAllLevelTiles()) {
                                if ((t.getX() == positionX + 1) && (t.getY() == positionY)) {
                                    tileColours = t.getColours();
                                    if (tileColours.contains(COLOUR)) {
                                        if (tileToMoveTo == null){
                                            tileToMoveTo = Tile.getTile(positionX + 1,positionY);
                                            directionToFace = "x";
                                            this.image = new Image("images/snakex.png");
                                        }

                                    }
                                }
                            }
                        }else if (i.equalsIgnoreCase("y")) {
                            for (Tile t : Tile.getAllLevelTiles()) {
                                if ((t.getX() == positionX) && (t.getY() == positionY + 1)) {
                                    tileColours = t.getColours();
                                    if (tileColours.contains(COLOUR)) {
                                        if (tileToMoveTo == null) {
                                            tileToMoveTo = Tile.getTile(positionX,positionY + 1);
                                            directionToFace = "y";
                                            this.image = new Image("images/snake-y.png");
                                        }
                                    }
                                }
                            }
                        }else if (i.equalsIgnoreCase("-x")) {
                            for (Tile t : Tile.getAllLevelTiles()) {
                                if ((t.getX() == positionX - 1) && (t.getY() == positionY)) {
                                    tileColours = t.getColours();
                                    if (tileColours.contains(COLOUR)) {
                                        if (tileToMoveTo == null){
                                            tileToMoveTo = Tile.getTile(positionX - 1,positionY);
                                            directionToFace = "-x";
                                            this.image = new Image("images/snake-x.png");
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            case "y":
                checkOrder = new String[]{"x", "y", "-x", "-y"};
                if (positionY < maxHeight) {
                    for (String i : checkOrder) {
                        if (i.equalsIgnoreCase("-y")) {
                            for (Tile t : Tile.getAllLevelTiles()) {
                                if ((t.getX() == positionX) && (t.getY() == positionY - 1)) {
                                    tileColours = t.getColours();
                                    if (tileColours.contains(COLOUR)) {
                                        if (tileToMoveTo == null) {
                                            tileToMoveTo = Tile.getTile(positionX, positionY - 1);
                                            directionToFace = "-y";
                                            this.image = new Image("images/snakey.png");
                                        }
                                    }
                                }
                            }
                        } else if (i.equalsIgnoreCase("x")) {
                            for (Tile t : Tile.getAllLevelTiles()) {
                                if ((t.getX() == positionX + 1) && (t.getY() == positionY)) {
                                    tileColours = t.getColours();
                                    if (tileColours.contains(COLOUR)) {
                                        if (tileToMoveTo == null) {
                                            tileToMoveTo = Tile.getTile(positionX + 1, positionY);
                                            directionToFace = "x";
                                            this.image = new Image("images/snakex.png");
                                        }
                                    }
                                }
                            }
                        } else if (i.equalsIgnoreCase("y")) {
                            for (Tile t : Tile.getAllLevelTiles()) {
                                if ((t.getX() == positionX) && (t.getY() == positionY + 1)) {
                                    tileColours = t.getColours();
                                    if (tileColours.contains(COLOUR)) {
                                        if (tileToMoveTo == null) {
                                            tileToMoveTo = Tile.getTile(positionX, positionY + 1);
                                            directionToFace = "y";
                                            this.image = new Image("images/snake-y.png");
                                        }
                                    }
                                }
                            }
                        } else if (i.equalsIgnoreCase("-x")) {
                            for (Tile t : Tile.getAllLevelTiles()) {
                                if ((t.getX() == positionX - 1) && (t.getY() == positionY)) {
                                    tileColours = t.getColours();
                                    if (tileColours.contains(COLOUR)) {
                                        if (tileToMoveTo == null) {
                                            tileToMoveTo = Tile.getTile(positionX - 1, positionY);
                                            directionToFace = "-x";
                                            this.image = new Image("images/snake-x.png");
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                break;
            case "-x":
                checkOrder = new String[]{"y", "-x", "-y", "x"};
                if (positionX < maxWidth) {
                    for (String i : checkOrder) {
                        if (i.equalsIgnoreCase("-y")) {
                            for (Tile t : Tile.getAllLevelTiles()) {
                                if ((t.getX() == positionX) && (t.getY() == positionY - 1)) {
                                    tileColours = t.getColours();
                                    if (tileColours.contains(COLOUR)){
                                        if (tileToMoveTo == null){
                                            tileToMoveTo = Tile.getTile(positionX,positionY-1);
                                            directionToFace = "-y";
                                            this.image = new Image("images/snakey.png");
                                        }
                                    }
                                }
                            }
                        }else if (i.equalsIgnoreCase("x")) {
                            for (Tile t : Tile.getAllLevelTiles()) {
                                if ((t.getX() == positionX + 1) && (t.getY() == positionY)) {
                                    tileColours = t.getColours();
                                    if (tileColours.contains(COLOUR)) {
                                        if (tileToMoveTo == null){
                                            tileToMoveTo = Tile.getTile(positionX+1,positionY);
                                            directionToFace = "x";
                                            this.image = new Image("images/snakex.png");
                                        }
                                    }
                                }
                            }
                        }else if (i.equalsIgnoreCase("y")) {
                            for (Tile t : Tile.getAllLevelTiles()) {
                                if ((t.getX() == positionX) && (t.getY() == positionY + 1)) {
                                    tileColours = t.getColours();
                                    if (tileColours.contains(COLOUR)) {
                                        if (tileToMoveTo == null){
                                            tileToMoveTo = Tile.getTile(positionX,positionY+1);
                                            directionToFace = "y";
                                            this.image = new Image("images/snake-y.png");
                                        }
                                    }
                                }
                            }
                        } else if (i.equalsIgnoreCase("-x")) {
                            for (Tile t : Tile.getAllLevelTiles()) {
                                if ((t.getX() == positionX - 1) && (t.getY() == positionY)) {
                                    tileColours = t.getColours();
                                    if (tileColours.contains(COLOUR)) {
                                        if (tileToMoveTo == null){
                                            tileToMoveTo = Tile.getTile(positionX-1,positionY);
                                            directionToFace = "-x";
                                            this.image = new Image("images/snake-x.png");
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                break;
            case "-y":
                checkOrder = new String[] {"-x", "-y", "x", "y"};
                if (positionY < maxHeight) {
                    for (String i : checkOrder) {
                        if (i.equalsIgnoreCase("-y")) {
                            for (Tile t : Tile.getAllLevelTiles()) {
                                if ((t.getX() == positionX) && (t.getY() == positionY - 1)) {
                                    tileColours = t.getColours();
                                    if (tileColours.contains(COLOUR)) {
                                        if (tileToMoveTo == null){
                                            tileToMoveTo = Tile.getTile(positionX,positionY-1);
                                            directionToFace = "-y";
                                            this.image = new Image("images/snakey.png");
                                        }
                                    }
                                }
                            }
                        } else if (i.equalsIgnoreCase("x")) {
                            for (Tile t : Tile.getAllLevelTiles()) {
                                if ((t.getX() == positionX + 1) && (t.getY() == positionY)) {
                                    tileColours = t.getColours();
                                    if (tileColours.contains(COLOUR)) {
                                        if (tileToMoveTo == null){
                                            tileToMoveTo = Tile.getTile(positionX+1,positionY);
                                            directionToFace = "x";
                                            this.image = new Image("images/snakex.png");
                                        }
                                    }
                                }
                            }
                        } else if (i.equalsIgnoreCase("y")) {
                            for (Tile t : Tile.getAllLevelTiles()) {
                                if ((t.getX() == positionX) && (t.getY() == positionY + 1)) {
                                    tileColours = t.getColours();
                                    if (tileColours.contains(COLOUR)) {
                                        if (tileToMoveTo == null){
                                            tileToMoveTo = Tile.getTile(positionX,positionY+1);
                                            directionToFace = "y";
                                            this.image = new Image("images/snake-y.png");
                                        }
                                    }
                                }
                            }
                        } else if (i.equalsIgnoreCase("-x")) {
                            for (Tile t : Tile.getAllLevelTiles()) {
                                if ((t.getX() == positionX - 1) && (t.getY() == positionY)) {
                                    tileColours = t.getColours();
                                    if (tileColours.contains(COLOUR)) {
                                        if (tileToMoveTo == null){
                                            tileToMoveTo = Tile.getTile(positionX-1,positionY);
                                            directionToFace = "-x";
                                            this.image = new Image("images/snake-x.png");
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                break;
            default:
                break;

        }
        this.actualMove(tileToMoveTo,player,directionToFace);
    }

    /**
     * Actually moves the Flying Assassin to a given location.
     * @param tileToMoveTo The Tile to move to.
     * @param player The Player we need to check is on that tile or not.
     * @param directionToFace The direction it will then face.
     */
    private void actualMove(Tile tileToMoveTo, Player player, String directionToFace){
        if (tileToMoveTo != null){
            if (!(tileToMoveTo.getX() == player.getPlayerX() &&  tileToMoveTo.getY() == player.getPlayerY())){
                positionX = tileToMoveTo.getX();
                positionY = tileToMoveTo.getY();
                directionFacing = directionToFace;
            }
        }

    }
}
