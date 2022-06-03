package game.gameObjects;

public class player extends object{
    public player(double xPos, double yPos, double length, double height) {
        super(xPos, yPos, length, height);
        this.type = game.ownTypes.type.player;
    }

    @Override
    public void draw() {
        
    }

    @Override
    public void update() {

    }
}
