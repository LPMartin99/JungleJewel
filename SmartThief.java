import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @author Lincoln
 * @version 1.08
 * Class for Smart Thief that also internally handles the shortest path generation using Dijkstra's Algorithm.
 */
public class SmartThief extends Thief{

    private final int NO_PARENT = -1;
    private final int LEVEL1_EXIT = 19;
    private final int LEVEL2_EXIT = 57;
    private final int LEVEL3_EXIT = 97;

    int level;
    int[][] matrix;
    protected String nextDirection;

    private int currentIndex;
    Image stImage = new Image ("images/MonkeL.png");
    Image tile = new Image ("images/yellow.png");
    private int xOffset = (int)tile.getWidth() - (int)stImage.getWidth()/2;
    private int yOffset = (int)tile.getHeight() - (int)stImage.getHeight()/2;

    static final int V = 104;
    int minDistance(int dist[], Boolean sptSet[])
    {
        // Initialize min value
        int min = Integer.MAX_VALUE, min_index = -1;

        for (int v = 0; v < V; v++)
            if (sptSet[v] == false && dist[v] <= min) {
                min = dist[v];
                min_index = v;
            }

        return min_index;
    }
    /**
     * Constructor for the Smart Thief.
     * @param x Origin x coordinate of the Smart Thief.
     * @param y Origin y coordinate of the Smart Thief.
     * @param levelNumber Integer value for the current level.
     */
    public SmartThief(int x, int y, int levelNumber) {
        super(x,y,"images/MonkeL.png");

        this.level = levelNumber;
        this.matrix = matrix;
    }



