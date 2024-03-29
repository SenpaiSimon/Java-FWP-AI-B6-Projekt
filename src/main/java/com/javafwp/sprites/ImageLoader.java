package com.javafwp.sprites;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

// !! PUT ALL IMAGES IN ROOT DIRECTORY OF SPRITES -- PATHS ARE WEIRD !!

/*
 * HOW TO USE
 * fill out a rectangle with the image using:
 *
 * imageLoader imagaLoader = new imageLoader();
 * rect.setFill(imageLoader.loadImage("filename.fileextension"));
 */
public class ImageLoader {
    
    /** 
     * Methode um Bilder als Input-Stream zu laden und dann als JavaFX Bild zurückzugeben
     * 
     * HOW TO USE
     * imageLoader imagaLoader = new imageLoader();
     * rect.setFill(imageLoader.loadImage("filename.fileextension"));
     * 
     * @param fileName Name der zu ladenden Datei mit Endung
     * @return ImagePattern Zum setzen der Fill Color eines Rechtecks
     */
    public ImagePattern loadImage(String fileName) {
        Image image = new Image(getClass().getResourceAsStream(fileName), 1024, 1024, true, false);
        return new ImagePattern( image );
    }
}
