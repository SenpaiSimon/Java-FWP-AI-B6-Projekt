package game.gameObjects;

public class enemy extends object{
    public enemy(double xPos, double yPos, double length, double height) {
        super(xPos, yPos, length, height);
        this.type = game.ownTypes.type.enemy;
    }

    @Override
    public void draw() {
        
    }

    @Override
    public void update() {

    }
}
