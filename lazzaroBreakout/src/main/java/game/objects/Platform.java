package game.objects;

import game.*;
import javafx.scene.input.MouseEvent;

public class Platform extends GameObject {


    private Direction direction = Direction.STOP;

    private final static double SPEED = 300;


    public Platform() {
        super(Constant.SCREEN_WIDTH / 2, 420, Images.PLATFORM);
    }

    @Override
    public void update(double deltaInSec) {
        double distanceToMove = SPEED * deltaInSec;
        switch (direction) {
            case LEFT -> {
                if (x - distanceToMove < 0) {
                    x = 0;
                } else {
                    x -= distanceToMove;
                }
            }
            case RIGHT -> {
                if (x < Constant.SCREEN_WIDTH - getImage().getWidth())
                    x += distanceToMove;
            }
            case STOP -> {
                x = getX();
                y = getY();
            }
        }
    }


    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

}
