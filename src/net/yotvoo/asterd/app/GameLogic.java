package net.yotvoo.asterd.app;

import javafx.animation.AnimationTimer;
import javafx.scene.input.KeyCode;
import net.yotvoo.lib.network.ConnectionSettings;
import net.yotvoo.lib.network.SimpleConnection;
import net.yotvoo.lib.persist.SimplePersistance;


/**
 * GameLogic contains the logic of the game
 */
class GameLogic {

    private final GameView gameView;
    private final Sound sound;
    private final Control control;
    private final GameFieldModel gameFieldModel;
    private final SimpleConnection simpConn;
    //private final SimplePersistance simplePersistance;

    private boolean isGameActive;

    private long gameScore = 0;
    private long gameHiScore = 0;

    GameLogic(GameView gameView, Control control, Sound sound) {

        this.gameView = gameView;
        this.sound = sound;
        this.control = control;
        isGameActive = false;
        gameFieldModel = new GameFieldModel(gameView, sound);

        //Manage highscore persistance
        //simplePersistance = new SimplePersistance(Constants.HIGH_SCORE_FILE_NAME);
        //gameHiScore = simplePersistance.loadSimpleHighScore();
        gameHiScore = SimplePersistance.loadSimpleHighScore(Constants.HIGH_SCORE_FILE_NAME);
        gameView.updateHiScore(gameHiScore);


        ConnectionSettings connSett;
        //TODO remove test stuff (writing dummy connection data to the file)
        connSett = new ConnectionSettings(1,
                "Połączenie testowe.",
                "localhost",
                "55555",
                "jarek",
                "trzaslo",
                "takie sobie połączenie dla testu.");
        SimplePersistance.saveConnectionSettingsSingle(Constants.SIMPLE_CONNECTION_FILE_NAME, connSett);
        connSett = null;
        //END TODO

        //Initialize network connection stuff
        connSett = SimplePersistance.loadConnectionSettingsSingle(Constants.SIMPLE_CONNECTION_FILE_NAME);
        simpConn = new SimpleConnection(connSett);

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                onUpdate();
            }
        };
        timer.start();
    }


    private void setHiScore() {
        if (gameScore > gameHiScore) gameHiScore = gameScore;
        gameView.updateHiScore(gameHiScore);
        //simplePersistance.saveSimpleHighScore(gameHiScore);
        SimplePersistance.saveSimpleHighScore(Constants.HIGH_SCORE_FILE_NAME, gameHiScore);
    }


    private void newGame(){
        gameScore = 0;
        gameView.recreateContent();
        gameView.updateHiScore(gameHiScore);
        gameFieldModel.prepareNewGame();
        isGameActive = true;
    }

    private void gameOver(){
        setHiScore();
        sound.playPlayerCrash();
        gameView.displayGameOver();
        isGameActive = false;
    }


    private void checkCommands() {
        //check in the bitset which keys are pressed and do the action

        if (!isGameActive()){

            //control screen actions/ out of the game
            if (control.checkIfKeyPressed(KeyCode.F5)) {
                newGame();
            }
        }
        else {

            //during the game actions
            if (control.checkIfKeyPressed(KeyCode.LEFT)) {
                gameFieldModel.rotatePlayer(GameFieldModel.ROTATE_LEFT);
            }

            if (control.checkIfKeyPressed(KeyCode.RIGHT)) {
                gameFieldModel.rotatePlayer(GameFieldModel.ROTATE_RIGHT);
            }

            if (control.checkIfKeyPressed(KeyCode.UP)) {
                gameFieldModel.acceleratePlayer();
                sound.playThrust();
            }

            if (control.checkIfKeyPressed(KeyCode.SPACE)) {
                gameFieldModel.shootABullet();
            }
        }
    }

    private void onUpdate() {
        int updateResult;

        checkCommands(); //checks keyboard keys pressed or other commands;
        if (isGameActive) {
            try {
                updateResult = gameFieldModel.update();
                if (updateResult >= 0) {
                    gameScore += updateResult;
                    gameFieldModel.spawnEnemy();
                    gameView.updateScore(gameScore);
                }
                else //if updateResult is negative it means player has been destroyed or other game end condition reached
                    gameOver();
            }
            catch (Exception e){
                AsterDroidsApp.log("Problem w onUpdate " + e.toString());
            }
        }
    }

    private boolean isGameActive() {
        return isGameActive;
    }

}
