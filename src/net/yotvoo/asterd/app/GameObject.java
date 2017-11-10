package net.yotvoo.asterd.app;

import javafx.geometry.Point2D;
import javafx.scene.shape.Shape;

/**
 * @author
 */

public class GameObject {

    final Double ACCELERATION = 0.1d;
    private final double ROTATE_STEP = 3d;

    private Shape view;
    private Point2D velocity = new Point2D(0, 0);
    //private Point2D orientation = new Point2D(0,0);
    private Long ageStart;
    private final Long MAX_BULLET_AGE = 1000L; // 1 sek
    private double maxVelocityMagnitude = 6;

    private int gameObjectSize; //current size (loosly corresponding to shape size), it is about how many times it can split

    public int getGameObjectSize() {
        return gameObjectSize;
    }

    public void setGameObjectSize(int size) {
        this.gameObjectSize = size;
    }


    public double getMaxVelocityMagnitude() {
        return maxVelocityMagnitude;
    }

    public void setMaxVelocityMagnitude(double maxVelocityMagnitude) {
        this.maxVelocityMagnitude = maxVelocityMagnitude;
    }

    /**
     * @return object age in ms
     */
    public Long getAge() {
        return System.currentTimeMillis() - ageStart;
    }

    public void resetAge() {
        this.ageStart = System.currentTimeMillis();
    }

    public boolean isOlderThen(Long age){
        return ( getAge() > age );
    }

    public boolean isTooOld(){
        return ( getAge() > MAX_BULLET_AGE);
    };


    public Point2D getOrientation() {
        return new Point2D(Math.cos(Math.toRadians(getRotate())), Math.sin(Math.toRadians(getRotate())));
    }
/*
    public void setOrientation(Point2D orientation) {
        this.orientation = orientation;
    }
*/

    private boolean alive = true;

    public GameObject(Shape view) {
        this.view = view;
        ageStart = System.currentTimeMillis();
    }

    public GameObject() {
        ageStart = System.currentTimeMillis();
    }

    public void update() {
        view.setTranslateX(view.getTranslateX() + velocity.getX());
        view.setTranslateY(view.getTranslateY() + velocity.getY());

        //TODO change the bounds to the pane with and height, to accomodate field size changes
        if (view.getTranslateX()<0){ view.setTranslateX(1200);};
        if (view.getTranslateY()<0){ view.setTranslateY(800);};
        if (view.getTranslateX()>1200){ view.setTranslateX(0);};
        if (view.getTranslateY()>800){ view.setTranslateY(0);};
    }

    private double vectorMagnitude(Point2D vector){
        return Math.sqrt(Math.pow(vector.getX(),2)+Math.pow(vector.getY(),2));
    }

    public void setVelocity(Point2D velocity) {
        if (vectorMagnitude(velocity) <= maxVelocityMagnitude){
            this.velocity = velocity;
        }
        else{
            //normalize the velocity to maxVelocityMagnitude
            double factor = vectorMagnitude(velocity) / maxVelocityMagnitude;
            double x = velocity.getX() / factor;
            double y = velocity.getY() / factor;
            this.velocity = new Point2D(x,y);
        }
    }

    public Point2D getVelocity() {
        return velocity;
    }

    public Shape getView() {
        return view;
    }

    public void setView(Shape shape) {
        view = shape;
    }

    public boolean isAlive() {
        return alive;
    }

    public boolean isDead() {
        return !alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public double getRotate() {
        return view.getRotate();
    }

    public void rotateRight() {
        view.setRotate(view.getRotate() + ROTATE_STEP);
        //setVelocity(new Point2D(Math.cos(Math.toRadians(getRotate())), Math.sin(Math.toRadians(getRotate()))));
    }

    public void rotateLeft() {
        view.setRotate(view.getRotate() - ROTATE_STEP);
        //setVelocity(new Point2D(Math.cos(Math.toRadians(getRotate())), Math.sin(Math.toRadians(getRotate()))));
    }

    public void accelerate(){

        double xVelocity;
        double yVelocity;

        xVelocity = this.getVelocity().getX();
        yVelocity = this.getVelocity().getY();

        xVelocity = xVelocity + Math.cos(Math.toRadians(getRotate()))*ACCELERATION;
        yVelocity = yVelocity + Math.sin(Math.toRadians(getRotate()))*ACCELERATION;

        //setVelocity(new Point2D(Math.cos(Math.toRadians(getRotate())), Math.sin(Math.toRadians(getRotate()))));
        setVelocity(new Point2D(xVelocity,yVelocity));

    }

    public boolean isColliding(GameObject other) {

        //simple checking of probably rectangle bounds, not to exact
        //return getView().getBoundsInParent().intersects(other.getView().getBoundsInParent());
        try {
            return (Shape.intersect( view, other.getView()).getBoundsInLocal().getWidth() != -1);
        } catch ( Exception e) {
            AsterDroidsApp.log("Problem w czasie sprawdzania intersekcji Shapów: " + e.toString());
            return false;
        }
    }
}
