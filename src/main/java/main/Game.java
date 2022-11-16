package main;

import javax.swing.*;
import java.awt.*;
import entities.Player;
import inputs.Input;
//import levels.TileManager;

public class Game extends JPanel implements Runnable{

    GamePanel gamePanel;

//    private Input key = new Input(gamePanel);
    private Player player = new Player(0, 0);
    private GamePanel gp = new GamePanel(this);
    public final static int TILE_SIZE = 32;
    public final static float SCALE = 1.0f;
    public final static int TILES_IN_WIDTH = 26;
    public final static int TILE_IN_HEIGHT = 26;
    public final static int TS = (int)(TILE_SIZE * SCALE);
    public final static int GAME_WIDTH = TILE_SIZE * TILES_IN_WIDTH;
    public final static int GAME_GEIGHT = TILE_SIZE * TILE_IN_HEIGHT;

//    TileManager tileM = new TileManager(this);

    private Thread fps;
    final int ticks = 120;

    public Game() {
        gamePanel = new GamePanel(this);
        gamePanel.requestFocus();
        start();


    }
    public void start() {
        fps = new Thread(this);
        fps.start();
    }


    public void update() {
        player.update();
//        if(gp.enemy.intersects(player.hitbox)) {
//            System.out.println("Tocou");
//        }
    }

    public void render(Graphics g) {
        player.render(g);
    }


    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D graphics = (Graphics2D) g;
        gamePanel.paint(graphics);
//        tileM.paint(graphics);

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
