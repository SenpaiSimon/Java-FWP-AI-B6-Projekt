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
     * @param xPos Obere Linke Ecke des Objekts
     * @param yPos Obere Linke Ecke des Objekts
     * @param length X-Länge des Objekts
     * @param height Y-Länge des Objekts
     * @param dir Bewegungsrichtung
     * @param paint Farbe des Objekts
     */
    public projectile(double xPos, double yPos, double length, double height, Point2D dir, Paint paint) {
        super(xPos, yPos, length, height, paint);
        this.dir = dir.normalize();
        this.type = com.javafwp.game.ownTypes.type.projectile;
    }

    
    /** 
     * Bewegt die Projektile in den festgelekten Richtungsvektor
     * 
     * @param speed Schnelligkeit in Richtungsvektor
     */
    public void update(double speed) {
        setX(getX() + speed * dir.getX());
        setY(getY() + speed * dir.getY());
    }
}
