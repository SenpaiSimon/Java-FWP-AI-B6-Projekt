package com.javafwp.game.gameObjects;

import com.javafwp.game.ownTypes.type;

/*
 * Superclass for all the drawable things in the game
 */
public class object {
    protected double xPos;
    protected double yPos;
    protected double length;
    protected double height;
    protected type type;

    public object(double xPos, double yPos, double length, double height) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.length = length;
        this.height = height;
    }

    public void draw() {};
    public void update() {};
}
