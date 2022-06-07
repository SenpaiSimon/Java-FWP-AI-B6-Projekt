package com.javafwp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import com.javafwp.game.gameObjects.enemy;
import com.javafwp.game.gameObjects.plattform;
import com.javafwp.game.gameObjects.player;
import com.javafwp.game.ownTypes.gameState;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class gameLoop extends Application{
    AnimationTimer timer;

    private gameState gameState;
    private ArrayList <enemy> enemys = new ArrayList<enemy>();
    private ArrayList <plattform> plattforms = new ArrayList<plattform>();
    private player player;
    Stage primaryStage;
    Text scoreText;
    
    private int score = 0;

    /*
     * Globals for fine-tuning
     */ 
    // Window Stuff
    private int width = 1280;
    private int height = 720;

    // Player stuff
    private Color playerColor = Color.CORAL;
    private double gravity = 0.4;
    private double jumpForce = 15;
    private double moveSpeed = 5;

    // plattform stuff
    private double scrollSpeed = 4;
    private int distanceMinX = 200;
    private int distanceMaxX = 350;
    private int distanceY = 150;
    private int plattformHeight = 10;
    private int plattformWidth = 100;
    private Color plattformColor = Color.WHITE;

    // main menu stuff
    Text title;

    // shop stuff
    Text shopText;

    // debug prints -- may slow down application
    public static boolean debug = false;

    private HashMap<KeyCode, Boolean> keys = new HashMap<KeyCode, Boolean>();

    private Pane appRoot = new Pane();
    private Pane gameRoot = new Pane();
    private Pane scoreRoot = new Pane();
    private Pane menuRoot = new Pane();
    private Pane shopRoot = new Pane();

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        init(primaryStage);

        // this will run at 60 fps
        timer = new AnimationTimer() {
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

    private boolean isPressedKey(KeyCode key) {
        return keys.getOrDefault(key, false);
    }

    private void init(Stage primaryStage) {
        // setup the main scene
        Scene scene = new Scene(appRoot);
        scene.setOnKeyPressed(event -> keys.put(event.getCode(), true));
        scene.setOnKeyReleased(event -> keys.put(event.getCode(), false));
        scene.setFill(Color.BLACK);

        // setup the primary stage order has to be like this
        primaryStage.setTitle("Simple Sidescroller");
        primaryStage.setScene(scene);
        scene.getWindow().setHeight(height);
        scene.getWindow().setWidth(width);
        primaryStage.show();

        // Setup playing state

        // add the player
        player = new player((width/4) * 3 + 10, height/2 - 50, 10, 15, playerColor, gravity);

        // add the score text
        scoreText = new Text();
        scoreText.setTranslateX(width/32);
        scoreText.setTranslateY(height/30);
        scoreText.setStyle("-fx-font: 24 arial;");
        scoreText.setFill(Color.WHITE);

        // add game parts to its panes
        scoreRoot.getChildren().add(scoreText);
        gameRoot.getChildren().add(player.getEntity());


        // setup menu state
        title = new Text();
        title.setText("JAVA FWP Sidescroller");
        title.setTranslateX(width/2);
        title.setTranslateY(height/4);
        title.setStyle("-fx-font: 50 arial;");
        title.setFill(Color.GOLD);

        menuRoot.getChildren().addAll(title);

        // setup shop state
        shopText = new Text();
        shopText.setText("this is shop");
        shopText.setTranslateX(width/2);
        shopText.setTranslateY(height/4);
        shopText.setStyle("-fx-font: 50 arial;");
        shopText.setFill(Color.GOLD);

        shopRoot.getChildren().addAll(shopText);

        // set initial state
        switchState(com.javafwp.game.ownTypes.gameState.mainMenu);
    }

    private void switchState(gameState newState) {
        if(gameState == newState)   {
            return; // nothing to do
        }
        
        switch(newState) {
            case mainMenu: 
                if(gameState == com.javafwp.game.ownTypes.gameState.playing) {
                    appRoot.getChildren().removeAll(gameRoot, scoreRoot);
                } else if(gameState == com.javafwp.game.ownTypes.gameState.shop) {
                    appRoot.getChildren().removeAll(shopRoot);
                } 
                appRoot.getChildren().add(menuRoot);
                System.out.println("main menu");
            break;

            case playing:
                if(gameState == com.javafwp.game.ownTypes.gameState.mainMenu) {
                    appRoot.getChildren().removeAll(menuRoot);
                }
                appRoot.getChildren().addAll(scoreRoot, gameRoot);
                resetGame(); // reset player on scene change
                System.out.println("playing");
            break;

            case shop:
                if(gameState == com.javafwp.game.ownTypes.gameState.mainMenu) {
                    appRoot.getChildren().removeAll(menuRoot);
                }
                appRoot.getChildren().addAll(shopRoot);
                System.out.println("shop");
            break;
            
            case exit:
                if(gameState == com.javafwp.game.ownTypes.gameState.mainMenu) {
                    primaryStage.close();
                }
            break;

            default:

            break;
        }

        gameState = newState;
    }

    private void addPlattform() {
        if(plattforms.size() != 0) {
            plattform lastPlattform = plattforms.get(plattforms.size() - 1);
            if(lastPlattform.getEntity().getTranslateX() + lastPlattform.getWidth() < width) { // plattform is fully in window
                plattform newPlatt = new plattform(width + randomBetweenBounds(distanceMinX, distanceMaxX), height/2 + randomBetweenBounds(-distanceY, distanceY), plattformWidth, plattformHeight, plattformColor);
                plattforms.add(newPlatt);
                gameRoot.getChildren().add(newPlatt.getEntity());
                score++;
            }
        } else {
            plattform firstPlatt = new plattform(width/4 * 3, height/2, plattformWidth, plattformHeight, Color.DEEPPINK);
            plattforms.add(firstPlatt);
            gameRoot.getChildren().add(firstPlatt.getEntity());
        }
    }

    private void removeOffscreenPlattform() {
        if(plattforms.size() != 0) {
            if(plattforms.get(0).getEntity().getTranslateX() + plattforms.get(0).getWidth() + 100 < 0) {
                gameRoot.getChildren().remove(plattforms.get(0).getEntity());
                plattforms.remove(0);
            }
        }
    }

    private int randomBetweenBounds(int min, int max) {
        Random ran = new Random();
        return min + ran.nextInt(max - min);
    }

    private void playerDeath() {
        if(player.getEntity().getTranslateY() + player.getHeight() > height) {
            resetGame();
        }
    }

    private void resetGame() {
        // what happens player when death
        player.death((width/4) * 3 + 10, height/2 - 50);
        score = 0;

        // remove them from the scene and clear the list
        for(plattform platt: plattforms) {
            gameRoot.getChildren().remove(platt.getEntity());
        }
        plattforms.clear();
    }

    private void updateTexts() {
        scoreText.setText("Score: " + score);
    }

    private void keyActions() {
        if(isPressedKey(KeyCode.D)) {
            player.move(new Point2D(moveSpeed, 0)); 
        }
        if(isPressedKey(KeyCode.A)) {
            player.move(new Point2D(-moveSpeed, 0));
        }
        if(isPressedKey(KeyCode.SPACE)) {
            player.jump(jumpForce);
        }
        if(isPressedKey(KeyCode.ESCAPE)) {
            if(gameState == com.javafwp.game.ownTypes.gameState.playing) {
                switchState(com.javafwp.game.ownTypes.gameState.mainMenu);
            } else if(gameState == com.javafwp.game.ownTypes.gameState.mainMenu) {
                switchState(com.javafwp.game.ownTypes.gameState.exit);
            }   else if(gameState == com.javafwp.game.ownTypes.gameState.shop)  {
                switchState(com.javafwp.game.ownTypes.gameState.mainMenu);
            }
            synchronousInputDelay(100);
        }
        if(isPressedKey(KeyCode.Q)) {
            if(gameState == com.javafwp.game.ownTypes.gameState.mainMenu) {
                switchState(com.javafwp.game.ownTypes.gameState.playing);
            }
            synchronousInputDelay(100);
        }
        if(isPressedKey(KeyCode.E)) { 
            if(gameState == com.javafwp.game.ownTypes.gameState.shop) {
                switchState(com.javafwp.game.ownTypes.gameState.mainMenu);
            } else if(gameState == com.javafwp.game.ownTypes.gameState.mainMenu) {
                switchState(com.javafwp.game.ownTypes.gameState.shop);
            }
            synchronousInputDelay(100);
        }
    }

    private void synchronousInputDelay(int delay) {
        if(gameState != com.javafwp.game.ownTypes.gameState.playing) {
            try {
                TimeUnit.MILLISECONDS.sleep(delay);
            } catch (Exception e) {
                // if we're here we've mangled the space time continuum
                System.out.println(e);
            };
        };
    }

    private void update() {
        keyActions();

        if(gameState == com.javafwp.game.ownTypes.gameState.playing) {
             // player stuff
            player.update(plattforms, enemys, scrollSpeed);
            playerDeath();

            // plattform stuff
            addPlattform();
            removeOffscreenPlattform();

            // text stuff
            updateTexts();

            for(plattform object: plattforms) {
                object.update(scrollSpeed);
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
