package game;

import javafx.scene.image.Image;

public class Images {

    public final static Image GAME_BACKGROUND = getImage("background.jpg");
    public final static Image PLATFORM = getImage("platform.png");
    public final static Image BALL = getImage("ball.png");
    public final static Image BRICK = getImage("brick.png");

    private static Image getImage(String imagePath) {
        return new Image("/Images/" + imagePath);
    }
}
