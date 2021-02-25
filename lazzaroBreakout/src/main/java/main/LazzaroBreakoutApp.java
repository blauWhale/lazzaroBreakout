package main;

import gui.*;

import javafx.application.Application;

import javafx.stage.Stage;

import javax.swing.*;


public class LazzaroBreakoutApp extends Application {



    public static void main(String[] args) {
     launch(args);
    }

    @Override
    public void start(Stage stage) {

        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            public void uncaughtException(Thread t, Throwable e) {
                // TODO: Logging
                e.printStackTrace();
                System.out.println(e.getMessage());
                JOptionPane.showMessageDialog(null, "Something went wrong!");
            }
        });

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
