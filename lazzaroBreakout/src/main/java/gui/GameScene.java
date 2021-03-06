package gui;

import game.*;
import game.objects.*;
import gui.common.BaseScene;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;


import static game.Images.*;
import static game.Images.GREEN_BRICK;

public class GameScene extends BaseScene {

    private Platform platform;
    private long lastTimeInNanoSec;
    private List<Life> lifes = new ArrayList<Life>();
    private List<Brick> wallOfBricks = new ArrayList<Brick>();
    boolean mousewasclicked;
    private int currentScore = 0;
    private String highestScore = "default:0";
    private Label score = new Label();
    private Label highScore = new Label();
    private List<Ball> balls = new ArrayList<Ball>();
    private static final double POWERUP_CHANCE = 4;
    private List<PowerUp> powerUpslist = new ArrayList<PowerUp>();
    private AnimationTimer timer;

    public GameScene(Navigator navigator) {
        super(navigator, GAME_BACKGROUND);

        Group root = (Group) getRoot();
        root.getChildren().add(score);
        root.getChildren().add(highScore);
    }

    @Override
    public void start() {
        prepare();
        Sound.play(MusicType.BACKGROUND);
        score.setFont(new Font("Arial bold", 20));
        score.setLayoutX(420);
        score.setLayoutY(450);
        highScore.setFont(new Font("Arial bold", 15));
        highScore.setLayoutX(420);
        highScore.setLayoutY(475);

        setOnMouseMoved(e -> {
            double mouseXPos = e.getX();
            double middlePoint = platform.getX() + PLATFORM.getWidth() / 2;
            if (mouseXPos - middlePoint < 25 && mouseXPos - middlePoint > -25) {
                platform.setDirection(Direction.STOP);
            } else if (mouseXPos > middlePoint) {
                platform.setDirection(Direction.RIGHT);
            } else if (mouseXPos < middlePoint) {
                platform.setDirection(Direction.LEFT);
            }
        });

        setOnMouseClicked(e -> {
            if (mousewasclicked) {
                //  ball.setStatus(Status.STOP);
            } else {
                balls.get(0).setStatus(Status.PLAY);
                balls.get(0).setStepY(-1);
                mousewasclicked = true;
            }
        });

        lifes.add(new Life(60, 450));
        lifes.add(new Life(80, 450));
        lifes.add(new Life(100, 450));

        wallOfBricks.add(new Brick(400, 50, BLACK_BRICK, 2, 300));
        wallOfBricks.add(new Brick(300, 50, BLACK_BRICK, 2, 300));
        wallOfBricks.add(new Brick(200, 50, BLACK_BRICK, 2, 300));
        wallOfBricks.add(new Brick(100, 50, BLACK_BRICK, 2, 300));

        wallOfBricks.add(new Brick(100, 80, BLUE_BRICK, 1, 200));
        wallOfBricks.add(new Brick(200, 80, BLUE_BRICK, 1, 200));
        wallOfBricks.add(new Brick(300, 80, BLUE_BRICK, 1, 200));
        wallOfBricks.add(new Brick(400, 80, BLUE_BRICK, 1, 200));

        wallOfBricks.add(new Brick(100, 110, GREEN_BRICK, 0, 100));
        wallOfBricks.add(new Brick(200, 110, GREEN_BRICK, 0, 100));
        wallOfBricks.add(new Brick(300, 110, GREEN_BRICK, 0, 100));
        wallOfBricks.add(new Brick(400, 110, GREEN_BRICK, 0, 100));

        balls.add(new Ball(0, 0, BALL, platform, Status.STOP));

        if (highestScore.equals("default:0")) {
            highestScore = this.getHighestScore();
        }
        checkScore();
        checkGameDifficulty();


        lastTimeInNanoSec = System.nanoTime();
        timer = new AnimationTimer() {
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

    @Override
    public void stop() {
        timer.stop();
        Sound.play(MusicType.STOP);
    }

    private void prepare() {
        lifes.clear();
        balls.clear();
        powerUpslist.clear();
        currentScore = 0;
        platform = new Platform(Constant.SCREEN_WIDTH / 2, 420, Images.PLATFORM);
        mousewasclicked = false;
    }


    private void paint() {
        gc.drawImage(GAME_BACKGROUND, 0, 0);
        platform.draw(gc);
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

        score.setText("Points:" + currentScore);
        highScore.setText("Highscore:" + highestScore);
    }

    private void update(double deltaInSec) {
        platform.update(deltaInSec);
        ArrayList<Ball> deadExtraBall = new ArrayList<>();
        for (Ball ball : balls) {
            ball.update(deltaInSec);
            if (checkBallBelowPlatform(ball)){
                deadExtraBall.add(ball);
            }
            checkBrickCollides(ball);
        }
        balls.removeAll(deadExtraBall);
        ArrayList<PowerUp> deadPowerups = new ArrayList<>();
        for (PowerUp powerUps : powerUpslist) {
            powerUps.update(deltaInSec);
            if (powerUps.collidesWith(platform)) {
                checkPowerUp(powerUps.getPowerType());
                deadPowerups.add(powerUps);
            }
        }
        powerUpslist.removeAll(deadPowerups);

    }

    private void checkIfWon() {
        if (wallOfBricks.size() == 0) {
            Sound.play(MusicType.STOP);
            for (Ball ball : balls) {
                ball.resetToPlatform();
            }
            checkScore();
            navigator.goTo(SceneType.WINNER_SCREEN);
        }
    }

    private void checkBrickCollides(Ball ball) {
        ArrayList<Brick> deadBricks = new ArrayList<>();
        for (Brick brick : wallOfBricks) {
            if (brick.collidesWith(ball)) {
                Sound.playTest(SoundEffectType.BALL_BOUNCE);
                checkBrick(ball);
                if (brick.getDifficulty() == 0) {
                    currentScore = currentScore + brick.getPoints();
                    int randomNum = ThreadLocalRandom.current().nextInt(1, 9 + 1);
                    if (randomNum > POWERUP_CHANCE) {
                        int PowerupType = ThreadLocalRandom.current().nextInt(1, 6 + 1);
                        dropPowerUp(brick.getX(), brick.getY(), PowerupType);
                    }
                    deadBricks.add(brick);
                    Sound.playTest(SoundEffectType.BRICK_DESTROYED);

                } else {
                    brick.setDifficulty(brick.getDifficulty() - 1);
                }
            }
        }
        wallOfBricks.removeAll(deadBricks);
        checkIfWon();
    }

    private boolean checkBallBelowPlatform(Ball ball) {

        if (ball.getY() > platform.getY()) {
            if (ball.isExtra()) {
                return true;
            } else {
                if (lifes.size() > 0) {
                    lifes.remove(lifes.size() - 1);
                    ball.resetToPlatform();
                    mousewasclicked = false;
                } else {
                    navigator.goTo(SceneType.GAMEOVER_SCREEN);
                    ball.resetToPlatform();
                    checkScore();
                }

            }
        }
        return false;
    }

    private void checkBrick(Ball ball) {
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

    private void dropPowerUp(double brickPosX, double brickPosY, int type) {
        switch (type) {
            case 1 -> {
                //Extra Leben
                powerUpslist.add(new PowerUp(brickPosX, brickPosY, POWERUP, type));
            }
            case 2 -> {
                //double points
                powerUpslist.add(new PowerUp(brickPosX, brickPosY, POWERUPDOUBLE, type));
            }
            case 3 -> {
                //Extra Ball
                powerUpslist.add(new PowerUp(brickPosX, brickPosY, POWERUPBALL, type));
            }
            case 4 -> {
                //long Platform
                powerUpslist.add(new PowerUp(brickPosX, brickPosY, POWERUPPLATFORM, type));
            }
            case 5 -> {
                //Easy Bricks
                powerUpslist.add(new PowerUp(brickPosX, brickPosY, POWERUPBRICK, type));
            }
            case 6 -> {
                //Quick Platform
                powerUpslist.add(new PowerUp(brickPosX, brickPosY, POWERUPSPEED, type));
            }
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
                //double points
                for (Brick brick : wallOfBricks) {
                    brick.setPoints(brick.getPoints() * 2);
                }
            }
            case 3 -> {
                //Extra Ball
                balls.add(new Ball(balls.get(0).getX() + 50, balls.get(0).getY(), Images.EXTRABALL, platform, Status.PLAY, true));
            }
            case 4 -> {
                //long Platform
                platform.setImage(LONGPLATFORM);
            }
            case 5 -> {
                //Easy Brick
                for (Brick brick : wallOfBricks) {
                    brick.setDifficulty(0);
                    brick.setImage(GREEN_BRICK);
                }
            }
            case 6 -> {
                //Quick Platform
                platform.setPlatformSpeed(500);
            }
        }
        Sound.playTest(SoundEffectType.POWERUP_PICKUP);
    }

    public String getHighestScore() {
        FileReader readFile;
        BufferedReader reader = null;
        // Read the first line of the File and then return it
        try {
            readFile = new FileReader("highscore.dat");
            reader = new BufferedReader(readFile);
            return reader.readLine();
        }
        // If File not found or if an error occurs, set High-Score to 0
        catch (Exception e) {
            return "0";
        }
        // Close the Reader
        finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (Exception e) {
            }
        }
    }

    public void checkScore() {
        // Split the Highscore int into an array of 2 parts, one the name and the other with the number(Name /:/ 100) Samuel  ("100") -> int 100 -> 90 > 100 -> true/false

        String[] subStrings = highestScore.split(":");
        String currentHighScore = subStrings[1];
        int currentHighScoreInt = Integer.parseInt(currentHighScore);
        if (currentScore > currentHighScoreInt) { // Integer.parseInt(highestScore.split(":")[1])){
            // Creates a Textfield that asks for Players name if they set a new record
            String name = JOptionPane.showInputDialog("You set a new Highscore! Enter your name:");
            highestScore = name + ":" + currentScore;

            File scoreFile = new File("highscore.dat");
            // Create a new File if doesn't exist
            if (!scoreFile.exists()) {
                try {
                    scoreFile.createNewFile();
                } catch (Exception e) {
                }
            }
            // Creates a FileWriter, that stores the File and creates a BufferedWriter, which allows us to write to the File
            FileWriter writeFile = null;
            BufferedWriter writer = null;
            try {
                writeFile = new FileWriter(scoreFile);
                writer = new BufferedWriter(writeFile);
                writer.write(this.highestScore);
            } catch (Exception e) {
            } finally {
                try {
                    if (writer != null) {
                        writer.close();
                    }
                } catch (Exception e) {
                }
            }
        }
    }

    private void checkGameDifficulty() {
        switch (StartScene.getGameDifficulty()) {
            case 1 -> {
                //Easy
                for (Brick brick : wallOfBricks) {
                    brick.setDifficulty(0);
                    brick.setImage(GREEN_BRICK);
                    brick.setPoints(100);
                }
            }
            case 2 -> {
                //Normal
            }
            case 3 -> {
                //Hard
                for (Brick brick : wallOfBricks) {
                    brick.setDifficulty(2);
                    if (brick.getImage() == GREEN_BRICK) {
                        brick.setImage(BLACK_BRICK);
                    }
                    brick.setPoints(300);
                }
            }
        }
    }
}
