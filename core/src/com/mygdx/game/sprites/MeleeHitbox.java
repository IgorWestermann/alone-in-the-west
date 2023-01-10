/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygdx.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.mygdx.game.screens.EntityHandler;
import com.mygdx.game.screens.MapHandler;
import com.mygdx.game.sprites.mobs.Mob;

/**
 *
 * @author Hugo
 */
public class MeleeHitbox extends Entity {
    private TextureRegion texture;
    private Mob sourceMob;
    private float selfDestructTimer = 0 ;
    private float selfDestructLimit = 0.5f;

    public MeleeHitbox(MapHandler mapHandler, EntityHandler entityHandler, Mob sourceMob, Vector2 direction) {

        super(mapHandler, entityHandler, sourceMob.getMyCategory() == CollisionCategories.PLAYER_BODY ? CollisionCategories.PLAYER_PROJECTILE : CollisionCategories.ENEMY_PROJECTILE,
                new short[]{
                    CollisionCategories.WALL,
                    sourceMob.getMyCategory() == CollisionCategories.PLAYER_BODY ? CollisionCategories.ENEMY_BODY : CollisionCategories.PLAYER_BODY,}
        );

        this.sourceMob = sourceMob;

        defineThisBody(direction.x ,direction.y);
        
        entityHandler.watchEntity(this);

        //System.out.println("New Projectile");
        //System.out.println("Source Mob" + sourceMob);
        //System.out.println("Categoty" + this.getMyCategory());
        //System.out.println("Collision mask" + this.collidesWith);


    }
    
    protected void defineThisBody(float x, float y) {
        this.texture = new TextureRegion(new Texture("debugTexture.png"));
        super.createBoxSensorBody(sourceMob.getBodyW()/2, sourceMob.getBodyW()/2, BodyDef.BodyType.KinematicBody, x, y, 0);
    }

    @Override
    protected TextureRegion getFrame(float dt) {
        return texture;
    }

    @Override
    public void update(float f) {
        
        
        if(selfDestructTimer > selfDestructLimit){
            this.toSelfDestruct = true;
        }
        selfDestructTimer+=f;
        super.setPosition((body.getPosition().x - super.getWidth() / 2) + boxXOffset, (body.getPosition().y - super.getHeight() / 2) + boxYOffset);
        super.setRegion(getFrame(f));
    }
}
