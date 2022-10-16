package main;

import entities.Player;
import inputs.Input;
import inputs.MouseInput;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {

    // Screen settings
//    public final int TILESIZE = 48;
//    public final int ROW = 12;
//    public final int COL = 16;
    public final int WIDTH = 768; //768
    public final int HEIGHT = 576; // 576

//    // Map settings
//    public final int WORLD_COL = 50;
//    public final int WORLD_ROW = 50;
//    public final int WORLD_WIDTH = WORLD_COL * TILESIZE;
//    public final int WORLD_HEIGHT = WORLD_ROW * TILESIZE;

    // Player
    private Player player;


    private Game game;

    private JFrame jframe;

    public GamePanel(Game game) {

        this.game = game;

        Dimension size = new Dimension(1280, 720);

        jframe = new JFrame();

        jframe.setSize(WIDTH,HEIGHT);
        setMaximumSize(size);
        setMinimumSize(size);
        setPreferredSize(size);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setResizable(false);
        jframe.add(this);
        jframe.setLocationRelativeTo(null);
        jframe.pack();
        jframe.setVisible(true);

        this.addKeyListener(new Input(this));
        this.addMouseListener(new MouseInput(this));
        this.addMouseMotionListener(new MouseInput(this));

    }




    public void paint(Graphics g) {
        super.paint(g);
        game.render(g);

    }

    public Game getGame() {
        return game;
    }

}
