package net.yotvoo.asterd.app;

import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.List;

class GameFieldModel {

    static final int ROTATE_RIGHT = 1;
    static final int ROTATE_LEFT = 2;

    private final GameView gameView;
    private final Sound sound;
    private final List<GameObject> bullets = new ArrayList<>();
    private final List<GameObject> enemies = new ArrayList<>();
    private GameObject player;
    private double lastBulletTimeMS = 0;

    GameFieldModel(GameView gameView, Sound sound) {
        this.gameView = gameView;
        this.sound = sound;
    }

    private void createPlayer(){
        player = new PlayerObject();
        player.setVelocity(new Point2D(1, 0));
        player.setMaxVelocityMagnitude(5);
        addGameObjectToView(player, 300, 300);
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

    /**
     * @return number of hits (used to calculate score
     */
    private int checkBulletsCollissions() {

        int colissionNumber = 0;
        List<GameObject> listOfNewGameObjects = new ArrayList<>();

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
                        colissionNumber++;
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
        return colissionNumber;

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
    @SuppressWarnings("Convert2Diamond")
    private ArrayList<GameObject> destroyEnemy(GameObject enemy, GameObject bullet) {

        final int MAX_SHARDS = 2;
        int shardsCount;
        EnemyObject enemyShard;
        @SuppressWarnings("Convert2Diamond") ArrayList<GameObject> gameObjects = new ArrayList<GameObject>();

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


    void prepareNewGame() {
        enemies.clear();
        bullets.clear();
        player = null;
        createPlayer();
    }

    private boolean isPlayerDestroyed() {
        boolean isDestroyed = false;
        for (GameObject enemy : enemies) {
            if (enemy.isColliding(player)) {
                AsterDroidsApp.log("Killed with enemy. Enemy velocity " + enemy.getVelocity().toString());
                isDestroyed = true;
                break;
            }
        }
        return isDestroyed;
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
    }

    void spawnEnemy(){
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
    void shootABullet(){

        if ((lastBulletTimeMS + Constants.BULLETS_INTERVAL) < System.currentTimeMillis()) {
            lastBulletTimeMS = System.currentTimeMillis();
            BulletObject bullet = new BulletObject();
            bullet.setMaxVelocityMagnitude(8);
            bullet.setVelocity(player.getOrientation().normalize().multiply(7));
            addBullet(bullet, player.getView().getTranslateX(), player.getView().getTranslateY());
            sound.playShooting();
        }
    }

    /**
     * @return score earned by player during this update, -1 means player is destroyed, positive
     */
    int update() {
        int scoreEarned = checkBulletsCollissions();
        bullets.removeIf(GameObject::isDead);
        enemies.removeIf(GameObject::isDead);
        boolean isPlayerDestroyed = isPlayerDestroyed();
        bullets.forEach(GameObject::update);
        enemies.forEach(GameObject::update);
        player.update();
        if (isPlayerDestroyed)
            return -1;
        else
            return scoreEarned;
    }

    void rotatePlayer(int rotate) {
        if (rotate == ROTATE_RIGHT)
            player.rotateRight();
        else
            player.rotateLeft();
    }

    void acceleratePlayer() {
        player.accelerate();
    }
}
