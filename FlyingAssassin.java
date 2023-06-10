import javafx.scene.image.Image;

/**
 * @author Lincoln, Sandie, James
 * @version 1.5
 * Represents a Flying Assassin NPC.
 * This class stores the movement direction that the Flying Assassin moves in,
 * and the move behaviour, whilst inheriting from the NPC class.
 */
public class FlyingAssassin extends NPC {
    private String movementDirection;

    /**
     * This constructs a Flying Assassin NPC,
     * storing the specific direction for movement.
     * @param movementDirection The direction of movement.
     * @param x The X coordinate.
     * @param y The Y coordinate.
     */
    public FlyingAssassin(String movementDirection, int x, int y){
        super(x,y,"images/parrotR.png");
        this.movementDirection = movementDirection;
    }

    /**
     * This method moves the Flying Assassin,
     * turning it in an opposite direction when a Board boundary is met
     * @param maxWidth The maximum width of the board
     * @param maxHeight The maximum height of the board.
     */
    public void faMove(int maxWidth, int maxHeight){
        switch (movementDirection){
            case "x":
                this.image = new Image("images/parrotR.png");
                this.positionX = positionX + 1;
                if (positionX == maxWidth - 1) {
                    this.movementDirection = "-x";
                }
                break;
            case "-x":
                this.image = new Image("images/parrotL.png");
                this.positionX = positionX - 1;
                if (positionX == 0) {
                    this.movementDirection = "x";
                }
                break;
            case "y":
                this.image = new Image("images/parrotD.png");
                this.positionY = positionY + 1;
                if (positionY == maxHeight - 1) {
                    this.movementDirection = "-y";
                }
                break;
            case "-y":
                this.image = new Image("images/parrotU.png");
                this.positionY = positionY - 1;
                if (positionY == 0) {
                    this.movementDirection = "y";
                }
                break;
            default:
                break;
        }
    }

    /**
     * Returns the movement direction of the Flying Assassin.
     * @return movementDirection
     */
    public String getMovementDirection() {
        return movementDirection;
    }

    /**
     * Checks when the FA moves, if it collides with a player then the game ends.
     * @param player The Player to check position of.
     * @param board The Board to look at.
     */
    public void checkPlayerCollision(Player player, Board board){
        if (this.getPosX() == player.getPlayerX()
                && this.getPosY() == player.getPlayerY()){
            board.gameOver();
        }
    }

}