package game.objects;

import javafx.geometry.BoundingBox;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class GameObject {

    private Image image;
    protected double x;
    protected double y;

    public GameObject(double x, double y, Image image) {
        this.x = x;
        this.y = y;
        this.image = image;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    protected BoundingBox getBoundingBox() {
        return new BoundingBox(this.x, this.y, image.getWidth(), image.getHeight());
    }

    public boolean collidesWith(GameObject e) {
        return getBoundingBox().intersects(e.getBoundingBox());
    }

    public abstract void update(double deltaInSec);

    public void draw(GraphicsContext gc) {
        gc.drawImage(image, x, y);
    }
}