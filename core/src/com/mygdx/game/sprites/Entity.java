/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygdx.game.sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.mygdx.game.screens.EntityHandler;
import com.mygdx.game.screens.MapHandler;

/**
 *
 * @author Hugo
 */
public abstract class Entity extends Sprite {

    protected MapHandler mapHandler;
    protected EntityHandler entityHandler;
    protected Body body;

    protected float boxXOffset = 0;
    protected float boxYOffset = 0;

    protected short myCategory;
    protected short collidesWith;

    protected boolean toSelfDestruct = false;

    public Entity(MapHandler mapHandler, EntityHandler entityHandler, short category, short[] collidesWith) {
        this.mapHandler = mapHandler;
        this.entityHandler = entityHandler;

        setCategory(category);
        setCollidesWith(collidesWith);

    }

    public Entity(MapHandler mapHandler, EntityHandler entityHandler, short category, short[] collidesWith, float sourceX, float sourceY) {
        this.mapHandler = mapHandler;
        this.entityHandler = entityHandler;

        setCategory(category);
        setCollidesWith(collidesWith);
    }

    public short getMyCategory() {
        return myCategory;
    }

    public boolean isToSelfDestruct() {
        return toSelfDestruct;
    }
    
    protected void setSelfDestruct(){
        this.toSelfDestruct = true;
    }

    public MapHandler getMapHandler() {
        return mapHandler;
    }

    public EntityHandler getEntityHandler() {
        return entityHandler;
    }

    private void setCategory(short category) {
        this.myCategory = category;
    }

    private void setCollidesWith(short[] collides) {

        short mask = 0;

        for (short m : collides) {
            mask = (short) (mask | m);
        }
        this.collidesWith = mask;
    }

    public Body getBody() {
        return body;
    }

    protected void createBoxCollisionBody(float boxW, float boxH, BodyDef.BodyType type, float inicialX, float inicialY, float dumping) {

        System.out.println("Creating" + this.toString());
        System.out.println("Width" + boxW);
        System.out.println("Heigh" + boxH);
        System.out.println("Inicial X " + inicialX);
        System.out.println("Inicial Y " + inicialY);

        BodyDef bdef = new BodyDef();
        //cria a definicao do corpo fisico

        bdef.position.set(inicialX, inicialY);
        bdef.type = type;

        //adiciona o corpo ao mundo
        this.body = this.mapHandler.getWorld().createBody(bdef);

        //definicoes de fixture e formato
        FixtureDef fdef = new FixtureDef();

        //shape da collisionBox
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(boxW, boxH);

        fdef.shape = shape;
        fdef.filter.categoryBits = myCategory;
        fdef.filter.maskBits = collidesWith;

        body.createFixture(fdef);
        body.setUserData(this);

        //aqui esta uma especie de atrito que o corpo sofre
        body.setLinearDamping(dumping);
    }

    public void createCircleCollisionBox(int radius, BodyDef.BodyType type, float inicialX, float inicialY, float dumping) {
        BodyDef bdef = new BodyDef();
        //cria a definicao do corpo fisico
        bdef.position.set(inicialX, inicialY);
        bdef.type = type;

        //adiciona o corpo ao mundo
        this.body = this.mapHandler.getWorld().createBody(bdef);

        //definicoes de fixture e formato
        FixtureDef fdef = new FixtureDef();

        //shape da collisionBox
        CircleShape shape = new CircleShape();
        shape.setRadius(radius);

        fdef.shape = shape;
        fdef.filter.categoryBits = myCategory;
        fdef.filter.maskBits = collidesWith;

        body.createFixture(fdef);
        body.setUserData(this);

        //aqui esta uma especie de atrito que o corpo sofre
        body.setLinearDamping(dumping);
    }

    protected abstract TextureRegion getFrame(float dt);

    public abstract void update(float f);
}
