/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygdx.game.sprites;

import com.mygdx.game.constants.CollisionCategories;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.mygdx.game.constants.Direction;
import com.mygdx.game.sprites.mobs.controllers.EntityHandler;
import com.mygdx.game.sprites.mobs.controllers.MapHandler;
import com.mygdx.game.sprites.Entity;
import com.mygdx.game.sprites.mobs.Mob;

/**
 *
 * @author Hugo
 */
public class Projectile extends Entity {

    private TextureRegion texture;
    private Mob sourceMob;
    private final int damage;
    
    

    public Projectile(MapHandler mapHandler, EntityHandler entityHandler, Mob sourceMob, Vector2 direction) {

        super(mapHandler, entityHandler, sourceMob.getMyCategory() == CollisionCategories.PLAYER_BODY ? CollisionCategories.PLAYER_PROJECTILE : CollisionCategories.ENEMY_PROJECTILE,
                new short[]{
                    CollisionCategories.WALL,
                    sourceMob.getMyCategory() == CollisionCategories.PLAYER_BODY ? CollisionCategories.ENEMY_BODY : CollisionCategories.PLAYER_BODY,}
        );

        this.sourceMob = sourceMob;

        float x = sourceMob.getBody().getWorldCenter().x;
        float y = sourceMob.getBody().getWorldCenter().y;
        
        this.damage = sourceMob.getAttackDamage();

        defineThisBody(x, y);
        entityHandler.watchEntity(this);

        //this.rotate(getProjectileRotation());
        this.body.setLinearDamping(0);
        this.body.applyLinearImpulse(direction , new Vector2(0,0) , true);

    }

    public int getDamage() {
        return damage;
    }

    protected void defineThisBody(float x, float y) {
        this.texture = new TextureRegion(new Texture("FX/bullet.png"));
        this.bodyH = 1;
        this.bodyW = 1;
        super.createCircleCollisionBox(1, BodyDef.BodyType.DynamicBody, x, y, 0);
        super.setBounds(0,0,4,4);
    }


    @Override
    protected TextureRegion getFrame(float dt) {
        return texture;
    }
    
    private int getProjectileRotation(){
        
        Direction d = sourceMob.getDirection();
        
        switch (d) {
            case E:
                return 0;
            case NE:
                return 45;
            case N:
                return 90;
            case NW:
                return 135;
            case W:
                return 180;
            case SW:
                return 225;
            case S:
                return 270;
            case SE:
                return 315;
            default:
                throw new AssertionError();
        }
    }

    @Override
    public void update(float f) {
        
        super.setPosition((body.getPosition().x - super.getWidth()/2) + boxXOffset, (body.getPosition().y - super.getHeight()/2) + boxYOffset);
        super.setRegion(getFrame(f));
    }

}
