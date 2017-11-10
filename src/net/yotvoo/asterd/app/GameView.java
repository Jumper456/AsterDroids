package net.yotvoo.asterd.app;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Path;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;

/**
 * GameView maintains visual elements of the game application
 */
public class GameView {
    private Pane root;

    public Scene getScene() {
        return scene;
    }

    private Stage stage;
    private Scene scene;
    private Label gameScoreLabel;
    private Label gameHiScoreLabel;
    private Label gameStatusLabel;
    private Label keysLabel;

    public GameView(Stage stage) {
        this.stage = stage;
        scene = new Scene(createRoot());
        stage.setScene(scene);
        createContent();
        gameStatusLabel.setText("F5 nowa gra");

        stage.setTitle("AsterDroids");
        stage.setResizable(false);
        stage.show();


    }

    private Shape createStar(double maxSize, int x, int y){
        Shape star = new Circle(x,y,maxSize/2, Color.DARKGRAY);
        return star;
    }

    private void createStarfield(int starsNumber, double maxSize){
        for (int i = 0; i < starsNumber; i++){
            int x = (int) Math.floor( root.getWidth() * Math.random());
            int y = (int) Math.floor(  root.getHeight() * Math.random());
            double size = Constants.MAX_STAR_SIZE * Math.random();
            if (size < 1) size = 1;
            root.getChildren().add(createStar( size, x, y ));
        }
    }
    private void styleAndPlaceLabel(Label label,int x, int y, int fontSize){

        label.setStyle("-fx-font-size: " + fontSize + "px;\n" +
                "    -fx-font-weight: bold;\n" +
                "    -fx-text-fill: #333333;\n" +
                "    -fx-effect: dropshadow( gaussian , rgba(255,255,255,0.5) , 0,0,0,1 );");

        label.setTranslateX(x);
        label.setTranslateY(y);
        root.getChildren().add(label);

    }

    private void createContent(){
        gameScoreLabel = new Label();
        styleAndPlaceLabel(gameScoreLabel,20,20,30);
        gameScoreLabel.setText("0");

        gameHiScoreLabel = new Label();
        styleAndPlaceLabel(gameHiScoreLabel, 20, 60, 30);
        updateHiScore(0);

        gameStatusLabel = new Label();
        styleAndPlaceLabel(gameStatusLabel,100,300, 100);

        keysLabel = new Label();
        styleAndPlaceLabel(keysLabel,400,10, 30);


        createStarfield(Constants.STARS_NUMBER, Constants.MAX_STAR_SIZE);
    }

    private Parent createRoot() {
        root = new Pane();
        root.setPrefSize(1200, 800);
        root.setStyle("-fx-background-color: #000000;");
        return root;
    }


    public void updateKeysLabel(String s) {
        keysLabel.setText(s);
    }

    public void updateHiScore(long gameHiScore) {
        gameHiScoreLabel.setText("Highest Score: " + gameHiScore);
    }

    public void addGameObject(Shape shape) {
        root.getChildren().add(shape);
    }

    public void removeGameObject(Shape shape) {
        root.getChildren().remove(shape);
    }

    public void removeGameObjectsAll(Shape... shapes) {
        root.getChildren().removeAll(shapes);
    }

    public void recreateContent() {
        root.getChildren().clear();
        createContent();
    }

    public void displayGameOver() {
        gameStatusLabel.setText("Game Over F5 nowa gra");
    }


    public double getWidth() {
        return root.getWidth();
    }

    public double getHeight() {
        return root.getHeight();
    }

    public void updateScore(long gameScore) {
        gameScoreLabel.setText("Score: " + gameScore);
    }

    private void drawCollision(Shape shape1 , Shape shape2){
        Shape path = Path.intersect(shape1, shape2);
        path.setFill(Color.RED);
        root.getChildren().addAll(path);
    }

}
