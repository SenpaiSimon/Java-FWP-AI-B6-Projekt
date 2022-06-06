package com.javafwp.game.gameObjects;

public class player extends object{
    boolean canJump = true;

    public player(double xPos, double yPos, double length, double height) {
        super(xPos, yPos, length, height);
        this.type = com.javafwp.game.ownTypes.type.player;
    }

    @Override
    public void draw() {

    }

    @Override
    public void update() {

    }
}
