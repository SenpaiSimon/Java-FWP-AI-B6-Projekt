package com.javafwp;
import java.util.ArrayList;
import java.util.HashMap;

import com.javafwp.game.gameObjects.enemy;
import com.javafwp.game.gameObjects.plattform;
import com.javafwp.game.gameObjects.player;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;


public class gameLoop extends Application{
    private ArrayList <enemy> enemys = new ArrayList<enemy>();
    private ArrayList <plattform> plattforms = new ArrayList<plattform>();
    private player player;

    /*
     * Globals for fine-tuning
     */ 
    // Window Stuff
    private int width = 1280;
    private int height = 720;
    private Color backColor = Color.BLACK;

    // Player stuff
    private Color playerColor = Color.CORAL;
    private double gravity = 0.2;
    private double jumpForce = 10;

    // plattform stuff
    private double scrollSpeed = 2;

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
        init(primaryStage);

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

    public void init(Stage primaryStage) {
        // setup scene
        Scene scene = new Scene(appRoot);
        scene.setOnKeyPressed(event -> keys.put(event.getCode(), true));
        scene.setOnKeyReleased(event -> keys.put(event.getCode(), false));
        primaryStage.setTitle("Simple Sidescroller");
        primaryStage.setScene(scene);
        primaryStage.show();

        // add the player and background
        Rectangle background = new Rectangle(width, height);
        background.setFill(backColor);
        player = new player(10, 10, 10, 15, playerColor, gravity);

        // debug plattform
        plattform platt = new plattform(0, 500, 1000, 50, Color.WHITE);
        plattforms.add(platt);

        // add everything to the screen
        appRoot.getChildren().addAll(background, player.getEntity(), platt.getEntity());
    }

    public void keyActions() {
        if(isPressedKey(KeyCode.D)) {
            player.move(new Point2D(5, 0)); 
        }
        if(isPressedKey(KeyCode.A)) {
            player.move(new Point2D(-5, 0));
        }
        if(isPressedKey(KeyCode.SPACE)) {
            player.jump(jumpForce);
        }
    }

    public void update() {
        keyActions();
        player.update(plattforms, enemys);

        for(plattform object: plattforms) {
            object.update();
        }
        for(enemy object: enemys) {
            object.update();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
