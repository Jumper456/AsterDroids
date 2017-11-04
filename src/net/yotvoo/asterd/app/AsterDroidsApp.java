package net.yotvoo.asterd.app;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author
 */
public class AsterDroidsApp extends Application {


    private Sound sound;

    private Pane root;

    private List<GameObject> bullets = new ArrayList<>();
    private List<GameObject> enemies = new ArrayList<>();

    private GameObject player;

    private boolean isGameActive = true;

    private Label gameScoreLabel;
    private long gameScore = 0;

    private Label gameStatusLabel;

    private static final double MAX_ENEMY_SIZE = 40;
    private static final double MIN_ENEMY_SIZE = 5;
    private static final double MAX_ENEMY_SPEED = 3;
    private static final double MAX_ENEMY_PROXIMITY = 100;
    private static final double MAX_ENEMY_COUNT = 30;
    private static final double ENEMY_SPAWN_RATIO = 0.02;


    public static void log(String string){
        System.out.println(string);
    };

    private void createPlayer(){
        player = new Player();
        player.setVelocity(new Point2D(1, 0));
        addGameObject(player, 300, 300);

    }

    private void createContent(){
        gameScoreLabel = new Label();
        gameScoreLabel.setStyle("-fx-font-size: 20px;\n" +
                "    -fx-font-weight: bold;\n" +
                "    -fx-text-fill: #333333;\n" +
                "    -fx-effect: dropshadow( gaussian , rgba(255,255,255,0.5) , 0,0,0,1 );");

        gameScoreLabel.setTranslateX(20);
        gameScoreLabel.setTranslateY(20);
        gameScoreLabel.setTranslateZ(100 );
        gameScoreLabel.setText("Wynik na razie zero");
        root.getChildren().add(gameScoreLabel);

        gameStatusLabel = new Label();
        gameStatusLabel.setStyle("-fx-font-size: 90px;\n" +
                "    -fx-font-weight: bold;\n" +
                "    -fx-text-fill: #333333;\n" +
                "    -fx-effect: dropshadow( gaussian , rgba(255,255,255,0.5) , 0,0,0,1 );");

        gameStatusLabel.setTranslateX(100);
        gameStatusLabel.setTranslateY(400);
        gameStatusLabel.setTranslateZ(100);
        root.getChildren().add(gameStatusLabel);

    };

    private Parent createRoot() {

        root = new Pane();
        root.setPrefSize(1200, 800);
        root.setStyle("-fx-background-color: #000000;");

        return root;
    }

    private void addBullet(GameObject bullet, double x, double y) {
        bullets.add(bullet);
        addGameObject(bullet, x, y);
    }

    private void addEnemy(GameObject enemy, double x, double y) {
        enemies.add(enemy);
        addGameObject(enemy, x, y);
    }

    private void addGameObject(GameObject object, double x, double y) {
        object.getView().setTranslateX(x);
        object.getView().setTranslateY(y);
        root.getChildren().add(object.getView());
    }

    private void checkBulletsCollissions() {
        for (GameObject bullet : bullets) {
            if (bullet.isTooOld()) {
                bullet.setAlive(false);
                root.getChildren().remove(bullet.getView());
            } else {
                for (GameObject enemy : enemies) {
                    if (bullet.isColliding(enemy)) {

                        sound.playAsteroidExplosion();
                        bullet.setAlive(false);
                        enemy.setAlive(false);

                        root.getChildren().removeAll(bullet.getView(), enemy.getView());
                        gameScore++;
                    }
                }
            }

        }
    }

    private void newGame(){
        gameScore = 0;
        root.getChildren().clear();
        createContent();
        enemies.clear();
        bullets.clear();
        player = null;
        createPlayer();
        isGameActive = true;

    };

    private void gameOver(){
        sound.playPlayerCrash();
        gameStatusLabel.setText("Game Over F5 nowa gra");
        isGameActive = false;
    }


    private void drawCollision(Shape shape1 , Shape shape2){
        Shape path = Path.intersect(shape1, shape2);
        path.setFill(Color.RED);
        root.getChildren().addAll(path);
    }

    private void checkPlayerCollission() {
        for (GameObject enemy : enemies) {
            if (enemy.isColliding(player)) {
                gameOver();
            }
        }



    }

    private void addGameObjectWithProximityCheck(GameObject enemy){

        double x,y;

        x = Math.random() * root.getWidth();
        y = Math.random() * root.getHeight();

        if (Math.abs ( player.getView().getTranslateX() - x ) >= MAX_ENEMY_PROXIMITY) {
            if (Math.abs ( player.getView().getTranslateY() - y ) >= MAX_ENEMY_PROXIMITY) {
                addEnemy(enemy, x, y);
            }
        }
    };

