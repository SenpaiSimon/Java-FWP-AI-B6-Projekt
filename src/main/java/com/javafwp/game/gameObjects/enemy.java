package com.javafwp.game.gameObjects;

import javafx.geometry.Point2D;
import javafx.scene.paint.Paint;

/**
 * Gegener Objekt 
 * Erbt von Objekt.java
 */
public class enemy extends object{

    private Paint[] frames;
    private int animationCounter = 0;

    /**
     * Konstruktor für Gegner
     * 
     * @param xPos Obere Linke Ecke des Objekts
     * @param yPos Obere Linke Ecke des Objekts
     * @param length X-Länge des Objekts
     * @param height Y-Länge des Objekts
     * @param frames Animations Frames des Objekts
     */
    public enemy(double xPos, double yPos, double length, double height, Paint[] frames) {
        super(xPos, yPos, length, height, frames[0]);
        this.type = com.javafwp.game.ownTypes.type.enemy;

        this.frames = frames;
    }

    
    /** 
     * Bewegt Gegner genau auf de Spieler zu mit definierter Geschwindigkeit
     * 
     * @param tick Interner System tick
     * @param playerPos Aktuelle Position de Spielers
     * @param speed Bewegungsgeschwindigkeit zum Spieler
     */
    public void update(long tick, Point2D playerPos, double speed) {
        Point2D dir = new Point2D(
            playerPos.getX() - getX(),
            playerPos.getY() - getY()).normalize();

        addX(dir.getX() * speed);
        addY(dir.getY() * speed);

        if(tick % 500 == 0)    {
            animationCounter++;
            animationCounter = animationCounter % this.frames.length;
            this.setFill(this.frames[animationCounter]);
        }
    }
}
