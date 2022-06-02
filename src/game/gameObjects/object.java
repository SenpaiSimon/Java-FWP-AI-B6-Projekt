package src.game.gameObjects;

import src.game.ownTypes.type;

/*
 * Superclass for all the drawable things in the game
 */
public class object {
    protected double xPos;
    protected double yPos;
    protected double length;
    protected double height;
    protected type type;


    public object(double xPos, double yPos, double length, double height, type type) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.length = length;
        this.height = height;
        this.type = type;
    }
}
