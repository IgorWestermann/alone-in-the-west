/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygdx.game.sprites.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.controllers.Entity;
import com.mygdx.game.screens.*;
import com.mygdx.game.sprites.*;
import com.mygdx.game.sprites.mobs.Cactus;
import com.mygdx.game.sprites.mobs.Coffin;

/**
 *
 * @author Hugo
 */
public class Spawner extends Entity {

    public enum enemyType {
        CACTUS, COFFIN, BOTH_EQUAL, RAMDON
    }

    private float x;
    private float y;

    private int totalMobsToSpawn;
    private float timer;
    private float cooldown;

    private enemyType spawnType;

    private Texture closed;
    private Texture open;

    private boolean alternate = false;

    public Spawner(MapHandler mh , EntityHandler eh , float x, float y, int totalMobsToSpawn, int cooldown, enemyType spawnType) {
        
        super(mh, eh, (short)0, new short[]{});
        
        this.timer = 0;
        this.x = x;
        this.y = y;
        this.totalMobsToSpawn = totalMobsToSpawn;
        this.cooldown = cooldown;
        this.spawnType = spawnType;
      
    }



    @Override
    protected TextureRegion getFrame(float dt) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void spawnEnemy(float dt) {
        
        System.out.println("dt " + dt);
        
        this.timer += dt;
        
        System.out.println(timer);
        System.out.println("B");

        if (timer > cooldown && totalMobsToSpawn > 0) {
            System.out.println("C");
            switch (spawnType) {
                
                case CACTUS:
                    new Cactus(mapHandler, entityHandler, this.x, this.y);
                    timer = 0;
                    break;
                case COFFIN:
                    new Coffin(mapHandler, entityHandler, this.x, this.y);
                    timer = 0;
                    break;
                case BOTH_EQUAL:
                    if (alternate) {
                        new Coffin(mapHandler, entityHandler, this.x, this.y);
                    } else {
                        new Cactus(mapHandler, entityHandler, this.x, this.y);
                    }
                    alternate = !alternate;
                    timer = 0;
                    break;
                case RAMDON:

                    float r = (float) Math.random();
                    if (r < 0.5) {
                        new Coffin(mapHandler, entityHandler, this.x, this.y);
                    } else {
                        new Cactus(mapHandler, entityHandler, this.x, this.y);
                    }
                    timer = 0;
                    break;
                default:
                    throw new AssertionError();    
            }
            
        
            totalMobsToSpawn--;
        }

    }

    @Override
    public void update(float f) {
        spawnEnemy(f);
    }

}
