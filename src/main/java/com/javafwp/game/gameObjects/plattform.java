package com.javafwp.game.gameObjects;

import javafx.scene.paint.Paint;

public class plattform extends object{
    public plattform(double xPos, double yPos, double length, double height, Paint paint) {
        super(xPos, yPos, length, height, paint);
        this.type = com.javafwp.game.ownTypes.type.plattform;
    }

    public void update(double speed) {
        entity.setTranslateX(entity.getTranslateX() - speed);
    }
}
