package game;

import javafx.scene.canvas.GraphicsContext;

public class Space {

    public void load(){

    }

    public void draw(GraphicsContext gc) {
        gc.clearRect(0, 0, Constant.SCREEN_WIDTH, Constant.SCREEN_HEIGHT);
        gc.drawImage(Images.GAME_BACKGROUND, 0, 0);
    }

}
