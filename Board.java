import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.scene.canvas.Canvas;
import javafx.util.Duration;
import java.util.ArrayList;

/**
 * @author Mai, Sandie, James, Lincoln
 * @version 2.0
 * Represents and draws the Game Board,
 * whilst managing the timing of movements.
 */
public class Board{
    private Graph graph;
    private Canvas playerCanvas;
    private Canvas itemCanvas;
    private Canvas NPCCanvas;
    private Label timerText;
    private Label scoreText;
    private Timeline timeline;
    private Timeline bombTimeLine;
    private boolean gamePaused = false;
    private int score = 0;

    private final Stage STAGE;
    private final int MAX_HEIGHT;
    private final int MAX_WIDTH;
    private final Level CURRENT_LEVEL;
    private final Timer TIMER;

    private final Image Y = new Image("images/yellow.png"); //sandstone
    private final Image R = new Image("images/red.png"); //mud
    private final Image M = new Image("images/magenta.png"); //magma
    private final Image G = new Image("images/green.png"); //grass
    private final Image C = new Image("images/cyan.png"); //grassy stone
    private final Image B = new Image("images/blue.png"); //stone
    private final int TILE_SIZE = 2 * (int)Y.getWidth();

    private final Player PLAYER;
    private final PlayerProfile LOGGED_IN = PlayerProfile.findLoggedIn();

    /**
     * Constructs a Board using Level information.
     * @param stage The stage to show.
     * @param level The Level to represent and draw.
     */
    public Board(Stage stage, Level level) {
        this.STAGE = stage;
        this.MAX_WIDTH = level.getMaxWidth();
        this.MAX_HEIGHT = level.getMaxHeight();
        this.CURRENT_LEVEL = level;
        this.TIMER = level.getGameTimer();
        this.PLAYER = level.getPlayer();
        start();
    }

    /**
     * Starts the process of creating the window.
     */
    private void start(){
        Pane root = createGUI();
        Scene scene = new Scene(root);

        //The scene reacts when a key is pressed
        scene.addEventFilter(KeyEvent.KEY_PRESSED,
                event -> processKeyEvent(event));

        createTimeLines();
        setStage(scene);

        graph = new Graph(CURRENT_LEVEL);
        graph.fillMatrix();

        drawNPCLayer();
        drawPlayerLayer();
        drawItemLayer();
    }

    /**
     * Creates the TimeLines used in the time management of the Board
     */
    private void createTimeLines(){
        final int TICK_RATE = 1000; //Ticks every second
        timeline = new Timeline(new KeyFrame(Duration.millis(TICK_RATE),
                event -> tickClock()));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        bombTimeLine = new Timeline(new KeyFrame(
                Duration.millis(TICK_RATE), event ->{}));
        bombTimeLine.setCycleCount(3);
    }

    /**
     * Initialises scene.
     * @param scene Scene to pass into the Stage.
     */
    private void setStage(Scene scene){
        STAGE.setTitle("Jungle Jewel - Level " + CURRENT_LEVEL.getLevelNumber());
        STAGE.getIcons().add(new Image("images/diamondicon.png"));
        STAGE.setScene(scene);
        STAGE.setResizable(false);
        STAGE.sizeToScene();
        STAGE.centerOnScreen();
        STAGE.sizeToScene();
        STAGE.show();

    }

    /**
     * Creates the root pane.
     * @return The root Pane.
     */
    private Pane createGUI() {
        BorderPane root = new BorderPane();
        StackPane gameLayer = new StackPane();
        HBox infoBar;

        int canvasHeight = MAX_HEIGHT * 2 * (int) Y.getHeight();
        int canvasWidth = MAX_WIDTH * 2 * (int) Y.getHeight();

        infoBar = createInfoBar();
        root.setTop(infoBar);
        root.setCenter(gameLayer);
        gameLayer.getChildren().add(createBoard());

        itemCanvas = new Canvas(canvasWidth, canvasHeight);
        gameLayer.getChildren().add(itemCanvas);

        playerCanvas = new Canvas(canvasWidth, canvasHeight);
        gameLayer.getChildren().add(playerCanvas);

        NPCCanvas = new Canvas(canvasWidth, canvasHeight);
        gameLayer.getChildren().add(NPCCanvas);

        return root;
    }

