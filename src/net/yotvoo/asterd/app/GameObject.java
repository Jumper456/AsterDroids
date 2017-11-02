package net.yotvoo.asterd.app;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.shape.Shape;

/**
 * @author
 */

public class GameObject {

    final Double ACCELERATION = 0.3d;
    private Node view;
    private Point2D velocity = new Point2D(0, 0);
    //private Point2D orientation = new Point2D(0,0);
    private Long ageStart;
    private final Long MAX_BULLET_AGE = 1000L; // 1 sek
    private final double MAX_VELOCITY_MAGNITUDE = 6; //TODO nie mogę ograniczać prędkośi pocisków,
                                                        // trzeba osobno sterować prędkościami różnych elementów

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

    public GameObject(Node view) {
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
        if (vectorMagnitude(velocity) <= MAX_VELOCITY_MAGNITUDE){
            this.velocity = velocity;
        }
    }

    public Point2D getVelocity() {
        return velocity;
    }

    public Node getView() {
        return view;
    }

    public void setView(Node node) {
        view = node;
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
        view.setRotate(view.getRotate() + 5);
        //setVelocity(new Point2D(Math.cos(Math.toRadians(getRotate())), Math.sin(Math.toRadians(getRotate()))));
    }

    public void rotateLeft() {
        view.setRotate(view.getRotate() - 5);
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
        return getView().getBoundsInParent().intersects(other.getView().getBoundsInParent());
        //return (Shape.intersect( view, other) != null);
    }
}
