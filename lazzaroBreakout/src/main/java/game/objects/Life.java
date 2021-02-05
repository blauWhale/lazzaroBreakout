package game.objects;

import game.Images;
import javafx.scene.image.Image;

public class Life extends GameObject {


    public Life(double x, double y) {
        super(x, y, Images.HEART);
    }


    @Override
    public void update(double deltaInSec) {

    }
}
