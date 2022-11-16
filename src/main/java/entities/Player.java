package entities;


import inputs.Input;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

import static utils.Constants.Directions.*;
import static utils.Constants.PlayerConstants.*;

public class Player extends Entity {

    Input input;
    public Hitbox hitbox;

    // Images
    public int spriteTick = 0, spriteIndex, spriteSpeed = 360;
    private BufferedImage[] downIdleAni, leftIdleAni, rightIdleAni, upIdleAni,
            downMoveAni, upMoveAni, rightMoveAni, leftMoveAni;
//                            attackLeft, attackRight, attackUp, attackDown;
    private BufferedImage[][] sideAnimations;
    private BufferedImage[][] backAnimations;
    private BufferedImage[][] frontAnimations;
    private BufferedImage[][] angleDownAnimations;
    private BufferedImage[][] angleUpAnimations;

    // Actions
    private int playerAction = IDLE;
    private int playerDir = -1;
    private final float playerSpeed = 0.13f;
    private final float diagonalSpeed = 0.00000001f;
    private boolean moving = false;
    private boolean up;
    private boolean right;
    private boolean down;
    private boolean left;
    private boolean attacking;
    public String direction = "down";

    public Player(float x, float y) {
        super(x, y);
//        this.direction = "down";
//        this.isIdle = true;
        importImg();
        initHitbox();
    }

    private void importImg() {
        InputStream isSide = getClass().getResourceAsStream("/PlayerSideSheet.png");
        InputStream isBack = getClass().getResourceAsStream("/PlayerBackSheet.png");
        InputStream isFront = getClass().getResourceAsStream("/PlayerFrontSheet.png");
        InputStream isAngleUp = getClass().getResourceAsStream("/PlayerAngleUpSheet.png");
        InputStream isAngleDown = getClass().getResourceAsStream("/PlayerAngleDownSheet.png");

        try {
            BufferedImage imgSide = ImageIO.read(isSide);
            BufferedImage imgBack = ImageIO.read(isBack);
            BufferedImage imgFront = ImageIO.read(isFront);
            BufferedImage imgAngleUp = ImageIO.read(isAngleUp);
            BufferedImage imgAngleDown = ImageIO.read(isAngleDown);

            sideAnimations = new BufferedImage[5][14];
            backAnimations = new BufferedImage[5][14];
            frontAnimations = new BufferedImage[5][14];
            angleUpAnimations = new BufferedImage[5][14];
            angleDownAnimations = new BufferedImage[5][14];
            for (int j = 0; j < sideAnimations.length; j++) {
                for (int i = 0; i < sideAnimations[j].length; i++) {
                    sideAnimations[j][i] = imgSide.getSubimage(i*48, j*44, 48, 44);
                    backAnimations[j][i] = imgBack.getSubimage(i*48, j*44, 48, 44);
                    frontAnimations[j][i] = imgFront.getSubimage(i*48, j*44, 48, 44);
                    angleDownAnimations[j][i] = imgAngleDown.getSubimage(i*48, j*44, 48, 44);
                    angleUpAnimations[j][i] = imgAngleUp.getSubimage(i*48, j*44, 48, 44);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void animationTick() {
        spriteTick++;
        if(spriteTick >= spriteSpeed) {
            spriteTick = 0;
            spriteIndex++;
            if(spriteIndex >= GetSpriteAmount(playerAction)) {
                spriteIndex = 0;
                attacking = false;
            }
        }
    }

    private void setAnimation() {

        int startAni = playerAction;
        if(moving) {
            playerAction = RUNNING;
        } else if (attacking) {
            playerAction = ATTACK;
        } else {
            playerAction = IDLE;
        }

        if (startAni != playerAction) {
            spriteTick = 0;
            spriteIndex = 0;
        }
    }

    public void update() {
        moving = false;
        if (up && !down) {
            y -= playerSpeed;
            moving = true;
            direction = "up";
        } else if (down && !up) {
            y += playerSpeed;
            moving = true;
            direction = "down";
        }
        if (left && !right) {
            x -= playerSpeed;
            moving = true;
            direction = "left";
        } else if (right && !left) {
            x += playerSpeed;
            moving = true;
            direction = "right";
        }
        if (right && up) {
            y -= playerSpeed / 8;
            x += playerSpeed / 8;
            moving = true;
            direction = "duRight";
        } else if (right && down) {
            y += playerSpeed / 8;
            x += playerSpeed / 8;
            moving = true;
            direction = "ddRight";
        }
        if (left && up) {
            y -= playerSpeed / 8;
            x -= playerSpeed / 8;
            moving = true;
            direction = "duLeft";
        } else if (left && down) {
            y += playerSpeed / 8;
            x -= playerSpeed / 8;
            moving = true;
            direction = "ddLeft";
        }

    }



    public void render(Graphics g) {
        drawHitbox(g);
        animationTick();
        setAnimation();
        updateHitbox();
        update();

        if(moving) {
            if(Objects.equals(direction, "left")) {
                g.drawImage(sideAnimations[playerAction][spriteIndex], (int)x + 96, (int)y, -96,88, null);
            } else if (direction == "right") {
                g.drawImage(sideAnimations[playerAction][spriteIndex], (int)x, (int)y, 96,88, null);
            } else if (direction == "up") {
                g.drawImage(backAnimations[playerAction][spriteIndex], (int)x, (int)y, 96,88, null);
            } else if(direction == "down") {
                g.drawImage(frontAnimations[playerAction][spriteIndex], (int)x, (int)y, 96,88, null);
            }
            if (direction == "duRight") {
                g.drawImage(angleUpAnimations[playerAction][spriteIndex], (int)x, (int)y, 96,88, null);
            } else if (direction == "ddRight") {
                g.drawImage(angleDownAnimations[playerAction][spriteIndex], (int)x, (int)y, 96,88, null);
            } else if (direction == "duLeft") {
                g.drawImage(angleUpAnimations[playerAction][spriteIndex], (int)x + 96, (int)y, -96,88, null);
            } else if (direction == "ddLeft") {
                g.drawImage(angleDownAnimations[playerAction][spriteIndex], (int)x  + 96, (int)y, -96,88, null);
            }
        } else {
            g.drawImage(frontAnimations[playerAction][spriteIndex], (int)x, (int)y, 96,88, null);
        }
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
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

    public boolean isAttacking() {
        return attacking;
    }

    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
    }
}
