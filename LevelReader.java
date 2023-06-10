import java.io.*;
import java.util.Locale;
import java.util.Scanner;
import java.util.ArrayList;

/**
 * @author James, Jason, Sandie
 * @version 1.3
 * Deals with input/output to Level files.
 */
public class LevelReader {

    /**
     * Takes a file name and establishes a connection with the file
     * @param filename The name of the file you want to access
     * @return Level object
     */
    public static Level readFile(String filename) {
        try {
            File file = new File(filename);
            Scanner reader = new Scanner(file);
            return readLevelFile(reader);
        } catch (IOException e) {
            System.out.println("Couldn't find: " + filename);
            System.exit(0);
            return null;
        }
    }

    /**
     * Takes values from the Level file and populates a Level object.
     * @param reader The Scanner used to read the file
     * @return The created Level Object
     * @throws IOException if the file is not found
     */
    public static Level readLevelFile(Scanner reader) throws IOException{
        ArrayList<NPC> NPCList = new ArrayList<>();
        ArrayList<Tile> tiles = new ArrayList<>();
        ArrayList<Item> items = new ArrayList<>();
        ArrayList<HighScore> highScores = new ArrayList<>();

        int levelNum = reader.nextInt();
        Player player = new Player(reader.nextInt(),reader.nextInt());

        int timerRead = reader.nextInt();
        Timer timer = new Timer(timerRead);

        int width = reader.nextInt();
        int height = reader.nextInt();

        String[] tileColours = new String[width * height];
        for (int i = 0; i < (height * width); i++) {
            tileColours[i] = reader.next();
        }
        int counter = 0;
        for (int k = 0; k < (height); k++) {
            for (int j = 0; j < width; j++) {
                ArrayList<Character> colours = new ArrayList<>();
                for (int p = 0; p < tileColours[counter].length(); p++) {
                    colours.add(tileColours[counter].charAt(p));
                }
                Tile newTile = new Tile(j, k, colours);
                tiles.add(newTile);
                counter++;
            }
        }
        String indicator;
        boolean NPCsAllRead = false;
        while (!NPCsAllRead) {
            indicator = reader.next();
            switch (indicator) {
                case "FlyingAssassin":
                    int[] parrotPos = new int[2];
                    parrotPos[0] = reader.nextInt();
                    parrotPos[1] = reader.nextInt();
                    String parrotDirection = reader.next();
                    NPCList.add(new FlyingAssassin(
                            parrotDirection, parrotPos[0], parrotPos[1]));
                    break;
                case "SmartThief":
                    int[] monkeyPos = new int[3];
                    monkeyPos[0] = reader.nextInt();
                    monkeyPos[1] = reader.nextInt();
                    monkeyPos[2] = reader.nextInt();
                    NPCList.add(new SmartThief(monkeyPos[0], monkeyPos[1], monkeyPos[2]));
                    break;
                case "FloorFollowing":
                    int[] snakePos = new int[2];
                    snakePos[0] = reader.nextInt();
                    snakePos[1] = reader.nextInt();
                    String snakeDirection = reader.next();
                    char colour = reader.next().charAt(0);
                    NPCList.add(new FloorFollowingThief(
                            snakeDirection, snakePos[0], snakePos[1], colour));
                    break;
                case "Door":
                    int[] door = new int[2];
                    door[0] = reader.nextInt();
                    door[1] = reader.nextInt();
                    items.add(new Door(door[0], door[1]));
                    break;
                case "Lever":
                    int[] lever = new int[2];
                    lever[0] = reader.nextInt();
                    lever[1] = reader.nextInt();
                    char leverColour = reader.next().charAt(0);
                    items.add(new Lever(lever[0], lever[1], leverColour));
                    break;
                case "Gate":
                    int[] gate = new int[2];
                    gate[0] = reader.nextInt();
                    gate[1] = reader.nextInt();
                    char gateColour = reader.next().charAt(0);
                    items.add(new Gate(gate[0],gate[1],gateColour));
                    break;
                case "Bomb":
                    int[] bomb = new int[2];
                    bomb[0] = reader.nextInt();
                    bomb[1] = reader.nextInt();
                    items.add(new Bomb(bomb[0],bomb[1]));
                    break;
                case "Clock":
                    int[] hourGlass = new int[2];
                    hourGlass[0] = reader.nextInt();
                    hourGlass[1] = reader.nextInt();
                    items.add(new Clock(hourGlass[0],hourGlass[1]));
                    break;
                case "Diamond":
                    int[] diamond = new int[2];
                    diamond[0] = reader.nextInt();
                    diamond[1] = reader.nextInt();
                    items.add(new Diamond(diamond[0],diamond[1]));
                    break;
                case "Ruby":
                    int[] ruby = new int[2];
                    ruby[0] = reader.nextInt();
                    ruby[1] = reader.nextInt();
                    items.add(new Ruby(ruby[0],ruby[1]));
                    break;
                case "Gold":
                    int[] gold = new int[2];
                    gold[0] = reader.nextInt();
                    gold[1] = reader.nextInt();
                    items.add(new Gold(gold[0],gold[1]));
                    break;
                case "Silver":
                    int[] silver = new int[2];
                    silver[0] = reader.nextInt();
                    silver[1] = reader.nextInt();
                    items.add(new Silver(silver[0],silver[1]));
                    break;
                default:
                    NPCsAllRead = true;
                    break;
            }
        }
        while(reader.hasNextLine()){
            String nextValue = reader.next();
            if(nextValue.equals("null")){
                highScores.add(null);
            }else{
                highScores.add(new HighScore( nextValue,reader.nextInt()));
            }
        }
        reader.close();
        Tile.setAllLevelTiles(tiles);
        Item.setAllLevelItems(items);
        NPC.setAllLevelNPCs(NPCList);
        return new Level(levelNum, timer, width, height, highScores,player);
    }

