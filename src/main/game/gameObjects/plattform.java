package main.game.gameObjects;

public class plattform extends object{
    public plattform(double xPos, double yPos, double length, double height) {
        super(xPos, yPos, length, height);
        this.type = main.game.ownTypes.type.plattform;
    }

    @Override
    public void draw() {

    }

    @Override
    public void update() {

    }
}
