package game;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Sound {
    private static MediaPlayer musicPlayer;
    private final static Map<String, Media> cache = new HashMap<>();

    public static void play(MusicType music) {
        if (musicPlayer != null) {
            musicPlayer.stop();
        }

        musicPlayer = createMediaPlayer(getSoundFileName(music));
        musicPlayer.setVolume(0.5);
        musicPlayer.play();
    }

    public static void play(SoundEffectType soundEffect) {
        MediaPlayer player = createMediaPlayer(getSoundFileName(soundEffect));
        player.play();
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
            default:
                throw new RuntimeException("No Soundfilename set for this enum value:" + soundEffect);
        }
    }

    private static String getSoundFileName(MusicType music) {
        switch (music) {
            case BACKGROUND:
                return "background.wav";
            case INTRO:
                return "intro.wav";
            default:
                throw new RuntimeException("No Soundfilename set for this enum value:" + music);
        }
    }
}
