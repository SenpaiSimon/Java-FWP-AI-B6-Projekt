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
import com.javafwp.game.highscoreSystem.highscoreSystem;
import com.javafwp.game.ownTypes.gameState;
import com.javafwp.sprites.imageLoader;
import com.javafwp.sound.musicPlayer;
import com.javafwp.sound.paralellSfxPlayer;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Hauptklasse des Spieles, hier werden alle anderen Klassen verwaltet
 * Erbt von javafx.application.Application
 * Implementiert globals.java
 */
public class gameLoop extends Application implements globals{
    AnimationTimer timer;
    private gameState gameState;
    Stage primaryStage;
    Text scoreText;
    imageLoader imageLoader = new imageLoader();

    private int score = 0;
    private paralellSfxPlayer scoreSfx = new paralellSfxPlayer("score.wav", 0.1, 5);

    /**
     * Globals are now in the globals interface
     */

    /**
     * Private Variable Declarations
     */
    // Window Stuff
    private Rectangle playingBackground;

    // Player stuff
    private player player;

    // plattform stuff
    private ArrayList <plattform> plattforms = new ArrayList<plattform>();
    private Paint plattformPaint = imageLoader.loadImage("platform.png");

    // missle stuff
    private ArrayList <projectile> projectiles = new ArrayList<projectile>();
    private Paint missleColor = imageLoader.loadImage("pizza.png");
    private paralellSfxPlayer missileSfx = new paralellSfxPlayer("throw.wav", 0.4, 5);

    // heatbar stuff
    private heatbar heatbar;

    // enemy stuff
    private ArrayList <enemy> enemys = new ArrayList<enemy>();
    int currentTick = 0;
    private paralellSfxPlayer enemySfx = new paralellSfxPlayer("enemy.wav", 0.5, 5);

    // highscore system
    highscoreSystem highscoreSystem = new highscoreSystem(textXPos, textYPos, maxEntries, maxNameLength);

    // death screen stuff
    private Text deathMessage;

    // main menu stuff
    private Rectangle menuBackground;
    private Rectangle menuOverlay;
    private int menuFrameCounter;
    private Paint[] menuFrames;

    // misc stuff
    private Rectangle shopBackground;
    private Text shopText;
    private Text resetText;
    private TextField password;

    // sound stuff
    private musicPlayer musicPlayer;

    // debug prints -- may slow down application
    public static boolean debug = false;

    private HashMap<KeyCode, Boolean> keys = new HashMap<KeyCode, Boolean>();

    private Pane appRoot = new Pane();
    private Pane gameRoot = new Pane();
    private Pane scoreRoot = new Pane();
    private Pane menuRoot = new Pane();
    private Pane shopRoot = new Pane();
    private Pane deathRoot = new Pane();


    /**
     * Initialisiert alle Notwendigen Bestandteile der Anwendung
     * Startet den Timer, welcher unser Spiel periodisch updated -- sollte mit 60 FPS laufen
     *
     * @param primaryStage Hauptanzeige, wird mit launch automatisch gesetzt
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        init(primaryStage);

        System.out.println(System.getProperty("os.name"));

        final long delay;
        if(System.getProperty("os.name").contains("Mac OS X"))  {
            delay = 8_333_334;
        }   else    {
            delay = 0;
        }

        // this will run at 60 fps
        timer = new AnimationTimer() {
            private long last_updated;

            // Game Loop
            @Override
            public void handle(long now) {
                if(debug) {
                    System.out.println(keys.toString());
                    System.out.println("Player X: " + player.getX() + "Player Y: " + player.getY());
                }

                if(now - last_updated >= delay)    {
                    last_updated = now;
                    update(now);
                }
            }
        };
        timer.start();
    }


    /**
     * Methode um Tastaturanschläge einfach abzufragen
     *
     * @param key KeyCode zum prüfen
     * @return boolean Ob Key gedrückt ist
     */
    private boolean isPressedKey(KeyCode key) {
        return keys.getOrDefault(key, false);
    }


    /**
     * Initialisiert alle Bestandteile der Hauptszene
     * Ruft die Init Funktionen der anderen Teile auf
     *
     * @param primaryStage Hauptanzeige, wird mit launch automatisch gesetzt
     */
    private void init(Stage primaryStage) {
        // setup the main scene
        Scene scene = new Scene(appRoot);
        scene.setOnKeyPressed(event -> keys.put(event.getCode(), true));
        scene.setOnKeyReleased(event -> keys.put(event.getCode(), false));
        scene.setFill(Color.BLACK);

        // setup the primary stage order has to be like this
        primaryStage.setTitle("Pizza Hut 2077");
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

        musicPlayer = new musicPlayer();

        // init the rest
        initPlayState();
        initMenuState();
        initShopState();
        initHeatbar();
        initDeathScreen();

        // set initial state
        switchState(com.javafwp.game.ownTypes.gameState.mainMenu);
    }

