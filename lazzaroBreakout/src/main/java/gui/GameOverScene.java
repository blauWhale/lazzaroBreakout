package gui;

import game.Images;
import gui.common.BaseScene;
import javafx.scene.Scene;
import javafx.scene.image.Image;

public class GameOverScene extends BaseScene {
    public GameOverScene(Navigator navigator) {
        super(navigator, Images.GAMEOVER);
    }
}
