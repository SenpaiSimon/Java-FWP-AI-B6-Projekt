package com.javafwp.game.gameObjects;

import javafx.geometry.Point2D;
import javafx.scene.paint.Paint;

/**
 * Schießbare Projektile 
 * Erbt von Objekt.java
 */
public class projectile extends object {
    Point2D dir;

    /**
     * Konstruktor für Projektile
     * 
     * @param xPos
     * @param yPos
     * @param length
     * @param height
     * @param dir
     * @param paint
     */
    public projectile(double xPos, double yPos, double length, double height, Point2D dir, Paint paint) {
        super(xPos, yPos, length, height, paint);
        this.dir = dir.normalize();
        this.type = com.javafwp.game.ownTypes.type.projectile;
    }

    
    /** 
     * Bewegt die Projektile in den festgelekten Richtungsvektor
     * 
     * @param speed
     */
    public void update(double speed) {
        setX(getX() + speed * dir.getX());
        setY(getY() + speed * dir.getY());
    }
}
