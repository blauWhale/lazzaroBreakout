package gui.common;

import gui.Navigator;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import static game.Constant.SCREEN_HEIGHT;
import static game.Constant.SCREEN_WIDTH;


public abstract class BaseScene extends Scene {
    protected final Navigator navigator;
    protected final Canvas canvas;
    protected GraphicsContext gc;

    public BaseScene(Navigator navigator) {
        super(new Group());
        this.navigator = navigator;
        canvas = new Canvas(SCREEN_WIDTH ,SCREEN_HEIGHT);
        ((Group)getRoot()).getChildren().add(canvas);
    }

    public BaseScene(Navigator navigator, Image backgroundImage) {
        this(navigator);
        drawBackgroundImage(backgroundImage);
    }

    private void drawBackgroundImage(Image backgroundImage){
        gc = canvas.getGraphicsContext2D();
        gc.drawImage(backgroundImage, 0, 0);
    }

    public abstract void start();

    public abstract void stop();


}