    /**
     * Main movement method for the Smart Thief.
     * @param maxWidth Width of the board.
     * @param maxHeight Height of the board.
     * @param itemList ArrayList of the items present on the board.
     * @param tiles ArrayList of tiles that make up the board.
     */
    public void stMove(int maxWidth, int maxHeight, ArrayList<Item> itemList, ArrayList<Tile> tiles) {
        ArrayList<Character> currentSTColours = new ArrayList<>(Tile.getTile(positionX,positionY).getColours());
        setIndex(getPosX(), getPosY(), maxWidth);
        boolean moveAvailable = false;
        Tile tileToMoveTo = null;
        int countX;
        int countY;
        ArrayList<Character> tileColours;
        Tile tileToCheck;


        ArrayList<String> directions = runDijkstra(getIndex(), itemList, tiles);
        System.out.println(directions);

        if (directions.size() > 0) {
            nextDirection = directions.get(0);


            switch(nextDirection) {
                //Checks the next instruction and thus uses the correct code for that direction.
                case "x":
                    //Starts count so it looks at the rightmost tile and goes across the row
                    countX = positionX + 1;
                    //Loops through the row until a move is found or until we reach the last tile on the board
                    while ((countX < maxWidth) && (!moveAvailable)) {
                        tileToCheck = Tile.getTile(countX,positionY);
                        tileColours = new ArrayList<>(tileToCheck.getColours());
                        for (Character colour : currentSTColours) {
                            if (tileColours.contains(colour)) {
                                moveAvailable = true;
                                tileToMoveTo = tileToCheck;
                            }
                        }
                        countX++;
                    }
                    if (moveAvailable && isMoveValid(tileToMoveTo)) {
                        this.positionX = tileToMoveTo.getX();
                        this.positionY = tileToMoveTo.getY();
                        setIndex(positionX, positionY, maxWidth);
                    }
                    break;
                case "y":
                    countY = positionY + 1;
                    while ((countY < maxHeight) && (!moveAvailable)) {
                        tileToCheck = Tile.getTile(positionX,countY);
                        tileColours = new ArrayList<>(tileToCheck.getColours());
                        for (Character colour : currentSTColours) {
                            if (tileColours.contains(colour)) {
                                moveAvailable = true;
                                tileToMoveTo = tileToCheck;
                            }
                        }
                        countY++;
                    }
                    if(moveAvailable && isMoveValid(tileToMoveTo)) {
                        this.positionX = tileToMoveTo.getX();
                        this.positionY = tileToMoveTo.getY();
                        setIndex(positionX, positionY, maxWidth);
                    }
                    break;
                case "-x":
                    countX = positionX - 1;
                    while ((countX > -1) && (!moveAvailable)) {
                        tileToCheck = Tile.getTile(countX,positionY);
                        tileColours = new ArrayList<>(tileToCheck.getColours());
                        for (Character colour : currentSTColours) {
                            if (tileColours.contains(colour)) {
                                moveAvailable = true;
                                tileToMoveTo = tileToCheck;
                            }
                        }
                        countX--;
                    }
                    if(moveAvailable && isMoveValid(tileToMoveTo)) {
                        this.positionX = tileToMoveTo.getX();
                        this.positionY = tileToMoveTo.getY();
                        setIndex(positionX, positionY, maxWidth);
                    }
                    break;
                case "-y":
                    countY = positionY - 1;
                    while ((countY > -1) && (!moveAvailable)) {
                        tileToCheck = Tile.getTile(positionX,countY);
                        tileColours = new ArrayList<>(tileToCheck.getColours());
                        for (Character colour : currentSTColours) {
                            if (tileColours.contains(colour)) {
                                moveAvailable = true;
                                tileToMoveTo = tileToCheck;
                            }
                        }
                        countY--;
                    }
                    if(moveAvailable && isMoveValid(tileToMoveTo)) {
                        this.positionX = tileToMoveTo.getX();
                        this.positionY = tileToMoveTo.getY();
                        setIndex(positionX, positionY, maxWidth);
                    }
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Method that checks that the Smart Thief's next move is valid.
     * @param tileToMoveTo Tile that the Smart Thief wants to move to.
     * @return returns a True or False depending on valid move.
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
     * Method that runs Dijkstra's Algorithm
     * @param adjacencyMatrix The matrix upon which to perform the algorithm
     * @param startVertex The starting location from which distances will be calculated
     * @param itemList the list of items in the level
     * @param tiles the list of tiles in the level
     * @return returns the resultant shortest path from the origin to the nearest item.
     */
    public ArrayList<String> dijkstra(int[][] adjacencyMatrix, int startVertex, ArrayList<Item> itemList, ArrayList<Tile> tiles) {

        //sets the total number of vertices to check as the width of the adjacency matrix.
        int nVertices = adjacencyMatrix[0].length;

        //shortestDistances[i] will hold the shortest distance from source to current index being checked.
        int[] shortestDistances = new int[nVertices];

        //check if the tested vertex is added to the shortest path or not
        boolean[] added = new boolean[nVertices];

        //Initialize all distances as INFINITE and added[] as false
        for (int vertexIndex = 0; vertexIndex < nVertices;
             vertexIndex++)
        {
            shortestDistances[vertexIndex] = Integer.MAX_VALUE;
            added[vertexIndex] = false;
        }

        // Distance of source vertex from itself is always 0
        shortestDistances[startVertex] = 0;

        //Parent array to store shortest path tree
        int[] parents = new int[nVertices];

        //The starting vertex does not have a parent
        parents[startVertex] = NO_PARENT;

        //Find the shortest path for all vertices
        for (int i = 1; i < nVertices; i++)
        {

            //Pick the minimum distance vertex from the set of vertices not yet processed. nearestVertex is
            //always equal to startNode in first iteration.
            int nearestVertex = startVertex;
            int shortestDistance = Integer.MAX_VALUE;
            for (int vertexIndex = 0;
                 vertexIndex < nVertices;
                 vertexIndex++)
            {
                if (!added[vertexIndex] &&
                        shortestDistances[vertexIndex] <
                                shortestDistance)
                {
                    nearestVertex = vertexIndex;
                    shortestDistance = shortestDistances[vertexIndex];
                }
            }

            //Mark the picked vertex as processed
            added[nearestVertex] = true;

            //Update dist value of the adjacent vertices of the picked vertex.
            for (int vertexIndex = 0;
                 vertexIndex < nVertices;
                 vertexIndex++)
            {
                int edgeDistance = adjacencyMatrix[nearestVertex][vertexIndex];

                if (edgeDistance > 0
                        && ((shortestDistance + edgeDistance) <
                        shortestDistances[vertexIndex]))
                {
                    parents[vertexIndex] = nearestVertex;
                    shortestDistances[vertexIndex] = shortestDistance +
                            edgeDistance;
                }
            }
        }
        return(createPath(startVertex, shortestDistances,parents,itemList,tiles));
    }

    /**
     * Method to obtain the list of overall index of each item
     * @param itemList takes in the list of all items
     * @param tiles takes in the list of all tiles
     * @param itemNodes takes in a list of indexes for the items.
     * @return returns list of indexes.
     */
    public int itemIndex(ArrayList<Item> itemList, ArrayList<Tile> tiles, ArrayList<Integer> itemNodes) {
        //loops over the entire list of indexes to find the indexes that have items.
        for(int i = 0; i < tiles.size(); i++) {
            Tile currentTile = tiles.get(i);
            int tX = currentTile.getX();
            int tY = currentTile.getY();
            for(int j = 0; j < itemList.size(); j++){
                Item currentItem = itemList.get(j);
                int iX = currentItem.getPositionX();
                int iY = currentItem.getPositionY();
                int itemNodeVal = (currentItem.getPositionY() * 13 + currentItem.getPositionX());
                if(Item.getAllLevelItems() != null) {
                    //Ensures that Bombs, Gates and the Door aren't set as target indexes during normal play.
                    if((tX == iX && tY == iY) && !(currentItem instanceof Bomb || currentItem instanceof Gate
                            || currentItem instanceof Door)) {
                        if(!(itemNodes.contains(i))) {
                            itemNodes.add(i);
                        }
                    }
                } else {
                    if (tX == iX && tY == iY) {
                        if (!(itemNodes.contains(i))) {
                            itemNodes.add(i);
                        }
                    }
                }
            }
        }
        return 0;
    }

    /**
     * Method to generate the path the Smart Thief is to follow as an ArrayList.
     * @param startVertex the current location of the Smart Thief (also the origin of the path).
     * @param distances the array of distances between each index.
     * @param parents the array of preceding indexes.
     * @param itemList the list of all items present in the level.
     * @param tiles the list of all tiles in the level
     * @return outputs the resultant shortest path
     */
    public ArrayList<String> createPath(int startVertex, int[] distances, int[] parents, ArrayList<Item> itemList,
                                        ArrayList<Tile> tiles) {
        ArrayList<Integer> itemNodes = new ArrayList<Integer>();
        int nVertices = distances.length;
        itemIndex(itemList, tiles, itemNodes);
        ArrayList<Integer> path = new ArrayList<>();
        ArrayList<Integer> distance = new ArrayList<Integer>();

        //Checks if there are items left in the level
        if(itemNodes.size() > 0 && Item.getAllLevelItems().size() > 0) {
            for (int vertexIndex = 0; vertexIndex < nVertices; vertexIndex++) {
                for(int i = 0; i < itemNodes.size(); i++) {
                    if (vertexIndex != startVertex && vertexIndex == itemNodes.get(i)) {
                        path.clear();
                        printPath(vertexIndex, parents, path);
                        distance.add(path.size());
                    }
                }
            }

            //Generates the index value of the closest item
            if(distance.size() > 0) {
                int closestItemDistanceIndex = distance.indexOf(Collections.min(distance));
                int closestItemIndex = itemNodes.get(closestItemDistanceIndex);


                for (int vertexIndex = 0; vertexIndex < nVertices; vertexIndex++) {
                    if (vertexIndex != startVertex && vertexIndex == closestItemIndex) {
                        path.clear();
                        printPath(vertexIndex, parents, path);
                        return getDirections(path, tiles);
                    }
                }
            }
            //If there are no items left, the destination index is set to the location of the door for that level.
        } else {
            if(level == 1) {
                for (int vertexIndex = 0; vertexIndex < nVertices; vertexIndex++) {
                    if (vertexIndex != startVertex && vertexIndex == LEVEL1_EXIT) {
                        path.clear();
                        printPath(vertexIndex, parents, path);
                        return getDirections(path, tiles);
                    }
                }
            } else if (level == 2) {
                for (int vertexIndex = 0; vertexIndex < nVertices; vertexIndex++) {
                    if (vertexIndex != startVertex && vertexIndex == LEVEL2_EXIT) {
                        path.clear();
                        printPath(vertexIndex, parents, path);
                        return getDirections(path, tiles);
                    }
                }
            } else if (level == 3) {
                for (int vertexIndex = 0; vertexIndex < nVertices; vertexIndex++) {
                    if (vertexIndex != startVertex && vertexIndex == LEVEL3_EXIT) {
                        path.clear();
                        printPath(vertexIndex, parents, path);
                        return getDirections(path, tiles);
                    }
                }
            }
        }
        return getDirections(path, tiles);
    }


    /**
     * Method that iteratively generates the arraylist of the path from the SmartThief's current location
     * to the closest item location.
     * @param currentVertex The index currently being processed.
     * @param parents the array of parent indexes.
     * @param path the ArrayList of the path the Smart Thief is to take.
     */
    private void printPath(int currentVertex, int[] parents, ArrayList<Integer> path) {

        //ArrayList<ArrayList> pathList = new ArrayList<ArrayList>();
        // Base case : Source node has
        // been processed
        if (currentVertex == NO_PARENT)
        {
            return;
        }
        printPath(parents[currentVertex], parents, path);
        path.add(currentVertex);
    }

    /**
     * Method that calls the dijkstra method.
     * @param source the starting index.
     * @param itemList the list of all items present in the level.
     * @param tiles the list of all tiles present in the level.
     * @return returns the result of the dijkstra() method.
     */
    public ArrayList<String> runDijkstra(int source,ArrayList<Item> itemList, ArrayList<Tile> tiles) {
        return dijkstra(matrix, source, itemList, tiles);
    }

    /**
     * Method that converts the path ArrayList into an ArrayList of strings that are the corresponding directional
     * instructions. This is used to instruct the movement of the Smart Thief.
     * @param path the ArrayList of the path the Smart Thief is to take.
     * @param tiles The ArrayList of all tiles in the level.
     * @return returns the direction ArrayList.
     */
    public ArrayList<String> getDirections(ArrayList<Integer> path, ArrayList<Tile> tiles) {
        ArrayList<String> directions = new ArrayList<>();
        for(int i = 0; i + 1 < path.size(); i++) {
            int iX = tiles.get(path.get(i)).getX();
            int iY = tiles.get(path.get(i)).getY();
            int iX2 = tiles.get(path.get(i + 1)).getX();
            int iY2 = tiles.get(path.get(i + 1)).getY();

            if (iX2 > iX && iY == iY2) {
                directions.add("x");
            } else if (iX2 == iX && iY < iY2) {
                directions.add("y");
            } else if (iX2 < iX && iY == iY2) {
                directions.add("-x");
            } else if (iX2 == iX && iY > iY2) {
                directions.add("-y");
            }
        }
        return directions;
    }

    /**
     *Sets the current Matrix from an inputted matrix.
     * @param matrix the input matrix.
     */
    public void setMatrix(int[][] matrix) {
        this.matrix = matrix;
    }

    /**
     *Takes in the Smart Thief's current location and then converts that into the corresponding overall index value.
     * @param stX Current X position of the Smart Thief.
     * @param stY Current Y position of the Smart Thief.
     * @param maxWidth The width of the board.
     */
    private void setIndex(int stX, int stY, int maxWidth) {
        currentIndex = stY * maxWidth + stX;
    }

    /**
     * Returns the current index value of the Smart Thief.
     * @return returns currentIndex.
     */
    public int getIndex() {
        return currentIndex;
    }

    /**
     * Returns the current level of the Smart Thief
     * @return level
     */
    public int getLevel() {
        return level;
    }
}