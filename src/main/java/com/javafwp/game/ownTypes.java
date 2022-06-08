package com.javafwp.game;

public class ownTypes {
    public enum type {
        enemy,
        plattform,
        player,
        projectile,
        heatbar
    };

    public enum gameState {
        playing,
        exit,
        mainMenu,
        shop,
        death
    };
}
