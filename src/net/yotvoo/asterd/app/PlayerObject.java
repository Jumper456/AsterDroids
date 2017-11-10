package net.yotvoo.asterd.app;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import net.yotvoo.asterd.app.GameObject;

public class PlayerObject extends GameObject {

    PlayerObject() {
        super();
/*
            Rectangle ship = new Rectangle(40, 20, Color.BLUE);
            ship.setArcWidth(10);
            ship.setArcHeight(10);
*/
        Polygon ship = new Polygon();
        ship.getPoints().addAll(-30d,-10d,
                0d,0d,
                -30d,10d);

        ship.setFill(Color.BLUEVIOLET);
        super.setView(ship);

    }
}