    /**
     * Creates the InfoBar to display buttons and Level information.
     * @return The HBox infoBox
     */
    private HBox createInfoBar() {
        final int SPACING = 10; //In pixels
        HBox infoBar = new HBox();
        infoBar.setSpacing(SPACING);
        infoBar.setPadding(new Insets(SPACING, SPACING, SPACING, SPACING));

        scoreText = new Label("Score: " + 0);
        scoreText.setTextAlignment(TextAlignment.LEFT);
        infoBar.getChildren().add(scoreText);

        timerText = new Label("Time Left: " + TIMER.getSecondsLeft());
        timerText.setTextAlignment(TextAlignment.RIGHT);
        infoBar.getChildren().add(timerText);

        createButtons(infoBar);

        return infoBar;
    }

    /**
     * Creates the Buttons and associated event handling for buttons in the InfoBar.
     * No buttons are clickable if no user is logged in.
     * Load is not available if no previous save file is available.
     * @param infoBar The InfoBar to add the buttons to.
     */
    private void createButtons(HBox infoBar){
        Button pauseButton = new Button("Pause");
        Button resumeButton = new Button("Resume");
        Button saveButton = new Button("Save");
        Button loadButton = new Button("Load");
        infoBar.getChildren().addAll(pauseButton, resumeButton,
                saveButton, loadButton);

        resumeButton.setDisable(true);
        if (LOGGED_IN == null){
            saveButton.setDisable(true);
            loadButton.setDisable(true);
        }else {
            if (!(LevelReader.isSaveAvailable(CURRENT_LEVEL.getLevelNumber()))){
                loadButton.setDisable(true);
            }
        }

        pauseButton.setOnAction(e -> {
            pauseButton.setDisable(true);
            timeline.stop();
            gamePaused = true;
            resumeButton.setDisable(false);
        });

        resumeButton.setOnAction(e -> {
            resumeButton.setDisable(true);
            timeline.play();
            gamePaused=false;
            pauseButton.setDisable(false);
        });

        saveButton.setOnAction(e -> LevelReader.saveLevel(CURRENT_LEVEL));
        loadButton.setOnAction(e -> {
            timeline.stop();
            Level loadedLevel = LevelReader.loadLevel(CURRENT_LEVEL);
            new Board(STAGE, loadedLevel);
        });
    }

    /**
     * For each tick, coordinates movement and checks.
     */
    private void tickClock() {
        timer();
        for (NPC n : NPC.getAllLevelNPCs()) {
            if (n instanceof FlyingAssassin){
                ((FlyingAssassin) n).faMove(MAX_WIDTH, MAX_HEIGHT);
                drawNPCLayer();
                ((FlyingAssassin) n).checkPlayerCollision(PLAYER, this);
                drawNPCLayer();
            }else if (n instanceof FloorFollowingThief){
                ((FloorFollowingThief) n).ffMove(MAX_HEIGHT, MAX_WIDTH, PLAYER);
                drawItemLayer();
                drawNPCLayer();
            }else if (n instanceof SmartThief) {
                ((SmartThief) n).setMatrix(graph.getMatrix());
                ((SmartThief) n).stMove(MAX_WIDTH, MAX_HEIGHT, Item.getAllLevelItems(), Tile.getAllLevelTiles());
            }
            if (n instanceof Thief) {
                ((Thief) n).thiefItemInteractions(TIMER,this);
                Bomb.bombCheck(n.getPosX(),n.getPosY(), bombTimeLine, this);
            }
        }
        NPC.checkNPCCollisions();
        graph.fillMatrix();
    }

    /**
     * Adjusts Timer accordingly with the ticks and displays the time remaining.
     */
    private void timer() {
        TIMER.setSecondsLeft(TIMER.getSecondsLeft() - 1);
        timerText.setText("Time Left: " + TIMER.getSecondsLeft());
        if (TIMER.getSecondsLeft() == 0) {
            gameOver();
        }

    }

