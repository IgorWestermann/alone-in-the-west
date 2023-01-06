/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygdx.game.sprites;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.mygdx.game.constants.Direction;
import com.mygdx.game.screens.EntityHandler;
import com.mygdx.game.screens.MapHandler;
import com.mygdx.game.sprites.Entity;
import com.mygdx.game.sprites.mobs.Mob;

/**
 *
 * @author Hugo
 */
public class Projectile extends Entity {

    private TextureRegion texture;
    private Mob sourceMob;

    public Projectile(MapHandler mapHandler, EntityHandler entityHandler, Mob sourceMob, Vector2 direction) {

        super(mapHandler, entityHandler, sourceMob.getMyCategory() == CollisionCategories.PLAYER_BODY ? CollisionCategories.PLAYER_PROJECTILE : CollisionCategories.ENEMY_PROJECTILE,
                new short[]{
                    CollisionCategories.WALL,
                    sourceMob.getMyCategory() == CollisionCategories.PLAYER_BODY ? CollisionCategories.ENEMY_BODY : CollisionCategories.PLAYER_BODY,}
        );

        this.sourceMob = sourceMob;

        float x = sourceMob.getBody().getWorldCenter().x;
        float y = sourceMob.getBody().getWorldCenter().y;

        defineThisBody(x, y);
        entityHandler.watchEntity(this);

        this.body.setLinearVelocity(direction);

    }

    private short setMyCategory(Mob sourceMob) {
        if (sourceMob.getMyCategory() == CollisionCategories.PLAYER_BODY) {
            return CollisionCategories.PLAYER_PROJECTILE;
        } else if (sourceMob.getMyCategory() == CollisionCategories.ENEMY_BODY) {
            return CollisionCategories.ENEMY_PROJECTILE;
        } else {
            return CollisionCategories.PLAYER_PROJECTILE | CollisionCategories.ENEMY_PROJECTILE;
        }
    }

    protected void defineThisBody(float x, float y) {
        this.texture = new TextureRegion(new Texture("FX/bullet.png"));
        super.createCircleCollisionBox(1, BodyDef.BodyType.DynamicBody, x, y, 0);
        super.setBounds(0,0,4,4);
    }


    @Override
    protected TextureRegion getFrame(float dt) {
        return texture;
    }

    @Override
    public void update(float f) {
        super.setPosition((body.getPosition().x - super.getWidth() / 2) + boxXOffset, (body.getPosition().y - super.getHeight() / 2) + boxYOffset);
        super.setRegion(getFrame(f));
    }

}
