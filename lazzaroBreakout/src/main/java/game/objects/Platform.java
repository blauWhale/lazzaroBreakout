package game.objects;
import game.*;
import javafx.scene.image.Image;

public class Platform extends GameObject {

    private Direction direction = Direction.STOP;
    private double PLATFORM_SPEED = 300;
    Image image;
    public Platform(double x, double y,Image image) {
        super(x,y, image);
    }
    @Override
    public void update(double deltaInSec) {
        double distanceToMove = PLATFORM_SPEED * deltaInSec;
        switch (direction) {
            case LEFT -> {
                if (x - distanceToMove < 0) {
                    x = 0;
                } else {
                    x -= distanceToMove;
                }
            }
            case RIGHT -> {
                if (x < Constant.SCREEN_WIDTH - this.getImage().getWidth())
                    x += distanceToMove;
            }
            case STOP -> {
                x = getX();
                y = getY();
            }
        }
    }


    public double getPlatformSpeed() {
        return PLATFORM_SPEED;
    }

    public void setPlatformSpeed(double platformSpeed) {
        PLATFORM_SPEED = platformSpeed;
    }


    public Direction getDirection() {
        return direction;
    }
    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}
