import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import java.util.ArrayList;

/**
 * @author Sandie, Lincoln
 * @version 2.0
 */
public class Player {
    private int playerX;
    private int playerY;
    Image playerImage = new Image("images/Explorer-1.png");
    Image tile = new Image("images/yellow.png");
    private final int X_OFFSET = (int)tile.getWidth() - (int)playerImage.getWidth()/2;
    private final int Y_OFFSET = (int)tile.getHeight() - (int)playerImage.getHeight()/2;

    /**
     * Constructs a Player object.
     * @param playerX X coordinate
     * @param playerY Y coordinate
     */
    public Player(int playerX, int playerY) {
        this.playerX = playerX;
        this.playerY = playerY;
    }

    /**
     * Returns Player X coordinate
     * @return PlayerX
     */
    public int getPlayerX() {
        return playerX;
    }

    /**
     * Returns Player Y coordinate.
     * @return PlayerY
     */
    public int getPlayerY() {
        return playerY;
    }

    /**
     * Returns Player image
     * @return playerImage
     */
    public Image getPlayerImage() {
        return playerImage;
    }

    /**
     * Returns the X Offset
     * @return X offset
     */
    public int getXOffset() {
        return X_OFFSET;
    }

    /**
     * Returns the Y Offset
     * @return Y offset
     */
    public int getYOffset() {
        return Y_OFFSET;
    }

    /**
     * Moves the player depending on the event.
     * @param event The key that was pressed in Board
     * @param currentTile The player's current Tile
     * @param maxHeight The max Height of the Board
     * @param maxWidth  The max Width of the Board
     */
    public void movePlayer(KeyEvent event, Tile currentTile, int maxHeight, int maxWidth){
        ArrayList<Character> currentPlayerColours = new ArrayList<>(Tile.getTile(playerX,playerY).getColours());

        boolean moveAvailable = false;
        Tile tileToMoveTo = null;
        int countX;
        int countY;
        ArrayList<Character> tileColours;
        Tile tileToCheck;
        switch (event.getCode()) {

            case RIGHT:
            case D:
                playerImage = new Image("images/Explorer-4.png");
                //Starts count so it looks at the rightmost tile and goes across the row
                countX = playerX + 1;
                //Loops through the row until a move is found or until we reach the last tile on the board
                while ((countX < maxWidth) && (!moveAvailable)) {
                    tileToCheck = Tile.getTile(countX,playerY);
                    tileColours = new ArrayList<>(tileToCheck.getColours());
                    for (Character colour : currentPlayerColours) {
                        if (tileColours.contains(colour)) {
                            moveAvailable = true;
                            tileToMoveTo = tileToCheck;
                        }
                            }
                    countX++;
                }
                if (moveAvailable && isMoveValid(tileToMoveTo)) {
                    this.playerX = tileToMoveTo.getX();
                    this.playerY = tileToMoveTo.getY();
                }
                break;
            case DOWN:
            case S:
                playerImage = new Image("images/Explorer-1.png");
                countY = playerY + 1;
                while ((countY < maxHeight) && (!moveAvailable)) {
                    tileToCheck = Tile.getTile(playerX,countY);
                    tileColours = new ArrayList<>(tileToCheck.getColours());
                    for (Character colour : currentPlayerColours) {
                                if (tileColours.contains(colour)) {
                                    moveAvailable = true;
                                    tileToMoveTo = tileToCheck;
                                }
                    }
                    countY++;
                }
                if(moveAvailable && isMoveValid(tileToMoveTo)) {
                    this.playerX = tileToMoveTo.getX();
                    this.playerY = tileToMoveTo.getY();
                }
                break;
            case LEFT:
            case A:
                playerImage = new Image("images/Explorer-3.png");
                countX = playerX - 1;
                while ((countX > -1) && (!moveAvailable)) {
                    tileToCheck = Tile.getTile(countX,playerY);
                    tileColours = new ArrayList<>(tileToCheck.getColours());
                    for (Character colour : currentPlayerColours) {
                        if (tileColours.contains(colour)) {
                            moveAvailable = true;
                            tileToMoveTo = tileToCheck;
                        }
                    }
                    countX--;
                }
                if(moveAvailable && isMoveValid(tileToMoveTo)) {
                    this.playerX = tileToMoveTo.getX();
                    this.playerY = tileToMoveTo.getY();
                }
                break;
            case UP:
            case W:
                playerImage = new Image("images/Explorer-2.png");
                countY = playerY - 1;
                while ((countY > -1) && (!moveAvailable)) {
                    tileToCheck = Tile.getTile(playerX,countY);
                    tileColours = new ArrayList<>(tileToCheck.getColours());
                    for (Character colour : currentPlayerColours) {
                        if (tileColours.contains(colour)) {
                            moveAvailable = true;
                            tileToMoveTo = tileToCheck;
                        }
                    }
                    countY--;
                }
                if(moveAvailable && isMoveValid(tileToMoveTo)) {
                    this.playerX = tileToMoveTo.getX();
                    this.playerY = tileToMoveTo.getY();
                }
                break;
            default:
                break;
        }
    }

    /**
     * Checks if a Player move is valid
     * @param tileToMoveTo The tile the Player wants to move to.
     * @return true if the move is valid
     */
    public boolean isMoveValid(Tile tileToMoveTo) {
        Item itemPresent = Item.getItem(tileToMoveTo.getX(), tileToMoveTo.getY());
        NPC npcPresent = NPC.getNPC(tileToMoveTo.getX(),tileToMoveTo.getY());
        if (itemPresent instanceof Gate || itemPresent instanceof Bomb) {
            return false;
        } else if (npcPresent instanceof Thief) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Manages the Player's interactions with Items on the same Tile.
     * @param timer The game timer.
     * @param score The player's current score
     * @param scoreText The score being displayed.
     * @param board The Board the player is on.
     */
    public void playerItemInteractions(Timer timer,int score, Label scoreText, Board board){
        Item itemOnTile = Item.getItem(playerX,playerY);
        if (itemOnTile != null){
            if (itemOnTile.isCollectible()) {
                if(itemOnTile instanceof Clock){
                    ((Clock) itemOnTile).collected(timer, this);
                }else if(itemOnTile instanceof Loot){
                    board.setScore(board.getScore() + ((Loot) itemOnTile).getPointsValue());
                    scoreText.setText("Score: " + board.getScore());
                } else if(itemOnTile instanceof Lever){
                    ((Lever) itemOnTile).removeGates(Tile.getAllLevelTiles());
                }
                Item.removeItem(itemOnTile);
            } else if(itemOnTile instanceof Door){
                if(((Door) itemOnTile).isOpen(Item.getAllLevelItems())){
                    board.gameWon();
                }
            }
        }
    }

    /**
     * Checks whether the player has collided with a Flying Assassin NPC
     * @param board the Board the game is on.
     */
    public void checkPlayerFlyingAssassinCollision(Board board){
        for (NPC n : NPC.getAllLevelNPCs()){
            if (n instanceof FlyingAssassin) {
                if (playerX == n.getPosX() && playerY == n.getPosY()) {
                    board.gameOver();
                }
            }
        }
    }
}