    /**
     * Rewrites the Level file to contain the updated High Score list
     * @param newHighScores The updated list of High Scores.
     * @param levelNumber The number of the Level associated with the High Scores.
     */
    public static void changeHighscores(ArrayList<HighScore> newHighScores,int levelNumber){
        try {
            FileReader reader = new FileReader(
                    "src/Level" + levelNumber + ".txt");
            BufferedReader br = br = new BufferedReader(reader);
            String line;
            PrintWriter writer
                    = new PrintWriter(new BufferedWriter(
                    new FileWriter(
                            "src/Level" + levelNumber + ".temp")));
            boolean levelDetailsCopied = false;
            while (!levelDetailsCopied) {
                if((line = br.readLine()).equals("done")){
                    levelDetailsCopied = true;
                }
                writer.println(line);
            }
            int count = 0;
            while((line = br.readLine()) != null){
                String newHighScore;
                if(newHighScores.get(count) == null){
                    newHighScore = "null";
                }else{
                    newHighScore = newHighScores.get(count).getUsername()
                            + " " + newHighScores.get(count).getScore();
                }
                if (count == 9){
                    if(!(line.equals(newHighScore))){
                        writer.print(newHighScore);
                    } else {
                        writer.print(line);
                    }
                }else {
                    if(!(line.equals(newHighScore))){
                        writer.println(newHighScore);
                    } else {
                        writer.println(line);
                    }
                }

                count++;
            }
            writer.close();
            reader.close();
            File realName = new File("src/Level" + levelNumber + ".txt");
            realName.delete(); // remove the old file
            new File("src/Level" + levelNumber + ".temp").renameTo(realName);
        } catch(IOException e){
            System.out.println("Could not find "
                    + "src/Level" + levelNumber + ".txt");
            System.exit(0);
        }
    }

