import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;


/**
 * @author Sandie, Lincoln
 * @version 1.03
 */
public class PlayerProfileReader {

    /**
     * Reads the player profile file
     */
    public static void readFile() {
        try {
            File file = new File("src/PlayerProfiles.txt");
            Scanner reader = new Scanner(file);
            readProfileFile(reader);
        } catch (IOException e) {
            System.out.println("Could not find src/PlayerProfiles.txt");
            System.exit(0);

        }
    }

    /**
     * Takes a scanner and reads through the file and populating player profiles
     * @param reader the scanner to read the file
     * @throws IOException if file could not be found
     */
    public static void readProfileFile(Scanner reader) throws IOException {
        ArrayList<PlayerProfile> allProfiles = new ArrayList<>();
        while(reader.hasNext()){
            //System.out.println(reader.next() + reader.next() + (reader.next()));
            allProfiles.add(new PlayerProfile(reader.next(), reader.next(), reader.nextInt()));
        }
        reader.close();
        PlayerProfile.setAllPlayerProfiles(allProfiles);

    }

    /**
     * Rewrites the player profile file with a given list of player profiles
     * @param allProfiles A list of all the player profiles
     */
    public static void reWrite(ArrayList<PlayerProfile> allProfiles) {
        try {
            File file = new File("src/PlayerProfiles.txt");
            FileOutputStream fos = new FileOutputStream(file);

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
            for (int i = 0; i < PlayerProfile.getAllPlayerProfiles().size(); i++) {
                bw.write(allProfiles.get(i).getPlayerUsername() + " "
                        + allProfiles.get(i).getPlayerPassword() + " " + allProfiles.get(i).getMaxLevel());
                bw.newLine();
            }
            bw.close();
        } catch (IOException e) {
            System.out.println("Could not find src/PlayerProfiles.txt");
            System.exit(0);

        }
    }
}
