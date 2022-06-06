package com.javafwp.game.gameObjects;

import javafx.scene.paint.Color;

public class enemy extends object{
    public enemy(double xPos, double yPos, double length, double height, Color color) {
        super(xPos, yPos, length, height, color);
        this.type = com.javafwp.game.ownTypes.type.enemy;
    }

    @Override
    public void draw() {

    }

    @Override
    public void update() {

    }
}
