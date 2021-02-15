package game.objects;

import com.sun.javafx.geom.Vec2d;
import game.*;

import game.Direction;
import javafx.geometry.Bounds;

import static game.Constant.SCREEN_HEIGHT;
import static game.Constant.SCREEN_WIDTH;
import static game.Images.BALL;
import static game.Images.PLATFORM;
import static game.Status.*;

public class Ball extends GameObject {


    private Status status = Status.STOP;

    private Platform platform = new Platform();
    private Direction direction = Direction.STOP;
    double stepX = 1;
    double stepY = -1;
    private final static double SPEED = 5;


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
                        this.setStepY(-1);
                    }
                    case DOWN -> {
                        this.setStepY(1);
                    }
                }
                this.setX(this.getX() + this.getStepX() * getSPEED());
                this.setY(this.getY() + this.getStepY() * getSPEED());
                changeDirectionAtBoarder();
                checkBorders();
            }


            case STOP -> {
                x = platform.getX();
                y = platform.getY() - PLATFORM.getHeight() + BALL.getHeight();

            }
        }
    }

    private void changeDirectionAtBoarder() {
        if (y < 0) {
            setDirection(Direction.DOWN);
        }
        if (this.collidesWith(platform)) {
            setDirection(Direction.UP);
        }
        if (y > SCREEN_HEIGHT) {
            setDirection(Direction.UP);
        }

    }

    public void checkBorders() {
        boolean atRightBorder = this.getX() >= (SCREEN_WIDTH - BALL.getWidth());
        boolean atLeftBorder = this.getX() <= (0);
        boolean atBottomBorder = this.getY() >= (SCREEN_HEIGHT - BALL.getHeight());
        boolean atTopBorder = this.getY() <= (SCREEN_HEIGHT);

        if (atLeftBorder) {
            this.setStepX(1);
        }
        if (atRightBorder) {
            this.setStepX(-1);
        }
        if (atTopBorder) {
            this.setStepY(1);
        }
        if (atBottomBorder) {
            this.setStepY(-1);
        }

    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getStepX() {
        return stepX;
    }

    public void setStepX(double stepX) {
        this.stepX = stepX;
    }

    public double getStepY() {
        return stepY;
    }

    public void setStepY(double stepY) {
        this.stepY = stepY;
    }

    public static double getSPEED() {
        return SPEED;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}