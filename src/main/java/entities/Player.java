package entities;


import inputs.Input;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import static utils.Constants.PlayerConstants.*;

public class Player extends Entity {

    Input input;

    // Images
    public int spriteTick = 0, spriteIndex, spriteSpeed = 50;
    private BufferedImage[] downIdleAni, leftIdleAni, rightIdleAni, upIdleAni,
            downMoveAni, upMoveAni, rightMoveAni, leftMoveAni;
//                            attackLeft, attackRight, attackUp, attackDown;

    //  Player
//    public boolean isIdle = true;
//    public String direction;
    public int playerAction = isIdle ? IDLE : RUNNING;

    private boolean up, down, left, right, action;

    public Hitbox hitbox;

//    public float x = 100,y = 100;



    public Player(float x, float y, int width, int height, Input input) {
        super(x, y, width, height);
        this.input = input;
        this.direction = "down";
        this.isIdle = true;
        initHitbox();
        load();
    }



    private void load() {

        downIdleAni = new BufferedImage[8];
        upIdleAni = new BufferedImage[8];
        rightIdleAni = new BufferedImage[8];
        leftIdleAni = new BufferedImage[8];

        downMoveAni = new BufferedImage[8];
        upMoveAni = new BufferedImage[8];
        rightMoveAni = new BufferedImage[8];
        leftMoveAni = new BufferedImage[8];

        try {
            downIdleAni[0] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/down_idle/0.png")));
            downIdleAni[1] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/down_idle/1.png")));
            downIdleAni[2] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/down_idle/2.png")));
            downIdleAni[3] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/down_idle/3.png")));
            downIdleAni[4] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/down_idle/4.png")));
            downIdleAni[5] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/down_idle/5.png")));
            downIdleAni[6] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/down_idle/6.png")));
            downIdleAni[7] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/down_idle/7.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }


        for (int i = 0; i <= 5; i++) {
            try {
                leftIdleAni[i] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/left_idle/" + i + ".png")));
                upIdleAni[i] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/up_idle/" + i + ".png")));
                rightIdleAni[i] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/right_idle/" + i + ".png")));
                downIdleAni[i] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/down_idle/" + i + ".png")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i <= 7; i++) {
            try {
                leftMoveAni[i] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/left/" + i + ".png")));
                upMoveAni[i] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/up/" + i + ".png")));
                rightMoveAni[i] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/right/" + i + ".png")));
                downMoveAni[i] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/down/" + i + ".png")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void update() {
        updateHitbox();
        if (up && !down) {
            y -= 2;
            direction = "up";
            isIdle = false;
        } else if (down && !up) {
            y += 2;
            direction = "down";
            isIdle = false;
        } else if (left && !right) {
            x -= 2;
            direction = "left";
            isIdle = false;
        } else if (right && !left) {
            x += 2;
            direction = "right";
            isIdle = false;
        } else {
            isIdle = true;
        }

        spriteTick++;
        if(spriteTick >= spriteSpeed) {
            spriteTick = 0;
            spriteIndex++;
            if(spriteIndex > GetSpriteAmount(playerAction))
                spriteIndex = 0;
        }

        if(isIdle)
            playerAction = IDLE;
        else
            playerAction = RUNNING;



    }

    public void render(Graphics g) {
//        hitbox = new Hitbox((int)x + 35, (int)y, 64, 128, Color.red);
        drawHitbox(g);
//        hitbox.draw(g);
        if(isIdle) {
            if(Objects.equals(direction, "up")) {
                g.drawImage(upIdleAni[spriteIndex], (int)x, (int)y, null);
            }else if(Objects.equals(direction, "down")){
                g.drawImage(downIdleAni[spriteIndex], (int)x, (int)y, null);
            }else if(Objects.equals(direction, "left")){
                g.drawImage(leftIdleAni[spriteIndex], (int)x, (int)y, null);
            }else if(Objects.equals(direction, "right")){
                g.drawImage(rightIdleAni[spriteIndex], (int)x, (int)y, null);
            } else {
                g.drawImage(downIdleAni[spriteIndex], (int)x, (int)y, null);
            }
        } else {
            if(Objects.equals(direction, "up")) {
                g.drawImage(upMoveAni[spriteIndex], (int)x, (int)y, null);
            }else if(Objects.equals(direction, "down")){
                g.drawImage(downMoveAni[spriteIndex], (int)x, (int)y, null);
            }else if(Objects.equals(direction, "left")){
                g.drawImage(leftMoveAni[spriteIndex], (int)x, (int)y, null);
            }else if(Objects.equals(direction, "right")){
                g.drawImage(rightMoveAni[spriteIndex], (int)x, (int)y, null);
            }
        }
    }


    public void setIdle(boolean idle) {
        this.isIdle = idle;
    }

    public void changeXDelta(int value) {
        this.x += value;
    }

    public void changeYDelta(int value) {
        this.y += value;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isAction() {
        return action;
    }

    public void setAction(boolean action) {
        this.action = action;
    }
}
