package com.javafwp.game.gameObjects;

import com.javafwp.game.ownTypes.type;

import javafx.scene.Node;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

/*
 * Superclass for all the drawable things in the game
 */
public class object implements objectInterface {
    protected type type;
    protected Rectangle entity;

    public object(double xPos, double yPos, double length, double height, Paint paint) {
        entity = new Rectangle(length, height);
        entity.setTranslateX(xPos);
        entity.setTranslateY(yPos);
        entity.setFill(paint);
    }

    
    /** 
     * @return Node
     */
    public Node getEntity() {
        return entity;
    }

    
    /** 
     * @return double
     */
    public double getWidth () {
        return entity.getWidth();
    }

    
    /** 
     * @return double
     */
    public double getHeight () {
        return entity.getHeight();
    }

    
    /** 
     * @return type
     */
    public type getType() {
        return type;
    }

    
    /** 
     * @return double
     */
    public double getX() {
        return entity.getTranslateX();
    }
    
    /** 
     * @return double
     */
    public double getY() {
        return entity.getTranslateY();
    }

    
    /** 
     * @param paint
     */
    public void setFill(Paint paint) {
        entity.setFill(paint);
    }

    
    /** 
     * @param x
     */
    public void setX(double x) {
        entity.setTranslateX(x);
    }

    
    /** 
     * @param y
     */
    public void setY(double y) {
        entity.setTranslateY(y);
    }

    
    /** 
     * @param x
     */
    public void addX(double x) {
        setX(getX() + x);
    }

    
    /** 
     * @param y
     */
    public void addY(double y) {
        setY(getY() + y);
    }
}
