package com.javafwp.game.gameObjects;

import java.util.ArrayList;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

public class player extends object{
    boolean canJump = true;
    Point2D vel;

    public player(double xPos, double yPos, double length, double height, Color color) {
        super(xPos, yPos, length, height, color);
        this.type = com.javafwp.game.ownTypes.type.player;
        vel = new Point2D(0, 0);
    }

    // Applys Gravity
    public void update(ArrayList<plattform> plattforms, ArrayList<enemy> enemys) {
        entity.setTranslateY(entity.getTranslateY() + 1);
    }

    // moves in x Dir
    public void move(Point2D dir) {
        entity.setTranslateX(entity.getTranslateX() + dir.getX());
    }   

    // jump yea
    public void jump() {
        if(canJump) {
            vel.add(0, -30);
            canJump = true;
        }
    }
}