    /**
     * Checks is a save file is available.
     * @param levelNumber The Level we are checking for a save file for.
     * @return true if a save file is present for the Level and user logged in, false otherwise
     */
    public static boolean isSaveAvailable(int levelNumber) {
        String username = PlayerProfile.findLoggedIn().getPlayerUsername();
        try {
            File file = new File("src/Level"
                    + levelNumber + username + "Save.txt");
            Scanner in = new Scanner(file);
            in.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * Rewrites/Writes the current Level state to a save file.
     * @param level The Level we are saving the state of.
     */
    public static void saveLevel(Level level) {
        if (isSaveAvailable(level.getLevelNumber())) {
            File oldFile = new File("src/Level" + level.getLevelNumber()
                    + PlayerProfile.findLoggedIn().getPlayerUsername() + "Save.txt");
            oldFile.delete();
        }
        File newSave = new File("src/Level" + level.getLevelNumber()
                + PlayerProfile.findLoggedIn().getPlayerUsername() + "Save.txt");
        try {
            newSave.createNewFile();
            PrintWriter writer =
                    new PrintWriter(new BufferedWriter(new FileWriter(newSave)));
            writer.println(level.getLevelNumber());
            writer.println(level.getPlayer().getPlayerX() + " "
                    + level.getPlayer().getPlayerY());
            writer.println(level.getGameTimer().getSecondsLeft());
            writer.println(level.getMaxWidth() + " " + level.getMaxHeight());
            ArrayList<Tile> allTiles = Tile.getAllLevelTiles();
            for(Tile t : allTiles){
                ArrayList<Character> colours = t.getColours();
                StringBuilder tColours = (new StringBuilder());
                for(Character c : colours){
                    tColours.append(c);
                }
                if(t.getX() == level.getMaxWidth()-1){
                    writer.println(" " +
                            tColours.toString().toUpperCase(Locale.ROOT));
                } else {
                    writer.print(" " +
                            tColours.toString().toUpperCase(Locale.ROOT));
                }
            }
            for(int i = 0; i < level.getMaxHeight(); i++){
                for(int j = 0; i < level.getMaxWidth(); i++){
                    ArrayList<Character> colours =
                            Tile.getTile(i,j).getColours();
                }
                writer.println();
            }
            for(NPC n: NPC.getAllLevelNPCs()){
                if(n instanceof FlyingAssassin){
                    writer.println("FlyingAssassin " + n.getPosX() + " " +
                            n.getPosY() + " "
                            + ((FlyingAssassin) n).getMovementDirection());
                } else if(n instanceof FloorFollowingThief){
                    writer.println("FloorFollowing "
                            + n.getPosX() + " " + n.getPosY() +
                            " " + n.getDirectionFacing() + " "
                            + ((FloorFollowingThief) n).getColour());
                } else {
                    writer.println("SmartThief " + n.getPosX() + " "
                            + n.getPosY() + " " + ((SmartThief) n).getLevel());
                }
            }
            for(Item i: Item.getAllLevelItems()){
                if(i instanceof Gate || i  instanceof Lever){
                    writer.println(i.getClass().getName() + " " +
                            i.getPositionX() + " " + i.getPositionY()
                            + " " + i.getColour());
                } else {
                    writer.println(i.getClass().getName() + " "
                            + i.getPositionX() + " " + i.getPositionY());
                }
            }
            writer.print("done");
            for(HighScore h: level.getHighScores()){
                writer.println();
                if(h != null){
                    writer.print(h.getUsername() + " " + h.getScore());
                }else{
                    writer.print("null");
                }
            }
            writer.close();

        } catch (IOException e) {
            System.out.println("Could not find " + "Level" + level.getLevelNumber()
                    + PlayerProfile.findLoggedIn().getPlayerUsername() + "Save.txt");
            System.exit(0);
        }
    }

    /**
     * Loads a level into the state it was saved
     * @param level The Level to load the save for.
     * @return The Level created from the load.
     */
    public static Level loadLevel(Level level){
        return readFile("src/Level" + level.getLevelNumber()
                + PlayerProfile.findLoggedIn().getPlayerUsername() + "Save.txt");
    }
}