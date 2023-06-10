import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.scene.image.Image;
/**
 * @author Sandie, James
 * @version 1.2
 * This class creates a main menu screen
 */
public class MainMenu {
    private final Stage STAGE;
    private Motd newMessage = new Motd();
    private PlayerProfile profileLoggedIn;

    /**
     * Creates a main screen.
     * @param stage
     */
    public MainMenu(Stage stage) {
        this.STAGE = stage;
        profileLoggedIn = null;
        buildUI();
    }

    /**
     * Adds the necessary controls onto the screen.
     */
    private void buildUI() {
        VBox root = new VBox();
        root.setSpacing(5);

        Label title = new Label("Jungle Jewel");

        Button levelSelect = new Button("Play");
        Button logIn = new Button("Log in");

        Text mOTD = new Text();
        mOTD.setFill(Color.WHITESMOKE);
        mOTD.setStyle("-fx-font: 20 Castellar");
        mOTD.setTextAlignment(TextAlignment.CENTER);
        mOTD.setText(Motd.getMotd());
        mOTD.setWrappingWidth(400);
        root.getChildren().addAll(title,levelSelect,logIn,mOTD);
        root.setAlignment(Pos.CENTER);
        root.setPrefSize(900,600);

        Scene scene = new Scene(root);
        root.getStylesheets().add("menu.css");
        STAGE.setTitle("Jungle Jewel - Main Menu");
        STAGE.getIcons().add(new Image("images/diamondicon.png"));
        STAGE.setScene(scene);
        STAGE.setMinHeight(400);
        STAGE.setMinWidth(550);
        STAGE.setResizable(false);
        STAGE.show();

        levelSelect.setOnAction(e -> loadLevelSelectScreen());
        logIn.setOnAction((e -> loadLoginScreen()));

    }

    /**
     * Loads the level select screen.
     */
    private void loadLevelSelectScreen(){
        if (profileLoggedIn == null){
            new LevelSelect(STAGE,1);
        }
    }

    /**
     * Loads the login screen
     */
    private void loadLoginScreen(){
        new LogIn(STAGE);
    }

}