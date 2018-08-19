package road;

import gamestuff.Actor;
import java.awt.Color;

public class Score extends Actor {

    public static int score = 0;
    Color c = new Color(127, 127, 127, 127);

    public Score() {
        setYourImage("/icon/ap.png");
    }

    public static void addPoint() {
        score++;
    }

    public void refreshPoints() {
//        this.setOpaque(true);
        this.setSize(200, 50);
        this.setBackground(c);
        this.setForeground(Color.WHITE);
        this.setFont(new java.awt.Font("Comic Sans MS", 1, 16));
        this.setText("Punkty: " + score);
    }
}
