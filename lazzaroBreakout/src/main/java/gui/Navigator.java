package gui;

import gui.common.BaseScene;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class Navigator {

    private final Stage stage;
    private final Map<SceneType, BaseScene> viewMap = new HashMap<>();

    public Navigator(Stage stage) {
        this.stage = stage;
    }

    public void registerScene(SceneType enumScene, BaseScene scene) {
        viewMap.put(enumScene, scene);
    }

    public void goTo(SceneType scene) {
        BaseScene activeScene = viewMap.get(scene);
        activeScene.start();
        stage.setScene(activeScene);
    }
}
