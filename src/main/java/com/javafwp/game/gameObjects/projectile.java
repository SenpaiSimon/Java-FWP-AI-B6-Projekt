package com.javafwp.game.gameObjects;

import javafx.scene.paint.Color;

public class projectile extends object {
    public projectile(double xPos, double yPos, double length, double height, Color color) {
        super(xPos, yPos, length, height, color);
        this.type = com.javafwp.game.ownTypes.type.projectile;
    }

    public void update(double speed) {
        entity.setTranslateX(entity.getTranslateX() + speed);
    }
}
