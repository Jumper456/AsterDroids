package net.yotvoo.asterd.app;

import javafx.animation.AnimationTimer;
import javafx.scene.input.KeyCode;


/**
 * GameLogic contains the logic of the game
 */
public class GameLogic {

    private GameView gameView;
    private Sound sound;
    private Controll controll;
    private GameFieldModel gameFieldModel;

    private boolean isGameActive;

    private long gameScore = 0;
    private long gameHiScore = 0;

    public GameLogic(GameView gameView, Controll controll, Sound sound) {

        this.gameView = gameView;
        this.sound = sound;
        this.controll = controll;
        isGameActive = false;
        gameFieldModel = new GameFieldModel(gameView, sound);

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
    }


    void newGame(){
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

            //controll screen actions/ out of the game
            if (controll.checkIfKeyPressed(KeyCode.F5)) {
                newGame();;
            }
        }
        else {

            //during the game actions
            if (controll.checkIfKeyPressed(KeyCode.LEFT)) {
                gameFieldModel.rotatePlayer(GameFieldModel.ROTATE_LEFT);
            }

            if (controll.checkIfKeyPressed(KeyCode.RIGHT)) {
                gameFieldModel.rotatePlayer(GameFieldModel.ROTATE_RIGHT);
            }

            if (controll.checkIfKeyPressed(KeyCode.UP)) {
                gameFieldModel.acceleratePlayer();
                sound.playThrust();
            }

            if (controll.checkIfKeyPressed(KeyCode.SPACE)) {
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

    public boolean isGameActive() {
        return isGameActive;
    }

}
