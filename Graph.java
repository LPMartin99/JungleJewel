import java.util.ArrayList;

/**
 * @author Lincoln
 * @version 1.4
 * This class handles the generation and management of the adjacency matrix upon which path finding is performed.
 */
public class Graph {

    private final int NO_PARENT = -1;
    ArrayList<Tile> tiles;
    Tile currentTile;
    int[][] matrix;
    int size;
    int maxHeight;
    int maxWidth;
    String currentLevel;
    ArrayList<Integer> vertexPath = new ArrayList<Integer>();
    ArrayList<Integer> itemNodes = new ArrayList<Integer>();
    Level level;

    /**
     * Constructor for Graph
     * @param level takes in the level data to generate the appropriate adjacency matrix
     */
    public Graph(Level level){
        this.level = level;
        this.tiles = Tile.getAllLevelTiles();
        this.maxWidth = level.getMaxWidth();
        this.maxHeight = level.getMaxHeight();
        this.size = maxWidth * maxHeight;
        this.currentLevel = Integer.toString(level.getLevelNumber());
        this.matrix = new int[size][size];
    }

    /**
     * Manually encodes the level in adjacency matrix form
     */
    public void fillMatrix() {

        String[] checkOrder = {"x", "y", "-x", "-y"};
        boolean moveAvailable = false;
        int countX;
        int countY;
        Tile tileToCheck;
        Tile tileToAdd = null;
        ArrayList<Character> tileColours;

        for (int i = 0; i < maxHeight; i++) {
            for (int j = 0; j < maxWidth; j++) {
                ArrayList<Character> currentTileColours = new ArrayList<>(Tile.getTile(j,i).getColours());


                for (String k: checkOrder) {
                    switch(k) {
                        case "x":
                            countX = j + 1;
                            int nodeCheck = i*maxWidth+countX;
                            //Loops through the row until a move is found or until we reach the last tile on the board
                            while ((countX < maxWidth) && (!moveAvailable)) {
                                tileToCheck = Tile.getTile(countX,i);
                                tileColours = new ArrayList<>(tileToCheck.getColours());
                                for (Character colour : currentTileColours) {
                                    if (tileColours.contains(colour)) {
                                        moveAvailable = true;
                                        tileToAdd = tileToCheck;
                                    }
                                }
                                countX++;
                            }
                            if (moveAvailable && isMoveValid(tileToAdd)) {
                                int currentNode = i * maxWidth + j;
                                int nodeToAdd = (tileToAdd.getY() * maxWidth + tileToAdd.getX());
                                addEdgeOne(currentNode, nodeToAdd);
                            }
                            moveAvailable = false;
                            break;
                        case "y":
                            countY = i + 1;
                            //Loops through the row until a move is found or until we reach the last tile on the board
                            while ((countY < maxHeight) && (!moveAvailable)) {
                                tileToCheck = Tile.getTile(j,countY);
                                tileColours = new ArrayList<>(tileToCheck.getColours());
                                for (Character colour : currentTileColours) {
                                    if (tileColours.contains(colour)) {
                                        moveAvailable = true;
                                        tileToAdd = tileToCheck;
                                    }
                                }
                                countY++;
                            }
                            if (moveAvailable && isMoveValid(tileToAdd)) {
                                int currentNode = i * maxWidth + j;
                                int nodeToAdd = (tileToAdd.getY() * maxWidth + tileToAdd.getX());
                                addEdgeOne(currentNode, nodeToAdd);
                            }
                            moveAvailable = false;
                            break;
                        case "-x":
                            countX = j - 1;
                            //Loops through the row until a move is found or until we reach the last tile on the board
                            while ((countX > -1) && (!moveAvailable)) {
                                tileToCheck = Tile.getTile(countX,i);
                                tileColours = new ArrayList<>(tileToCheck.getColours());
                                for (Character colour : currentTileColours) {
                                    if (tileColours.contains(colour)) {
                                        moveAvailable = true;
                                        tileToAdd = tileToCheck;
                                    }
                                }
                                countX--;
                            }
                            if (moveAvailable && isMoveValid(tileToAdd)) {
                                int currentNode = i * maxWidth + j;
                                int nodeToAdd = (tileToAdd.getY() * maxWidth + tileToAdd.getX());
                                addEdgeOne(currentNode, nodeToAdd);
                            }
                            moveAvailable = false;
                            break;
                        case "-y":
                            countY = i - 1;
                            int nodeChecked = (countY*maxWidth + i);
                            //Loops through the row until a move is found or until we reach the last tile on the board
                            while ((countY > -1) && (!moveAvailable)) {
                                tileToCheck = Tile.getTile(j,countY);
                                tileColours = new ArrayList<>(tileToCheck.getColours());
                                for (Character colour : currentTileColours) {
                                    if (tileColours.contains(colour)) {
                                        moveAvailable = true;
                                        tileToAdd = tileToCheck;
                                    }
                                }
                                countY--;
                            }
                            if (moveAvailable && isMoveValid(tileToAdd)) {
                                int currentNode = i * maxWidth + j;
                                int nodeToAdd = (tileToAdd.getY() * maxWidth + tileToAdd.getX());
                                addEdgeOne(currentNode, nodeToAdd);
                            }
                            moveAvailable = false;
                            break;
                    }
                }
            }
        }

        /*switch(currentLevel) {
            case "1":
                addEdge(0,1);
                addEdge(0,13);
                //addEdge(1,2);
                addEdge(1,14);
                //addEdge(2,6);
                //addEdge(2,15);
                addEdge(3,4);
                addEdge(3,29);
                addEdge(4,5);
                addEdge(4,17);
                addEdge(5,7);
                addEdge(5,18);
                //addEdge(6,10);
                //addEdgeOne(6,45);
                addEdge(6,45);
                addEdge(7,8);
                addEdge(7,20);
                addEdge(8,9);
                addEdge(8,21);
                addEdge(9,37);
                //addEdge(10,11);
                //addEdge(10,23);
                addEdge(11,12);
                addEdge(12,25);
                addEdge(13,14);
                addEdge(13,26);
                addEdge(14,16);
                //addEdge(15,23);
                addEdge(15,28);
                addEdge(16,19);
                addEdge(16,94);
                addEdge(17,18);
                addEdge(17,30);
                addEdge(18,20);
                addEdge(18,31);
                addEdge(19,22);
                addEdge(19,97);
                addEdge(20,21);
                addEdge(20,33);
                addEdge(21,34);
                addEdge(22,24);
                addEdge(22,100);
                //addEdge(23,36);
                addEdge(24,25);
                addEdge(24,37);
                addEdge(25,38);
                addEdge(26,27);
                addEdge(26,39);
                addEdge(27,37);
                addEdge(27,40);
                addEdge(28,36);
                addEdge(28,41);
                addEdge(29,30);
                addEdge(29,42);
                addEdge(30,31);
                addEdge(30,43);
                addEdge(31,32);
                addEdge(31,44);
                addEdge(32,33);
                addEdge(32,45);
                addEdge(33,34);
                addEdge(33,46);
                addEdge(34,35);
                addEdge(34,47);
                addEdge(35,48);
                addEdge(36,49);
                addEdge(37,38);
                addEdge(37,50);
                addEdge(38,51);
                addEdge(39,40);
                addEdge(39,52);
                addEdge(40,50);
                addEdge(40,53);
                addEdge(41,49);
                addEdge(41,54);
                addEdge(42,43);
                addEdge(42,68);
                addEdge(43,44);
                addEdge(44,45);
                addEdge(45,46);
                addEdge(45,58);
                addEdge(46,47);
                addEdge(47,48);
                addEdge(48,74);
                addEdge(49,62);
                addEdge(50,51);
                addEdge(50,63);
                addEdge(51,64);
                addEdge(52,53);
                addEdge(52,65);
                addEdge(53,63);
                addEdge(53,66);
                addEdge(54,55);
                addEdge(54,67);
                addEdge(55,56);
                addEdge(55,81);
                addEdge(56,57);
                addEdge(56,69);
                addEdge(57,58);
                addEdge(57,70);
                addEdge(58,59);
                addEdge(58,71);
                addEdge(59,60);
                addEdge(59,72);
                addEdge(60,61);
                addEdge(60,73);
                addEdge(61,62);
                addEdge(61,87);
                //addEdge(62,75);
                addEdge(63,64);
                addEdge(63,76);
                addEdge(64,77);
                addEdge(65,66);
                addEdge(66,68);
                addEdge(66,79);
                addEdge(67,69);
                addEdge(67,80);
                addEdge(68,74);
                addEdge(69,70);
                addEdge(69,82);
                addEdge(70,71);
                addEdge(70,83);
                addEdge(71,72);
                addEdge(71,84);
                addEdge(72,73);
                addEdge(72,85);
                //addEdge(73,75);
                addEdge(73,86);
                addEdge(74,76);
                //addEdge(75,88);
                addEdge(76,77);
                addEdge(76,89);
                addEdge(77,90);
                addEdge(78,79);
                addEdge(78,91);
                addEdge(79,89);
                addEdge(79,92);
                addEdge(80,81);
                addEdge(81,82);
                addEdge(82,83);
                addEdge(83,84);
                addEdge(84,85);
                addEdge(85,86);
                addEdge(86,87);
                addEdge(87,88);
                addEdge(89,90);
                addEdge(89,102);
                addEdge(90,103);
                addEdge(91,92);
                addEdge(92,93);
                addEdge(93,94);
                addEdge(94,95);
                addEdge(95,96);
                addEdge(96,97);
                addEdge(97,98);
                addEdge(98,99);
                addEdge(99,100);
                addEdge(100,101);
                addEdge(101,102);
                addEdge(102,103);
                break;
            case "2":
                addEdge(0,1);
                addEdge(0,13);
                addEdge(1,2);
                addEdge(1,92);
                addEdge(2,3);
                addEdge(2,54);
                addEdge(3,4);
                addEdge(3,55);
                addEdge(4,5);
                addEdge(4,56);
                addEdge(5,6);
                addEdge(5,57);
                addEdge(6,7);
                addEdge(6,19);
                addEdge(7,8);
                addEdge(7,59);
                addEdge(8,9);
                addEdge(8,60);
                addEdge(9,10);
                addEdge(9,61);
                addEdge(10,11);
                addEdge(10,62);
                addEdge(11,12);
                addEdge(11,102);
                addEdge(12,25);
                addEdge(13,14);
                addEdge(13,26);
                addEdge(14,16);
                addEdge(14,40);
                addEdge(15,17);
                addEdge(15,28);
                addEdge(16,18);
                addEdge(16,42);
                addEdge(17,19);
                addEdge(17,30);
                addEdge(18,20);
                addEdge(18,44);
                addEdge(19,21);
                addEdge(19,32);
                addEdge(20,22);
                addEdge(20,46);
                addEdge(21,23);
                addEdge(21,34);
                addEdge(22,24);
                addEdge(22,48);
                addEdge(23,36);
                addEdge(24,25);
                addEdge(24,50);
                addEdge(25,38);
                addEdge(26,38);
                addEdge(26,49);
                addEdge(27,29);
                addEdge(27,53);
                addEdge(28,41);
                addEdge(28,30);
                addEdge(29,31);
                addEdge(29,81);
                addEdge(30,32);
                addEdge(30,43);
                addEdge(31,33);
                addEdge(31,83);
                addEdge(32,33);
                addEdge(32,45);
                addEdge(33,35);
                addEdge(33,85);
                addEdge(34,36);
                addEdge(34,47);
                addEdge(35,37);
                addEdge(35,87);
                addEdge(36,49);
                addEdge(37,63);
                addEdge(38,51);
                addEdge(39,51);
                addEdge(39,52);
                addEdge(40,42);
                addEdge(40,66);
                addEdge(41,43);
                addEdge(41,67);
                addEdge(42,44);
                addEdge(42,68);
                addEdge(43,45);
                addEdge(43,69);
                addEdge(44,46);
                addEdge(44,70);
                addEdge(45,47);
                addEdge(45,71);
                addEdge(46,48);
                addEdge(46,72);
                addEdge(47,49);
                addEdge(47,73);
                addEdge(48,50);
                addEdge(48,74);
                addEdge(48,74);
                addEdge(49,75);
                addEdge(50,76);
                addEdge(51,64);
                addEdge(52,65);
                addEdge(52,54);
                addEdge(53,63);
                addEdge(53,79);
                addEdge(54,55);
                addEdge(54,93);
                addEdge(55,56);
                addEdge(55,94);
                addEdge(56,57);
                addEdge(56,95);
                addEdge(57,58);
                addEdge(57,96);
                addEdgeOne(58,97);
                addEdgeOne(58,6);
                addEdge(58,59);
                addEdge(59,60);
                addEdge(59,98);
                addEdge(60,61);
                addEdge(60,99);
                addEdge(61,62);
                addEdge(61,100);
                addEdge(62,64);
                addEdge(62,101);
                addEdge(63,89);
                addEdge(64,77);
                addEdge(65,77);
                addEdge(65,78);
                addEdge(66,68);
                addEdge(67,69);
                addEdge(67,80);
                addEdge(68,70);
                addEdge(69,71);
                addEdge(69,82);
                addEdge(70,72);
                addEdge(71,73);
                addEdge(71,84);
                addEdge(72,74);
                addEdge(73,75);
                addEdge(73,86);
                addEdge(74,76);
                addEdge(75,88);
                addEdge(77,90);
                addEdge(78,79);
                addEdge(78,91);
                addEdge(79,81);
                addEdge(80,82);
                addEdge(81,83);
                addEdge(82,84);
                addEdge(83,85);
                addEdge(84,86);
                addEdge(84,97);
                addEdge(85,87);
                addEdge(86,88);
                addEdge(89,90);
                addEdge(90,103);
                addEdge(91,92);
                addEdge(92,93);
                addEdge(93,94);
                addEdge(94,95);
                addEdge(95,96);
                addEdge(96,97);
                addEdge(97,98);
                addEdge(98,99);
                addEdge(99,100);
                addEdge(100,101);
                addEdge(101,102);
                addEdge(102,103);
            case "3":
                addEdge(0,1);
                addEdge(0,13);
                addEdge(1,2);
                addEdge(1,92);
                addEdge(2,10);
                addEdge(2,15);
                addEdge(3,4);
                addEdge(3,16);
                addEdge(4,5);
                //addEdge(4,46);
                addEdge(5,6);
                addEdge(5,6);
                addEdge(5,57);
                addEdge(6,7);
                addEdge(6,19);
                addEdge(7,8);
                addEdge(7,59);
                addEdge(8,9);
                addEdge(8,60);
                addEdge(9,22);
                addEdge(10,11);
                addEdge(10,23);
                addEdge(11,12);
                addEdge(11,102);
                addEdge(12,25);
                addEdge(13,15);
                addEdge(13,26);
                addEdge(14,16);
                addEdge(14,27);
                addEdge(15,23);
                addEdge(15,28);
                addEdge(16,22);
                addEdge(16,29);
                addEdge(17,18);
                addEdge(17,30);
                addEdge(18,19);
                addEdge(18,31);
                addEdge(19,20);
                addEdge(19,32);
                addEdge(20,21);
                addEdge(20,33);
                addEdge(21,34);
                addEdge(22,24);
                addEdge(22,35);
                addEdge(23,25);
                addEdge(23,36);
                addEdge(24,37);
                addEdge(25,38);
                addEdge(26,28);
                addEdge(26,39);
                addEdge(27,37);
                addEdge(27,30);
                addEdge(28,36);
                addEdge(28,41);
                addEdge(29,35);
                addEdge(29,42);
                addEdge(30,31);
                addEdge(30,43);
                addEdge(31,32);
                addEdge(31,44);
                addEdge(32,33);
                addEdge(32,45);
                addEdge(33,34);
                addEdge(33,46);
                addEdge(34,47);
                addEdge(35,48);
                addEdge(36,38);
                addEdge(36,49);
                addEdge(37,50);
                addEdge(38,51);
                addEdge(39,41);
                addEdge(39,52);
                addEdge(40,50);
                addEdge(40,53);
                addEdge(41,49);
                addEdge(41,54);
                addEdge(42,48);
                addEdge(42,55);
                addEdge(43,44);
                addEdge(44,45);
                addEdge(44,70);
                addEdge(45,46);
                addEdge(45,71);
                addEdge(46,47);
                addEdge(46,72);
                addEdge(48,61);
                addEdge(49,51);
                addEdge(49,62);
                addEdge(50,63);
                addEdge(51,64);
                addEdge(52,54);
                addEdge(52,65);
                addEdge(53,63);
                addEdge(53,66);
                addEdge(54,62);
                addEdge(54,67);
                //addEdge(55,56);
                //addEdge(56,57);
                addEdge(57,58);
                addEdge(58,59);
                addEdgeOne(58,6);
                addEdge(59,60);
                addEdge(60,61);
                addEdge(62,64);
                addEdge(63,76);
                addEdge(64,77);
                addEdge(65,67);
                addEdge(65,78);
                addEdge(66,76);
                addEdge(66,79);
                addEdge(67,68);
                addEdge(67,93);
                addEdge(68,69);
                addEdge(69,70);
                addEdge(69,95);
                addEdge(70,71);
                addEdge(70,96);
                addEdge(71,72);
                addEdge(72,73);
                addEdge(72,98);
                addEdge(73,74);
                addEdge(73,99);
                addEdge(74,75);
                addEdge(75,77);
                addEdge(75,101);
                addEdge(76,89);
                addEdge(77,90);
                addEdge(78,90);
                addEdge(78,91);
                addEdge(79,80);
                addEdge(80,81);
                addEdge(81,82);
                addEdge(81,94);
                addEdge(82,83);
                addEdge(83,84);
                addEdge(85,86);
                addEdge(86,87);
                addEdge(87,88);
                addEdge(87,100);
                addEdge(88,89);
                addEdge(90,103);
                addEdge(91,92);
                addEdge(92,93);
                addEdge(93,95);
                addEdge(94,100);
                addEdge(95,96);
                addEdge(97,98);
                addEdge(98,99);
                addEdge(99,101);
                addEdge(101,102);
                addEdge(102,103);
            break;
            default:
                System.out.println("UNRECOGNISED LEVEL");
                break;
        }*/
    }

