package com.javafwp;
import java.util.ArrayList;
import java.util.HashMap;

import com.javafwp.game.gameObjects.object;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;


public class gameLoop extends Application{
    private ArrayList <object> gameObjects = new ArrayList<object>();

    // debug prints -- may slow down application
    public boolean debug;

    private HashMap<KeyCode, Boolean> keys = new HashMap<KeyCode, Boolean>();

    private Pane appRoot = new Pane();
    private Pane gameRoot = new Pane();
    private Pane uiRoot = new Pane();

    public gameLoop(boolean debug) {
        this.debug = debug;
    }

    public gameLoop() {
        this.debug = false;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        System.out.println("start");
        init();
        Scene scene = new Scene(appRoot);
        scene.setOnKeyPressed(event -> keys.put(event.getCode(), true));
        scene.setOnKeyReleased(event -> keys.put(event.getCode(), false));
        primaryStage.setTitle("Simple Sidescroller");
        primaryStage.setScene(scene);
        primaryStage.show();


        // this will run at 60 fps
        AnimationTimer timer = new AnimationTimer() {
            @Override
            // Game Loop
            public void handle(long now) {
                // System.out.println(now);
                System.out.println(keys.toString());
                update();
            }
        };

        timer.start();
    }

    public void init() {
        Rectangle background = new Rectangle(1280, 720);

        appRoot.getChildren().addAll(background);
    }

    public void update() {

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

    public static void main(String[] args) {
        launch(args);
    }
}
