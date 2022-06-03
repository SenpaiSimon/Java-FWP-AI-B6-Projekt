package game;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javafx.scene.layout.Pane;
import game.gameObjects.object;
import game.gameObjects.player;

public class game {
    private ArrayList <object> gameObjects = new ArrayList<object>();
    private long tick;

    // debug prints -- may slow down application
    public boolean debug;

    public Pane test;

    public game(boolean debug) {
        this.debug = debug;
    }

    public game() {
        this.debug = false;
    }

    public void start(int fps) {
        init();
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

                if(tick % 60 == 0 && debug) {
                    System.out.println("ticks: " + tick); // Debug test print
                }
        
            }
        }, 0, (1000/fps), TimeUnit.MILLISECONDS);
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
