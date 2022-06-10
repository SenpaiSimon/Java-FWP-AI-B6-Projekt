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
     * @param xPos
     * @param yPos
     * @param length
     * @param height
     * @param paint
     */
    public plattform(double xPos, double yPos, double length, double height, Paint paint) {
        super(xPos, yPos, length, height, paint);
        this.type = com.javafwp.game.ownTypes.type.plattform;
    }

    
    /** 
     * Bewegt die Plattformen zum linken Rand des Bildschirms
     * 
     * @param speed
     */
    public void update(double speed) {
        addX(-speed);
    }
}
