package entities;

import java.awt.*;

public abstract class Entity {

    protected float x,y;
    public String direction;
    public boolean isIdle;
    private int width, height;
    private Color color;
    protected Rectangle hitbox;

    public Entity(float x, float y) {
        this.x = x;
        this.y = y;
//        this.width = width;
//        this.height = height;

    }
    protected void initHitbox() {
            hitbox = new Rectangle((int)x, (int)y, width, height);
    }

    protected void drawHitbox(Graphics g) {
        g.setColor(Color.RED);
        g.drawRect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);
    }

    protected void updateHitbox() {
        hitbox.x = (int)x;
        hitbox.y = (int)y;
    }

    public Rectangle getHitbox() {
        return hitbox;
    }




}
