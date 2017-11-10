package net.yotvoo.asterd.app;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

class Sound {

    private Media soundThrust;
    //private MediaPlayer mediaPlayerShooting;
    private Media soundShooting;
    private Media soundAsteroidExplosion;
    private Media soundPlayerCrash;

    Sound() /*throws URISyntaxException */{
        try {
            soundShooting = new Media(getClass().getResource("laser_gun.mp3").toURI().toString());
            soundAsteroidExplosion = new Media(getClass().getResource("boom_bang.mp3").toURI().toString());
            soundPlayerCrash = new Media(getClass().getResource("boom_grenade.mp3").toURI().toString());
            soundThrust = new Media(getClass().getResource("rocket-thrust-short.wav").toURI().toString());
        } catch (Exception e) {
            AsterDroidsApp.log("Problem podczas ładowania dźwięków");
            AsterDroidsApp.log("Exception: " + e.toString());
        }

    }

    private void play(Media media){
        if (media != null) {
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            mediaPlayer.play();
        }
    }

    void playThrust(){
        play(soundThrust);
    }

    void playPlayerCrash(){
        play(soundPlayerCrash);
    }

    void playAsteroidExplosion(){
        play(soundAsteroidExplosion);
    }

    void playShooting(){
        play(soundShooting);

/*
        mediaPlayerShooting.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                mediaPlayerShooting.stop();
            }
        });
*/
    }
}
