package com.javafwp.game.gameObjects;

import javafx.scene.paint.Paint;

/**
 * Plattform Objekte 
 * Erbt von Objekt.java
 */
public class plattform extends object{
    /**
     * Konstruktor für Plattformen
     * 
     * @param xPos Obere Linke Ecke des Objekts
     * @param yPos Obere Linke Ecke des Objekts
     * @param length X-Länge des Objekts
     * @param height Y-Länge des Objekts
     * @param paint Farbe des Objekts
     */
    public plattform(double xPos, double yPos, double length, double height, Paint paint) {
        super(xPos, yPos, length, height, paint);
        this.type = com.javafwp.game.ownTypes.type.plattform;
    }

    
    /** 
     * Bewegt die Plattformen zum linken Rand des Bildschirms
     * 
     * @param speed Geschwindkeit mit der sich die Plattformen nach links bewegen
     */
    public void update(double speed) {
        addX(-speed);
    }
}
