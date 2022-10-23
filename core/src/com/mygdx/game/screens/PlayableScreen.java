/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.sprites.BaseSprite;

/**
 *
 * @author Hugo
 */
public class PlayableScreen implements Screen {

    private final int W_WIDTH = 400;
    private final int W_HEIGHT = 200;

    private final MyGdxGame game;

    //variavel teste de personagel
    public BaseSprite mainCharacter;

    //variaveis de camera
    private final OrthographicCamera cam;
    private final FitViewport port;

    //variaveis de mapa
    private final TmxMapLoader mapLoader;
    private final TiledMap map;
    private final OrthogonalTiledMapRenderer mapRenderer;

    //variaveis de colisão
    private final World world;
    private final Box2DDebugRenderer box2DDebugRenderer;

    public PlayableScreen(MyGdxGame game) {
        this.game = game;
        cam = new OrthographicCamera();
        //viewport é usado pra manter a proporção da tela
        port = new FitViewport(W_WIDTH, W_HEIGHT, cam);

        //objeto que carrega tmx , ojeto mapa em si , objeto que é responsavel por renderizar
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("mapa1.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map);

        //camera posicionada no centro do mapa
        cam.position.set(port.getWorldWidth() / 2, port.getWorldHeight() / 2, 0);

        //este vector2 é um vetor responsavel pela gravidade
        //o segundo parametro sinaliza para não calcular objetos em descanso 
        world = new World(new Vector2(0, 0), true);
        box2DDebugRenderer = new Box2DDebugRenderer();

        ///area de testes para criar objetos com colisão
        ///cada classe deve possuir suas proprias variaveis de definição quanto a colisão
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();

        FixtureDef fdef = new FixtureDef(); //fixture é responsavel por dizer o quanto o objeto é fixo em tela e tambem lida com a elasticidade das colisões e atrito
        Body body;

        ///itera sobre o objeto do mapa e pega uma camada especifica determinada pelo indice ou nome
        ///para cada objeto nessa camada cria uma definição de corpo
        //sera bom tratar cada camada como uma classe com sua propria definição e jogar esse for em um metodo proprio
        /*
        for(MapObject obj : map.getLayers().get("nome da camada").getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject)obj).getRectangle();
            //nesse caso definimos os corpos como estaticos, que não são afetados por forças externas
            //dinamicos sofrem influencia de forças externas
            //cineticos podem tem comportamento manipulado por codigo, mas não colisões externas
            bdef.type = BodyDef.BodyType.StaticBody;
            
            //cria uma definição de corpo ridigo ao redor do retangulo obtido pela camada do mapa
            bdef.position.set(rect.getX()+rect.getWidth()/2, rect.getY() + rect.getHeight()/2);
            
            //cria um objeto de corpo rigido e adiciona ao mundo
            body = world.createBody(bdef);
            
            //define o formato de cada objeto como uma box retangular
            shape.setAsBox(rect.getWidth()/2, rect.getHeight()/2);
            fdef.shape = shape;
            
            body.createFixture(fdef);
        }
         */
        mainCharacter = new BaseSprite(world, this);
    }

    //lembrar de criar ou utilizar uma classe propria pra lidar com os inputs
    //esta esta sendo utilizada para testes
    public void handleInput() {

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            mainCharacter.body.applyLinearImpulse(new Vector2(0, 7f), mainCharacter.body.getWorldCenter(), true);
        } else {
            mainCharacter.body.setLinearDamping(7);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            mainCharacter.body.applyLinearImpulse(new Vector2(0, -7f), mainCharacter.body.getWorldCenter(), true);
        } else {
            mainCharacter.body.setLinearDamping(7);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            mainCharacter.body.applyLinearImpulse(new Vector2(-7f, 0), mainCharacter.body.getWorldCenter(), true);
        } else {
            mainCharacter.body.setLinearDamping(7);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            mainCharacter.body.applyLinearImpulse(new Vector2(7f, 0), mainCharacter.body.getWorldCenter(), true);
        } else {
            mainCharacter.body.setLinearDamping(7);
        }
    }

    //esse metodo reune atualizaçao de objetos
    //porem o ideal é ser quebrado em categorias menores relativas
    public void update(float dt) {

        handleInput();
        //os dois ultimos parametros são responsaveis pelo refino dos calculos de colisão
        world.step(1 / 60f, 12, 12);

        cam.update();
        mainCharacter.update(dt);
        //camera segue o personagem
        cam.position.x = mainCharacter.body.getPosition().x;
        cam.position.y = mainCharacter.body.getPosition().y;
        mapRenderer.setView(cam);
    }

    @Override
    public void show() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void render(float f) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        mapRenderer.render();

        //esse metodo desenha as collision boxes do mapa
        box2DDebugRenderer.render(world, cam.combined);

        update(f);

        game.batch.setProjectionMatrix(cam.combined);

        //tudo que for ser desenhado precisa estar entre batch.begin() e batch.end()
        game.batch.begin();

        mainCharacter.draw(game.batch);

        game.batch.end();
    }

    @Override
    public void resize(int w, int h) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        port.update(w, h);
    }

    @Override
    public void pause() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void resume() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void hide() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void dispose() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

    }

}