    /**
     * Takes in two tile index values and adds an edge connecting the two tiles
     * in both directions.
     * @param t1 the first tile index
     * @param t2 the second tile index
     */
    public void addEdge(int t1, int t2) {

        matrix[t1][t2] = 1;
        matrix[t2][t1] = 1;
    }

    /**
     * Takes in two tile index values and adds an edge connecting the first
     * to the second in the adjacency matrix.
     * @param t1 the first tile index
     * @param t2 the second tile index
     */
    public void addEdgeOne(int t1, int t2) {
        matrix[t1][t2] = 1;
    }

    /**
     * Outputs a text representation of the adjacency matrix for visual checking.
     */
    public void print() {

        System.out.print("  ");
        for(int i = 0; i < tiles.size(); i++) {
            System.out.print(i + " ");
        }
        System.out.println();

        for(int i = 0; i < matrix.length; i++) {
            System.out.print(i + " ");
            for(int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    /**
     * Takes in an input matrix and sets it as the current matrix.
     * @param matrix input matrix
     */
    public void setMatrix(int[][] matrix) {
        this.matrix = matrix;
    }

    /**
     * Getter for the matrix.
     * @return returns the generated adjacency matrix
     */
    public int[][] getMatrix() {
        return matrix;
    }

    /**
     * Getter for the level number.
     * @return returns the integer value for the current level.
     */
    public String getLevelNumber () {
        return currentLevel;
    }

    public int getNodeVal(Tile t) {
        int nodeVal = t.getY()*maxWidth + t.getX();
        return nodeVal;
    }

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

}
