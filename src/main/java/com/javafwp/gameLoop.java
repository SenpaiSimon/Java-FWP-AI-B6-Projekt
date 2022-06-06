package com.javafwp;
import java.util.ArrayList;
import java.util.HashMap;

import com.javafwp.game.gameObjects.enemy;
import com.javafwp.game.gameObjects.object;
import com.javafwp.game.gameObjects.plattform;
import com.javafwp.game.gameObjects.player;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;


public class gameLoop extends Application{
    private ArrayList <enemy> enemys = new ArrayList<enemy>();
    private ArrayList <plattform> plattforms = new ArrayList<plattform>();
    private player player;

    // debug prints -- may slow down application
    public boolean debug = true;

    private HashMap<KeyCode, Boolean> keys = new HashMap<KeyCode, Boolean>();

    private Pane appRoot = new Pane();
    private Pane gameRoot = new Pane();
    private Pane uiRoot = new Pane();

    private boolean isPressedKey(KeyCode key) {
        return keys.getOrDefault(key, false);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
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
                if(debug) {
                    System.out.println(keys.toString());
                    System.out.println("Player X: " + player.getEntity().getTranslateX() + "Player Y: " + player.getEntity().getTranslateY());
                }
                update();
            }
        };

        timer.start();
    }

    public void init() {
        Rectangle background = new Rectangle(1280, 720);
        player = new player(10, 10, 10, 15, javafx.scene.paint.Color.CORAL);
        appRoot.getChildren().addAll(background, player.getEntity());
    }

    public void keyActions() {
        if(isPressedKey(KeyCode.D)) {
            player.move(new Point2D(5, 0)); 
        }
        if(isPressedKey(KeyCode.A)) {
            player.move(new Point2D(-5, 0));
        }
        if(isPressedKey(KeyCode.SPACE)) {
            player.jump();
        }
    }

    public void update() {
        keyActions();
        for(plattform object: plattforms) {
            object.update();
        }
        for(enemy object: enemys) {
            object.update();
        }
        player.update(plattforms, enemys);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
