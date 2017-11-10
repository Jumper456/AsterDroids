package net.yotvoo.asterd.app;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;


/**
 * @author
 */
public class AsterDroidsApp extends Application {

    private GameLogic gameLogic;
    private GameView gameView;
    private Controll controll;
    private Sound sound;


    public static void log(String string){
        System.out.println(string);
    };


    @Override
    public void start(Stage stage) throws Exception {

        sound = new Sound();
        gameView = new GameView(stage);
        controll = new Controll(gameView);
        gameLogic = new GameLogic(gameView, controll, sound);

    }

    public static void main(String[] args) {
        launch(args);
    }
}
