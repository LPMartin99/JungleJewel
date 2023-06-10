import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

/**
 * @author Sandie
 * @version 1.2
 * A class to represent a Player Profile.
 */
public class PlayerProfile {
    private static ArrayList<PlayerProfile> allPlayerProfiles;
    private String playerUsername;
    private String playerPassword;
    private boolean isLoggedin;
    private int maxLevelUnlocked;

    /**
     * Creates a Player Profile
     * @param name Name of the Player
     * @param playerPassword The player's password.
     * @param maxLevel The max Level they have reached.
     */
    public PlayerProfile(String name,String playerPassword, int maxLevel){
        this.playerUsername = name;
        this.playerPassword = playerPassword;
        this.maxLevelUnlocked = maxLevel;
        this.isLoggedin = false;
    }


    /**
     * Finds a Player Profile using a given Username;
     * @param username Profile name to search for
     * @return The player profile associated with the name.
     */
    public static PlayerProfile getProfileOf (String username){
        PlayerProfile associatedProfile = null;
        for (PlayerProfile p : allPlayerProfiles){
            if(username.equalsIgnoreCase(p.getPlayerUsername())){
                associatedProfile = p;
                return associatedProfile;
            }
        }
        return associatedProfile;
    }

    /**
     * Updates the list of overall Player Profiles
     * @param allPlayerProfiles The new list of all Player Profiles
     */
    public static void setAllPlayerProfiles(ArrayList<PlayerProfile> allPlayerProfiles) {
        PlayerProfile.allPlayerProfiles = allPlayerProfiles;
    }

    /**
     * Returns a list of all player profiles.
     * @return ArrayList of Player Profiles
     */
    public static ArrayList<PlayerProfile> getAllPlayerProfiles() {
        return allPlayerProfiles;
    }

    /**
     * Removes a given Player Profile from the Overall list
     * @param profileToRemove The profile to remove.
     */
    public static void removePlayerProfile(PlayerProfile profileToRemove){
        allPlayerProfiles.remove(profileToRemove);
    }

    /**
     * Finds the Player Profile that is currently logged in.
     * @return Returns the logged in profile.
     */
    public static PlayerProfile findLoggedIn(){
        PlayerProfile loggedIn = null;
        for (PlayerProfile p : allPlayerProfiles){
            if(p.isLoggedin){
                loggedIn = p;
            }
        }
        return loggedIn;
    }

    /**
     * Sets a Profile to be logged in.
     * @param loggedin True or False
     */
    public void setLoggedin(boolean loggedin) {
        isLoggedin = loggedin;
    }

    /**
     * Returns the max level of a Profile
     * @return max level
     */
    public int getMaxLevel(){
        return maxLevelUnlocked;
    }

    /**
     * Returns a Profile's username
     * @return username
     */
    public String getPlayerUsername() {
        return playerUsername;
    }

    /**
     * Returns a Profile's password
     * @return password
     */
    public String getPlayerPassword() {
        return playerPassword;
    }

    /**
     * Sets the max Level unlocked to the level number given.
     * @param maxLevelUnlocked The Level to Unlock
     */
    public void setMaxLevelUnlocked(int maxLevelUnlocked) {
        this.maxLevelUnlocked = maxLevelUnlocked;
    }
}
