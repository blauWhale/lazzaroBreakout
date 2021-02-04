package game.objects;

import game.Images;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Brick extends GameObject{

    public int brickWall[][];

    public Brick(double row, double col, Image image) {
        super(row, col, image);
        brickWall = new int[(int) row][(int) col];
        for (int i = 0; i < brickWall.length; i++){
            for (int j = 0; j < brickWall[0].length; j++){
                brickWall[i][j] = 1;
            }
        }
    }

    @Override
    public void draw(GraphicsContext gc){
        for (int i = 0; i < brickWall.length; i++){
            for (int j = 0; j < brickWall[0].length; j++){
               if (brickWall[i][j] > 0){
                   gc.drawImage(Images.BRICK, j * Images.BRICK.getWidth() + 10, i * Images.BRICK.getHeight() + 10);
               }
            }
        }
    }

    @Override
    public void update(double deltaInSec) {

    }
}
