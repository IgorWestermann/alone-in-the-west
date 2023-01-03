/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygdx.game.sprites.objects;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.screens.*;
import com.mygdx.game.sprites.*;
/**
 *
 * @author Hugo
 */
public class Spawner extends Entity{

    public Spawner(MapHandler mapHandler, EntityHandler entityHandler, short category, short[] collidesWith) {
        super(mapHandler, entityHandler, CollisionCategories.SPAWNER, new short[]{});
    }

    @Override
    protected TextureRegion getFrame(float dt) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void update(float f) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    
}
