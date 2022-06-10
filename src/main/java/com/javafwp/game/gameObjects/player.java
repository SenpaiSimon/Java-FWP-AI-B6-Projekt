package com.javafwp.game.gameObjects;

import java.util.ArrayList;

import javafx.geometry.Point2D;
import javafx.scene.paint.Paint;

/**
 * Spieler Objekt 
 * Erbt von Objekt.java
 */
public class player extends object{
    boolean canJump = false;
    Point2D vel;

    double acc;

    Paint[] idleFrames;
    Paint[] runningFrames;
    int animationFrameCounter = 0;

    /**
     * Konstruktor für den Spieler
     * 
     * @param xPos
     * @param yPos
     * @param length
     * @param height
     * @param acc
     * @param idleFrames
     * @param runningFrames
     */
    public player(double xPos, double yPos, double length, double height, double acc, Paint[] idleFrames, Paint[] runningFrames) {
        super(xPos, yPos, length, height, idleFrames[0]);
        this.type = com.javafwp.game.ownTypes.type.player;
        vel = new Point2D(0, 1);
        this.acc = acc;

        this.idleFrames = idleFrames;
        this.runningFrames = runningFrames;
    }

    
    /** 
     * Prüft Kolliossionen des Spielers ab
     * Gibt dem Spieler Schwerkraft
     * Setzt die Animationen des Spielers
     * 
     * @param tick
     * @param plattforms
     * @param scrollSpeed
     */
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
            setY(collPlattform.getY() - getHeight());
            canJump = true;

            // move player alongside plattform
            move(new Point2D(-scrollSpeed, 0));
        } else {
            // accel downwards
            vel = vel.add(0, acc);
            addY(vel.getY());
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

    
    /** 
     * Gibt den Geschwindigkeitsvektor des Spielers an
     * 
     * @return Point2D
     */
    public Point2D getVel() {
        return vel;
    }

    
    /** 
     * Bewegt den Spieler um einen Vektor
     * 
     * @param dir
     */
    // moves in x Dir
    public void move(Point2D dir) {
        addX(dir.getX());
    }

    
    /** 
     * Lässt den Spieler springen mit einer definierten Kraft
     * 
     * @param force
     */
    // jumping
    public void jump(double force) {
        if(canJump) {
            addY(-1);
            vel = vel.add(0, -force);
            canJump = false;
        }
    }

    
    /**
     * Setzt den Spieler nach einen Reset zurück
     *  
     * @param spawnX
     * @param spawnY
     */
    // dying
    public void death(double spawnX, double spawnY) {
        setY(spawnY);
        setX(spawnX);
        vel = new Point2D(0, 0);
    }

    
    /** 
     * Gibt an ob der Spieler zur Zeit springen kann/darf
     * 
     * @return boolean
     */
    public boolean getCanJump() {
        return canJump;
    }
}
