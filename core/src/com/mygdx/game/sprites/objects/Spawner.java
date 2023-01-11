
package com.mygdx.game.sprites.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.mygdx.game.constants.GlobalConfig;
import com.mygdx.game.controllers.Entity;
import com.mygdx.game.controllers.EntityHandler;
import com.mygdx.game.controllers.MapHandler;
import com.mygdx.game.entities.mobs.*;

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

    private TextureRegion closed;
    private TextureRegion open;

    private boolean alternate = false;

    private boolean isOpen = false;
    private boolean finished = false;
    private float closeAnimationTimer = 0;

    public Spawner(MapHandler mh, EntityHandler eh, float x, float y, enemyType spawnType, float boxW, float boxH) {

        super(mh, eh, (short) 0, new short[]{});

        this.timer = 0;
        this.x = x;
        this.y = y;
        this.totalMobsToSpawn = GlobalConfig.SpawnerMaxSpawns;
        this.cooldown = GlobalConfig.SpawnerCooldowns;
        this.spawnType = spawnType;

        this.bodyW = boxW;
        this.bodyH = boxH;

        this.open = new TextureRegion(new Texture("Buildings/open_door.png"));
        this.closed = new TextureRegion(new Texture("Buildings/closed_door.png"));
        

        createBoxSensorBody(boxW, boxH, BodyDef.BodyType.StaticBody, x, y, timer);
        super.setBounds(x, y, boxW, boxH);

        eh.watchEntity(this);

    }

    @Override
    protected TextureRegion getFrame(float dt) {

        closeAnimationTimer += dt;

        if (isOpen && closeAnimationTimer < cooldown/2) {
            return open;
        } else {
            closeAnimationTimer = 0;
            this.isOpen = false;
            return closed;
        }
    }

    private void spawnEnemy(float dt) {

        this.timer += dt;

        if (timer > cooldown && totalMobsToSpawn > 0) {
            switch (spawnType) {

                case CACTUS:
                    new Cactus(mapHandler, entityHandler, this.x, this.y - this.bodyH / 2);
                    timer = 0;
                    break;
                case COFFIN:
                    new Coffin(mapHandler, entityHandler, this.x, this.y - this.bodyH / 2);
                    timer = 0;
                    break;
                case BOTH_EQUAL:
                    if (alternate) {
                        new Coffin(mapHandler, entityHandler, this.x, this.y - this.bodyH / 2);
                    } else {
                        new Cactus(mapHandler, entityHandler, this.x, this.y - this.bodyH / 2);
                    }
                    alternate = !alternate;
                    timer = 0;
                    break;
                case RAMDON:

                    float r = (float) Math.random();
                    if (r < 0.5) {
                        new Coffin(mapHandler, entityHandler, this.x, this.y - this.bodyH / 2);
                    } else {
                        new Cactus(mapHandler, entityHandler, this.x, this.y - this.bodyH / 2);
                    }
                    timer = 0;
                    break;
                default:
                    throw new AssertionError();
            }
            this.isOpen = true;
            totalMobsToSpawn--;
            
            if(totalMobsToSpawn <= 0){
                this.finished = true;
            }
        }

    }

    public boolean isFinished() {
        return finished;
    }
    
    public void increaseTotalSpawnsBy(int amout) throws Exception {
        
        if(!finished){
            throw new Exception("Mudando o total de spawns em quando o jogo esta ativo");
            
        }else{
            this.totalMobsToSpawn += amout;
        }
    }
    
    public void reset() throws Exception {
        
         if(!finished){
            throw new Exception("Resetando spawner com o jogo ativo");
        }else {
             this.finished = false;
             this.totalMobsToSpawn = GlobalConfig.SpawnerMaxSpawns;
        }
        
        
    }

    public void setTotalMobsToSpawn(int totalMobsToSpawn) throws Exception {
        
        if(!finished){
            throw new Exception("Mudando o total de mobs spawnados com o jogo ativo");
        }else {
            this.totalMobsToSpawn = totalMobsToSpawn;
        }

    }

    public void setCooldown(float cooldown) {
        this.cooldown = cooldown;
    }

    @Override
    public void update(float f) {
        spawnEnemy(f);
        super.setPosition((body.getPosition().x - super.getWidth() / 2) + boxXOffset, (body.getPosition().y - super.getHeight() / 2) + boxYOffset);
        super.setRegion(getFrame(f));
    }

}
