package gui;

import game.Images;
import game.Status;
import gui.common.BaseScene;

public class StartScene extends BaseScene {

    public StartScene(Navigator navigator) {
        super(navigator, Images.WELCOME);

        setOnMouseClicked(e -> navigator.goTo(SceneType.GAME_SCREEN));

    }

    @Override
    public void start() {
        //TODO
    }
}
