package gui;

import game.*;
import gui.common.BaseScene;

public class WinnerScene extends BaseScene {

    public WinnerScene(Navigator navigator) {
        super(navigator, Images.WINNER);

        setOnMouseClicked(e -> {
            navigator.goTo(SceneType.START_SCREEN);
        });

    }

    @Override
    public void start() {
        Sound.play(SoundEffectType.WINNER_SOUND);
    }

    @Override
    public void stop() {
    }

}
