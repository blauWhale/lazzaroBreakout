package game.objects;

import game.*;

import static game.Images.BALL;
import static game.Images.PLATFORM;
import static game.Status.*;

public class Ball extends GameObject {


    private Status status = Status.STOP;

    Platform platform = new Platform();

    private final static double SPEED = 100;

    public Ball(double x, double y, Platform platform) {
        super(x,y, Images.BALL);
        platform = this.platform;
    }


    @Override
    public void update(double deltaInSec) {
        double distanceToMove = SPEED * deltaInSec;
        switch (status) {
            case PLAY -> {
                y -= SPEED * deltaInSec;
                if (y < 0){
                    y += SPEED * deltaInSec;

                }
            }
            case STOP -> {
                x = platform.getX();
                y = platform.getY() - PLATFORM.getHeight() + BALL.getHeight();
                }
            }
        }




    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}