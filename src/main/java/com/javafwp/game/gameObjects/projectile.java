package com.javafwp.game.gameObjects;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

public class projectile extends object {
    Point2D dir;

    public projectile(double xPos, double yPos, double length, double height, Color color, Point2D dir) {
        super(xPos, yPos, length, height, color);
        this.dir = dir.normalize();
        this.type = com.javafwp.game.ownTypes.type.projectile;
    }

    public void update(double speed) {
        entity.setTranslateX(entity.getTranslateX() + speed * dir.getX());
        entity.setTranslateY(entity.getTranslateY() + speed * dir.getY());
    }
}
