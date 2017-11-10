package net.yotvoo.asterd.app;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class BulletObject extends GameObject {
    BulletObject() {
        super(new Circle(5, 5, 5, Color.CADETBLUE));
    }
}

