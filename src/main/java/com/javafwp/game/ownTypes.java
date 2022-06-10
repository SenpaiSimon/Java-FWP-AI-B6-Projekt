package com.javafwp.game;

/**
 * Klasse, welche eigenen Typen definiert
 */
public class ownTypes {
    /**
     * Typ Enum um die Unterklassen von Objekt klar festzustellen
     */
    public enum type {
        enemy,
        plattform,
        player,
        projectile,
        heatbar
    };

    /**
     * Zustand des Spiels
     */
    public enum gameState {
        playing,
        exit,
        mainMenu,
        shop,
        death
    };
}
