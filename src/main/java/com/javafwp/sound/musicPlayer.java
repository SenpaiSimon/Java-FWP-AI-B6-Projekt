package com.javafwp.sound;


import com.javafwp.game.ownTypes.gameState;

import java.lang.Math;
import java.io.File;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class musicPlayer {
    
    private MediaPlayer   menuLoop;
    private MediaPlayer   start;
    private MediaPlayer   gameLoop;
    private MediaPlayer   end;

    private MediaPlayer  cuedClip;
    private MediaPlayer  currentClip;

    private gameState lastGamestate = null;

    private MediaPlayer  loadAudioClip(String filename)    {
        return  new MediaPlayer (new Media( getClass().getResource(filename).toExternalForm()  ) );
    }

    public musicPlayer()   {
        this.menuLoop   = this.loadAudioClip("intro.wav"); 
        this.start      = this.loadAudioClip("start.wav"); 
        this.gameLoop   = this.loadAudioClip("game.wav"); 
        this.end        = this.loadAudioClip("end.wav"); 

        this.menuLoop.setCycleCount(AudioClip.INDEFINITE);
        this.gameLoop.setCycleCount(AudioClip.INDEFINITE);
    }

    private void updateCued()   {

    }

    public void updateMusic(gameState gameState)  {

        if(gameState == this.lastGamestate) {
         
                if(currentClip == null && cuedClip != null) {
                    currentClip = cuedClip;
                    currentClip.play();
                }
                if(currentClip != null && cuedClip == null)   {
                    currentClip.stop();
                    currentClip = null;
                }
                if(currentClip != cuedClip && currentClip != null && cuedClip != null) {
                    currentClip.stop();
                    currentClip = cuedClip;
                    currentClip.play();
                }
        
        }   else    {
            // we've transistioned from a state
            // cue the appropriate clip

            switch(gameState)   {
                case mainMenu:
                    this.cuedClip = menuLoop;
                    break;
                case playing:
                    this.cuedClip = start;
                    this.cuedClip.setOnEndOfMedia( () -> {
                        this.cuedClip = gameLoop;
                    });
                    break;
                case death:
                    this.cuedClip = end;
                    break;
                default:
                    break;
            }
        }

        lastGamestate = gameState;
    }


}
