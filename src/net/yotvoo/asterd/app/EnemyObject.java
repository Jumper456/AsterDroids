package net.yotvoo.asterd.app;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;


public class EnemyObject extends GameObject {

/*
        private double getRandomSize(double maxSize){
            double size = maxSize * Math.random();
            if (size < MIN_ENEMY_SIZE) size = MIN_ENEMY_SIZE;
            return size;
        }
*/

    private static Double[] generateRandomPolygon(Double baseSize, int vertexCount){

        Double  elements[] = new Double[vertexCount*2];

        for (int i = 0; i < vertexCount*2 ; i+=2){
            elements[i] = Math.random() * baseSize;
            elements[i+1] = Math.random() * baseSize;
        }

        return elements;
    }


    private Shape preparePolyAsteroid(double size, int vertexCount){
        double sizeCopy = size * 10;
        Polygon polygon = new Polygon();
        polygon.getPoints().addAll(-0.30d*sizeCopy, -0.10d*sizeCopy, 0d, 0d, -0.30d*sizeCopy,
                0.10d*sizeCopy, -0.20d*sizeCopy, -0.10d*sizeCopy);
        //polygon.getPoints().addAll(generateRandomPolygon((double)size, vertexCount));
        polygon.setFill(Color.DARKGOLDENROD);
        return polygon;
    }

    private Shape prepareCircleAsteroid(double size) {
        return  new Circle(0, 0, size, Color.DARKGOLDENROD);
    }


    private Shape prepareRectangleAsteroid(double size) {
        return  new Rectangle( size, size, Color.DARKGOLDENROD);
    }

    EnemyObject(int size) {
        super();
        setGameObjectSize(size);
        Shape shape = prepareCircleAsteroid(size);
        super.setView(shape);

/*
            double random = Math.random();
            if (random < 0.3d) {
                Shape shape = preparePolyAsteroid(size ,5);
                super.setView(shape);
            }
            else if (random < 0.6d) {
                Shape shape = prepareCircleAsteroid(size);
                super.setView(shape);
            }
            else if (random < 0.9d) {
                Shape shape = prepareRectangleAsteroid(size);
                super.setView(shape);

            }
            else {
                Shape shape = preparePolyAsteroid(size ,8);
                super.setView(shape);

            }
*/
    }

}
