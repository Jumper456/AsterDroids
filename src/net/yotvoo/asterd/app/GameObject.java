package net.yotvoo.asterd.app;

import javafx.geometry.Point2D;
import javafx.scene.shape.Shape;

@SuppressWarnings("SameParameterValue")
class GameObject {
    private Shape view;
    private Point2D velocity = new Point2D(0, 0);
    @SuppressWarnings("CanBeFinal")
    private long ageStart;
    private double maxVelocityMagnitude = 6d;
    private boolean alive = true;
    //current size (losly corresponding to shape size), it is about how many times it can split
    private int gameObjectSize;


    void setMaxVelocityMagnitude(double maxVelocityMagnitude) {
        this.maxVelocityMagnitude = maxVelocityMagnitude;
    }

    int getGameObjectSize() {
        return gameObjectSize;
    }

    void setGameObjectSize(int sizeParam) {
        this.gameObjectSize = sizeParam;
    }


    /**
     * @return object age in ms
     */
    private long getAge() {
        return System.currentTimeMillis() - ageStart;
    }

/*
    public void resetAge() {
        this.ageStart = System.currentTimeMillis();
    }
*/

/*
    public boolean isOlderThen(Long age){
        return ( getAge() > age );
    }
*/

    boolean isTooOld(){
        return ( getAge() > Constants.MAX_BULLET_AGE);
    }


    Point2D getOrientation() {
        return new Point2D(Math.cos(Math.toRadians(getRotate())), Math.sin(Math.toRadians(getRotate())));
    }


    GameObject(Shape view) {
        this.view = view;
        ageStart = System.currentTimeMillis();
    }

    GameObject() {
        ageStart = System.currentTimeMillis();
    }

    void update() {
        view.setTranslateX(view.getTranslateX() + velocity.getX());
        view.setTranslateY(view.getTranslateY() + velocity.getY());

        //TODO change the bounds to the pane with and height, to acomodate field size changes
        if (view.getTranslateX()<0){ view.setTranslateX(1200);}
        if (view.getTranslateY()<0){ view.setTranslateY(800);}
        if (view.getTranslateX()>1200){ view.setTranslateX(0);}
        if (view.getTranslateY()>800){ view.setTranslateY(0);}
    }

    private double vectorMagnitude(Point2D vector){
        return Math.sqrt(Math.pow(vector.getX(),2)+Math.pow(vector.getY(),2));
    }

    void setVelocity(Point2D velocity) {
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

    Point2D getVelocity() {
        return velocity;
    }

    Shape getView() {
        return view;
    }

    void setView(Shape shape) {
        view = shape;
    }

    boolean isDead() {
        return !alive;
    }

    void setAlive(boolean alive) {
        this.alive = alive;
    }

    private double getRotate() {
        return view.getRotate();
    }

    void rotateRight() {
        view.setRotate(view.getRotate() + Constants.ROTATE_STEP);
        //setVelocity(new Point2D(Math.cos(Math.toRadians(getRotate())), Math.sin(Math.toRadians(getRotate()))));
    }

    void rotateLeft() {
        view.setRotate(view.getRotate() - Constants.ROTATE_STEP);
        //setVelocity(new Point2D(Math.cos(Math.toRadians(getRotate())), Math.sin(Math.toRadians(getRotate()))));
    }

    void accelerate(){

        double xVelocity;
        double yVelocity;

        xVelocity = this.getVelocity().getX();
        yVelocity = this.getVelocity().getY();

        xVelocity = xVelocity + Math.cos(Math.toRadians(getRotate()))*Constants.ACCELERATION;
        yVelocity = yVelocity + Math.sin(Math.toRadians(getRotate()))*Constants.ACCELERATION;

        //setVelocity(new Point2D(Math.cos(Math.toRadians(getRotate())), Math.sin(Math.toRadians(getRotate()))));
        setVelocity(new Point2D(xVelocity,yVelocity));

    }

    boolean isColliding(GameObject other) {

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
