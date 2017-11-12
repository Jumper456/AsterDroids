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
    private final SimpleConnection simpleConnection;
    private final AnimationTimer timer;
    private ConnectionSettings connectionSettings;

    public ConnectionSettings getConnectionSettings() {
        return connectionSettings;
    }
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



/*

        connectionSettings = new ConnectionSettings(1,
                "Połączenie testowe",
                "localhost",
                "55555",
                "jarek",
                "trzaslo",
                "takie sobie połączenie dla testu.");
        SimplePersistance.saveConnectionSettingsSingle(Constants.SIMPLE_CONNECTION_FILE_NAME, connectionSettings);
*/


        //Initialize network connection stuff
        connectionSettings = SimplePersistance.loadConnectionSettingsSingle(Constants.SIMPLE_CONNECTION_FILE_NAME);
        if (connectionSettings != null)
            System.out.println("Connection settings loaded: " + connectionSettings.toString());
        else
            System.out.println("Connection settings not loaded: ");

        simpleConnection = new SimpleConnection(connectionSettings);

        timer = new AnimationTimer() {
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
            if (control.checkIfKeyPressed(KeyCode.ESCAPE)) {
                gameExit();
            }
        }
        else {
            //during the game actions
            if (control.checkIfKeyPressed(KeyCode.ESCAPE)) {
                gameOver();
            }

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

    private void gameExit() {
        gameView.closeAndExit();
    }

    public void changeNetworkSettings() {
        System.out.println("changeNetworkSettings has been called");
        if (connectionSettings != null) {
            System.out.println("changeNetworkSettings: " + connectionSettings.toString());
        }
        NetworkSettingsForm networkSettingsForm = new NetworkSettingsForm(connectionSettings);
        connectionSettings = networkSettingsForm.showAndWait();
        if (connectionSettings != null) {
            SimplePersistance.saveConnectionSettingsSingle(Constants.SIMPLE_CONNECTION_FILE_NAME, connectionSettings);
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
