package com.javafwp.sound;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import javafx.util.Duration;

/**
 *  Sound Effect Manager um mehrere Effekte parallel abzuspielen
 */
public class ParalellSfxPlayer {
    private MediaPlayer[] clips;

    /**
     * Konstruktor des SFX managers
     * Lädt die Sound Dateien vor
     */
    public ParalellSfxPlayer(String filename, double volume, int amount)  {
        this.clips = new MediaPlayer[amount];

        Media media = new Media(getClass().getResource(filename).toExternalForm());
        for(int i = 0; i < amount; i++) {
            this.clips[i] = new MediaPlayer(media);
            this.clips[i].setVolume(volume);
        }
    }

    /**
     * Spielt eine Instanz des SFX ab
     * Falls keine freien Instanzen verfügbar sind, wird die älteste neu gestartet
     */
    public void play()  {
        int index = -1;
        Duration maxTime = Duration.UNKNOWN;
        Duration currentTime;

        // find clip with the most progressed playback
        for(int i = 0; i < this.clips.length; i++)   {
            currentTime = this.clips[i].getCurrentTime();

            // if the current time is at the end of playback
            if(currentTime.compareTo(this.clips[i].getStopTime()) >= 0)  {
                index = i;
                break;
            }

            // if the current time is greater than the last maximum
            if(maxTime.compareTo(currentTime) < 0 || maxTime == Duration.UNKNOWN)  {
                maxTime = currentTime;
                index = i;
            }
        }

        if(index >= 0)  {
            this.clips[index].stop();
            this.clips[index].play();
        }
    }
}
