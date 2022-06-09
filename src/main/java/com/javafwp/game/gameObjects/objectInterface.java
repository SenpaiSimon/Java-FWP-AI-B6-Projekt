package com.javafwp.game.gameObjects;

import com.javafwp.game.ownTypes.type;

import javafx.scene.Node;
import javafx.scene.paint.Paint;

public interface objectInterface {
    public Node getEntity();
    public double getWidth ();
    public double getHeight ();
    public double getX();
    public double getY();
    public type getType();
    public void setFill(Paint paint);
    public void setX(double x);
    public void setY(double y);
    public void addX(double x);
    public void addY(double y);
}