    private void spawnEnemy(){
        if (enemies.size() <= MAX_ENEMY_COUNT) {
            if (Math.random() < ENEMY_SPAWN_RATIO) {
                int size = (int) Math.floor(MAX_ENEMY_SIZE * Math.random());
                if (size < MIN_ENEMY_SIZE) size = (int) MIN_ENEMY_SIZE;
                Enemy enemy = new Enemy(size);
                enemy.setVelocity(new Point2D((Math.random() - 0.5d) * MAX_ENEMY_SPEED,
                        (Math.random() - 0.5d) * MAX_ENEMY_SPEED));
                addGameObjectWithProximityCheck(enemy);

            }
        }
    }

    private void onUpdate() {
        if (isGameActive) {
            checkBulletsCollissions();


            bullets.removeIf(GameObject::isDead);
            enemies.removeIf(GameObject::isDead);

            checkPlayerCollission();

            bullets.forEach(GameObject::update);
            enemies.forEach(GameObject::update);

            player.update();


            gameScoreLabel.setText("Score: " + gameScore);
            spawnEnemy();
        }
    }


    private static class Player extends GameObject {

        Player() {
            super();
/*
            Rectangle ship = new Rectangle(40, 20, Color.BLUE);
            ship.setArcWidth(10);
            ship.setArcHeight(10);
*/
            Polygon ship = new Polygon();
            ship.getPoints().addAll(new Double[]{-30d,-10d,
                                                0d,0d,
                                                -30d,10d});

            ship.setFill(Color.BLUEVIOLET);
            super.setView(ship);


        }
    }


    private static Double[] generateRandomPolygon(Double baseSize, int vertexCount){

        Double  elements[] = new Double[vertexCount*2];

        for (int i = 0; i < vertexCount * 2; i++){
            elements[i] = Math.random() * baseSize;
        }

        return elements;
    }

    private static class Enemy extends GameObject {
/*
        Enemy(int size) {
            super(new Circle(size, size, size, Color.DARKGOLDENROD));
        }
*/
        Enemy(int size) {
            super(new Rectangle( size, size, Color.DARKGOLDENROD));
        }
/*        Enemy(int size) {


            super();
            Polygon polygon = new Polygon();
            //polygon.getPoints().addAll(new Double[]{-30d, -10d, 0d, 0d, -30d, 10d, -20d, -10d});
            polygon.getPoints().addAll(generateRandomPolygon(MAX_ENEMY_SIZE, 5));
            polygon.setFill(Color.DARKGOLDENROD);
            super.setView(polygon);
        }
*/


    }

    private static class Bullet extends GameObject {
        Bullet() {
            super(new Circle(5, 5, 5, Color.CADETBLUE));
        }
    }

    private void startKeyHandling(Scene scene){
        scene.setOnKeyPressed(e -> {
            if (!isGameActive){
                if (e.getCode() == KeyCode.F5) {
                    newGame();
                }
            }
            else {
                if (e.getCode() == KeyCode.LEFT) {
                    player.rotateLeft();
                } else if (e.getCode() == KeyCode.RIGHT) {
                    player.rotateRight();
                } else if (e.getCode() == KeyCode.UP) {
                    player.accelerate();
                } else if (e.getCode() == KeyCode.SPACE) {
                    Bullet bullet = new Bullet();
                    bullet.setVelocity(player.getOrientation().normalize().multiply(5));
                    addBullet(bullet, player.getView().getTranslateX(), player.getView().getTranslateY());
                    sound.playShooting();
                }
            }
        });

    }

    @Override
    public void start(Stage stage) throws Exception {
/*

        String musicFile = "C:/Users/Jumper/IdeaProjects/AsterDroids/res/" + "explosion_internal_loud_bang_blow_up_safe.mp3";
        URL url = getClass().getResource();
        AsterDroidsApp.log(url.toString());
        String uri = url.toURI().toString();
        AsterDroidsApp.log(uri);

*/
        isGameActive = false;
        stage.setScene(new Scene(createRoot()));
        createContent();
        gameStatusLabel.setText("F5 nowa gra");
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                onUpdate();
            }
        };
        timer.start();
        startKeyHandling(stage.getScene());
        stage.setTitle("AsterDroids");
        stage.setResizable(false);
        stage.show();
        sound = new Sound();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
