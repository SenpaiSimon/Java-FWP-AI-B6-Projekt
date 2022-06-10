package com.javafwp;

public interface globals {
    // Window Stuff
    int width = 1280;
    int height = 720;

    // Player stuff
    double gravity = 0.4;
    double jumpForce = 15;
    double moveSpeed = 5;

    // plattform stuff
    double scrollSpeed = 3;
    int distanceMinX = 200;
    int distanceMaxX = 350;
    int distanceY = 150;
    int plattformHeight = 21 * 2;
    int plattformWidth = 68 * 2;

    // missle stuff
    double projectileSpeed = 10;
    double missleLength = 32;
    double missleHeight = 32;

    // heat stuff
    int heatPerShot = 50;
    int maxHeat = 300;
    int coolSpeed = 2;
    double heatX = 10;
    double heatY = 10;
    double heatHeigth = 10;

    // enemy stuff
    int maxEnemyCount = 6;
    int ticksBetweenSpawns = 100;
    int enemyLength = 48;
    int enemyHeight = 48;
    int enemySpeed = 1;
    int minEnemyDistanceX = width + 100;
    int minEnemyDistanceY = height + 100;
}
