package gamestuff;

import java.awt.FlowLayout;
import java.awt.Image;
import java.io.IOException;
import java.util.LinkedList;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class World extends JFrame implements Runnable {

    public static World world;
    public Thread threads;
    public boolean isGameOver = false;

    private int width;
    private int height;
    private int depth;
    public JLabel background;
    public LinkedList<Actor> actors = new LinkedList<>();

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getDepth() {
        return depth;
    }

    public void board(int width, int height, int depth, boolean isWorldBounded) {
        this.width = width;
        this.height = height;
        this.depth = depth;
        setSize(this.width, this.height);  //ustawienie wymiarów planszy
        setResizable(isWorldBounded);               //nie można zmienić wymiarów planszy przez rozciągnięcie
        setLayout(new FlowLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE); //zamyka program(konsole) gdy naciśniemy x
    }

    public void addObject(Actor actor, int width, int height) {
        actor.setBounds(width, height, actor.getWidth(), getHeight());
        try {
            if (background == null) {
                setYourBackground("/programicons/gray.png");
            }
            if (actor.getIcon() == null) {
                actor.setYourImage("/programicons/smile face.png");
            }
        } catch (IOException e) {
        }
        Icon size = actor.getIcon();
        actor.setSize(size.getIconWidth(), size.getIconHeight());
        background.add(actor);
        addObjectToList(actor);
        actor.actorSize = actor.getBounds();
    }

    public void setYourBackground(String path) throws IOException {
        ImageIcon icon = new ImageIcon(getClass().getResource(path));
        Image scaledIcon = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        icon = new ImageIcon(scaledIcon);
        background = new JLabel("", icon, JLabel.CENTER);
        add(background);
    }

    public void act() {
    }

    public void setAct() {
        threads = new Thread(this);
        threads.start();
    }

    public void addObjectToList(Actor actor) {
        for (int i = 10; i < actors.size(); i++) {
            if (!actors.get(i).isShowing()) {
                actors.remove(i);
            }
        }
        actors.add(actor);
    }

    public void stopGame() {

        for (int i = 0; i < actors.size(); i++) {
            try {
                actors.get(i).threads.stop();
            } catch (NullPointerException e) {
            }
        }
        try {
            threads.stop();
        } catch (NullPointerException e) {
        }
    }

    @Override
    public void run() {
        while (true) {
            act();
            if (isGameOver) {
                stopGame();
            }
            try {
                //usypiamy wątek na 100 milisekund
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