    /**
     * Initialisiert die Leiste welche Hitze anzeigt und dessen Bestandteile
     */
    private void initHeatbar() {
        heatbar = new heatbar(heatX, heatY, maxHeat, heatHeigth, Color.WHITE, Color.ORANGE);
        scoreRoot.getChildren().addAll(heatbar.getEntities());
    }

    /**
     * Initialisiert die Shop Seite und dessen Bestandteile
     */
    private void initShopState() {
        // background
        shopBackground = new Rectangle(width, height);
        shopBackground.setTranslateX(0);
        shopBackground.setTranslateY(0);
        shopBackground.setFill(imageLoader.loadImage("shopBackground.jpg"));

        // setup shop state
        shopText = new Text();
        shopText.setText("Developers: \n\nRobin Prillwitz\t\t-\t00805291 \nSimon Obermeier\t-\t00800498 \nAnton Kraus\t\t-\t00804697");
        shopText.setTranslateX(width/32);
        shopText.setTranslateY(height/8);
        shopText.setStyle("-fx-font: 50 arial;");
        shopText.setFill(Color.WHITE);

        // resetText
        resetText = new Text();
        resetText.setText("Reset ALL Scores globally!");
        resetText.setTranslateX(width/32 * 24);
        resetText.setTranslateY(height/8 * 7);
        resetText.setStyle("-fx-font: 25 arial;");
        resetText.setFill(Color.RED);

        // textbox
        password = new TextField();
        password.setTranslateY(resetText.getBoundsInParent().getMinY() + resetText.getBoundsInParent().getHeight() + 10);
        password.setTranslateX(width/32 * 24);
        password.setPromptText("Admin Pass");
        password.setFocusTraversable(false);
        password.setOnAction(event -> {
            boolean success = highscoreSystem.resetScore(password.getText());
            if(success) {
                resetText.setText("Success!");
            } else {
                resetText.setText("Wrong Password!");
            }
        });

        // add them to the screen
        shopRoot.getChildren().addAll(shopBackground, shopText, resetText, password);
    }

    /**
     * Initialisiert die Menu Seite und dessen Bestandteile
     */
    private void initMenuState() {
        // background Image
        menuBackground = new Rectangle(width, height);
        menuBackground.setTranslateX(0);
        menuBackground.setTranslateY(0);
        // menuBackground.setFill(Color.BLACK);
        menuBackground.setFill(imageLoader.loadImage("splashscreen.png"));

        menuFrameCounter = 0;
        menuFrames = new Paint[10];
        for(int i = 0; i < menuFrames.length; i++) {
            String filename = "Flames0"+i+".png";
            menuFrames[i] = imageLoader.loadImage(filename);
        }

        menuOverlay = new Rectangle(width, height);
        menuOverlay.setTranslateX(0);
        menuOverlay.setTranslateY(0);

        menuRoot.getChildren().addAll(menuBackground, menuOverlay);
    }

    /**
     * Initialisiert den Spielzustand und dessen Bestandteile
     */
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

    /**
     * Initialisiert den Todes Screen und dessen Bestandteile
     */
    private void initDeathScreen() {
        // text
        deathMessage = new Text();
        deathMessage.setText("u ded lol \nDominos wins!");
        deathMessage.setTranslateX(width/2);
        deathMessage.setTranslateY(height/4);
        deathMessage.setStyle("-fx-font: 50 arial;");
        deathMessage.setFill(Color.GOLD);

        // add everything
        deathRoot.getChildren().addAll(deathMessage);
        deathRoot.getChildren().addAll(highscoreSystem.getEntities());
    }


