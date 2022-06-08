package com.javafwp.game.gameObjects;

import javafx.geometry.Point2D;
import javafx.scene.paint.Paint;

public class enemy extends object{

    private Paint[] frames;
    private int animationCounter = 0;

    public enemy(double xPos, double yPos, double length, double height, Paint[] frames) {
        super(xPos, yPos, length, height, frames[0]);
        this.type = com.javafwp.game.ownTypes.type.enemy;

        this.frames = frames;
    }

    public void update(long tick, Point2D playerPos, double speed) {
        Point2D dir = new Point2D(
            playerPos.getX() - entity.getTranslateX(),
            playerPos.getY() - entity.getTranslateY()).normalize();

        entity.setTranslateX(entity.getTranslateX() + dir.getX() * speed);
        entity.setTranslateY(entity.getTranslateY() + dir.getY() * speed);

        if(tick % 500 == 0)    {
            animationCounter++;
            animationCounter = animationCounter % this.frames.length;
            this.setFill(this.frames[animationCounter]);
        }
    }
}
