package net.yotvoo.asterd.app;

import javafx.animation.AnimationTimer;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;
import javafx.scene.shape.Shape;

import java.util.ArrayList;
import java.util.List;

/**
 * GameLogic contains the logic of the game
 */
public class GameLogic {

    private GameView gameView;
    private Sound sound;
    private Controll controll;
    private List<GameObject> bullets = new ArrayList<>();
    private List<GameObject> enemies = new ArrayList<>();

    private GameObject player;

    private boolean isGameActive;

    private long gameScore = 0;
    private long gameHiScore = 0;
    private double lastBulletTimeMS = 0;

    public GameLogic(GameView gameView, Controll controll, Sound sound) {
        this.gameView = gameView;
        this.sound = sound;
        this.controll = controll;
        isGameActive = false;

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                onUpdate();
            }
        };
        timer.start();
    }

    private void createPlayer(){
        player = new PlayerObject();
        player.setVelocity(new Point2D(1, 0));
        player.setMaxVelocityMagnitude(5);
        addGameObjectToView(player, 300, 300);
    }

    private void setHiScore() {
        if (gameScore > gameHiScore) gameHiScore = gameScore;
        gameView.updateHiScore(gameHiScore);
    }
    private void addBullet(GameObject bullet, double x, double y) {
        bullets.add(bullet);
        addGameObjectToView(bullet, x, y);
    }

    private void addEnemy(GameObject enemy, double x, double y) {
        enemies.add(enemy);
        addGameObjectToView(enemy, x, y);
    }

    private void addGameObjectToView(GameObject object, double x, double y) {
        object.getView().setTranslateX(x);
        object.getView().setTranslateY(y);
        gameView.addGameObject(object.getView());
    }
    private void checkBulletsCollissions() {

        List<GameObject> listOfNewGameObjects = new ArrayList<>();
/*
        for (String str : myArrayList) {
            if (someCondition) {
                toRemove.add(str);
            }
        }
        myArrayList.removeAll(toRemove);
*/

        for (GameObject bullet : bullets) {
            if (bullet.isTooOld()) {
                bullet.setAlive(false);

                gameView.removeGameObject(bullet.getView());
            } else {
                for (GameObject enemy : enemies) {
                    if (bullet.isColliding(enemy)) {

                        sound.playAsteroidExplosion();

                        listOfNewGameObjects = destroyEnemy(enemy,bullet);
                        bullet.setAlive(false);

                        gameView.removeGameObjectsAll(bullet.getView(), enemy.getView());
                        gameScore++;
                    }
                }
                //needed to use this construction to avoid ConcurentModificationException
                //due to the ArrayList modification during iterating this list
                enemies.addAll(listOfNewGameObjects);
                listOfNewGameObjects.clear();

                if (listOfNewGameObjects.size()>0) {
                    AsterDroidsApp.log("Ilość shardów: " + listOfNewGameObjects.size());
                    //AsterDroidsApp.log(listOfNewGameObjects.toString());
                    AsterDroidsApp.log("Ilość asteroidów : " + enemies.size());

                }

            }
        }
    }

    private EnemyObject generateEnemyShard(double size, GameObject bullet){

        EnemyObject enemy = new EnemyObject((int)size);
        enemy.setVelocity(new Point2D((Math.random() - 0.5d) * Constants.MAX_ENEMY_SPEED,
                (Math.random() - 0.5d) * Constants.MAX_ENEMY_SPEED));
        AsterDroidsApp.log("Spawned enemy shard with velocity " + enemy.getVelocity().toString());
        return enemy;

    }


    /**
     * @param enemy - enemy to be shareded or destroyed
     * @param bullet - bullet which colided the enemy
     *
     * This function destroys the enemy if it is small enough or creates shards
     * These shards should travel in the direction of the shard with some random direction added
     * and with bullet momentum also added
     */
    private ArrayList<GameObject> destroyEnemy(GameObject enemy, GameObject bullet) {

        final int MAX_SHARDS = 2;
        int shardsCount;
        EnemyObject enemyShard;
        ArrayList<GameObject> gameObjects = new ArrayList<GameObject>();

        if (enemy.getGameObjectSize() > Constants.MIN_ENEMY_SIZE){

            shardsCount = (int) Math.round(Math.random() * MAX_SHARDS) + 2 ;
            for (int i = 0; i < shardsCount; i++){
                enemyShard = generateEnemyShard(enemy.getGameObjectSize()/Math.sqrt(shardsCount), bullet);
                gameObjects.add(enemyShard);
                addGameObjectToView(enemyShard, enemy.getView().getTranslateX(), enemy.getView().getTranslateY());  // adds to View
            }

            //int size = (int) Math.floor(MAX_ENEMY_SIZE * Math.random());
            //if (size < MIN_ENEMY_SIZE) size = (int) MIN_ENEMY_SIZE;
        }
        enemy.setAlive(false);
        //root.getChildren().remove(enemy.getView());
        return gameObjects;
    }

    void newGame(){
        gameScore = 0;
        gameView.recreateContent();
        enemies.clear();
        bullets.clear();
        player = null;
        createPlayer();
        isGameActive = true;
    };

    private void gameOver(){
        setHiScore();
        sound.playPlayerCrash();
        gameView.displayGameOver();
        isGameActive = false;
    }

    private void checkPlayerCollission() {
        for (GameObject enemy : enemies) {
            if (enemy.isColliding(player)) {
                AsterDroidsApp.log("Killed with enemy velocity " + enemy.getVelocity().toString());
                gameOver();
            }
        }
    }

    private void addGameObjectWithProximityCheck(GameObject enemy){

        double x,y;

        x = Math.random() * gameView.getWidth();
        y = Math.random() * gameView.getHeight();

        if (Math.abs ( player.getView().getTranslateX() - x ) >= Constants.MAX_ENEMY_PROXIMITY) {
            if (Math.abs ( player.getView().getTranslateY() - y ) >= Constants.MAX_ENEMY_PROXIMITY) {
                addEnemy(enemy, x, y);
                AsterDroidsApp.log("Spawned enemy with velocity " + enemy.getVelocity().toString());
            }
        }
    };

    private void spawnEnemy(){
        try {
            if (enemies.size() <= Constants.MAX_ENEMY_COUNT) {
                if (Math.random() < Constants.ENEMY_SPAWN_RATIO) {
                    int size = (int) Math.floor(Constants.MAX_ENEMY_SIZE * Math.random());
                    if (size < Constants.MIN_ENEMY_SIZE) size = (int) Constants.MIN_ENEMY_SIZE;
                    EnemyObject enemy = new EnemyObject(size);
                    enemy.setVelocity(new Point2D((Math.random() - 0.5d) * Constants.MAX_ENEMY_SPEED,
                            (Math.random() - 0.5d) * Constants.MAX_ENEMY_SPEED));
                    addGameObjectWithProximityCheck(enemy);

                }
            }
        } catch (Exception e){
            AsterDroidsApp.log("Problem w spawnEnemy " + e.toString());
        }
    }

    /**
     * Conditionally shoots a bullet, i.e. creates a bullet with correct velocity if
     * there are conditions the shot should be made
     */
    private void shootABullet(){

        if ((lastBulletTimeMS + Constants.BULLETS_INTERVAL) < System.currentTimeMillis()) {
            lastBulletTimeMS = System.currentTimeMillis();
            BulletObject bullet = new BulletObject();
            bullet.setMaxVelocityMagnitude(8);
            bullet.setVelocity(player.getOrientation().normalize().multiply(7));
            addBullet(bullet, player.getView().getTranslateX(), player.getView().getTranslateY());
            sound.playShooting();
        }
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
                player.rotateLeft();
            }

            if (controll.checkIfKeyPressed(KeyCode.RIGHT)) {
                player.rotateRight();
            }

            if (controll.checkIfKeyPressed(KeyCode.UP)) {
                player.accelerate();
                sound.playThrust();
            }

            if (controll.checkIfKeyPressed(KeyCode.SPACE)) {
                shootABullet();
            }
        }
    }

    private void onUpdate() {

        checkCommands(); //checks keyboard keys pressed or other commands;
        if (isGameActive) {
            try {
                checkBulletsCollissions();
                bullets.removeIf(GameObject::isDead);
                enemies.removeIf(GameObject::isDead);
                checkPlayerCollission();
                bullets.forEach(GameObject::update);
                enemies.forEach(GameObject::update);
                player.update();
                gameView.updateScore(gameScore);
                spawnEnemy();
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
