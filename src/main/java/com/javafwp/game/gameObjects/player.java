package com.javafwp.game.gameObjects;

import java.util.ArrayList;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

public class player extends object{
    boolean canJump = false;
    Point2D vel;
    double acc;

    public player(double xPos, double yPos, double length, double height, Color color, double acc) {
        super(xPos, yPos, length, height, color);
        this.type = com.javafwp.game.ownTypes.type.player;
        vel = new Point2D(0, 1);
        this.acc = acc;
    }

    // Applys Gravity and Handles Collissions
    public void update(ArrayList<plattform> plattforms, double scrollSpeed) {
        boolean coll = false;
        plattform collPlattform = null;

        // check if any intersection occured
        for(plattform plattform: plattforms) {
            if(entity.getBoundsInParent().intersects(plattform.getEntity().getBoundsInParent())) {
                collPlattform = plattform;
                coll = true;
                break;
            }
        } 
        
        // handle collision
        if(coll) {
            vel = new Point2D(vel.getX(), 0);
            entity.setTranslateY(collPlattform.getEntity().getTranslateY() - entity.getHeight());
            canJump = true;
            collPlattform.setFill(Color.GREEN);
            
            // move player alongside plattform
            move(new Point2D(-scrollSpeed, 0));
        } else {
            // accel downwards
            vel = vel.add(0, acc);
            entity.setTranslateY(entity.getTranslateY() + vel.getY());
        }
    }

    // moves in x Dir
    public void move(Point2D dir) {
        entity.setTranslateX(entity.getTranslateX() + dir.getX());
    }   

    // jumping
    public void jump(double force) {
        if(canJump) {
            entity.setTranslateY(entity.getTranslateY() - 1);
            vel = vel.add(0, -force);
            canJump = false;
        }
    }

    // dying
    public void death(double spawnX, double spawnY) { 
        entity.setTranslateY(spawnY);
        entity.setTranslateX(spawnX);
        vel = new Point2D(0, 0);
    }

    public boolean getCanJump() {
        return canJump;
    }
}
