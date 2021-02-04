package game;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

import static game.Constant.SCREEN_WIDTH;

public class MouseEventHandler implements EventHandler<MouseEvent> {

    private boolean isMousOnLeftSide = false;
    private boolean isMousOnRightSide = false;


    public void onMouseMove(MouseEvent event){
        if (event.getY() >= SCREEN_WIDTH/2){
            isMousOnRightSide = false;
            isMousOnLeftSide = true;

        }

        if (event.getY() <= SCREEN_WIDTH/2){
            isMousOnLeftSide = false;
            isMousOnRightSide = true;
        }

    }

    public boolean isOnLeftSide() {
        return isMousOnLeftSide;
    }

    public boolean isOnRightSide() {
        return isMousOnRightSide;
    }

    @Override
    public void handle(MouseEvent event) {

    }
}
