package src.game;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javafx.scene.layout.Pane;
import src.game.gameObjects.object;
import src.game.gameObjects.player;

public class game {
    private ArrayList <object> gameObjects = new ArrayList<object>();
    private long tick;

    public Pane test;

    public void start() {
        /*
         * Main Game Loop
         */
        final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                tick += 1;
                updateObjects();
                draw();
            }
        }, 0, 100, TimeUnit.MILLISECONDS);
    }

    public void init() {
        gameObjects.add(new player(1, 1, 4, 4));
    }

    public void draw() {
        System.out.println(tick); // Debug test print
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
