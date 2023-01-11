
package com.mygdx.game.sprites.objects;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.mygdx.game.controllers.EntityHandler;
import com.mygdx.game.controllers.MapHandler;
import com.mygdx.game.constants.CollisionCategories;
import com.mygdx.game.controllers.Entity;

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
