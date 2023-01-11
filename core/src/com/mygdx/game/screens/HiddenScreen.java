/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.constants.GlobalConfig;
/**
 *
 * @author Hugo
 */
public class HiddenScreen implements Screen {

    private BitmapFont dark;
    private BitmapFont light;
    private FitViewport viewport;
    private Stage stage;
    private TextureAtlas atlas;
    private Skin UIAtlas;
    private Table mainTable;
    private final MyGdxGame game;
    private Label.LabelStyle ls;
    private Label title;
    private TextField inputText;
    private TextField.TextFieldStyle tfs;

    public HiddenScreen(MyGdxGame game) {
        this.game = game;

        this.dark = new BitmapFont(Gdx.files.internal("Font/black_font.fnt"), false);
        this.light = new BitmapFont(Gdx.files.internal("Font/white_font.fnt"), false);

        viewport = new FitViewport(800, 560);
        stage = new Stage(viewport);

        Gdx.input.setInputProcessor(stage);

        atlas = new TextureAtlas("UI/AtlasUI.pack");

        UIAtlas = new Skin(atlas);
    }

    @Override
    public void show() {
        tfs = new TextField.TextFieldStyle();
        tfs.cursor = UIAtlas.getDrawable("skull_knob");
        tfs.font = dark;
        tfs.fontColor = Color.BLACK;
        tfs.background = UIAtlas.getDrawable("placa_4");
        
        mainTable = new Table();
        stage.addActor(mainTable);
        mainTable.setFillParent(true);
        mainTable.center();
        
        
        inputText = new TextField("", tfs);
        mainTable.add(inputText);
    }

    @Override
    public void render(float delta) {
        stage.act();
        
        System.out.println(inputText.getText());
        
        if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)){
            dispose();
            
            if(inputText.getText().equals(GlobalConfig.password)){
                game.setScreen(new Settings(game));
            }else{
                game.setScreen(new MenuScreen(game));
            }
        };
        
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        
    }

    @Override
    public void pause() {
       
    }

    @Override
    public void resume() {
       
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        
    }

}
