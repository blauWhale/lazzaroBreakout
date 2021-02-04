package game.objects;

import game.Space;
import game.Images;

public class Ball extends GameObject {
    public final static double BALLPOSX = 300;
    public final static double BALLPOSY = 400;

    private final static double SPEED = 100;

    public Ball(double x, double y) {
        super(x, y, Images.BALL);
    }

    @Override
    public void update(double deltaInSec) {
        y -= SPEED * deltaInSec;
    }
}
