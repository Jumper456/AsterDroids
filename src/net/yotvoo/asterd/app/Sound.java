package net.yotvoo.asterd.app;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

public class Sound {

    private Media soundThrust;
    //private MediaPlayer mediaPlayerShooting;
    private Media soundShooting;
    private Media soundAsteroidExplosion;
    private Media soundPlayerCrash;

    private String pathToSounds = "";
    //private String pathToSounds = ".\\\\res\\\\";
    // "C:\\\\Users\\\\Jumper\\\\Downloads\\\\Sounds\\\\";



    public Sound() /*throws URISyntaxException */{


        try {
//            String musicFile = pathToSounds + "laser_gun.mp3";
//            //String musicFile =  "LICENSE";
//            AsterDroidsApp.log("musicFile: " + musicFile);
//            //soundShooting = new Media(new File(musicFile).toURI().toString());
//            String uri = "";
//            URL url = getClass().getResource(musicFile);
//            uri = url.toURI().toString();
//            AsterDroidsApp.log(uri);
//            soundShooting = new Media (uri);

            soundShooting = new Media(getClass().getResource("laser_gun.mp3").toURI().toString());
            soundAsteroidExplosion = new Media(getClass().getResource("boom_bang.mp3").toURI().toString());
            soundPlayerCrash = new Media(getClass().getResource("boom_grenade.mp3").toURI().toString());
            soundThrust = new Media(getClass().getResource("rocket-thrust-short.wav").toURI().toString());

//            musicFile = pathToSounds + "laser_gun.mp3";     // For example
//            soundShooting = new Media(new File(musicFile).toURI().toString());

//            musicFile = pathToSounds + "boom_bang.mp3";     // For example
//            soundAsteroidExplosion = new Media(new File(musicFile).toURI().toString());
//
//            musicFile = pathToSounds + "boom_grenade.mp3";     // For example
//            soundPlayerCrash = new Media(new File(musicFile).toURI().toString());




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

    public void playThrust(){

        play(soundThrust);
    }


    public void playPlayerCrash(){

        play(soundPlayerCrash);
    }

    public void playAsteroidExplosion(){

        play(soundAsteroidExplosion);
    }

    public void playShooting(){

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
