package main;

import game.Direction;
import game.Images;
import game.Status;
import game.objects.Life;
import game.Points;
import game.objects.Ball;
import game.objects.Brick;
import game.objects.Platform;
import gui.*;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

import static game.Constant.SCREEN_HEIGHT;
import static game.Constant.SCREEN_WIDTH;
import static game.Images.*;


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
