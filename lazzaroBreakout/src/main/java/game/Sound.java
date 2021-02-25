package game;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Sound {
    private static MediaPlayer musicPlayer;
    private final static Map<String, Media> cache = new HashMap<>();
    private static MediaPlayer soundEffectPlayer;
    private static ArrayList<MediaPlayer> testEffects = new ArrayList<>();

    public static void play(MusicType music) {
        if (musicPlayer != null) {
            musicPlayer.stop();
        }

        if(music == MusicType.STOP)
            return;

        musicPlayer = createMediaPlayer(getMusicFileName(music));
        musicPlayer.setVolume(0.5);
        musicPlayer.play();
    }

    public static void play(SoundEffectType soundEffect) {

        if (soundEffectPlayer != null) {
            soundEffectPlayer.stop();
        }

        if(soundEffect == SoundEffectType.STOP)
            return;

        soundEffectPlayer = createMediaPlayer(getSoundFileName(soundEffect));
        soundEffectPlayer.play();
    }

    public static void playTest(SoundEffectType soundEffect){
        for(MediaPlayer player : testEffects){
            if(player.getCurrentTime() == player.getStopTime()){
                player.dispose();
            }
        }

        if(soundEffect == SoundEffectType.STOP)
            return;

        MediaPlayer newPlayer = createMediaPlayer(getSoundFileName(soundEffect));
        newPlayer.play();
        testEffects.add(newPlayer);
    }

    private static MediaPlayer createMediaPlayer(String filePath){
        filePath = "/sounds/" + filePath;

        if (!cache.containsKey(filePath)){
            URL url = Sound.class.getResource(filePath);
            if (url == null) {
                throw new RuntimeException("Could not load file: " + filePath);
            }

            cache.put(filePath, new Media(url.toString()));
        }

        return new MediaPlayer(cache.get(filePath));
    }

    private static String getSoundFileName(SoundEffectType soundEffect) {
        switch (soundEffect) {
            case BRICK_DESTROYED:
                return "brick_destroyed.wav";
            case BALL_BOUNCE:
                return "ball_bounce.wav";
            case POWERUP_PICKUP:
                return "powerup_pickup.wav";
            case LOSER_SOUND:
                return "loser_sound.wav";
            case WINNER_SOUND:
                return "winner_sound.mp3";
            case STOP:
                return "";
            default:
                throw new RuntimeException("No Soundfilename set for this enum value:" + soundEffect);
        }
    }

    private static String getMusicFileName(MusicType music) {
        switch (music) {
            case BACKGROUND:
                return "background.wav";
            case INTRO:
                return "intro.wav";
            case STOP:
                return "";
            default:
                throw new RuntimeException("No Soundfilename set for this enum value:" + music);
        }
    }
}
