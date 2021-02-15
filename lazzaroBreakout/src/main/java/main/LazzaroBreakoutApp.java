package main;

import game.Direction;
import game.Images;
import game.Status;
import game.objects.Life;
import game.Points;
import game.objects.Ball;
import game.objects.Brick;
import game.objects.Platform;
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

    private Platform platform = new Platform();
    private Canvas canvas;
    private GraphicsContext gc;
    private long lastTimeInNanoSec;
    private Ball ball = new Ball(0, 0, platform);
    private List<Life> lifes = new ArrayList<Life>();
    private List<Brick> wallOfBricks = new ArrayList<Brick>();
    boolean mousewasclicked = false;

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

        Label score = new Label("100");
        score.setFont(new Font("Arial", 40));
        score.setLayoutX(500);
        score.setLayoutY(450);


        Group root = new Group();
        canvas = new Canvas(SCREEN_WIDTH, SCREEN_HEIGHT);
        root.getChildren().add(canvas);
        root.getChildren().add(score);
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

        scene.setOnMouseClicked(e -> {
            if (mousewasclicked){
             //  ball.setStatus(Status.STOP);

            }
            else{
                ball.setStatus(Status.PLAY);
                ball.setDirection(Direction.UP);
                mousewasclicked = true;
            }


        });

        lifes.add(new Life(60, 450));
        lifes.add(new Life(80, 450));
        lifes.add(new Life(100, 450));

        wallOfBricks.add(new Brick(400, 20));
        wallOfBricks.add(new Brick(300, 20));
        wallOfBricks.add(new Brick(200, 20));
        wallOfBricks.add(new Brick(100, 20));

        wallOfBricks.add(new Brick(100, 50));
        wallOfBricks.add(new Brick(200, 50));
        wallOfBricks.add(new Brick(300, 50));
        wallOfBricks.add(new Brick(400, 50));





        stage.setTitle("Lazzaro Breakout");
        stage.setScene(scene);
        stage.show();
    }

    private void paint() {
        gc.drawImage(GAME_BACKGROUND, 0, 0);
        platform.draw(gc);
        ball.draw(gc);
        for (Life lifes : lifes) {
            lifes.draw(gc);
        }
        for (Brick wallOfBricks  : wallOfBricks ) {
            wallOfBricks.draw(gc);
        }

        }

    private void update(double deltaInSec) {
        platform.update(deltaInSec);
        ball.update(deltaInSec);
        for (Brick brick : wallOfBricks) {
            if (brick.collidesWith(ball)) {
                wallOfBricks.remove(brick);
            }
        }
    }

}
