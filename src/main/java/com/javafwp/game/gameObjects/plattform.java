package com.javafwp.game.gameObjects;

import javafx.scene.paint.Paint;

public class plattform extends object{
    public plattform(double xPos, double yPos, double length, double height, Paint paint) {
        super(xPos, yPos, length, height, paint);
        this.type = com.javafwp.game.ownTypes.type.plattform;
    }

    
    /** 
     * @param speed
     */
    public void update(double speed) {
        addX(-speed);
    }
}
