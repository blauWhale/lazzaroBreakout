package gui;

import game.Images;
import game.MusicType;
import game.Sound;
import game.SoundEffectType;
import gui.common.BaseScene;


public class GameOverScene extends BaseScene {

    public GameOverScene(Navigator navigator) {
        super(navigator, Images.GAMEOVER);

        setOnMouseClicked(e -> navigator.goTo(SceneType.START_SCREEN));
    }

    @Override
    public void start() {
        System.out.println("Loser");
        Sound.play(SoundEffectType.LOSER_SOUND);

    }

    @Override
    public void stop() {
    }

}
