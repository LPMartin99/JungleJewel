import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

/**
 * @author Mai, Sandie, James, Lincoln
 * @version 1.3
 * Constructs a log in page to implement profile management.
 */
public class LogIn {
    private final Stage STAGE;

    /**
     * Builds a Log-In page.
     * @param stage Stage to show.
     */
    public LogIn(Stage stage){
        this.STAGE  = stage;
        buildUI();
    }

    /**
     * Builds the javafx controls
     */
    public void buildUI() {
        BorderPane root = new BorderPane();
        GridPane grid = createLogInForm();
        root.getStylesheets().add("LogIn.css");
        STAGE.setTitle("Jungle Jewel - LogIn Set-Up");

        Label headerLabel = new Label("LogIn Set-Up");
        headerLabel.setStyle("-fx-font: 45 Papyrus");
        grid.add(headerLabel,0,0,2,1);
        GridPane.setHalignment(headerLabel,HPos.CENTER);
        GridPane.setMargin(headerLabel, new Insets(20,0,20,0));

        Text instructionLabel = new Text(
                "If you have an account enter your details and click log-in," +
                        " if you don't " + "then enter your details and press " +
                        "create account");
        instructionLabel.setFill(Color.GOLD);
        instructionLabel.setWrappingWidth(400);
        grid.add(instructionLabel,1,2);
        Text nameLabel = new Text("Username: ");
        nameLabel.setStyle("-fx-font: 15 Papyrus");
        nameLabel.setFill(Color.WHITE);
        grid.add(nameLabel,0,3);

        TextField nameField = new TextField();
        nameField.setMaxHeight(40);
        nameField.setMaxWidth(200);
        grid.add(nameField,1,4);

        Text passwordLabel = new Text ("Password: ");
        passwordLabel.setStyle("-fx-font: 15 Papyrus");
        passwordLabel.setFill(Color.WHITE);
        grid.add(passwordLabel,0,5);

        PasswordField passwordField = new PasswordField();
        passwordField.setMaxHeight(40);
        passwordField.setMaxWidth(200);
        grid.add(passwordField,1,6);

        Text guidingMessages = new Text();
        guidingMessages.setWrappingWidth(400);
        grid.add(guidingMessages,5,4);

        Button LogInButton = new Button("Log-In");
        LogInButton.setPrefWidth(100);
        grid.add(LogInButton,0,7,2,1);
        GridPane.setHalignment(LogInButton,HPos.CENTER);
        GridPane.setMargin(LogInButton,new Insets(5,0,5,0));

        Button CreateAccount = new Button("Create Account");
        CreateAccount.setMinWidth(105);
        CreateAccount.setPrefWidth(110);
        grid.add(CreateAccount,0,8,2,1);
        GridPane.setHalignment(CreateAccount,HPos.CENTER);
        GridPane.setMargin(CreateAccount,new Insets(5,0,5,0));

        Button DeleteAccount = new Button("Delete Account");
        DeleteAccount.setMinWidth(105);
        DeleteAccount.setPrefWidth(110);
        grid.add(DeleteAccount,0,9,2,1);
        GridPane.setHalignment(DeleteAccount,HPos.CENTER);
        GridPane.setMargin(DeleteAccount,new Insets(5,0,5,0));

        Button mainMenu = new Button("Main Menu");
        mainMenu.setMinWidth(105);
        mainMenu.setPrefWidth(110);
        grid.add(mainMenu,0,10,2,1);
        GridPane.setHalignment(mainMenu,HPos.CENTER);
        GridPane.setMargin(mainMenu,new Insets(5,0,5,0));

        root.setPrefSize(900,600);
        root.setCenter(grid);
        Scene scene = new Scene(root);
        STAGE.setScene(scene);

        STAGE.show();

        mainMenu.setOnAction(e -> mainMenuSelectScreen());
        CreateAccount.setOnAction(e -> createAccount(nameField.getText(),
                passwordField.getText(),guidingMessages));
        DeleteAccount.setOnAction(e -> deleteAccount(
                nameField.getText(),
                passwordField.getText(),nameField,passwordField,guidingMessages));
        LogInButton.setOnAction(e -> loginToAccount(nameField.getText(),
                passwordField.getText(),guidingMessages));
    }

    /**
     * Creates the form for people to enter log in details
     * @return the gridpane.
     */
    private GridPane createLogInForm(){
        GridPane gridPane = new GridPane();

        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(40,40,40,40));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        ColumnConstraints columnOne = new ColumnConstraints(100,100,Double.MAX_VALUE);
        columnOne.setHalignment(HPos.RIGHT);

