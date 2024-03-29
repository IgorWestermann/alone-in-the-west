
package com.mygdx.game.controllers;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

public abstract class Entity extends Sprite {

    protected MapHandler mapHandler;
    protected EntityHandler entityHandler;
    protected Body body;
    protected float bodyW;
    protected float bodyH;

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

    public float getBodyW() {
        return bodyW;
    }

    public float getBodyH() {
        return bodyH;
    }

    public short getMyCategory() {
        return myCategory;
    }

    public boolean isToSelfDestruct() {
        return toSelfDestruct;
    }

    protected void setSelfDestruct() {
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
    
    protected void createBoxSensorBody(float boxW, float boxH, BodyDef.BodyType type, float inicialX, float inicialY, float dumping) {

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
        fdef.isSensor = true;

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
