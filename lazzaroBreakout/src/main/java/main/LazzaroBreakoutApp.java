package main;

import game.Direction;
import game.MouseEventHandler;
import game.objects.Brick;
import game.objects.GameObject;
import game.objects.Platform;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;

import static game.Constant.SCREEN_HEIGHT;
import static game.Constant.SCREEN_WIDTH;
import static game.Images.*;


public class LazzaroBreakoutApp extends Application {

    private Platform platform = new Platform();
    private Canvas canvas;
    private GraphicsContext gc;
    private long lastTimeInNanoSec;
    private Brick brickWall = new Brick(5,5,BRICK);

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
                double deltaInSec = deltaInNanoSec / 1000000000d;
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

        scene.setOnMouseMoved(e -> {
            double mouseXPos = e.getX();
            if(e.getX() >= SCREEN_WIDTH/2) {
                platform.setDirection(Direction.RIGHT);
            } else {
                platform.setDirection(Direction.LEFT);
            }
        });

        stage.setTitle("Lazzaro Breakout");
        stage.setScene(scene);
        stage.show();
    }

    private void paint() {
        gc.drawImage(GAME_BACKGROUND, 0, 0);
        platform.draw(gc);
        brickWall.draw(gc);
        }

    private void update(double deltaInSec) {
        platform.update(deltaInSec);
    }

}
