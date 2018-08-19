package road;

import gamestuff.Logic;
import gamestuff.World;
import java.awt.Toolkit;
import java.io.IOException;

public class Road extends World {

    double x = 0;
    private Score score = new Score();

    public Road() throws IOException {
        this.setTitle("Hedgehog collecting apples");
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icon/jeż-z-jablkiem.png")));
        board(800, 600, 1, false);
        setYourBackground("/icon/Bez tytułu.png");

        Hedgehog hedgehog = new Hedgehog();
        addObject(hedgehog, getWidth() / 2, getHeight() - 80);
        addObject(new Home(), getWidth() / 2 - 35, getHeight() - 80);
        addObject(score, 50, 520);
        setApples();
        setAct();
    }

    public void changeScore() {
        score.refreshPoints();
        if (Score.score == 8) {
            Bravo bravo = new Bravo();
            addObject(bravo, getWidth() / 2 - bravo.getWidth() / 2, getHeight() / 2 - bravo.getHeight() / 2);
            repaint();
            stopGame();
        }
    }

    private void setApples() {
        int howManyApples = 8;
        int x = 35;
        for (int i = 0; i < howManyApples; i++) {
            // losuje polozenie jablek na osi Y
            int y = Logic.getRandomNumber(80) + 10;
            addObject(new Apple(), x, y);
            x = x + 100;
        }
    }

    @Override
    public void act() {
        changeScore();
        if (Logic.getRandomNumber(30) == 20) {
            if (Logic.getRandomNumber(100) >= 50) {
                if (Logic.getRandomNumber(2) == 0) {
                    addObject(new Car(0), 0, 385);
                } else {
                    addObject(new Car(0), 0, 338);
                }
            } else {
                if (Logic.getRandomNumber(2) == 0) {
                    addObject(new Car(180), getWidth() - 50, 225);
                } else {
                    addObject(new Car(180), getWidth() - 50, 273);
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        world = new Road();
        world.setVisible(true);

    }
}
