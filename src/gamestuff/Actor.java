package gamestuff;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Actor extends JLabel implements KeyListener, Runnable {

    public char key;
    public ImageIcon icon;
    public Thread threads;
    public Rectangle actorSize;
    public int angle = 0;

    public void useKeys() {
        this.addKeyListener(this);
        setFocusable(true);
    }

    public void setAct() {
        threads = new Thread(this);
        threads.start();
    }

    public void setYourImage(String path) {
        icon = new ImageIcon(getClass().getResource(path));
        this.setSize(icon.getIconWidth(), icon.getIconHeight());
        this.setIcon(icon);
    }

    public void act() {
    }

    public void move(int speed) {
        int x = (int) (speed * Math.cos(Math.toRadians(angle)));
        int y = (int) (speed * Math.sin(Math.toRadians(angle)));
        this.changeLocation(x, y);
    }

    public void setRotation(int angle) {
        this.angle = angle;

        Image img = this.icon.getImage();

        ImageIcon icon = new ImageIcon(img);
        double widthXSin = icon.getIconWidth() * Math.sin(Math.toRadians(90 - angle));
        if (widthXSin < 0) {
            widthXSin *= -1;
        }
        double heightXCos = icon.getIconHeight() * Math.cos(Math.toRadians(90 - angle));
        if (heightXCos < 0) {
            heightXCos *= -1;
        }
        int width = (int) (widthXSin + heightXCos);

        double widthXCos = icon.getIconWidth() * Math.cos(Math.toRadians(90 - angle));
        if (widthXCos < 0) {
            widthXCos *= -1;
        }
        double heightXSin = icon.getIconHeight() * Math.sin(Math.toRadians(90 - angle));
        if (heightXSin < 0) {
            heightXSin *= -1;
        }
        int height = (int) (widthXCos + heightXSin);

        BufferedImage blankCanvas = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = (Graphics2D) blankCanvas.createGraphics();
        g2.translate(blankCanvas.getWidth() / 2, blankCanvas.getHeight() / 2);
        g2.rotate(Math.toRadians(angle));
        g2.translate(-icon.getIconWidth() / 2, -icon.getIconHeight() / 2);
        g2.drawImage(img, 0, 0, null);

        this.setSize(width, height);
        this.setIcon(new ImageIcon(blankCanvas));
    }

    public void changeLocation(int stepX, int stepY) {
        this.setBounds(this.getX() + stepX, getY() + stepY, this.getWidth(), this.getHeight());
        this.actorSize = this.getBounds();
//        this.key = (char) 0;
    }

    public boolean isKeyDown(char key) {
        return (this.key == key);
    }

    public Actor getOneIntersectionObject(Class searchedActorClass) {
        int howManyActors = World.world.actors.size();
        for (int i = 0; i < howManyActors; i++) {
            Actor nextActor = World.world.actors.get(i);
            if (this.actorSize.intersects(nextActor.actorSize) && nextActor.getClass() == searchedActorClass) {
                return nextActor;
            }
        }
        return null;
    }

    public void removeObject(Actor actor) {
        actor.setSize(0, 0);
        actor.getParent().remove(actor);
        actor.repaint();
        actor.actorSize = actor.getBounds();
        actor.threads.stop();
    }

    public int getActorWorldWidt() {
        return (this.getParent().getWidth());
    }

    public int getActorWorldHeight() {
        return (this.getParent().getHeight());
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        this.key = e.getKeyChar();
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void run() {
        while (true) {
            act();
            this.key = (char) 0;
            try {
                //usypiamy wątek na 100 milisekund
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
