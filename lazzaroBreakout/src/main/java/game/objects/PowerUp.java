package game.objects;

import game.Images;
import javafx.scene.image.Image;

public class PowerUp extends GameObject {

    private static final double SPEED_OF_POWERUPS = 100;
    private double powerType;

    public PowerUp(double x, double y, Image image, double powerType) {
        super(x, y, image);
        this.powerType = powerType;
    }

    @Override
    public void update(double deltaInSec){
        double distanceToMove = SPEED_OF_POWERUPS * deltaInSec;
        y += distanceToMove;
    }

    public double getPowerType() {
        return powerType;
    }

    public void setPowerType(double powerType) {
        this.powerType = powerType;
    }
}