    /**
     * State Machine - Wechselt in den Zustand der Andwendung und lädt bzw entlädt
     * die jeweiligen Teile
     *
     * @param newState Wechselt in den gegebene State
     */
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
                    appRoot.getChildren().removeAll(deathRoot, gameRoot, scoreRoot);
                }
                appRoot.getChildren().add(menuRoot);
            break;

            case playing:
                if(gameState == com.javafwp.game.ownTypes.gameState.mainMenu) {
                    appRoot.getChildren().removeAll(menuRoot);
                } else if (gameState == com.javafwp.game.ownTypes.gameState.death) {
                    appRoot.getChildren().remove(deathRoot);
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
                resetText.setText("Reset ALL Scores globally!");
                password.clear();
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

                gameRoot.setEffect(new GaussianBlur());
                scoreRoot.setEffect(new GaussianBlur());

                appRoot.getChildren().addAll(deathRoot);
                highscoreSystem.generateText();
                highscoreSystem.addEntry(score);
            break;

            default:

            break;
        }

        gameState = newState;
    }

    /**
     * Fügt Plattformen in definierten Abständen hinzu
     */
    private void addPlattform() {
        if(plattforms.size() != 0) {
            plattform lastPlattform = plattforms.get(plattforms.size() - 1);
            if(lastPlattform.getX() + lastPlattform.getWidth() < width) { // plattform is fully in window
                plattform newPlatt = new plattform(width + randomBetweenBounds(distanceMinX, distanceMaxX), height/3 * 2 + randomBetweenBounds(-distanceY, distanceY), plattformWidth, plattformHeight, plattformPaint);
                plattforms.add(newPlatt);
                gameRoot.getChildren().add(newPlatt.getEntity());
                score++;
                scoreSfx.play();
            }
        } else {
            plattform firstPlatt = new plattform(width/4 * 3, height/2, plattformWidth, plattformHeight, plattformPaint);
            plattforms.add(firstPlatt);
            gameRoot.getChildren().add(firstPlatt.getEntity());
        }
    }

    /**
     * Entfernt Plattformen außerhalb des Bildschirms
     */
    private void removeOffscreenPlattform() {
        if(plattforms.size() != 0) {
            if(plattforms.get(0).getX() + plattforms.get(0).getWidth() + 100 < 0) {
                gameRoot.getChildren().remove(plattforms.get(0).getEntity());
                plattforms.remove(0);
            }
        }
    }


    /**
     * Wird durch das OnClick Event in der Hauptszene aufgerufen
     * Fügt ein neues Projektil hinzu
     * Die Richtung wird durch den Vektor zwischen Spieler und Maus angegeben
     *
     * @param mousePos Wird automatisch beim Event Listener gesetzt
     */
    private void addMissle(MouseEvent mousePos) {
        //shoot me baby
        Point2D dir = new Point2D(1, 0);;
        if(mousePos != null) {
            dir = new Point2D(-(player.getX() - mousePos.getX()), -(player.getY() - mousePos.getY()));
        }

        // retain a bit of the players velocity
        dir = dir.add(player.getVel().multiply(0.5));

        missileSfx.play();

        projectile newProj = new projectile(
            player.getX() + player.getWidth() / 2.0,
            player.getY(),
            missleLength, missleHeight, dir, missleColor);
        projectiles.add(newProj);
        gameRoot.getChildren().addAll(newProj.getEntity());
    }

    /**
     * Entfernt jegliche Projektile, welche nicht mehr auf dem Bildschirm liegen
     */
    private void removeOffscreenMissle() {
        for(projectile proj: projectiles) {
            if((proj.getX() > width + 100) ||          // right edge
               (proj.getX() + missleLength < -100) ||  // left edge
               (proj.getY() + missleHeight < -100) ||  // top edge
               (proj.getY() > height + 100)) {         // bottom edge
                gameRoot.getChildren().remove(proj.getEntity());
                projectiles.remove(proj);
                break;
            }
        }
    }

    /**
     * Fügt Gegner an zufälligen Stellen außerhalb des Bildschirm hinzu
     * Dies geschieht in definierten Zeitabständen
     */
    private void addEnemy() {
        if(currentTick >= ticksBetweenSpawns && enemys.size() <= maxEnemyCount) {
            int xPos = 0;
            int yPos = 0;
            Random ran = new Random();
            switch(1 + ran.nextInt(4)) {
                case 0: //left edge
                    xPos = (int)(player.getX() - minEnemyDistanceX);
                    yPos = randomBetweenBounds(0, height);
                break;

                case 1: //right edge
                    xPos = (int)(player.getX() + minEnemyDistanceX);
                    yPos = randomBetweenBounds(0, height);
                break;

                case 2: //top edge
                    xPos = randomBetweenBounds(0, width);
                    yPos = (int)(player.getY() - minEnemyDistanceY);
                break;

                case 3: //bottom edge
                    xPos = randomBetweenBounds(0, width);
                    yPos = (int)(player.getY() + minEnemyDistanceY);
                break;
            }
            Paint[] enemyFrames = new Paint[4];
            enemyFrames[0] = imageLoader.loadImage("dominos1.png");
            enemyFrames[1] = imageLoader.loadImage("dominos2.png");
            enemyFrames[2] = imageLoader.loadImage("dominos3.png");
            enemyFrames[3] = imageLoader.loadImage("dominos4.png");
            enemy enem = new enemy(xPos, yPos, enemyLength, enemyHeight, enemyFrames);
            enemys.add(enem);
            gameRoot.getChildren().add(enem.getEntity());
            currentTick = 0;
        }
        currentTick++;
    }

    /**
     * Handelt die Kollission zwischen Gegner und Spieler ab
     */
    private void enemyPlayerColl() {
        for(enemy enem: enemys) {
            if(player.getEntity().getBoundsInParent().intersects(enem.getEntity().getBoundsInParent())) {
                switchState(com.javafwp.game.ownTypes.gameState.death);
                break;
            }
        }
    }

    /**
     * Handelt die Kollission zwischen Gegner und Projektil ab
     */
    private void enemyProjColl() {
        loop:
        for(enemy enem: enemys) {
            for(projectile proj: projectiles) {
                if(enem.getEntity().getBoundsInParent().intersects(proj.getEntity().getBoundsInParent())) {
                    gameRoot.getChildren().removeAll(enem.getEntity(), proj.getEntity());
                    projectiles.remove(proj);
                    enemys.remove(enem);
                    score++;
                    enemySfx.play();
                    scoreSfx.play();
                    break loop;
                }
            }
        }
    }


    /**
     * Einfache Hilfsfunktion, welche einen zufälligen Wert zwischen den Grenzen zurückgibt
     *
     * @param min Minimaler Zufallswert
     * @param max Maximaler Zufallswert
     * @return int Zufallswert
     */
    private int randomBetweenBounds(int min, int max) {
        Random ran = new Random();
        return min + ran.nextInt(max - min);
    }

    /**
     * Setzt das Spiel zurück wenn der Spiele unten aus dem Bildschirm fällt
     */
    private void playerDeath() {
        if(player.getY() + player.getHeight() > height) {
            switchState(com.javafwp.game.ownTypes.gameState.death);
        }
    }

    /**
     * Setzt das Spiel in seinen initilan Spielzustand zurück nach z.B. gescheiterten Versuchen
     */
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

        scoreRoot.setEffect(null);
        gameRoot.setEffect(null);
    }

    /**
     * Updated den Score rechts oben beim Spielen
     */
    private void updateTexts() {
        scoreText.setText("Score: " + score);
    }

    /**
     * Methode um Aktionen in zusammenhang mit Tastenanschlägen auf der Tastatur abzuhandeln
     * Aktionen sind abhänging vom aktuellen Zustand des Spiels und wechseln auch bedingt den Zustand
     */
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
            } else if(gameState == com.javafwp.game.ownTypes.gameState.mainMenu) {
                switchState(com.javafwp.game.ownTypes.gameState.playing);
                synchronousInputDelay(100);
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


    /**
     * Input verzögerung für Tastenanschläge von Zustands-Wechseln um Flickern zu vermeiden
     *
     * @param delay Wartezeit
     */
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


    /**
     * Globale Update Methode um alle Gegenstände, Tastenanschläge und Animationen
     * zu aktualisieren
     *
     * @param tick Interner Systemtick
     */
    private void update(long tick) {
        keyActions();
        musicPlayer.updateMusic(gameState);

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
                object.update(tick, new Point2D(player.getX(), player.getY()), enemySpeed);
            }
            enemyPlayerColl();
            enemyProjColl();
        }   else if(gameState == com.javafwp.game.ownTypes.gameState.mainMenu)  {
            if(tick % 1000 == 0) {
                menuFrameCounter++;
                menuFrameCounter = menuFrameCounter % menuFrames.length;
                menuOverlay.setFill(menuFrames[menuFrameCounter]);
            }
        }
    }


    /**
     * Durchschleifen der Main Methode aus der mainStart.java
     * Notwendig da mainStart.java nicht von Application erbt und somit keine
     * launch(args) Mathode hat
     *
     * @param args Argumente aus der commandozeile, werden automatisch gesetzt
     */
    public static void main(String[] args) {
        launch(args);
    }
}
