package game.objects;

import game.Constant;
import game.Images;
import game.MouseEventHandler;
import game.Space;
import javafx.scene.input.MouseEvent;

public class Platform extends GameObject{

    public final static double PLATFORMPOSX = 275;
    public final static double PLATFORMPOSY = 420;
    private final static double SPEED = 100;
    private final MouseEventHandler mouseEvent;

    public Platform(MouseEventHandler mouseEvent) {
        super(Constant.SCREEN_WIDTH / 2, PLATFORMPOSY, Images.PLATFORM);
        this.mouseEvent = mouseEvent;
    }

    @Override
    public void update(double deltaInSec) {
        handleNavigationEvents(deltaInSec);

    }

    private void handleNavigationEvents(double deltaInSec) {
        double distanceToMove = SPEED * deltaInSec;

        if (mouseEvent.isOnRightSide() && getX() < Constant.SCREEN_WIDTH - getImage().getWidth())
            x += distanceToMove;
        if (mouseEvent.isOnLeftSide() && getX() > 0)
            x -= distanceToMove;
    }
}
