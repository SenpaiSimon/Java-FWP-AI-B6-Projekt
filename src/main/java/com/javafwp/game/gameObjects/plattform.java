package com.javafwp.game.gameObjects;

import javafx.scene.paint.Color;

public class plattform extends object{
    public plattform(double xPos, double yPos, double length, double height, Color color) {
        super(xPos, yPos, length, height, color);
        this.type = com.javafwp.game.ownTypes.type.plattform;
    }

    public void update(double speed) {
        entity.setTranslateX(entity.getTranslateX() - speed);
    }
}
