package main;

import gui.*;

import javafx.application.Application;

import javafx.stage.Stage;


public class LazzaroBreakoutApp extends Application {



    public static void main(String[] args) {
     launch(args);
    }

    @Override
    public void start(Stage stage) {

        stage.setTitle("Lazzaro Breakout");
        Navigator navigator = new Navigator(stage);
        navigator.registerScene(SceneType.START_SCREEN, new StartScene(navigator));
        navigator.registerScene(SceneType.GAMEOVER_SCREEN, new GameOverScene(navigator));
        navigator.registerScene(SceneType.WINNER_SCREEN, new WinnerScene(navigator));
        navigator.registerScene(SceneType.GAME_SCREEN, new GameScene(navigator));
        navigator.goTo(SceneType.START_SCREEN);
        stage.show();

    }
}
