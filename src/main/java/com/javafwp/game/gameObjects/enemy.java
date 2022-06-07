package com.javafwp.game.gameObjects;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

public class enemy extends object{
    public enemy(double xPos, double yPos, double length, double height, Color color) {
        super(xPos, yPos, length, height, color);
        this.type = com.javafwp.game.ownTypes.type.enemy;
    }

    public void update(Point2D playerPos, double speed) {
        Point2D dir = new Point2D(playerPos.getX() - entity.getTranslateX(), playerPos.getY() - entity.getTranslateY()).normalize();
        entity.setTranslateX(entity.getTranslateX() + dir.getX() * speed);
        entity.setTranslateY(entity.getTranslateY() + dir.getY() * speed);
    }
}
