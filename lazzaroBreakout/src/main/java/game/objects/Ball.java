package game.objects;
import com.sun.javafx.geom.Vec2d;
import game.*;
import game.Direction;
import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import static game.Constant.SCREEN_HEIGHT;
import static game.Constant.SCREEN_WIDTH;
import static game.Images.*;
import static game.Status.*;
public class Ball extends GameObject {

    private Status status = Status.STOP;
    private Platform platform;
    private Direction direction = Direction.STOP;
    double stepX = 1;
    double stepY = -1;
    private final static double SPEED = 3;
    private boolean extra = false;

    public Ball(double x, double y, Image image, Platform platform, Status status) {
        this(x,y, image,platform,status,false);
    }

    public Ball(double x, double y, Image image, Platform platform, Status status, boolean isExtra) {
        super(x, y, image);
        this.platform = platform;
        this.status = status;
        this.extra = isExtra;
    }

    @Override
    public void update(double deltaInSec) {
        double distanceToMove = SPEED * deltaInSec;
        switch (status) {
            case PLAY -> {
                this.setX(this.getX() + this.getStepX() * getSPEED());
                this.setY(this.getY() + this.getStepY() * getSPEED());
                changeDirectionAtPlatform();
                checkBorders();
            }

            case STOP -> {
                x = platform.getX();
                y = platform.getY() - PLATFORM.getHeight() + BALL.getHeight();
            }
        }
    }
    private void changeDirectionAtPlatform() {
        if (this.collidesWith(platform)) {
            this.setStepY(-1);
            Sound.play(SoundEffectType.BALL_BOUNCE);
        }
    }
    public void checkBorders() {
        boolean atRightBorder = this.getX() >= (SCREEN_WIDTH - BALL.getWidth());
        boolean atLeftBorder = this.getX() <= 0;
        boolean atBottomBorder = this.getY() >= (SCREEN_HEIGHT - BALL.getHeight());
        boolean atTopBorder = this.getY() <= 0;
        if (atLeftBorder) {
            this.setStepX(1);
            Sound.play(SoundEffectType.BALL_BOUNCE);
        }
        if (atRightBorder) {
            this.setStepX(-1);
            Sound.play(SoundEffectType.BALL_BOUNCE);
        }
        if (atTopBorder) {
            this.setStepY(1);
            Sound.play(SoundEffectType.BALL_BOUNCE);
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
    public boolean isExtra() {
        return extra;
    }
    public void setExtra(boolean extra) {
        this.extra = extra;
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
    public void resetToPlatform(){
        this.status = STOP;
        this.stepY = 1;
    }
}