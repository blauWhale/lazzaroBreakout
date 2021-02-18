package gui;

import game.Images;
import gui.common.BaseScene;

public class WinnerScene extends BaseScene {

    public WinnerScene(Navigator navigator) {
        super(navigator, Images.WINNER);

        setOnMouseClicked(e -> navigator.goTo(SceneType.START_SCREEN));
    }

    @Override
    public void start() {
    }
}
