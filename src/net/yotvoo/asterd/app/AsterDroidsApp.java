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
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;

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

    private boolean gameActive = true;

    private Label gameScoreLabel;
    private long gameScore = 0;

    private Label gameStatusLabel;

    private Parent createContent() {

        sound = new Sound();

        root = new Pane();
        root.setPrefSize(1200, 800);
        root.setStyle("-fx-background-color: #000000;");

        gameScoreLabel = new Label();
        gameScoreLabel.setStyle("-fx-font-size: 20px;\n" +
                "    -fx-font-weight: bold;\n" +
                "    -fx-text-fill: #333333;\n" +
                "    -fx-effect: dropshadow( gaussian , rgba(255,255,255,0.5) , 0,0,0,1 );");

        gameScoreLabel.setTranslateX(20);
        gameScoreLabel.setTranslateY(20);
        gameScoreLabel.setTranslateZ(100 );
        root.getChildren().add(gameScoreLabel);

        gameStatusLabel = new Label();
        gameStatusLabel.setStyle("-fx-font-size: 100px;\n" +
                "    -fx-font-weight: bold;\n" +
                "    -fx-text-fill: #333333;\n" +
                "    -fx-effect: dropshadow( gaussian , rgba(255,255,255,0.5) , 0,0,0,1 );");

        gameStatusLabel.setTranslateX(500);
        gameStatusLabel.setTranslateY(400);
        gameStatusLabel.setTranslateZ(100);
        root.getChildren().add(gameStatusLabel);


        player = new Player();
        player.setVelocity(new Point2D(1, 0));
        addGameObject(player, 300, 300);

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                onUpdate();
            }
        };
        timer.start();

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
                        bullet.setAlive(false);
                        enemy.setAlive(false);

                        root.getChildren().removeAll(bullet.getView(), enemy.getView());
                        gameScore++;
                    }
                }
            }

        }
    }

    private void gameOver(){
        gameStatusLabel.setText("Game Over");
        gameActive = false;
    }

    private void checkPlayerCollission() {
        for (GameObject enemy : enemies) {
            if (enemy.isColliding(player)) {
                gameOver();
            }
        }
    }

    private void onUpdate() {
        if (gameActive) {
            checkBulletsCollissions();


            bullets.removeIf(GameObject::isDead);
            enemies.removeIf(GameObject::isDead);

            checkPlayerCollission();

            bullets.forEach(GameObject::update);
            enemies.forEach(GameObject::update);

            player.update();


            gameScoreLabel.setText("Score: " + gameScore);


            if (Math.random() < 0.02) {
                Enemy enemy = new Enemy();
                enemy.setVelocity(new Point2D(Math.random(), Math.random()));
                addEnemy(enemy, Math.random() * root.getWidth(), Math.random() * root.getHeight());
            }



        }
    }

    private void newGame(){

    };

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

    private static class Enemy extends GameObject {
        Enemy() {
            super(new Circle(15, 15, 15, Color.DARKGOLDENROD));
        }
    }

    private static class Bullet extends GameObject {
        Bullet() {
            super(new Circle(5, 5, 5, Color.CADETBLUE));
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setScene(new Scene(createContent()));
        stage.getScene().setOnKeyPressed(e -> {
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
        });
        stage.setTitle("AsterDroids");
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
