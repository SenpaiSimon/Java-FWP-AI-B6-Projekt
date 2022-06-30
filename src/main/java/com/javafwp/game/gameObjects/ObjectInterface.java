package com.javafwp.game.gameObjects;

import com.javafwp.game.OwnTypes.type;

import javafx.scene.Node;
import javafx.scene.paint.Paint;

/**
 * Interface welches alle Funktionen angibt, welche ein Spielobjekt haben sollte
 */
public interface ObjectInterface {
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
