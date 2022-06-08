package com.javafwp.game.gameObjects;

import java.util.ArrayList;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class player extends object{
    boolean canJump = false;
    Point2D vel;
    double acc;

    Paint[] idleFrames;
    Paint[] runningFrames;
    int animationFrameCounter = 0;

    public player(double xPos, double yPos, double length, double height, double acc, Paint[] idleFrames, Paint[] runningFrames) {
        super(xPos, yPos, length, height, idleFrames[0]);
        this.type = com.javafwp.game.ownTypes.type.player;
        vel = new Point2D(0, 1);
        this.acc = acc;

        this.idleFrames = idleFrames;
        this.runningFrames = runningFrames;
    }

    // Applys Gravity and Handles Collissions
    public void update(long tick, ArrayList<plattform> plattforms, double scrollSpeed) {
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
            // collPlattform.setFill(Color.GREEN);

            // move player alongside plattform
            move(new Point2D(-scrollSpeed, 0));
        } else {
            // accel downwards
            vel = vel.add(0, acc);
            entity.setTranslateY(entity.getTranslateY() + vel.getY());
        }

        if(tick % 2000 == 0) {
            animationFrameCounter++;

            if(this.vel.magnitude() > 0)    {
                animationFrameCounter = animationFrameCounter % runningFrames.length;
                this.setFill((Paint)runningFrames[animationFrameCounter]);
            }   else    {
                animationFrameCounter = animationFrameCounter % idleFrames.length;
                this.setFill((Paint)idleFrames[animationFrameCounter]);
            }
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
