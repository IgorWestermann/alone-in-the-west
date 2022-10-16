package main;

import javax.swing.*;
import java.awt.*;
import entities.Player;
import inputs.Input;

public class Game extends JPanel implements Runnable{

    GamePanel gamePanel;

    private Input key = new Input(gamePanel);
    private Player player = new Player(200, 200, key);

    private Thread fps;
    final int ticks = 120;


    public Game() {
        initClasses();
        gamePanel = new GamePanel(this);
        gamePanel.requestFocus();
        start();


    }

    private void initClasses() {

    }

    public void start() {
        fps = new Thread(this);
        fps.start();
    }


    public void update() {
        player.update();
    }

    public void render(Graphics g) {
        player.render(g);
    }


    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D graphics = (Graphics2D) g;
        gamePanel.paint(graphics);


        g.dispose();

    }

    @Override
    public void run() {
        long initialTime = System.nanoTime();
        final double timeF = 1000000000 / ticks;
        double deltaF = 0;

        // game loop
        while(fps != null) {
            long currentTime = System.nanoTime();
            deltaF += (currentTime - initialTime) / timeF;
            initialTime = currentTime;
            if (deltaF >= 1) {
                update();
                deltaF--;
            }
            gamePanel.repaint();
        }
    }

    public Player getPlayer() {
        return player;
    }
}
