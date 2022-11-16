package inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import main.GamePanel;
import main.Game;
import entities.*;
import static utils.Constants.Directions.*;

import static utils.Constants.PlayerConstants.*;

public class Input implements KeyListener {

    private GamePanel gp;
    private Game game;

    public boolean up, down, left, right, action;


    public Input(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_W) {
            gp.getGame().getPlayer().setUp(true);
        } else if (key == KeyEvent.VK_S) {
            gp.getGame().getPlayer().setDown(true);
        } else if (key == KeyEvent.VK_D) {
            gp.getGame().getPlayer().setRight(true);
        } else if (key == KeyEvent.VK_A) {
            gp.getGame().getPlayer().setLeft(true);
        } else if (key == KeyEvent.VK_SPACE) {
            gp.getGame().getPlayer().setAttacking(true);
        }
    }


    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_W) {
            gp.getGame().getPlayer().setUp(false);
        } else if (key == KeyEvent.VK_S) {
            gp.getGame().getPlayer().setDown(false);
        } else if (key == KeyEvent.VK_D) {
            gp.getGame().getPlayer().setRight(false);
        } else if (key == KeyEvent.VK_A) {
            gp.getGame().getPlayer().setLeft(false);
        } else if (key == KeyEvent.VK_SPACE) {
            gp.getGame().getPlayer().setAttacking(false);
        }
    }


}
