package road;

import gamestuff.Actor;
import gamestuff.World;

public class Hedgehog extends Actor {

    private int speed = 5;
    private boolean getApple = false;
    private boolean isAlive = true;
    
    public Hedgehog() {
        useKeys();
        setAct();
        setYourImage("/icon/jeż.png");
    }

    @Override
    public void act() {
        if (ifCarHits()) {
            GameOver gameOver = new GameOver();
            World.world.addObject(gameOver, getActorWorldWidt() / 2 - gameOver.getWidth() / 2, getActorWorldHeight() / 2 - gameOver.getHeight() / 2);
            gameOver.repaint();
            setYourImage("/icon/krew.png");
            isAlive = false;
            World.world.isGameOver = true;
        }
        if (isAlive) {
            hedgehogMoves();
            if (!getApple) {
                findApple();
            } else {
                findHome();
            }
        }
    }

    private boolean ifCarHits() {
        try {
            Actor car = getOneIntersectionObject(Car.class);

            if (car != null) {
                return true;
            }
        } catch (java.lang.NullPointerException e) {
//            System.out.println(e);
        }
        return false;
    }

    private void findApple() {
        try {
            Actor apple = getOneIntersectionObject(Apple.class);

            if (apple != null) {
                getApple = true;
                removeObject(apple);
            }
        } catch (java.lang.NullPointerException e) {
//            System.out.println(e);
        }
    }

    private void findHome() {
        try {
            Actor home = getOneIntersectionObject(Home.class);

            if (home != null) {
                Score.score++;
                getApple = false;
            }
        } catch (java.lang.NullPointerException e) {
//            System.out.println(e);
        }
    }

    private void hedgehogMoves() {
        if (isKeyDown('a')) {
            if (getApple) {
                setYourImage("/icon/jeż-z-jablkiem2.png");
            } else {
                setYourImage("/icon/jeż2.png");
            }
            this.angle = 180;
            move(speed);
        }
        if (isKeyDown('d')) {
            selectImage(getApple);
            this.angle = 0;
            move(speed);
        }
        if (isKeyDown('w')) {
            selectImage(getApple);
            setRotation(-90);
            move(speed);
        }
        if (isKeyDown('s')) {
            selectImage(getApple);
            setRotation(90);
            move(speed);
        }
    }
    
    private void selectImage(boolean getApple) {
        if (getApple) {
            setYourImage("/icon/jeż-z-jablkiem.png");
        } else {
            setYourImage("/icon/jeż.png");
        }
    }
}
