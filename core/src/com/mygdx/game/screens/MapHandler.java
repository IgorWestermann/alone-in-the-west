/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygdx.game.screens;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;

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

    //variaveis de colisão
    private final World world;
    private final Box2DDebugRenderer box2DDebugRenderer;

    public MapHandler(FitViewport port, OrthographicCamera cam, String mapName) {
        
        this.port = port;
        this.cam = cam;
        
        //objeto que carrega tmx , ojeto mapa em si , objeto que é responsavel por renderizar
        mapLoader = new TmxMapLoader();
        map = mapLoader.load(mapName);
        mapRenderer = new OrthogonalTiledMapRenderer(map);

        //camera posicionada no centro do mapa
        cam.position.set(port.getWorldWidth() / 2, port.getWorldHeight() / 2, 0);
        //este vector2 é um vetor responsavel pela gravidade
        //o segundo parametro sinaliza para não calcular objetos em descanso 
        world = new World(new Vector2(0, 0), true);
        box2DDebugRenderer = new Box2DDebugRenderer();

        this.loadCollisionBoxes();
    }

    public void loadCollisionBoxes() {
        ///cada classe deve possuir suas proprias variaveis de definição quanto a colisão
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef(); //fixture é responsavel por dizer o quanto o objeto é fixo em tela e tambem lida com a elasticidade das colisões e atrito
        Body body;
        ///itera sobre o objeto do mapa e pega uma camada especifica determinada pelo indice ou nome
        ///para cada objeto nessa camada cria uma definição de corpo
        //sera bom tratar cada camada como uma classe com sua propria definição e jogar esse for em um metodo proprio
        
        /*
        for (MapObject obj : map.getLayers().get("nome da camada").getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) obj).getRectangle();
            //nesse caso definimos os corpos como estaticos, que não são afetados por forças externas
            //dinamicos sofrem influencia de forças externas
            //cineticos podem tem comportamento manipulado por codigo, mas não colisões externas
            bdef.type = BodyDef.BodyType.StaticBody;

            //cria uma definição de corpo ridigo ao redor do retangulo obtido pela camada do mapa
            bdef.position.set(rect.getX() + rect.getWidth() / 2, rect.getY() + rect.getHeight() / 2);

            //cria um objeto de corpo rigido e adiciona ao mundo
            body = world.createBody(bdef);
            //define o formato de cada objeto como uma box retangular
            shape.setAsBox(rect.getWidth() / 2, rect.getHeight() / 2);
            fdef.shape = shape;
            body.createFixture(fdef);
        }
        */
    }
    public World getWorld() {
        return this.world;
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
