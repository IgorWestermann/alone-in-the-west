/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygdx.game.sprites.objects;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.mygdx.game.screens.EntityHandler;
import com.mygdx.game.screens.MapHandler;
import com.mygdx.game.sprites.CollisionCategories;
import com.mygdx.game.controllers.Entity;

/**
 *
 * @author Hugo
 */
public class Wall extends Entity  {

    public Wall(MapHandler mapHandler, EntityHandler entityHandler,float sourceX, float sourceY , float width , float heigth) {
        super(mapHandler, entityHandler, CollisionCategories.WALL, 
            new short []{
                CollisionCategories.ENEMY_BODY,
                CollisionCategories.PLAYER_BODY,
                CollisionCategories.PLAYER_PROJECTILE,
                CollisionCategories.ENEMY_PROJECTILE,
            });
        
        defineThisBody(sourceX, sourceY, width, heigth);
        
    }
    protected void defineThisBody(float startX, float startY , float width , float heigth) {
        createBoxCollisionBody(width, heigth, BodyDef.BodyType.StaticBody, startX, startY, 0);
    }
    @Override
    protected TextureRegion getFrame(float dt) {
       return null;
    }
    
    @Override
    public void update(float f) {
        
    }
    
}
