package gui;

import game.Direction;
import game.Images;
import game.Status;
import game.objects.*;
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

    private Platform platform = new Platform(Images.PLATFORM);
    private long lastTimeInNanoSec;
    private Ball ball = new Ball(0, 0, BALL, platform, Status.STOP);
    private List<Life> lifes = new ArrayList<Life>();
    private List<Brick> wallOfBricks = new ArrayList<Brick>();
    boolean mousewasclicked = false;
    private int currentScore = 0;
    private int highestScore = -1;
    private Label score = new Label();
    private Label highScore = new Label();
    private List<Ball> balls = new ArrayList<Ball>();
    private static final double POWERUP_CHANCE = 5;
    private List<PowerUp> powerUpslist = new ArrayList<PowerUp>();


    public GameScene(Navigator navigator) {
        super(navigator, GAME_BACKGROUND);
    }

    @Override
    public void start(){

        score.setFont(new Font("Arial bold", 20));
        score.setLayoutX(420);
        score.setLayoutY(450);
        highScore.setFont(new Font("Arial bold", 20));
        highScore.setLayoutX(420);
        highScore.setLayoutY(475);

        Group root = (Group) getRoot();
        root.getChildren().add(score);
        root.getChildren().add(highScore);


        setOnMouseMoved(e -> {
            double mouseXPos = e.getX();
            double middlePoint = platform.getX() + PLATFORM.getWidth() / 2;
            if (mouseXPos - middlePoint < 30 && mouseXPos - middlePoint > -30 ){
                platform.setDirection(Direction.STOP);
            }
            else if (mouseXPos > middlePoint) {
                platform.setDirection(Direction.RIGHT);
            }
            else if (mouseXPos < middlePoint){
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
        for (PowerUp powerUps : powerUpslist) {
            powerUps.draw(gc);
        }
        for (Ball balls : balls) {
            balls.draw(gc);
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
                    dropPowerUp(deltaInSec, brick.getX(), brick.getY());
                    score.setText("Points:" + Integer.toString(currentScore));
                    wallOfBricks.remove(brick);

                } else {
                    brick.setDifficulty(brick.getDifficulty() - 1);
                }
            }
        }

        if (wallOfBricks.size() == 0) {
            navigator.goTo(SceneType.WINNER_SCREEN);
            highestScore = currentScore;
            if (highestScore < currentScore) {
                highScore.setText("High-Score:" + Integer.toString(currentScore));
            } else if (highestScore > currentScore) {
                highScore.setText("High-Score:" + Integer.toString(highestScore));
            } else if (highestScore == currentScore) {
                highScore.setText("High-Score:" + Integer.toString(highestScore));
            }
        }

        if (ball.getY() > platform.getY()) {
            if (lifes.size() > 0) {
                lifes.remove(lifes.size() - 1);
                ball.resetToPlatform();
                mousewasclicked = false;
            } else {
                navigator.goTo(SceneType.GAMEOVER_SCREEN);
                ball.resetToPlatform();
                if (highestScore < currentScore) {
                    highScore.setText("High-Score:" + Integer.toString(currentScore));
                } else if (highestScore > currentScore) {
                    highScore.setText("High-Score:" + Integer.toString(highestScore));
                } else if (highestScore == currentScore) {
                    highScore.setText("High-Score:" + Integer.toString(highestScore));
                }
            }
        }
        for (Ball balls : balls) {
            balls.update(deltaInSec);
        }
        for (Ball otherBalls : balls) {
            if (otherBalls.getY() > platform.getY()) {
                balls.remove(otherBalls);
            }
        }
        for (PowerUp powerUps : powerUpslist) {
            powerUps.update(deltaInSec);
            if (powerUps.collidesWith(platform)) {
                checkPowerUp(powerUps.getPowerType());
                powerUpslist.remove(powerUps);
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

    private void dropPowerUp(double deltaInSec, double brickPosX, double brickPosY) {
        if (5 == /*Math.random() < deltaInSec */ POWERUP_CHANCE) {
            powerUpslist.add(new PowerUp(brickPosX, brickPosY, POWERUP, 3));
        }
    }
    private void checkPowerUp(double PowerType) {
        switch ((int) PowerType) {
            case 1 -> {
                //Extra Leben
                if (lifes.size() < 4) {
                    lifes.add(new Life(120, 450));
                }
            }
            case 2 -> {
                //Doppelte Punkte
                for (Brick brick : wallOfBricks) {
                    brick.setPoints(brick.getPoints() * 2);
                }
            }
            case 3 -> {
                balls.add(new Ball(ball.getX() + 50,ball.getY(),Images.EXTRABALL,platform, Status.PLAY));
            }
            case 4 -> {
                platform.setImage(LONGPLATFORM);
            }
        }
    }

}
