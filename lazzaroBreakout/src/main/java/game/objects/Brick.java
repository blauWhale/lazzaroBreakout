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
    private double difficulty;
    private int points;

    public Brick(double x, double y, Image image, double difficulty, int points) {
        super(x, y, image);
        this.difficulty = difficulty;
        this.points = points;
    }

    @Override
    public void update(double deltaInSec) {
        if (this.collidesWith(ball)) {
            System.out.println("Coolio");
        }
    }


    public double getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(double difficulty) {
        this.difficulty = difficulty;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
