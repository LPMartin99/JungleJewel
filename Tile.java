import java.util.ArrayList;

/**
 * @author Mai, Sandie, James
 * @version 1.0
 * A class to represent individual Tiles
 */
public class Tile {
    private static ArrayList<Tile> allLevelTiles;
    private int x;
    private int y;
    private ArrayList<Character> colours;

    /**
     * Constructs a Tile.
     * @param x X coordinate
     * @param y Y coordinate
     * @param colours Colours of the Tile
     */
    public Tile(int x, int y, ArrayList<Character> colours) {
        this.x = x;
        this.y = y;
        this.colours = colours;
    }

    /**
     * Returns the colours of the Tile
     * @return Colours
     */
    public ArrayList<Character> getColours() {
        return colours;
    }

    /**
     * Returns the X coordinate
     * @return X coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * Returns the Y coordinate
     * @return Y coordinate
     */
    public int getY() {
        return y;
    }

    /**
     * Sets the Tile list to the new list provided.
     * @param allLevelTiles new list of Tiles to add
     */
    public static void setAllLevelTiles(ArrayList<Tile> allLevelTiles) {
        Tile.allLevelTiles = new ArrayList<>(allLevelTiles);
    }

    /**
     * Returns the list of all level tiles
     * @return the list of all tiles
     */
    public static ArrayList<Tile> getAllLevelTiles() {
        return allLevelTiles;
    }

    /**
     * Returns a Tile at given coordinates
     * @param x X coordinate to check
     * @param y Y coordinate to check
     * @return Tile at the given coordinates
     */
    public static Tile getTile(int x, int y){
        Tile tile = null;
        for(Tile t: allLevelTiles){
            if(t.x == x && t.y == y){
                tile = t;
            }
        }
        return tile;
    }

    /**
     * Gets the list of surrounding Tiles from a given Tile
     * @param x X coordinate of the tile to check around
     * @param y Y coordinate of the tile to check around
     * @return The list of surrounding Tiles.
     */
    public static ArrayList<Tile> getSurroundingTiles(int x, int y){
        ArrayList<Tile> surroundingTiles = new ArrayList<Tile>();
        for (Tile t : allLevelTiles){
            if (t.getX() == x && t.getY() == y-1){
                surroundingTiles.add(t);
            }else if (t.getX() == x && t.getY() == y+1){
                surroundingTiles.add(t);
            }else if (t.getX() == x-1 && t.getY() == y){
                surroundingTiles.add(t);
            }else if (t.getX() == x+1 && t.getY() == y){
                surroundingTiles.add(t);
            }

        }
        return surroundingTiles;
    }
}