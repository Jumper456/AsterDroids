package net.yotvoo.asterd.app;


import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Path;
import javafx.scene.shape.Shape;


/**
 * GameView maintains visual elements of the game application
 */

@SuppressWarnings("SameParameterValue")
class GameView {

    private GameLogic gameLogic;
    private final Scene scene;
    private final Stage stage;
    private Pane gameFieldPanel;
    private BorderPane rootBorderPane;
    private Label gameScoreLabel;
    private Label gameHiScoreLabel;
    private Label gameStatusLabel;
    private Label keysLabel;

    GameView(Stage stage) {
        this.stage = stage;
        scene = new Scene(createRoot());
        stage.setScene(scene);
        createGameViewContent();
        gameStatusLabel.setText("F5 nowa gra");

        stage.setTitle("AsterDroids");
        stage.setResizable(false);
        stage.show();
    }

    private Shape createStar(double maxSize, int x, int y){
        return new Circle(x,y,maxSize/2, Color.DARKGRAY);
    }

    @SuppressWarnings("SameParameterValue")
    private void createStarfield(@SuppressWarnings("SameParameterValue") final int starsNumber, final double maxSize){
        for (int i = 0; i < starsNumber; i++){
            int x = (int) Math.floor( gameFieldPanel.getWidth() * Math.random());
            int y = (int) Math.floor(  gameFieldPanel.getHeight() * Math.random());
            double size = maxSize * Math.random();
            if (size < 1) size = 1;
            gameFieldPanel.getChildren().add(createStar( size, x, y ));
        }
    }
    private void styleAndPlaceLabel(Label label,int x, int y, int fontSize){
        label.setStyle("-fx-font-size: " + fontSize + "px;\n" +
                "    -fx-font-weight: bold;\n" +
                "    -fx-text-fill: #333333;\n" +
                "    -fx-effect: dropshadow( gaussian , rgba(255,255,255,0.5) , 0,0,0,1 );");

        label.setTranslateX(x);
        label.setTranslateY(y);
        gameFieldPanel.getChildren().add(label);
    }

    private void createGameViewContent(){
        gameScoreLabel = new Label();
        styleAndPlaceLabel(gameScoreLabel,20,20,30);
        gameScoreLabel.setText("0");

        gameHiScoreLabel = new Label();
        styleAndPlaceLabel(gameHiScoreLabel, 20, 60, 30);

        gameStatusLabel = new Label();
        styleAndPlaceLabel(gameStatusLabel,30,300, 100);

        keysLabel = new Label();
        styleAndPlaceLabel(keysLabel,400,10, 30);

        createStarfield(Constants.STARS_NUMBER, Constants.MAX_STAR_SIZE);
    }

    private Parent createRoot() {

        gameFieldPanel = new Pane();
        gameFieldPanel.setPrefSize(Constants.PREFERED_GAME_SCENE_WIDTH, Constants.PREFERED_GAME_SCENE_HEIGHT);
        gameFieldPanel.setStyle("-fx-background-color: #000000;");

        rootBorderPane = new BorderPane();
        rootBorderPane.setCenter(gameFieldPanel);

        HBox topHBox = new HBox();
        rootBorderPane.setTop(topHBox);

        Button connectionButton = new Button("Parametry połączenia");
        connectionButton.setOnMouseClicked(event -> gameLogic.changeNetworkSettings());
        topHBox.getChildren().add(connectionButton);

        Button helpButton = new Button("Pomoc");
        helpButton.setOnMouseClicked(event -> UtilityForms.showHelpStage());
        topHBox.getChildren().add(helpButton);

        return rootBorderPane;
    }

    void addGameObject(Shape shape) {
        gameFieldPanel.getChildren().add(shape);
    }

    void removeGameObject(Shape shape) {
        gameFieldPanel.getChildren().remove(shape);
    }

    void removeGameObjectsAll(Shape... shapes) {
        gameFieldPanel.getChildren().removeAll(shapes);
    }

    void recreateContent() {
        gameFieldPanel.getChildren().clear();
        createGameViewContent();
    }

    void displayGameOver() {
        gameStatusLabel.setText("Game Over F5 nowa gra");
    }

    void updateScore(long gameScore) {
        gameScoreLabel.setText("Score: " + gameScore);
    }

    void updateHiScore(long gameHiScore) {
        gameHiScoreLabel.setText("Highest Score: " + gameHiScore);
    }

    void updatePressedKeysLabel(String s) {
        keysLabel.setText(s);
    }

    double getWidth() {
        return gameFieldPanel.getWidth();
    }

    double getHeight() {
        return gameFieldPanel.getHeight();
    }

    Scene getScene() {
        return scene;
    }


    private void drawCollision(Shape shape1 , Shape shape2){
        Shape path = Path.intersect(shape1, shape2);
        path.setFill(Color.RED);
        gameFieldPanel.getChildren().addAll(path);
    }

    public void closeAndExit() {
        stage.close();
    }

    public GameLogic getGameLogic() {
        return gameLogic;
    }

    public void setGameLogic(GameLogic gameLogic) {
        this.gameLogic = gameLogic;
    }
}
