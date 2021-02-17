package gui;

import game.Direction;
import game.Status;
import game.objects.Ball;
import game.objects.Brick;
import game.objects.Life;
import game.objects.Platform;
import gui.common.BaseScene;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.List;

import static game.Constant.SCREEN_HEIGHT;
import static game.Constant.SCREEN_WIDTH;
import static game.Images.*;
import static game.Images.GREEN_BRICK;

public class GameScene extends BaseScene {

    private Platform platform = new Platform();

    private long lastTimeInNanoSec;
    private Ball ball = new Ball(0, 0, platform);
    private List<Life> lifes = new ArrayList<Life>();
    private List<Brick> wallOfBricks = new ArrayList<Brick>();
    boolean mousewasclicked = false;
    private int currentScore = 0;
    private Label score = new Label();

    public GameScene(Navigator navigator) {
        super(navigator, GAME_BACKGROUND);
    }

    @Override
    public void start(){

        score.setFont(new Font("Arial bold", 20));
        score.setLayoutX(420);
        score.setLayoutY(450);

        Group root = (Group) getRoot();
        root.getChildren().add(score);


        setOnMouseMoved(e -> {
            double mouseXPos = e.getX();
            if (e.getX() >= platform.getX() + PLATFORM.getWidth() / 2) {
                platform.setDirection(Direction.RIGHT);
            } else {
                platform.setDirection(Direction.LEFT);
            }
        });

        setOnMouseClicked(e -> {
            if (mousewasclicked) {
                //  ball.setStatus(Status.STOP);

            } else {
                ball.setStatus(Status.PLAY);
                ball.setStepY(-1);
                mousewasclicked = true;
            }


        });

        lifes.add(new Life(60, 450));
        lifes.add(new Life(80, 450));
        lifes.add(new Life(100, 450));

        wallOfBricks.add(new Brick(400, 50, BLACK_BRICK, 2,300));
        wallOfBricks.add(new Brick(300, 50, BLACK_BRICK, 2, 300));
        wallOfBricks.add(new Brick(200, 50, BLACK_BRICK, 2, 300));
        wallOfBricks.add(new Brick(100, 50, BLACK_BRICK, 2, 300));

        wallOfBricks.add(new Brick(100, 80, BLUE_BRICK, 1, 200));
        wallOfBricks.add(new Brick(200, 80, BLUE_BRICK, 1,200));
        wallOfBricks.add(new Brick(300, 80, BLUE_BRICK, 1,200));
        wallOfBricks.add(new Brick(400, 80, BLUE_BRICK, 1,200));

        wallOfBricks.add(new Brick(100, 110, GREEN_BRICK, 0,100));
        wallOfBricks.add(new Brick(200, 110, GREEN_BRICK, 0, 100));
        wallOfBricks.add(new Brick(300, 110, GREEN_BRICK, 0, 100));
        wallOfBricks.add(new Brick(400, 110, GREEN_BRICK, 0, 100));

        lastTimeInNanoSec = System.nanoTime();
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long currentTimeInNanoSec) {
                long deltaInNanoSec = currentTimeInNanoSec - lastTimeInNanoSec;
                double deltaInSec = deltaInNanoSec / 1000000000d;
                lastTimeInNanoSec = currentTimeInNanoSec;
                update(deltaInSec);
                paint();

            }
        };
        timer.start();
    }


    private void paint() {
        gc.drawImage(GAME_BACKGROUND, 0, 0);
        platform.draw(gc);
        ball.draw(gc);
        for (Life lifes : lifes) {
            lifes.draw(gc);
        }
        for (Brick wallOfBricks : wallOfBricks) {
            wallOfBricks.draw(gc);
        }
    }

    private void update(double deltaInSec) {
        platform.update(deltaInSec);
        ball.update(deltaInSec);
        for (Brick brick : wallOfBricks) {
            if (brick.collidesWith(ball)) {
                checkBrick();
                if (brick.getDifficulty() == 0) {
                    currentScore = currentScore + brick.getPoints();
                    score.setText("Points:" + Integer.toString(currentScore));
                    wallOfBricks.remove(brick);

                }
                else {
                    brick.setDifficulty(brick.getDifficulty() - 1);
                }

            }
        }

        if (wallOfBricks.size() == 0){
            navigator.goTo(SceneType.WINNER_SCREEN);
        }

        if (ball.getY() > platform.getY()) {
            if (lifes.size() > 0) {
                lifes.remove(lifes.size() - 1);
                ball.resetToPlatform();
                mousewasclicked = false;
            }
            else {
                System.out.println("Game Over");
                navigator.goTo(SceneType.GAMEOVER_SCREEN);
                ball.resetToPlatform();
            }
        }

    }

    private void checkBrick() {
        for (Brick b : wallOfBricks) {
            boolean brickDown = ball.getX() <= b.getX() + BRICK.getWidth() && ball.getX() >= b.getX() - BALL.getWidth() && ball.getY() <= b.getY() + BRICK.getHeight() + 3 && ball.getY() >= b.getY() + BRICK.getHeight() - 3;
            boolean brickUp = ball.getX() <= b.getX() + BRICK.getWidth() && ball.getX() >= b.getX() - BALL.getWidth() && ball.getY() <= b.getY() - BALL.getHeight() + 3 && ball.getY() >= b.getY() - BALL.getHeight() - 3;
            boolean brickLeft = ball.getY() <= b.getY() + BRICK.getHeight() && ball.getY() >= b.getY() - BALL.getHeight() && ball.getX() <= b.getX() - BALL.getWidth() + 3 && ball.getX() >= b.getX() - BALL.getWidth() - 3;
            boolean brickRight = ball.getY() <= b.getY() + BRICK.getHeight() && ball.getY() >= b.getY() - BALL.getHeight() && ball.getX() <= b.getX() + BRICK.getWidth() + 3 && ball.getX() >= b.getX() + BRICK.getWidth() - 3;

            if (brickLeft) {
                ball.setStepX(-1);
            }

            if (brickRight) {
                ball.setStepX(1);
            }

            if (brickDown) {
                ball.setStepY(1);
            }

            if (brickUp) {
                ball.setStepY(-1);
            }
        }
    }

}
