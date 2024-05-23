package TicTacToe.sounds;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import TicTacToe.tempForData.TempForData;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class sounds
{
    private static MediaPlayer mediaPlayer;
    private static MediaPlayer backgroundMediaPlayer;
    private static int currentSongIndex;

    //list of all songs
    private static List<String> songUrls = Arrays.asList("src/main/resources/TicTacToe/subway_surfers.mp3",
            "src/main/resources/TicTacToe/New_Friendly.mp3",
            "src/main/resources/TicTacToe/Cipher.mp3",
            "src/main/resources/TicTacToe/Call_to_Adventure.mp3");

    private static Media backgroundMusic;

    public static void initialize() {
        currentSongIndex = 0;
        Media sound = new Media(new File("src/main/resources/TicTacToe/btnclick.wav").toURI().toString());
        mediaPlayer = new MediaPlayer(sound);

        backgroundMusic = new Media(new File(songUrls.get(currentSongIndex)).toURI().toString());
        backgroundMediaPlayer = new MediaPlayer(backgroundMusic);

    }
    public static void playButtonClickSound() {
        mediaPlayer.seek(mediaPlayer.getStartTime());
        mediaPlayer.setVolume((double)(TempForData.soundVolume/100.0));
        mediaPlayer.play();
    }
    public static void playBackgroundMusic(){
        backgroundMediaPlayer.setOnEndOfMedia(new Runnable() {
            @Override
            public void run()
            {
                //go through the songs one by one
                currentSongIndex++;
                if (currentSongIndex >= songUrls.size())
                {
                    currentSongIndex = 0;
                }
                backgroundMusic = new Media(new File(songUrls.get(currentSongIndex)).toURI().toString());
                backgroundMediaPlayer = new MediaPlayer(backgroundMusic);
                playBackgroundMusic(); // Call itself to play the new song
            }
        });

        backgroundMediaPlayer.seek(backgroundMediaPlayer.getStartTime());
        backgroundMediaPlayer.setVolume((double)(TempForData.musicVolume/100.0));
        backgroundMediaPlayer.play();
    }

    public static void updateMusicVolume()
    {
        backgroundMediaPlayer.setVolume((double)(TempForData.musicVolume/100.0));
    }
}
