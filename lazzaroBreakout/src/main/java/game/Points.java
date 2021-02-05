package game;

import javafx.scene.control.Label;

public class Points extends Label {

    private int points = 0;

    public Points() {
        setLabelText();
    }
    public void addPoints() {
        points = points + 100;
        setLabelText();
    }
    private void setLabelText(){
        super.setText("Points: " + points);
    }
}