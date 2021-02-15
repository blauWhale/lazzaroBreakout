package game.objects;

import game.Direction;
import game.Images;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Brick extends GameObject{

    public int brickWall[][];
    private Ball ball;
    private boolean destroyed;

    public Brick(double x, double y) {
        super(x, y, Images.BRICK);
    }


    @Override
    public void update(double deltaInSec) {
        if (this.collidesWith(ball)) {
            System.out.println("Coolio");
        }
    }



    public boolean isDestroyed() {
        return destroyed;
    }

    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
    }
}
