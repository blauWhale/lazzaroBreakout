package game.objects;
import game.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
public class Platform extends GameObject {

    private Direction direction = Direction.STOP;
    private final static double SPEED = 300;
    Image image;
    public Platform(Image image) {
        super(Constant.SCREEN_WIDTH / 2, 420, image);
        this.image = image;
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
    @Override
    public Image getImage() {
        return image;
    }
    public void setImage(Image image) {
        this.image = image;
    }
    public Direction getDirection() {
        return direction;
    }
    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}
