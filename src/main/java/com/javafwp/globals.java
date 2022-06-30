package com.javafwp;

/**
 * Globale Variablen um Fine-Tuning an der Anwendung auszuführen
 */
public interface Globals {
    // Fenster Stuff

    /**
     * Breite des Fensters
     */
    int width = 1280;
    /**
     * Höhe des Fenster
     */
    int height = 720;


    // Spieler Stuff

    /**
     * Fallbeschleunigung des Spielers
     */
    double gravity = 0.4;
    /**
     * Sprungkraft des Spielers
     */
    double jumpForce = 15;
    /**
     * Bewegungsgeschwindigkeit des Spielers
     */
    double moveSpeed = 5;


    // Plattform Stuff

    /**
     * Geschwindigkeit der Plattformen
     */
    double scrollSpeed = 3;
    /**
     * Minimale X-Distanz zwischen zwei Plattformen
     */
    int distanceMinX = 200;
    /**
     * Maximale X-Distanz zwischen zwei Plattformen
     */
    int distanceMaxX = 350;
    /**
     * Maximale Y-Distanz über und unter der vorherigen Plattform
     */
    int distanceY = 150;
    /**
     * Höhe der Plattform
     */
    int plattformHeight = 21 * 2;
    /**
     * Breite der Plattform
     */
    int plattformWidth = 68 * 2;


    // Projektil Stuff

    /**
     * Geschwindigkeit der Projektile
     */
    double projectileSpeed = 10;
    /**
     * Länge eines Projektils
     */
    double missleLength = 32;
    /**
     * Höhe eines Projektils
     */
    double missleHeight = 32;


    // Hitzeleisten Stuff

    /**
     * Generierte Hitze pro Schuss
     */
    int heatPerShot = 50;
    /**
     * Maximale Hitze - definiert auch Länge
     */
    int maxHeat = 300;
    /**
     * Schnelligkeit des Abkühlens
     */
    int coolSpeed = 2;
    /**
     * X-Position der Hitzeleiste
     */
    double heatX = 10;
    /**
     * Y-Position der Hitzeleiste
     */
    double heatY = 10;
    /**
     * Höhe der Hitzeleiste
     */
    double heatHeigth = 10;


    // Gegener Stuff

    /**
     * Maximale Zahl gleichzeigter Gegner
     */
    int maxEnemyCount = 6;
    /**
     * Zeitintervalle zum Erscheinen der Gegenr
     */
    int ticksBetweenSpawns = 100;
    /**
     * Länge eines Gegners
     */
    int enemyLength = 48;
    /**
     * Höhe eines Gegners
     */
    int enemyHeight = 48;
    /**
     * Geschwindigkeit eines Gegners
     */
    int enemySpeed = 1;
    /**
     * Minimale Spawndistanz eines Gegners in X-Richtung
     */
    int minEnemyDistanceX = width + 100;
    /**
     * Minimale Spawndistanz eines Gegners in Y-Richtung
     */
    int minEnemyDistanceY = height + 100;


    // Highscore Stuff

    /**
     * Online Modus für Datenbank
     */
    boolean onlineMode = false;

    /**
     * Offline Passwort zum Daten zurücksetzen
     */
    String defaultPassword = "123456";

    /**
     * Maximale Anzahl an Einträgen
     */
    int maxEntries = 10;
    /**
     * Maximale Namenslänge
     */
    int maxNameLength = 10;
    /**
     * X-Pos des Highscores Texts
     */
    int textXPos = 100;
    /**
     * Y-Pos des Highscores Texts
     */
    int textYPos = 100;
}
