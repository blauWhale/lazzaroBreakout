package main;

import game.MouseEventHandler;
import game.objects.GameObject;
import game.objects.Platform;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.stage.Stage;

import static game.Constant.SCREEN_HEIGHT;
import static game.Constant.SCREEN_WIDTH;
import static game.Images.*;
import static game.objects.Ball.BALLPOSX;
import static game.objects.Ball.BALLPOSY;
import static game.objects.Platform.PLATFORMPOSX;
import static game.objects.Platform.PLATFORMPOSY;

public class LazzaroBreakoutApp extends Application {

    private Canvas canvas;
    private GraphicsContext gc;
    private long lastTimeInNanoSec;

    private void paint() {
        gc.drawImage(GAME_BACKGROUND, 0, 0);
        gc.drawImage(PLATFORM, PLATFORMPOSX, PLATFORMPOSY);
        gc.drawImage(BALL, BALLPOSX, BALLPOSY);
    }

    private void update(double deltaInSec) {
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        lastTimeInNanoSec = System.nanoTime();
        new AnimationTimer() {
            @Override
            public void handle(long currentTimeInNanoSec) {
                long deltaInNanoSec = currentTimeInNanoSec - lastTimeInNanoSec;
                double deltaInSec = deltaInNanoSec / 1000000000d; //oder: 1e9;
                lastTimeInNanoSec = currentTimeInNanoSec;
                update(deltaInSec);
                paint();
            }
        }.start();



        Group root = new Group();
        canvas = new Canvas(SCREEN_WIDTH, SCREEN_HEIGHT);
        root.getChildren().add(canvas);
        gc = canvas.getGraphicsContext2D();
        Scene scene = new Scene(root);

        scene.setOnMouseMoved((e) -> new MouseEventHandler(e));

        stage.setTitle("Lazzaro Breakout");
        stage.setScene(scene);
        stage.show();


    }

}
