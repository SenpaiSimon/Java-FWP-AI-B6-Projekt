package com.javafwp.game.gameObjects;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class heatbar extends object {
    private Color heatColor;
    private Rectangle heat;
    private int currentHeat;

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

    public void update(double coolSpeed) {
        if(currentHeat >= 0) {
            currentHeat -= coolSpeed;
        } 
        if(currentHeat < 0) {
            currentHeat = 0;
        }

        heat.setWidth(currentHeat);
    }

    public boolean addHeat(int heat) {
        if(currentHeat + heat <= entity.getWidth()) {
            currentHeat += heat;
            return true;
        } else {
            return false;
        }
    }

    public void reset() {
        currentHeat = 0;
        heat.setWidth(currentHeat);
    }

    public Node getHeatbar() {
        return heat;
    }
}
