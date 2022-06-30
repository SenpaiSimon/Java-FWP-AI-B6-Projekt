package com.javafwp.game.highscoreSystem;

/**
 * Einträge, welche in der Highscore Tabelle gespeichert werden
 */
public class HighscoreEntry {
    private String name;
    private int score;

    /**
     * Kontruktor für einen Highscore Eintrag
     * 
     * @param name Name des Eintrags
     * @param score Erreichte Punkte
     */
    public HighscoreEntry(String name, int score) {
        this.name = name;
        this.score = score;
    }

    
    /** 
     * Gibt den Score des Eintrags zurück
     * 
     * @return int Punkte des Eintrags
     */
    public int getScore() {
        return score;
    }

    
    /** 
     * Gibt den Namen des Eintrags zurück
     * 
     * @return String Name des Eintrags
     */
    public String getName() {
        return name;
    }
}
