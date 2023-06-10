import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author Mai Pitson
 * This class gets the message of the day though decrypting a puzzle
 * that is received through a HTTP GET request to the "CS-230: Message of the Day API
 * @version 1.1
 */
public class Motd {
    private static final String puzzleURL = "http://cswebcat.swansea.ac.uk/puzzle";
    private static final String messageURL = "http://cswebcat.swansea.ac.uk/message?solution=";

    /**
     * HTTP GET request
     * @param request
     * @return the result of the GET request
     * @throws IOException
     */
    private static String GetRequest(String request) throws IOException{
        String output;
        URL url = new URL(request);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        int httpResponseCode = con.getResponseCode();
        if (httpResponseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            output = response.toString();
            return output;
        } else {
            return null;
        }
    }

    /**
     * The method to perform the decryption of the message
     * @param encryptedCode  The String that needs to be decrypted to get the message of the day
     * @return the decrypted string of the message of the day
     */
    private static String solvePuzzle(String encryptedCode) {
        int length = encryptedCode.length();
        char[] puzzleArray = encryptedCode.toCharArray();
        int count=0;
        while (count<length) {
            int shift=count+1;
            char letter = encryptedCode.charAt(count);
            if (count % 2 == 0) {
                if ((letter-shift)<65) {
                    puzzleArray[count] = (char)((letter-shift)+26);
                } else {
                    puzzleArray[count] = (char) (letter - shift);
                }
                count+=1;
            } else {
                if ((letter+shift)>90) {
                    puzzleArray[count] = (char)((letter+shift)-26);
                } else {
                    puzzleArray[count] = (char) (letter + shift);
                }
                count+=1;
            }
        }
        String deciphered = new String(puzzleArray);
        String toAppend = deciphered + "CS-230";
        int front = toAppend.length();
        String decrypted = (front) + toAppend;
        return decrypted;
    }

    /**
     * Gets the message of the day
     * @return If the decryption was successful it will return the message of the day
     * If the decryption was unsuccessful, then it will return a string saying "INCORRECT_SOLUTION"
     *If an IO exception occurs, then it will return a string saying "IOException while creating message of the day"
     */
    public static String getMotd() {
        try {
            String puzzle = GetRequest(puzzleURL);
            String solved = solvePuzzle(puzzle);
            String message = GetRequest(messageURL+solved);
            if (message=="INCORRECT_SOLUTION"){
                return ("INCORRECT_SOLUTION");
            } else {
                return "\n" + message;
            }
        } catch (IOException e) {
            return("IOException creating the message of the day");
        }
    }
}