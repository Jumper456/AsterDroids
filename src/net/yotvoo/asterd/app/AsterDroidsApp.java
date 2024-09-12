package net.yotvoo.asterd.app;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * @author Jarosław Wachowicz
 * Simple game based on classic Asteroids game
 * Made for fun and learning purposes
 */
@SuppressWarnings({"FieldCanBeLocal", "unused"})
public class AsterDroidsApp extends Application {
    private static GameLogic gameLogic;
    static void log(String string){
        System.out.println(string);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Sound sound = new Sound();
        GameView gameView = new GameView(stage);
        Control control = new Control(gameView);
        gameLogic = new GameLogic(gameView, control, sound);
        gameView.setGameLogic(gameLogic);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
