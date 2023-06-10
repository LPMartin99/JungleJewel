import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * @author Mai, James
 * @version 1.0
 * This class represents a Game Over screen when a player loses a level.
 */
public class LosingScreen {
    private final Stage STAGE;
    private final int CURRENT_LEVEL;

    /**
     * This constructs the Game Over screen, taking in the stage and the
     * level number of the level just lost.
     * @param stage The stage to be shown.
     * @param currentLevel The current Level number
     */
    public LosingScreen(Stage stage, int currentLevel){
        this.STAGE = stage;
        this.CURRENT_LEVEL = currentLevel;
        buildUI();
    }

    /**
     * This method sets all the javafx controls for the Game Over window.
     */
    private void buildUI(){
        VBox root = new VBox();
        root.setSpacing(5);

        Label title = new Label("Game Over!!");
        Button retryLevel = new Button("Play again");
        Button levelSelect = new Button("Select Level");
        Button mainMenu = new Button("Main Menu");

        root.getChildren().addAll(title,retryLevel,levelSelect,mainMenu);
        root.setAlignment(Pos.CENTER);
        root.setPrefSize(900,600);

        Scene scene = new Scene(root);
        root.getStylesheets().add("losingScreen.css");
        STAGE.setTitle("Jungle Jewel - Game Over!");
        STAGE.getIcons().add(new Image("images/diamondicon.png"));
        STAGE.setScene(scene);
        STAGE.setMinHeight(400);
        STAGE.setMinWidth(550);
        STAGE.setResizable(false);
        STAGE.show();

        mainMenu.setOnAction(e -> mainMenuSelectScreen());
        levelSelect.setOnAction(e -> levelSelectScreen());
        retryLevel.setOnAction(e -> retryLevel());
    }

    /**
     * This method sets a button to reload the level game board.
     */
    private void retryLevel() {

        Level level = LevelReader.readFile("src/Level"+ CURRENT_LEVEL +".txt");
        new Board(STAGE,level);
    }

    /**
     * This method sets a button to take the user back to the Level Select screen.
     */
    private void levelSelectScreen() {
        new LevelSelect(STAGE, CURRENT_LEVEL);
    }

    /**
     * This method sets a button to take the user back to the main menu.
     */
    private void mainMenuSelectScreen(){
        new MainMenu(STAGE);
    }
}
