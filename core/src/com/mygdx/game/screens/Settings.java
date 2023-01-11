/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TransformDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.constants.Pair;

/**
 *
 * @author Hugo
 */
public class Settings implements Screen {

    private BitmapFont dark;
    private BitmapFont light;
    private FitViewport viewport;
    private Stage stage;
    private TextureAtlas atlas;
    private Skin buttonSkin;
    private Table mainTable;
    private final MyGdxGame game;
    private LabelStyle ls;
    private Label title;
    private Pair<Slider, Label> playerHealth;
    private Pair<Slider, Label> cactusHealth;
    private Pair<Slider, Label> coffinHealth;
    private Pair<Slider, Label> playerDamage;
    private Pair<Slider, Label> cactusDamage;
    private Pair<Slider, Label> coffinDamage;
    private Pair<Slider, Label> playerSpeed;
    private Pair<Slider, Label> cactusSpeed;
    private Pair<Slider, Label> coffinSpeed;
    private Pair<Slider, Label> spawnerMax;
    private Pair<Slider, Label> spawnerCooldown;
    private Slider.SliderStyle sliderStyle;
    private TextField.TextFieldStyle tfs;
    private ScrollPane scroll;
    private Table outerContainer;

    public Settings(MyGdxGame game) {

        this.game = game;
    }

    @Override
    public void show() {

        this.dark = new BitmapFont(Gdx.files.internal("Font/black_font.fnt"), false);
        this.light = new BitmapFont(Gdx.files.internal("Font/white_font.fnt"), false);

        viewport = new FitViewport(800, 560);
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);

        atlas = new TextureAtlas("UI/AtlasUI.pack");

        buttonSkin = new Skin(atlas);

        this.ls = new LabelStyle(light, Color.WHITE);

        sliderStyle = new Slider.SliderStyle();
        sliderStyle.background = buttonSkin.getDrawable("placa_2");
        sliderStyle.knob = buttonSkin.getDrawable("skull_knob");

        TextureRegionDrawable back = (TextureRegionDrawable) buttonSkin.getDrawable("placa_2");
        TextureRegionDrawable cursor = (TextureRegionDrawable) buttonSkin.getDrawable("skull_knob");

        tfs = new TextField.TextFieldStyle(dark, Color.BLACK, cursor, cursor, back);

        mainTable = new Table();
        mainTable.setFillParent(true);
        mainTable.center();

        scroll = new ScrollPane(mainTable);
        outerContainer = new Table();
        outerContainer.add(scroll).width(800).height(560);
        outerContainer.align(1).center().setX(0);

        addLabel("Configuracoes", 20);
        this.playerHealth = addSliderInput("Vida do Jogador ", 1, 20, 20);
        this.cactusHealth = addSliderInput("Vida do Cactu", 1, 20, 20);
        this.coffinHealth = addSliderInput("Vida do Caixao", 1, 20, 20);

        this.playerDamage = addSliderInput("Dano do Jogador ", 1, 20, 20);
        this.cactusDamage = addSliderInput("Dano do Cactu", 1, 20, 20);
        this.coffinDamage = addSliderInput("Dano do Caixao", 1, 20, 20);

        this.playerSpeed = addSliderInput("Velocidade do jogador ", 1, 20, 20);
        this.cactusSpeed = addSliderInput("Velocidade do Cactu", 1, 20, 20);
        this.coffinSpeed = addSliderInput("Velocidade do Caixao", 1, 20, 20);

        this.spawnerMax = addSliderInput("Maximo de Inimigos por Spawner", 1, 200, 20);
        this.spawnerCooldown = addSliderInput("Tempo de Recarga do Spawner", 1, 20, 20);

        stage.addActor(outerContainer);

    }

    private Actor addLabel(String name, int pad) {
        LabelStyle ls = new Label.LabelStyle(light, Color.WHITE);
        Label label = new Label(name, ls);

        Container titleContainer = new Container(title);
        titleContainer.pad(pad);

        mainTable.add(titleContainer);
        mainTable.row();

        return label;
    }

    private Pair<Slider, Label> addSliderInput(String name, int min, int max, int padding) {

        Label inputLabel = new Label(name, ls);
        Container inputLabelContainer = new Container(inputLabel);
        inputLabelContainer.pad(padding);

        Slider slider = new Slider(min, max, 1, false, sliderStyle);
        Container sliderContainer = new Container(slider);
        sliderContainer.pad(20);

        Label value = new Label(String.valueOf(1), ls);
        Container valueContainer = new Container(value);
        valueContainer.pad(20);

        mainTable.add(inputLabelContainer, sliderContainer, valueContainer);
        mainTable.row();

        return new Pair<Slider, Label>(slider, value);
    }

    private Actor addTextFieldInput(String name, int padding) {
        Label inputLabel = new Label(name, ls);
        Container inputLabelContainer = new Container(inputLabel);
        inputLabelContainer.pad(padding);

        TextField tf = new TextField(name, tfs);

        Container textFieldContainer = new Container(tf);
        textFieldContainer.pad(20);

        mainTable.add(inputLabelContainer, textFieldContainer);
        mainTable.row();

        return tf;
    }

    private void updateValue() {

        playerHealth.second.setText(String.valueOf(playerHealth.first.getValue()));
        cactusHealth.second.setText(String.valueOf(cactusHealth.first.getValue()));
        coffinHealth.second.setText(String.valueOf(coffinHealth.first.getValue()));
        playerDamage.second.setText(String.valueOf(playerDamage.first.getValue()));
        cactusDamage.second.setText(String.valueOf(cactusDamage.first.getValue()));
        coffinDamage.second.setText(String.valueOf(coffinDamage.first.getValue()));
        playerSpeed.second.setText(String.valueOf(playerSpeed.first.getValue()));
        cactusSpeed.second.setText(String.valueOf(cactusSpeed.first.getValue()));
        coffinSpeed.second.setText(String.valueOf(coffinSpeed.first.getValue()));
        spawnerMax.second.setText(String.valueOf(spawnerMax.first.getValue()));
        spawnerCooldown.second.setText(String.valueOf(spawnerCooldown.first.getValue()));

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        updateValue();

        outerContainer.setClip(true);
        outerContainer.setFillParent(true);

        stage.act(delta);
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
        dark.dispose();
        light.dispose();
        stage.dispose();
        atlas.dispose();
        buttonSkin.dispose();
    }

}