    /**
     * Visually draws the Game Board given by the Tiles.
     * @return The Pane containing the fully drawn Board.
     */
    private Pane createBoard(){
        GridPane gameBoard = new GridPane();
        int count1 = 0;
        for (int i = 0; i < MAX_HEIGHT ;i++){
            for (int j = 0; j < MAX_WIDTH; j++){
                GridPane square = new GridPane();
                ArrayList<Character> colours =
                        Tile.getAllLevelTiles().get(count1).getColours();
                int count2 = 0;
                for (int l = 0; l < 2; l++){
                    for (int k = 0; k <2; k++){
                        char colourOfTile = colours.get(count2);
                        switch (colourOfTile) {
                            case 'R':
                                square.add(new ImageView(R), k, l);
                                break;
                            case 'G':
                                square.add(new ImageView(G), k, l);
                                break;
                            case 'B':
                                square.add(new ImageView(B), k, l);
                                break;
                            case 'Y':
                                square.add(new ImageView(Y), k, l);
                                break;
                            case 'M':
                                square.add(new ImageView(M), k, l);
                                break;
                            default:
                                square.add(new ImageView(C), k, l);
                                break;
                        }
                        count2++;
                    }
                }
                gameBoard.add(square, j, i);
                count1++;
            }
        }
        return gameBoard;
    }

    /**
     * Draws the Player onto the Board.
     */
    public void drawPlayerLayer() {
        // Get the Graphic Context of the canvas. This is what we draw on.
        GraphicsContext gc = playerCanvas.getGraphicsContext2D();
        gc.clearRect(0, 0,
                playerCanvas.getWidth(), playerCanvas.getHeight());

        gc.drawImage(PLAYER.getPlayerImage(),
                PLAYER.getPlayerX() * TILE_SIZE + PLAYER.getXOffset(),
                PLAYER.getPlayerY() * TILE_SIZE  + PLAYER.getYOffset());
    }

    /**
     * Draws the Items onto the Board.
     */
    public void drawItemLayer() {
        GraphicsContext gc = itemCanvas.getGraphicsContext2D();
        gc.clearRect(0, 0, itemCanvas.getWidth(), itemCanvas.getHeight());

        for (Item i : Item.getAllLevelItems()) {
            gc.drawImage(i.getItemImage(),
                    i.getPositionX() * TILE_SIZE + i.getXOffset(),
                    i.getPositionY() * TILE_SIZE + i.getYOffset());
        }
    }

    /**
     * Draws the NPCs onto the board.
     */
    public void drawNPCLayer(){
        GraphicsContext gc = NPCCanvas.getGraphicsContext2D();
        gc.clearRect(0,0, NPCCanvas.getWidth(), NPCCanvas.getHeight());

        for (NPC n : NPC.getAllLevelNPCs()){
            gc.drawImage(n.getImage(),n.getPosX() * TILE_SIZE + n.getXOffset(),
                    n.getPosY() * TILE_SIZE + n.getYOffset());
        }
    }

    /**
     * Processes the key pressed and moves the Player accordingly.
     * @param event The key pressed.
     */
    private void processKeyEvent(KeyEvent event) {
        if (!gamePaused) {
            PLAYER.movePlayer(event,
                    Tile.getTile(PLAYER.getPlayerX(), PLAYER.getPlayerY()),
                    MAX_HEIGHT, MAX_WIDTH);
        }
        PLAYER.playerItemInteractions(TIMER,score,scoreText,this);
        Bomb.bombCheck(PLAYER.getPlayerX(), PLAYER.getPlayerY(),
                bombTimeLine, this);
        drawItemLayer();
        drawPlayerLayer();
        PLAYER.checkPlayerFlyingAssassinCollision(this);

        // Consume the event. This means we mark it as dealt with.
        // This stops other GUI nodes (buttons etc.) responding to it.
        event.consume();
    }

    /**
     * Ends the Game by showing the LosingScreen and stopping the timeline
     */
    public void gameOver(){
        timeline.stop();
        new LosingScreen(STAGE,CURRENT_LEVEL.getLevelNumber());
    }

    /**
     * Ends the Game by showing the WinningScreen screen and stopping the timeline
     */
    public void gameWon(){
        timeline.stop();
        new WinningScreen(STAGE, TIMER.getSecondsLeft(), score,
                CURRENT_LEVEL.getLevelNumber(), CURRENT_LEVEL.getHighScores());
    }

    /**
     * Returns the Player Score.
     * @return The current Player Score
     */
    public int getScore() {
        return score;
    }

    /**
     * Sets the Player score to a specified value
     * @param score The value to set score to
     */
    public void setScore(int score) {
        this.score = score;
    }
}