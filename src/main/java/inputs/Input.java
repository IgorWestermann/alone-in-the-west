package inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import main.GamePanel;
import entities.*;
import static utils.Constants.Directions.*;

import static utils.Constants.PlayerConstants.*;

public class Input implements KeyListener {

    private GamePanel gp;

    public boolean up, down, left, right, action;


    public Input(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }
//
//    @Override
//    public void keyPressed(KeyEvent e) {
//
//        switch (e.getKeyCode()) {
//            case KeyEvent.VK_W -> {
////                player.setIdle(false);
////                player.changeYDelta(-10);
////                player.direction = "up";
//            }
//            case KeyEvent.VK_A -> {
////                player.setIdle(false);
////                player.changeXDelta(-10);
////                player.direction = "left";
//            }
//            case KeyEvent.VK_S -> {
////                player.setIdle(false);
////                player.changeYDelta(10);
////                player.direction = "down";
//            }
//            case KeyEvent.VK_D -> {
////                player.setIdle(false);
////                player.changeXDelta(10);
////                player.direction = "right";
//            }
//        }
//    }
//
//
//    @Override
//    public void keyReleased(KeyEvent e) {
//        switch (e.getKeyCode()) {
//            case KeyEvent.VK_W, KeyEvent.VK_A, KeyEvent.VK_S, KeyEvent.VK_D -> player.setIdle(true);
//        }
//    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_UP) {
            gp.getGame().getPlayer().setUp(true);
        } else if (key == KeyEvent.VK_DOWN) {
            gp.getGame().getPlayer().setDown(true);
        } else if (key == KeyEvent.VK_RIGHT) {
            gp.getGame().getPlayer().setRight(true);
        } else if (key == KeyEvent.VK_LEFT) {
            gp.getGame().getPlayer().setLeft(true);
        } else if (key == KeyEvent.VK_A) {
            gp.getGame().getPlayer().setAction(true);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_UP) {
            gp.getGame().getPlayer().setUp(false);
        } else if (key == KeyEvent.VK_DOWN) {
            gp.getGame().getPlayer().setDown(false);
        } else if (key == KeyEvent.VK_RIGHT) {
            gp.getGame().getPlayer().setRight(false);
        } else if (key == KeyEvent.VK_LEFT) {
            gp.getGame().getPlayer().setLeft(false);
        } else if (key == KeyEvent.VK_A) {
            gp.getGame().getPlayer().setAction(false);
        }
    }

}
