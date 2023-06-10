import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import java.util.ArrayList;

/**
 * @author Mai, James, Jason, Sandie
 * @version 1.3
 * This class creates the Winning Screen.
 * This class also displays the highscore table.
 */
public class WinningScreen {
    private final Stage STAGE;
    private final int END_SCORE;
    private final int LEVEL_NUMBER;
    private ArrayList<HighScore> highScores;

    /**
     * Constructs a Winning Screen
     * @param stage The stage to show.
     * @param timeLeft The time left when the game ended
     * @param endScore The end score of the player
     * @param levelNumber The number of the level just completed
     * @param highScores The highscores list of the level
     */
    public WinningScreen(Stage stage, int timeLeft, int endScore, int levelNumber, ArrayList<HighScore> highScores){
        this.STAGE = stage;
        this.END_SCORE = endScore * (timeLeft % 10);
        this.LEVEL_NUMBER = levelNumber;
        this.highScores = highScores;
        buildUI();
    }

    /**
     * This method creates the winning screen
     * This method also puts the highscore onto the screen
     */
    private void buildUI(){
        if(PlayerProfile.findLoggedIn() != null){
            if(LEVEL_NUMBER == PlayerProfile.findLoggedIn().getMaxLevel()){
                PlayerProfile.findLoggedIn().setMaxLevelUnlocked(LEVEL_NUMBER + 1);
                PlayerProfileReader.reWrite(PlayerProfile.getAllPlayerProfiles());
            }
            int count = 0;
            boolean positionFound = false;
            while(count < 10 && !positionFound){
                HighScore h = highScores.get(count);
                if(h == null || END_SCORE > h.getScore()){
                    positionFound = true;
                    HighScore newHighScore = new HighScore(PlayerProfile.findLoggedIn().getPlayerUsername(), END_SCORE);
                    highScores.add(count,newHighScore);
                }
                count ++;
            }
            highScores.subList(10,highScores.size()).clear();
            LevelReader.changeHighscores(highScores,LEVEL_NUMBER);

        }
        VBox root = new VBox();
        root.setSpacing(5);

        Text title = new Text("You Won!");
        title.setStyle("-fx-font: 90 Papyrus");
        title.setFill(Color.WHITESMOKE);

        Button mainMenu = new Button("Level Select");
        Text displayScore = new Text("Total Score: " + END_SCORE);
        displayScore.setFill(Color.WHITESMOKE);
        displayScore.setStyle("-fx-font: 20 Papyrus");
        displayScore.setTextAlignment(TextAlignment.CENTER);
        displayScore.setWrappingWidth(400);

        TableView<HighScore> highScoreTable = new TableView();
        highScoreTable.setEditable(true);
        TableColumn<HighScore, String> usernameColumn = new TableColumn("Username");
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        TableColumn<HighScore, Integer> scoreColumn = new TableColumn("Score");
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));
        highScoreTable.getColumns().addAll(usernameColumn, scoreColumn);
        highScoreTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        highScoreTable.setMaxHeight(500);

        for(HighScore h : highScores) {
            if (h != null) {
                highScoreTable.getItems().add(h);
            }
        }
        BorderPane tablebox = new BorderPane(highScoreTable);
        highScoreTable.getStylesheets().add("HighScoreTable.css");
        tablebox.setMaxWidth(300);
        tablebox.setPrefSize(500, 292);
        Label highScoreLabel = new Label("HighScore Table");
        highScoreLabel.setFont(new Font("Tahoma", 20));


        root.getChildren().addAll(title,displayScore,mainMenu,tablebox);
        root.setAlignment(Pos.CENTER);
        root.setPrefSize(900,600);
        Scene scene = new Scene(root);
        root.getStylesheets().add("winningScreen.css");
        STAGE.setTitle("Jungle Jewel - You Won!!");
        STAGE.getIcons().add(new Image("images/diamondicon.png"));
        STAGE.setScene(scene);
        STAGE.setMinHeight(400);
        STAGE.setMinWidth(550);
        STAGE.setResizable(false);
        STAGE.show();

        if(PlayerProfile.findLoggedIn() != null){
            if(LEVEL_NUMBER == PlayerProfile.findLoggedIn().getMaxLevel()){
                PlayerProfile.findLoggedIn().setMaxLevelUnlocked(LEVEL_NUMBER + 1);
                PlayerProfileReader.reWrite(PlayerProfile.getAllPlayerProfiles());
            }
        }
        mainMenu.setOnAction(e -> mainMenuSelectScreen());

    }

    /**
     * This allows the user to go back to the level select screen
     * and passes into LevelSelect that they can if not already unlock the next level
     */
    private void mainMenuSelectScreen(){

        new LevelSelect(STAGE,LEVEL_NUMBER + 1);
    }
}
