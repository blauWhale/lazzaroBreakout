package game;

import javafx.scene.image.Image;

public class Images {

    public final static Image GAME_BACKGROUND = getImage("background.jpg");
    public final static Image PLATFORM = getImage("platform.png");
    public final static Image BALL = getImage("ball.png");
    public final static Image BRICK = getImage("brick.png");
    public final static Image GREEN_BRICK = getImage("green_brick.png");
    public final static Image BLUE_BRICK = getImage("blue_brick.png");
    public final static Image BLACK_BRICK = getImage("black_brick.png");
    public final static Image GAMEOVER = getImage("game_over.png");
    public final static Image WELCOME = getImage("welcome.png");
    public final static Image WINNER =  getImage("winner.png");




    public final static Image HEART = getImage("heart.png");

    private static Image getImage(String imagePath) {
        return new Image("/Images/" + imagePath);
    }
}
