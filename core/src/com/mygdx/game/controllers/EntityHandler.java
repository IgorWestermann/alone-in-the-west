
package com.mygdx.game.controllers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.entities.mobs.Cactus;
import com.mygdx.game.entities.mobs.Coffin;
import com.mygdx.game.entities.mobs.Player;

public class EntityHandler {

    private MapHandler mapHandler;
    private Array<Entity> entities;
    private Array<Cactus> cactusListArray;
    private Array<Coffin> coffinListArray;

    private Array<Entity> toBeRemoved;
    private Player player;
    
    private int totalEnemiesDead;

    public Player getPlayer() {
        return player;

    }

    public int getTotalEnemiesDead() {
        return totalEnemiesDead;
    }

    public void setPlayer(Player p) {
        this.player = p;
    }

    public EntityHandler(MapHandler mapHandler) {
        this.mapHandler = mapHandler;
        this.entities = new Array<>();
        this.toBeRemoved = new Array<>();

        this.cactusListArray = new Array<>();
        this.coffinListArray = new Array<>();
        
        totalEnemiesDead = 0;
    }

    public void watchEntity(Entity e) {

        if (e instanceof Cactus) {
            cactusListArray.add((Cactus) e);
        } else if (e instanceof Coffin) {
            coffinListArray.add((Coffin) e);
        }
        entities.add(e);
    }
    private void remove() {

        for (Entity e : toBeRemoved) {

            if (e instanceof Cactus) {
                cactusListArray.removeValue((Cactus) e , true);
                totalEnemiesDead++;
            } else if (e instanceof Coffin) {
                coffinListArray.removeValue((Coffin) e , true);
                totalEnemiesDead++;
            }

            this.entities.removeValue(e, true);
            this.mapHandler.getWorld().destroyBody(e.getBody());

        }
        toBeRemoved.clear();

    }
    public void addToBeRemoved(Entity e) {
        toBeRemoved.add(e);
    }

    public void verifyMobSelfDestruction() {
        for (Entity e : entities) {
            if (e.isToSelfDestruct()) {
                addToBeRemoved(e);
           }
       }
    }
    public boolean vefityMobsEnded(){
        return this.cactusListArray.size == 0 && this.coffinListArray.size == 0;
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
            e.draw(batch);
        }
    }
}
