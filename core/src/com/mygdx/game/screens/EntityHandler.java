/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygdx.game.screens;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.entities.mobs.Player;
import com.mygdx.game.controllers.Entity;

/**
 *
 * @author Hugo
 */
public class EntityHandler {

    private MapHandler mapHandler;
    private Array<Entity> entities;
    private Array<Entity> toBeRemoved;
    private Player player;

    private Texture t = new Texture("debugTexture.png");

    public Player getPlayer() {
        return player;

    }

    public void setPlayer(Player p) {
        this.player = p;
    }

    public EntityHandler(MapHandler mapHandler) {
        this.mapHandler = mapHandler;
        this.entities = new Array<>();
        this.toBeRemoved = new Array<>();
    }

    public void watchEntity(Entity e) {
        entities.add(e);
    }
    
    private void remove(){
        for(Entity e : toBeRemoved){
            this.mapHandler.getWorld().destroyBody(e.getBody());
            this.entities.removeValue(e, true);
        }
        toBeRemoved.clear();    
        
    }
    
    public void addToBeRemoved(Entity e){
        toBeRemoved.add(e);
    }
    
    public void verifyMobSelfDestruction(){
       for(Entity e : entities){
           if(e.isToSelfDestruct()){
               System.out.println("Removing " + e);
               addToBeRemoved(e);
           }
       }
    }

    public void update(float dt) {
        
        verifyMobSelfDestruction();
        remove();
        
        for (Entity e : entities) {
            e.update(dt);
            
        }
        
        
    }

    public void draw(SpriteBatch batch) {

        for (Entity e : entities) {
            //batch.draw(t, e.getBoundingRectangle().x, e.getBoundingRectangle().y, e.getBoundingRectangle().width, e.getBoundingRectangle().height);
            e.draw(batch);
        }

    }
}
