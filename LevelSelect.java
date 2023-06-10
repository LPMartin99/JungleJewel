import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * @author Sandie, James
 * @version 1.0
 * Shows a GUI where the User can select levels.
 * Does this while also restricting the user to only the Levels they have completed and the next Level.
 */
public class LevelSelect {
    private final Stage STAGE;
    private final int TOTAL_LEVELS = 3;
    final int PREF_WIDTH = 900;
    final int PREF_HEIGHT = 600;
    private int levelWidth = (PREF_WIDTH/TOTAL_LEVELS - 10);
    private static int maxLevel = 1;

    /**
     * Constructs a Level Select Window
     * @param stage The stage that needs to be shown.
     * @param maxLevelUnlocked The possible maximum Level a User can play.
     */
    public LevelSelect(Stage stage, int maxLevelUnlocked) {
        this.STAGE = stage;
        if (PlayerProfile.getAllPlayerProfiles() == null){
            PlayerProfileReader.readFile();
        }
        if (PlayerProfile.findLoggedIn() != null){
            maxLevel = PlayerProfile.findLoggedIn().getMaxLevel();
        }else {
            if (maxLevelUnlocked > maxLevel)
            maxLevel = maxLevelUnlocked;
        }
        buildUI();
    }


    /**
     * Populates the Level Select window with controls=
     */
    private void buildUI(){
        BorderPane root = new BorderPane();
        root.getStylesheets().add("LevelSelect.css");
        STAGE.setTitle("Jungle Jewel - Level Select");

        HBox title = new HBox();
        title.setAlignment(Pos.CENTER);
        Label titleText = new Label("Level Select");
        title.getChildren().add(titleText);

        Insets insets = new Insets(10,3,10,3);

        VBox level1 = new VBox();
        final int SPACING = (3);
        level1.setSpacing(SPACING);
        level1.setPadding(insets);
        level1.setAlignment(Pos.CENTER);
        Button level1Select = new Button("Level 1");
        ImageView level1Png = new ImageView(new Image("images/level1.png"));
        level1Png.setPreserveRatio(true);
        level1Png.setFitWidth(levelWidth);
        level1.getChildren().add(level1Png);
        level1.getChildren().add(level1Select);

        VBox level2 = new VBox();
        level2.setSpacing(SPACING);
        level2.setPadding(insets);
        level2.setAlignment(Pos.CENTER);
        Button level2Select = new Button("Level 2");
        if (maxLevel < 2){
            level2Select.setDisable(true);
        }
        ImageView level2Png = new ImageView(new Image("images/level2.png"));
        level2Png.setPreserveRatio(true);
        level2Png.setFitWidth(levelWidth);
        level2.getChildren().add(level2Png);
        level2.getChildren().add(level2Select);

        VBox level3 = new VBox();
        level3.setSpacing(SPACING);
        level3.setPadding(insets);
        level3.setAlignment(Pos.CENTER);
        Button level3Select = new Button("Level 3");
        if (maxLevel < 3){
            level3Select.setDisable(true);
        }
        ImageView level3Png = new ImageView(new Image("images/level3.png"));
        level3Png.setPreserveRatio(true);
        level3Png.setFitWidth(levelWidth);
        level3.getChildren().add(level3Png);
        level3.getChildren().add(level3Select);

        VBox mainMenu = new VBox();
        mainMenu.setSpacing(SPACING);
        mainMenu.setAlignment(Pos.CENTER);
        Button mainMenuSelect = new Button("Main Menu");
        mainMenu.getChildren().add(mainMenuSelect);

        GridPane selectionOptions = new GridPane();
        final int VGAP = 80;
        selectionOptions.add(level1,1,0);
        selectionOptions.add(level2,2,0);
        selectionOptions.add(level3,3,0);
        selectionOptions.add(mainMenu,2,1);
        selectionOptions.setVgap(VGAP);

        root.setPrefSize(PREF_WIDTH,PREF_HEIGHT);
        root.setTop(title);
        root.setCenter(selectionOptions);
        Scene scene = new Scene(root);
        STAGE.setScene(scene);
        STAGE.show();
        STAGE.centerOnScreen();

        mainMenuSelect.setOnAction(e -> mainMenuSelectScreen());
        level1Select.setOnAction(e -> loadLevel1());
        level2Select.setOnAction(e -> loadLevel2());
        level3Select.setOnAction(e -> loadLevel3());
    }

    /**
     * Loads Level 1 and displays the Board
     */
    private void loadLevel1(){
        Level level1 = LevelReader.readFile("src/Level1.txt");
        new Board(STAGE,level1);
    }

    /**
     * Loads Level 2 and displays the Board
     */
    private void loadLevel2(){
        Level level2 = LevelReader.readFile("src/Level2.txt");
        new Board(STAGE,level2);
    }

    /**
     * Loads Level 3 and displays the Board
     */
    private void loadLevel3(){
        Level level3 = LevelReader.readFile("src/Level3.txt");
        new Board(STAGE,level3);
    }

    /**
     * Takes the User back to the Main Menu screen.
     */
    private void mainMenuSelectScreen(){
        new MainMenu(STAGE);
    }

}
