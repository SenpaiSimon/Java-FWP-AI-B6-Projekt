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
public class imageLoader {
    public ImagePattern loadImage(String fileName) {
        return new ImagePattern((new Image(getClass().getResourceAsStream(fileName))));
    }
}
