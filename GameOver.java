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
 * This class is used to display a game over screen
 */

public class GameOver {
    private Stage stage;
    private VBox root;
    private Scene scene;
    private int currentLevel;
    /**
     * This creates the Game over page, with a stage and a current level
     * @param stage
     * @param currentLevel
     */
    public GameOver(Stage stage,int currentLevel){
        this.stage = stage;
        this.currentLevel = currentLevel;
        buildUI();
    }
    /**
     * This method is used to build the screen that is displayed when the user loses the game
     */
    private void buildUI(){
        root = new VBox();
        root.setSpacing(5);

        Label title = new Label("Game Over!!");
        Button retryLevel = new Button("Play again");
        Button levelSelect = new Button("Select Level");
        Button mainMenu = new Button("Main Menu");

        root.getChildren().addAll(title,retryLevel,levelSelect,mainMenu);
        root.setAlignment(Pos.CENTER);
        root.setPrefSize(900,600);

        scene = new Scene(root);
        root.getStylesheets().add("gameOver.css");
        stage.setTitle("Jungle Jewel - Game Over!");
        stage.getIcons().add(new Image("images/diamondicon.png"));
        stage.setScene(scene);
        stage.setMinHeight(400);
        stage.setMinWidth(550);
        stage.setResizable(false);
        stage.show();

        mainMenu.setOnAction(e -> mainMenuSelectScreen());
        levelSelect.setOnAction(e -> levelSelectScreen());
        retryLevel.setOnAction(e -> retryLevelSelectScreen());
    }
    /**
     * This method allows you to go back to the level select screen
     */
    private void retryLevelSelectScreen() {
        Level level = LevelReader.readFile("src/Level"+ currentLevel +".txt");
        new Board(stage,level);
    }
    /**
     * This allows the user to go back to the level select screen
     */
    private void levelSelectScreen() {
        new LevelSelect(stage, currentLevel);
    }
    /**
     * This allows the user to go back to the main menu screen
     */
    private void mainMenuSelectScreen(){
        new MainMenu(stage);
    }
}