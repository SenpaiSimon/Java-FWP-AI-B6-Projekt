package com.javafwp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import com.javafwp.game.gameObjects.enemy;
import com.javafwp.game.gameObjects.heatbar;
import com.javafwp.game.gameObjects.plattform;
import com.javafwp.game.gameObjects.player;
import com.javafwp.game.gameObjects.projectile;
import com.javafwp.game.ownTypes.gameState;
import com.javafwp.sprites.imageLoader;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class gameLoop extends Application{
    AnimationTimer timer;
    private gameState gameState;
    Stage primaryStage;
    Text scoreText;
    imageLoader imageLoader = new imageLoader();

    private int score = 0;

    /*
     * Globals for fine-tuning
     */
    // Window Stuff
    private int width = 1280;
    private int height = 720;
    private Rectangle playingBackground;

    // Player stuff
    private player player;
    private double gravity = 0.4;
    private double jumpForce = 15;
    private double moveSpeed = 5;

    // plattform stuff
    private ArrayList <plattform> plattforms = new ArrayList<plattform>();
    private double scrollSpeed = 3;
    private int distanceMinX = 200;
    private int distanceMaxX = 350;
    private int distanceY = 150;
    private int plattformHeight = 21 * 2;
    private int plattformWidth = 68 * 2;
    private Paint plattformPaint = imageLoader.loadImage("platform.png");

    // missle stuff
    private ArrayList <projectile> projectiles = new ArrayList<projectile>();
    private double projectileSpeed = 10;
    private double missleLength = 32;
    private double missleHeight = 32;
    private Paint missleColor = imageLoader.loadImage("pizza.png");

    // heat stuff
    private int heatPerShot = 50;
    private int maxHeat = 300;
    private int coolSpeed = 2;
    private heatbar heatbar;
    private double heatX = 10;
    private double heatY = 10;
    private double heatHeigth = 10;

    // enemy stuff
    private ArrayList <enemy> enemys = new ArrayList<enemy>();
    private int maxEnemyCount = 6;
    private int ticksBetweenSpawns = 100;
    private int currentTick = 0;
    private int enemyLength = 48;
    private int enemyHeight = 48;
    private int enemySpeed = 1;
    private int  minEnemyDistanceX = width + 100;
    private int  minEnemyDistanceY = height + 100;

    // death screen stuff
    private Text deathMessage;

    // main menu stuff
    private Rectangle menuBackground;
    private Text title;
    private Rectangle displayHelpMesage;

    // shop stuff
    private Rectangle shopBackground;
    private Text shopText;

    // debug prints -- may slow down application
    public static boolean debug = false;

    private HashMap<KeyCode, Boolean> keys = new HashMap<KeyCode, Boolean>();

    private Pane appRoot = new Pane();
    private Pane gameRoot = new Pane();
    private Pane scoreRoot = new Pane();
    private Pane menuRoot = new Pane();
    private Pane shopRoot = new Pane();
    private Pane deahRoot = new Pane();

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
                update(now);
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

        // add listener for mouse positions
        gameRoot.setOnMouseClicked(mousePointer -> {
            if(gameState == com.javafwp.game.ownTypes.gameState.playing) {
                if(heatbar.addHeat(heatPerShot)) {
                    addMissle(mousePointer);
                }
            }
        });

        // init the rest
        initPlayState();
        initMenuState();
        initShopState();
        initHeatbar();
        initDeathScreen();

        // set initial state
        switchState(com.javafwp.game.ownTypes.gameState.mainMenu);
    }

    private void initHeatbar() {
        heatbar = new heatbar(heatX, heatY, maxHeat, heatHeigth, Color.WHITE, Color.ORANGE);
        scoreRoot.getChildren().addAll(heatbar.getEntity(), heatbar.getHeatbar());
    }

    private void initShopState() {
        // background
        shopBackground = new Rectangle(width, height);
        shopBackground.setTranslateX(0);
        shopBackground.setTranslateY(0);
        shopBackground.setFill(imageLoader.loadImage("shopBackground.jpg"));

        // setup shop state
        shopText = new Text();
        shopText.setText("this is shop");
        shopText.setTranslateX(width/2);
        shopText.setTranslateY(height/4);
        shopText.setStyle("-fx-font: 50 arial;");
        shopText.setFill(Color.GOLD);

        shopRoot.getChildren().addAll(shopBackground, shopText);
    }

    private void initMenuState() {
        // background Image
        menuBackground = new Rectangle(width, height);
        menuBackground.setTranslateX(0);
        menuBackground.setTranslateY(0);
        menuBackground.setFill(imageLoader.loadImage("splashscreen.png"));

        // setup menu state
        title = new Text();
        title.setText("JAVA FWP Sidescroller");
        title.setTranslateX(width/2);
        title.setTranslateY(height/4);
        title.setStyle("-fx-font: 50 arial;");
        title.setFill(Color.GOLD);

        //button
        displayHelpMesage = new Rectangle(200, 50);
        displayHelpMesage.setTranslateX(60);
        displayHelpMesage.setTranslateY(60);
        displayHelpMesage.setFill(Color.RED);
        displayHelpMesage.setOnMouseClicked(event -> {
            displayHelpMesagePressed(event);
        });

        menuRoot.getChildren().addAll(menuBackground, title, displayHelpMesage);
    }

    private void initPlayState() {
        // background
        playingBackground = new Rectangle(width, height);
        playingBackground.setTranslateX(0);
        playingBackground.setTranslateY(0);
        playingBackground.setFill(imageLoader.loadImage("playingBackground.jpg"));

        // add the player
        Paint[] idleFrames = new Paint[2];
        idleFrames[0] = imageLoader.loadImage("player-idle1.png");
        idleFrames[1] = imageLoader.loadImage("player-idle2.png");
        Paint[] runningFrames = new Paint[2];
        runningFrames[0] = imageLoader.loadImage("player-running1.png");
        runningFrames[1] = imageLoader.loadImage("player-running2.png");

        player = new player(
            (width/4.0) * 3 + 10,
            height/2.0 - 50,
            64.0, 64.0,
            gravity,
            idleFrames,
            runningFrames);

        // add the score text
        scoreText = new Text();
        scoreText.setTranslateX(heatX);
        scoreText.setTranslateY(heatY + heatHeigth + 30);
        scoreText.setStyle("-fx-font: 24 arial;");
        scoreText.setFill(Color.WHITE);

        // add game parts to its panes
        scoreRoot.getChildren().add(scoreText);
        gameRoot.getChildren().addAll(playingBackground, player.getEntity());
    }

    private void initDeathScreen() {
        // text
        deathMessage = new Text();
        deathMessage.setText("u ded lol \nDominos wins!");
        deathMessage.setTranslateX(width/2);
        deathMessage.setTranslateY(height/4);
        deathMessage.setStyle("-fx-font: 50 arial;");
        deathMessage.setFill(Color.GOLD);

        // add everything
        deahRoot.getChildren().addAll(deathMessage);
    }

    private void displayHelpMesagePressed(MouseEvent mousePointer) {
        System.out.println();
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
                } else if(gameState == com.javafwp.game.ownTypes.gameState.death) {
                    appRoot.getChildren().removeAll(deahRoot, gameRoot, scoreRoot);
                }
                appRoot.getChildren().add(menuRoot);
            break;

            case playing:
                if(gameState == com.javafwp.game.ownTypes.gameState.mainMenu) {
                    appRoot.getChildren().removeAll(menuRoot);
                } else if (gameState == com.javafwp.game.ownTypes.gameState.death) {
                    appRoot.getChildren().remove(deahRoot);
                }

                if(gameState != com.javafwp.game.ownTypes.gameState.death) {
                    // because we dont unload these when in death menu
                    appRoot.getChildren().addAll(gameRoot, scoreRoot);
                }
                resetGame(); // reset player on scene change
            break;

            case shop:
                if(gameState == com.javafwp.game.ownTypes.gameState.mainMenu) {
                    appRoot.getChildren().removeAll(menuRoot);
                }
                appRoot.getChildren().addAll(shopRoot);
            break;

            case exit:
                if(gameState == com.javafwp.game.ownTypes.gameState.mainMenu) {
                    primaryStage.close();
                }
            break;

            case death:
                if(gameState == com.javafwp.game.ownTypes.gameState.playing) {
                    // nothing to unload yet
                }
                appRoot.getChildren().addAll(deahRoot);
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
                plattform newPlatt = new plattform(width + randomBetweenBounds(distanceMinX, distanceMaxX), height/3 * 2 + randomBetweenBounds(-distanceY, distanceY), plattformWidth, plattformHeight, plattformPaint);
                plattforms.add(newPlatt);
                gameRoot.getChildren().add(newPlatt.getEntity());
                score++;
            }
        } else {
            plattform firstPlatt = new plattform(width/4 * 3, height/2, plattformWidth, plattformHeight, plattformPaint);
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

    private void addMissle(MouseEvent mousePos) {
        //shoot me baby
        Point2D dir = new Point2D(1, 0);;
        if(mousePos != null) {
            dir = new Point2D(-(player.getEntity().getTranslateX() - mousePos.getX()), -(player.getEntity().getTranslateY() - mousePos.getY()));
        }

        // retain a bit of the players velocity
        dir = dir.add(player.getVel().multiply(0.5));

        projectile newProj = new projectile(
            player.getEntity().getTranslateX() + player.getWidth() / 2.0,
            player.getEntity().getTranslateY(),
            missleLength, missleHeight, dir, missleColor);
        projectiles.add(newProj);
        gameRoot.getChildren().addAll(newProj.getEntity());
    }

    private void removeOffscreenMissle() {
        for(projectile p: projectiles) {
            if((p.getEntity().getTranslateX() > width + 100) || // right edge
               (p.getEntity().getTranslateX() + missleLength < -100) || // left edge
               (p.getEntity().getTranslateY() + missleHeight < -100) || // top edge
               (p.getEntity().getTranslateY() > height + 100)) { // bottom edge
                gameRoot.getChildren().remove(p.getEntity());
                projectiles.remove(p);
                break;
            }
        }
    }

    private void addEnemy() {
        if(currentTick >= ticksBetweenSpawns && enemys.size() <= maxEnemyCount) {
            int xPos = 0;
            int yPos = 0;
            Random ran = new Random();
            switch(1 + ran.nextInt(4)) {
                case 0: //left edge
                    xPos = (int)(player.getEntity().getTranslateX() - minEnemyDistanceX);
                    yPos = randomBetweenBounds(0, height);
                break;

                case 1: //right edge
                    xPos = (int)(player.getEntity().getTranslateX() + minEnemyDistanceX);
                    yPos = randomBetweenBounds(0, height);
                break;

                case 2: //top edge
                    xPos = randomBetweenBounds(0, width);
                    yPos = (int)(player.getEntity().getTranslateY() - minEnemyDistanceY);
                break;

                case 3: //bottom edge
                    xPos = randomBetweenBounds(0, width);
                    yPos = (int)(player.getEntity().getTranslateY() + minEnemyDistanceY);
                break;
            }
            Paint[] enemyFrames = new Paint[2];
            enemyFrames[0] = imageLoader.loadImage("dominos1.png");
            enemyFrames[1] = imageLoader.loadImage("dominos2.png");
            enemy enem = new enemy(xPos, yPos, enemyLength, enemyHeight, enemyFrames);
            enemys.add(enem);
            gameRoot.getChildren().add(enem.getEntity());
            currentTick = 0;
        }
        currentTick++;
    }

    private void enemyPlayerColl() {
        for(enemy enem: enemys) {
            if(player.getEntity().getBoundsInParent().intersects(enem.getEntity().getBoundsInParent())) {
                switchState(com.javafwp.game.ownTypes.gameState.death);
                break;
            }
        }
    }

    private void enemyProjColl() {
        loop:
        for(enemy enem: enemys) {
            for(projectile proj: projectiles) {
                if(enem.getEntity().getBoundsInParent().intersects(proj.getEntity().getBoundsInParent())) {
                    gameRoot.getChildren().removeAll(enem.getEntity(), proj.getEntity());
                    projectiles.remove(proj);
                    enemys.remove(enem);
                    score++;
                    break loop;
                }
            }
        }
    }

    private int randomBetweenBounds(int min, int max) {
        Random ran = new Random();
        return min + ran.nextInt(max - min);
    }

    private void playerDeath() {
        if(player.getEntity().getTranslateY() + player.getHeight() > height) {
            switchState(com.javafwp.game.ownTypes.gameState.death);
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
        for(projectile proj: projectiles) {
            gameRoot.getChildren().remove(proj.getEntity());
        }
        for(enemy enem: enemys) {
            gameRoot.getChildren().remove(enem.getEntity());
        }

        projectiles.clear();
        enemys.clear();
        plattforms.clear();
        heatbar.reset();
    }

    private void updateTexts() {
        scoreText.setText("Score: " + score);
    }

    private void keyActions() {
        if(isPressedKey(KeyCode.D)) {
            if(gameState == com.javafwp.game.ownTypes.gameState.playing) {
                player.move(new Point2D(moveSpeed, 0));
            }
        }
        if(isPressedKey(KeyCode.A)) {
            if(gameState == com.javafwp.game.ownTypes.gameState.playing) {
                player.move(new Point2D(-moveSpeed, 0));
            }
        }
        if(isPressedKey(KeyCode.SPACE)) {
            if(gameState == com.javafwp.game.ownTypes.gameState.playing) {
                player.jump(jumpForce);
            } else if (gameState == com.javafwp.game.ownTypes.gameState.death) {
                switchState(com.javafwp.game.ownTypes.gameState.playing);
            }
        }
        if(isPressedKey(KeyCode.ESCAPE)) {
            if(gameState == com.javafwp.game.ownTypes.gameState.playing) {
                switchState(com.javafwp.game.ownTypes.gameState.mainMenu);
            } else if(gameState == com.javafwp.game.ownTypes.gameState.mainMenu) {
                switchState(com.javafwp.game.ownTypes.gameState.exit);
            } else if(gameState == com.javafwp.game.ownTypes.gameState.shop)  {
                switchState(com.javafwp.game.ownTypes.gameState.mainMenu);
            } else if(gameState == com.javafwp.game.ownTypes.gameState.death) {
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

    private void update(long tick) {
        keyActions();

        if(gameState == com.javafwp.game.ownTypes.gameState.playing) {
             // player stuff
            player.update(tick, plattforms, scrollSpeed);
            playerDeath();

            // plattform stuff
            addPlattform();
            removeOffscreenPlattform();

            for(plattform object: plattforms) {
                object.update(scrollSpeed);
            }

            // text stuff
            updateTexts();

            // missle stuff
            for(projectile object: projectiles) {
                object.update(projectileSpeed);
            }
            removeOffscreenMissle();
            heatbar.update(coolSpeed);

            // enemy stuff
            addEnemy();
            for(enemy object: enemys) {
                object.update(tick, new Point2D(player.getEntity().getTranslateX(), player.getEntity().getTranslateY()), enemySpeed);
            }
            enemyPlayerColl();
            enemyProjColl();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
