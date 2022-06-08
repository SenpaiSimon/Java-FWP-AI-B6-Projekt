package com.javafwp.game.gameObjects;

import com.javafwp.game.ownTypes.type;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

/*
 * Superclass for all the drawable things in the game
 */
public class object{
    protected type type;
    protected Rectangle entity;

    public object(double xPos, double yPos, double length, double height, Paint paint) {
        entity = new Rectangle(length, height);
        entity.setTranslateX(xPos);
        entity.setTranslateY(yPos);
        entity.setFill(paint);
    }

    public Node getEntity() {
        return entity;
    }

    public double getWidth () {
        return entity.getWidth();
    }

    public double getHeight () {
        return entity.getHeight();
    }

    public void setFill(Paint paint) {
        entity.setFill(paint);
    }
}
