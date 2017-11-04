package net.yotvoo.asterd.app;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

public class Sound {

    //private MediaPlayer mediaPlayerShooting;
    private Media soundShooting;
    private Media soundAsteroidExplosion;
    private Media soundPlayerCrash;

    private String pathToSounds = "res/";
    //private String pathToSounds = ".\\\\res\\\\";
    // "C:\\\\Users\\\\Jumper\\\\Downloads\\\\Sounds\\\\";



    public Sound() /*throws URISyntaxException */{


        try {
            String musicFile = pathToSounds + "julien_matthey_science_fiction_laser_002.mp3";     // For example
/*
            AsterDroidsApp.log("musicFile: " + musicFile);
            //soundShooting = new Media(new File(musicFile).toURI().toString());
            String uri = "";
            URL url = getClass().getResource(musicFile);
            uri = url.toURI().toString();
            AsterDroidsApp.log(uri);
            soundShooting = new Media (uri);
*/

            musicFile = pathToSounds + "laser_gun.mp3";     // For example
            soundShooting = new Media(new File(musicFile).toURI().toString());

            musicFile = pathToSounds + "boom_bang.mp3";     // For example
            soundAsteroidExplosion = new Media(new File(musicFile).toURI().toString());

            musicFile = pathToSounds + "boom_grenade.mp3";     // For example
            soundPlayerCrash = new Media(new File(musicFile).toURI().toString());




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
    };

    public void playPlayerCrash(){
        play(soundPlayerCrash);
    }

    public void playAsteroidExplosion(){

        play(soundAsteroidExplosion);
    };

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
