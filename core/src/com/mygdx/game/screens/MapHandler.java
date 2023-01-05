/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygdx.game.screens;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.sprites.objects.Wall;

/**
 *
 * @author Hugo
 */
public class MapHandler {

    //variaveis de camera
    private OrthographicCamera cam;
    private FitViewport port;

    //variaveis de mapa
    private final TmxMapLoader mapLoader;
    private final TiledMap map;
    private final OrthogonalTiledMapRenderer mapRenderer;

    //variaveis de colisao
    private final World world;
    private final Box2DDebugRenderer box2DDebugRenderer;

    public MapHandler(FitViewport port, OrthographicCamera cam, String mapName) {

        this.port = port;
        this.cam = cam;

        //objeto que carrega tmx , ojeto mapa em si , objeto que e responsavel por renderizar
        mapLoader = new TmxMapLoader();
        map = mapLoader.load(mapName);
        mapRenderer = new OrthogonalTiledMapRenderer(map);

        //camera posicionada no centro do mapa
        cam.position.set(port.getWorldWidth() / 2, port.getWorldHeight() / 2, 0);

        //este vector2 e um vetor responsavel pela gravidade
        //o segundo parametro sinaliza para nao calcular objetos em descanso
        world = new World(new Vector2(0, 0), true);
        box2DDebugRenderer = new Box2DDebugRenderer();

        this.loadCollisionBoxes();
    }

    public World getWorld() {
        return this.world;
    }

    public void loadCollisionBoxes() {

        for (MapObject obj : map.getLayers().get("Collision").getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) obj).getRectangle();
            new Wall(this, null, rect.getX() + rect.getWidth() / 2, rect.getY() + rect.getHeight() / 2, rect.getWidth() / 2, rect.getHeight() / 2);
            
        }

    }

    public void update(float dt) {
        world.step(1 / 60f, 12, 12);
        mapRenderer.setView(cam);
    }

    public void render(float dt) {
        mapRenderer.render();
        //esse metodo desenha as collision boxes do mapa
        box2DDebugRenderer.render(world, cam.combined);
    }
}
