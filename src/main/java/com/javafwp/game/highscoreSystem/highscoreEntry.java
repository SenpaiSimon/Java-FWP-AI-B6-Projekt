package com.javafwp.game.highscoreSystem;

/**
 * Einträge, welche in der Highscore Tabelle gespeichert werden
 */
public class highscoreEntry {
    private String name;
    private int score;

    /**
     * Kontruktor für einen Highscore Eintrag
     * 
     * @param name
     * @param score
     */
    public highscoreEntry(String name, int score) {
        this.name = name;
        this.score = score;
    }

    
    /** 
     * Gibt den Score des Eintrags zurück
     * 
     * @return int
     */
    public int getScore() {
        return score;
    }

    
    /** 
     * Gibt den Namen des Eintrags zurück
     * 
     * @return String
     */
    public String getName() {
        return name;
    }
}
