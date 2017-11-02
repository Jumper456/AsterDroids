package net.yotvoo.asterd.app;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;

public class Sound {

    //private MediaPlayer mediaPlayerShooting;
    private Media soundShooting;

    public Sound(){

        String musicFile = "C:\\Users\\Jumper\\Downloads\\Sounds\\julien_matthey_science_fiction_laser_002.mp3";     // For example
        soundShooting = new Media(new File(musicFile).toURI().toString());
    }

    public void playShooting(){
/*
        String musicFile = "StayTheNight.mp3";     // For example
        Media sound = new Media(new File(musicFile).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
*/
        //mediaPlayerShooting.set;
        MediaPlayer mediaPlayerShooting = new MediaPlayer(soundShooting);

/*
        mediaPlayerShooting.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                mediaPlayerShooting.stop();
            }
        });
*/

        mediaPlayerShooting.play();

    }
}
