package net.yotvoo.asterd.app;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;

public class Sound {

    //private MediaPlayer mediaPlayerShooting;
    private Media soundShooting;
    private Media soundAsteroidExplosion;
    private Media soundPlayerCrash;

    public Sound(){

        String musicFile = "C:\\Users\\Jumper\\Downloads\\Sounds\\julien_matthey_science_fiction_laser_002.mp3";     // For example
        soundShooting = new Media(new File(musicFile).toURI().toString());

        musicFile = "C:\\Users\\Jumper\\Downloads\\Sounds\\explosion_internal_loud_bang_blow_up_safe.mp3";     // For example
        soundAsteroidExplosion = new Media(new File(musicFile).toURI().toString());

        musicFile = "C:\\Users\\Jumper\\Downloads\\Sounds\\Blastwave_FX_GrenadeExplosion_S08WA.229.mp3";     // For example
        soundPlayerCrash = new Media(new File(musicFile).toURI().toString());

    }

    private void play(Media media){
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
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
        String musicFile = "StayTheNight.mp3";     // For example
        Media sound = new Media(new File(musicFile).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
*/
        //mediaPlayerShooting.set;
        //MediaPlayer mediaPlayerShooting = new MediaPlayer(soundShooting);

/*
        mediaPlayerShooting.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                mediaPlayerShooting.stop();
            }
        });
*/

        //mediaPlayerShooting.play();

    }
}
