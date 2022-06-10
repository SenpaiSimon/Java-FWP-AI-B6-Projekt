package com.javafwp.game.gameObjects;

import com.javafwp.game.ownTypes.type;

import javafx.scene.Node;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

/**
 * Oberklasse für alle Zeichenbaren Objekte des Spiels
 * Implementiert objectInterface.java
 */
public class object implements objectInterface {
    protected type type;
    protected Rectangle entity;

    /**
     * Konstruktor für Spielobjekte
     * 
     * @param xPos
     * @param yPos
     * @param length
     * @param height
     * @param paint
     */
    public object(double xPos, double yPos, double length, double height, Paint paint) {
        entity = new Rectangle(length, height);
        entity.setTranslateX(xPos);
        entity.setTranslateY(yPos);
        entity.setFill(paint);
    }

    
    /** 
     * Liefert den Zugriff auf das innere JavaFX Object 
     * 
     * @return Node
     */
    public Node getEntity() {
        return entity;
    }

    
    /** 
     * Gibt die Dicke des Objekts zurück
     * 
     * @return double
     */
    public double getWidth () {
        return entity.getWidth();
    }

    
    /** 
     * Gibt die Höhe des Objekts zurück
     * 
     * @return double
     */
    public double getHeight () {
        return entity.getHeight();
    }

    
    /** 
     * Gibt den Typ des Objekts zurück
     * 
     * @return type
     */
    public type getType() {
        return type;
    }

    
    /** 
     * Gibt die X-Position des Objekts zurück
     * 
     * @return double
     */
    public double getX() {
        return entity.getTranslateX();
    }
    
    /** 
     * Gibt die Y-Position des Objekts zurück
     * 
     * @return double
     */
    public double getY() {
        return entity.getTranslateY();
    }

    
    /** 
     * Setzt die Füllfarbe des Objekts
     * 
     * @param paint
     */
    public void setFill(Paint paint) {
        entity.setFill(paint);
    }

    
    /** 
     * Setzt die X-Position des Objekts
     * 
     * @param x
     */
    public void setX(double x) {
        entity.setTranslateX(x);
    }

    
    /** 
     * Setzt die Y-Position des Objekts
     * 
     * @param y
     */
    public void setY(double y) {
        entity.setTranslateY(y);
    }

    
    /** 
     * Verschiebt die X-Position des Objekts um den Wert
     * 
     * @param x
     */
    public void addX(double x) {
        setX(getX() + x);
    }

    
    /** 
     * Verschiebt die Y-Position des Objekts um den Wert
     * 
     * @param y
     */
    public void addY(double y) {
        setY(getY() + y);
    }
}
