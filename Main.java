import javafx.application.Application;
import javafx.stage.Stage;

/**
 * @author Mai
 * @version 1.1
 */
public class Main extends Application {
    /**
     * Starts the game
     * @param primaryStage the first stage be launched
     */
    public void start(Stage primaryStage) {
        new MainMenu(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }


}
