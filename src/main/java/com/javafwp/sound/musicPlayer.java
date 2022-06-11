package com.javafwp.sound;


import com.javafwp.game.ownTypes.gameState;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * Eigener Musik Player zum behandeln von Hintergrund Musik im Spiel
 */
public class musicPlayer {

    private MediaPlayer   menuLoop;
    private MediaPlayer   start;
    private MediaPlayer   gameLoop;
    private MediaPlayer   end;

    private MediaPlayer  cuedClip;
    private MediaPlayer  currentClip;

    private gameState lastGamestate = null;

    /**
     * Konstruktor des Musik Players
     * Lädt die Sound Dateien vor
     */
    public musicPlayer()   {
        this.menuLoop   = this.loadAudioClip("intro.wav");
        this.start      = this.loadAudioClip("start.wav");
        this.gameLoop   = this.loadAudioClip("game.wav");
        this.end        = this.loadAudioClip("end.wav");

        this.menuLoop.setCycleCount(AudioClip.INDEFINITE);
        this.gameLoop.setCycleCount(AudioClip.INDEFINITE);
    }

    /**
     * Lädt .wav Datei aus einer Datei als Media Player Objekt
     *
     * @param filename
     * @return
     */
    private MediaPlayer loadAudioClip(String filename) {
        return new MediaPlayer(new Media(getClass().getResource(filename).toExternalForm()));
    }

    /**
     * Wechselt die Musik und Übergänge je nach Zustand des Spiels
     *
     * @param gameState
     */
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
