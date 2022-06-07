package com.javafwp.sprites;

import javafx.scene.image.Image;

public class imageLoader {
    public Image loadImage(String fileName) {
        return new Image(getClass().getResourceAsStream(fileName));
    }
}
