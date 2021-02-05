package game.objects;

import game.*;

import static game.Status.*;

public class Ball extends GameObject {

    private Status status = Status.STOP;


    private final static double SPEED = 100;

    public Ball(double x, double y) {
        super(x, y, Images.BALL);
    }

    @Override
    public void update(double deltaInSec) {
        double distanceToMove = SPEED * deltaInSec;
        switch (status) {
            case START -> {
                y -= SPEED * deltaInSec;
            }
            case PLAY -> {
                //TODO
            }
            case STOP -> {
                //TODO
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