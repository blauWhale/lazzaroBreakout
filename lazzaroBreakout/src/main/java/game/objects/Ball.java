package game.objects;

import game.*;

import game.Direction;

import static game.Constant.SCREEN_HEIGHT;
import static game.Images.BALL;
import static game.Images.PLATFORM;
import static game.Status.*;

public class Ball extends GameObject {


    private Status status = Status.STOP;

    private Platform platform = new Platform();
    private Direction direction = Direction.STOP;

    private final static double SPEED = 250;

    public Ball(double x, double y, Platform platform) {
        super(x, y, Images.BALL);
        this.platform = platform;
    }


    @Override
    public void update(double deltaInSec) {
        double distanceToMove = SPEED * deltaInSec;
        switch (status) {
            case PLAY -> {
                switch (direction) {
                    case UP -> {
                        y -= SPEED * deltaInSec;
                    }
                    case DOWN -> {
                        y += SPEED * deltaInSec;
                    }
                }
                changeDirectionAtBoarder();
            }
            case STOP -> {
                    x = platform.getX();
                    y = platform.getY() - PLATFORM.getHeight() + BALL.getHeight();

                }
            }
        }

    private void changeDirectionAtBoarder() {
        if (y < 0){
            setDirection(Direction.DOWN);
        }
        if (y > SCREEN_HEIGHT){
            setDirection(Direction.UP);
        }

    }


    public Status getStatus () {
            return status;
        }

        public void setStatus (Status status){
            this.status = status;
        }

        public Direction getDirection () {
            return direction;
        }

        public void setDirection (Direction direction){
            this.direction = direction;
        }
    }