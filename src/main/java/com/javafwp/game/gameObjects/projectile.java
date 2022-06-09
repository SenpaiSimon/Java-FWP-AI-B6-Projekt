package com.javafwp.game.gameObjects;

import javafx.geometry.Point2D;
import javafx.scene.paint.Paint;

public class projectile extends object {
    Point2D dir;

    public projectile(double xPos, double yPos, double length, double height, Point2D dir, Paint paint) {
        super(xPos, yPos, length, height, paint);
        this.dir = dir.normalize();
        this.type = com.javafwp.game.ownTypes.type.projectile;
    }

    public void update(double speed) {
        setX(getX() + speed * dir.getX());
        setY(getY() + speed * dir.getY());
    }
}