        ColumnConstraints columnTwo = new ColumnConstraints(200,200,Double.MAX_VALUE);
        columnTwo.setHgrow(Priority.ALWAYS);

        gridPane.getColumnConstraints().addAll(columnOne,columnTwo);
        return gridPane;
    }

    /**
     * Uses the available MessageDigest library to use the MD5 hashing algorithm to ensure we are not storing passwords
     * in plaintext. Also used when verifying login details.
     * @param password the password inputted by the user
     * @return the hashed password
     */
    public static String generateSHA256(String password)
    {
        try {

            //Sets the MessageDigest to use the MD5 hash
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            // digest() method is called to calculate message digest
            // of an input digest() return array of byte
            byte[] hash = md.digest(password.getBytes());

            StringBuilder hexString = new StringBuilder(2 * hash.length);
            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if(hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        }

        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Creates a User account if possible and loads the Level Select Screen
     * @param givenUsername The username the User entered.
     * @param givenPassword The password the User entered.
     * @param guidingMessages The text box that will display warnings.
     */
    private void createAccount(String givenUsername, String givenPassword, Text guidingMessages) {
        PlayerProfileReader.readFile();
        if ((PlayerProfile.getProfileOf(givenUsername) == null)
                &&(!(givenPassword.trim().length() == 0))){
            PlayerProfile profileToAdd = new PlayerProfile(givenUsername, generateSHA256(givenPassword),1);
            ArrayList<PlayerProfile> currentList = PlayerProfile.getAllPlayerProfiles();
            currentList.add(profileToAdd);
            PlayerProfile.setAllPlayerProfiles(currentList);
            PlayerProfileReader.reWrite(PlayerProfile.getAllPlayerProfiles());
            if (PlayerProfile.findLoggedIn() != null){
                PlayerProfile.findLoggedIn().setLoggedin(false);
            }
            profileToAdd.setLoggedin(true);
            new LevelSelect(STAGE,1);
        } else {
            guidingMessages.setText("This username is taken and or please " +
                    "ensure your password is not just space characters");
        }
    }

    /**
     * Deletes a specified account if exists
     * @param givenUsername The username the User entered.
     * @param givenPassword The password the User entered.
     * @param nameField The field for entering a username.
     * @param passField The field for entering a password.
     * @param guidingMessages The text box that will display warnings.
     */
    private void deleteAccount(String givenUsername,String givenPassword,TextField nameField, PasswordField passField,Text guidingMessages){
        PlayerProfileReader.readFile();
        PlayerProfile profileToRemove = null;
        for (PlayerProfile p : PlayerProfile.getAllPlayerProfiles()){
            if (givenUsername.equals(p.getPlayerUsername()) && generateSHA256(givenPassword).equals(p.getPlayerPassword())){
                profileToRemove = p;
            }
        }
        if (profileToRemove != null){
            PlayerProfile.removePlayerProfile(profileToRemove);
            guidingMessages.setText("Successfully Deleted");

        }else {
            guidingMessages.setText("No such account exists, make sure your username and password are correct.");
        }
        PlayerProfileReader.reWrite(PlayerProfile.getAllPlayerProfiles());
        nameField.setText("");
        passField.setText("");

    }

    /**
     * Logs into the given account if possible
     * @param givenUsername The username the User entered.
     * @param givenPassword The password the User entered.
     * @param guidingMessages The text box that will display warnings.
     */
    private void loginToAccount(String givenUsername, String givenPassword,Text guidingMessages){
        PlayerProfileReader.readFile();
        PlayerProfile profileToAccess = null;
        for (PlayerProfile p : PlayerProfile.getAllPlayerProfiles()){
            if (givenUsername.equals(p.getPlayerUsername())){
                profileToAccess = p;
            }
        }
        if (profileToAccess == null){
            guidingMessages.setText("No such account exists");
        }else {
            if (generateSHA256(givenPassword).equals(profileToAccess.getPlayerPassword())){
                if (PlayerProfile.findLoggedIn() != null){
                    PlayerProfile.findLoggedIn().setLoggedin(false);
                }
                profileToAccess.setLoggedin(true);
                new LevelSelect(STAGE, profileToAccess.getMaxLevel());
            }else {
                guidingMessages.setText("Ensure your password is correct.");
            }
        }
    }

    /**
     * Shows the main menu
     */
    private void mainMenuSelectScreen(){
        new MainMenu(STAGE);
    }
}