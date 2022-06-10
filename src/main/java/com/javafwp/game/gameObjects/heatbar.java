package com.javafwp.game.gameObjects;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Hitzeleisten Objekt 
 * Erbt von Objekt.java
 */
public class heatbar extends object {
    private Color heatColor;
    private Rectangle heat;
    private int currentHeat;

    /**
     * Konstruktor für Hitzeleiste
     * 
     * @param xPos
     * @param yPos
     * @param maxHeat
     * @param height
     * @param baseColor
     * @param heatColor
     */
    public heatbar(double xPos, double yPos, double maxHeat, double height, Color baseColor, Color heatColor) {
        super(xPos, yPos, maxHeat, height, baseColor);
        this.heatColor = heatColor;
        heat = new Rectangle(50, height);
        heat.setTranslateX(xPos);
        heat.setTranslateY(yPos);
        heat.setFill(this.heatColor);
        currentHeat = 0;
        this.type = com.javafwp.game.ownTypes.type.heatbar;
    }

    
    /** 
     * Kühlt die Hitzeleiste nach und nach ab
     * 
     * @param coolSpeed
     */
    public void update(double coolSpeed) {
        if(currentHeat >= 0) {
            currentHeat -= coolSpeed;
        } 
        if(currentHeat < 0) {
            currentHeat = 0;
        }

        heat.setWidth(currentHeat);
    }

    
    /** 
     * Fügt Hitze hinzu zur leiste
     * 
     * @param heat
     * @return boolean
     */
    public boolean addHeat(int heat) {
        if(currentHeat + heat <= entity.getWidth()) {
            currentHeat += heat;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Setzt die Hitze auf 0 zurück
     */
    public void reset() {
        currentHeat = 0;
        heat.setWidth(currentHeat);
    }

    
    /** 
     * Gibt die inneren Node Objekte zurück
     * 
     * @return Node[]
     */
    public Node[] getEntities() {
        return new Node[]{entity, heat};
    }
}
