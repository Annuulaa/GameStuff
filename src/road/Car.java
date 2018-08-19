package road;

import gamestuff.Actor;
import gamestuff.Logic;

public class Car extends Actor {

    private int speed = 8;

    public Car(int angle) {
        int randomNumber = Logic.getRandomNumber(3);
        switch (randomNumber) {
            case 0:
                setYourImage("/icon/car01.png");
                break;
            case 1:
                setYourImage("/icon/car02.png");
                break;
            case 2:
                setYourImage("/icon/car03.png");
                break;
        }
        setRotation(angle);
        setAct();
        if (angle > 0) {
            speed = -speed;
        }
    }

    @Override
    public void act() {
        changeLocation(speed, 0);
        try {
            if (getX() > getActorWorldWidt() - this.getWidth() / 2 || getX() < -this.getWidth() / 2) {
                removeObject(this);
            }
        } catch (NullPointerException e) {
//            System.out.println(e);
        }

    }

}
