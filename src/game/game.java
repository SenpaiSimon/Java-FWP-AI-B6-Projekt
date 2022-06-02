package src.game;

import java.util.ArrayList;

import src.game.gameObjects.object;
import src.game.gameObjects.player;

public class game {
    private ArrayList <object> gameObjects = new ArrayList<object>();




    public void start() {
        /*
         * Main Game Loop
         */
        while(true) {
            updateObjects();
            draw();
        }
    }

    public void init() {
        gameObjects.add(new player(1, 1, 4, 4));
    }

    public void draw() {
        for(object object: gameObjects) {
            object.draw();
        }
    }

    public void updateObjects() {
        for(object object: gameObjects) {
            object.update();
        }
    }
}
