package gui;

import game.Images;
import gui.common.BaseScene;
import javafx.scene.Scene;

public class WinnerScene extends BaseScene {
    public WinnerScene(Navigator navigator) {
        super(navigator, Images.WINNER);
    }
}
