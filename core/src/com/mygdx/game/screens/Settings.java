
package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.constants.GlobalConfig;
import com.mygdx.game.constants.Pair;


public class Settings implements Screen {

    private BitmapFont dark;
    private BitmapFont light;
    private FitViewport viewport;
    private Stage stage;
    private TextureAtlas atlas;
    private Skin UIAtlas;
    private Table mainTable;
    private final MyGdxGame game;
    private LabelStyle ls;
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

        viewport = new FitViewport(1280, 720);
        stage = new Stage(viewport);

        Gdx.input.setInputProcessor(stage);

        atlas = new TextureAtlas("UI/AtlasUI.pack");

        UIAtlas = new Skin(atlas);

        this.ls = new LabelStyle(light, Color.WHITE);

        sliderStyle = new Slider.SliderStyle();
        sliderStyle.background = UIAtlas.getDrawable("placa_2");
        sliderStyle.knob = UIAtlas.getDrawable("skull_knob");

        TextureRegionDrawable back = (TextureRegionDrawable) UIAtlas.getDrawable("placa_2");
        TextureRegionDrawable cursor = (TextureRegionDrawable) UIAtlas.getDrawable("skull_knob");

        tfs = new TextField.TextFieldStyle(dark, Color.BLACK, cursor, cursor, back);

        mainTable = new Table();
        scroll = new ScrollPane(mainTable);

        mainTable.setFillParent(true);
        mainTable.pad(20);
        mainTable.padBottom(400);

        outerContainer = new Table();
        stage.addActor(outerContainer);
        outerContainer.setClip(true);
        outerContainer.setFillParent(true);

        outerContainer.background(UIAtlas.getDrawable("placa_4"));
        outerContainer.add(scroll);
        outerContainer.align(1).center();

        addLabel("Configuracoes", 20);
        this.playerHealth = addSliderInput("Vida do Jogador ", 1, 20, 20, 0);
        this.cactusHealth = addSliderInput("Vida do Cactu", 1, 20, 20, 0);
        this.coffinHealth = addSliderInput("Vida do Caix�o", 1, 20, 20, 0);

        this.playerDamage = addSliderInput("Dano do Jogador ", 1, 20, 20, 0);
        this.cactusDamage = addSliderInput("Dano do Cactu", 1, 20, 20, 0);
        this.coffinDamage = addSliderInput("Dano do Caix�o", 1, 20, 20, 0);

        this.playerSpeed = addSliderInput("Velocidade do jogador ", 1, 120, 20, 0);
        this.cactusSpeed = addSliderInput("Velocidade do Cactu", 1, 120, 20, 0);
        this.coffinSpeed = addSliderInput("Velocidade do Caix�o", 1, 120, 20, 0);

        this.spawnerMax = addSliderInput("Maximo de Inimigos por Spawner", 1, 200, 20, 0);
        this.spawnerCooldown = addSliderInput("Tempo de Recarga do Spawner", 1, 40, 20, 30);

        this.addTextButton("Confirmar").addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                saveValues();
                dispose();
                game.setScreen(new MenuScreen((MyGdxGame) game));
            }
        });

        this.addTextButton("Sair sem Salvar").addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dispose();
                game.setScreen(new MenuScreen((MyGdxGame) game));
            }
        });

        mainTable.row();

    }

    private TextButton addTextButton(String name) {

        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();

        style.up = UIAtlas.getDrawable("placa_2");
        style.down = UIAtlas.getDrawable("placa_2");
        style.over = UIAtlas.getDrawable("placa_2");
        style.pressedOffsetX = 2;
        style.pressedOffsetY = 2;
        style.font = dark;

        TextButton button = new TextButton(name, style);

        button.pad(20);
        //button.padBottom(5);

        Container container = new Container(button);
        container.pad(5);

        mainTable.add(container);

        return button;
    }

    private Actor addLabel(String name, int pad) {

        LabelStyle ls = new Label.LabelStyle(dark, Color.BLACK);
        Label label = new Label(name, ls);

        Container labelContainer = new Container(label);
        mainTable.add(labelContainer);

        labelContainer.pad(pad);

        mainTable.row();

        return label;
    }

    private Pair<Slider, Label> addSliderInput(String name, int min, int max, int padding, int spaceBottom) {

        LabelStyle ls = new Label.LabelStyle(dark, Color.BLACK);

        Label inputLabel = new Label(name, ls);
        Container inputLabelContainer = new Container(inputLabel);

        Slider slider = new Slider(min, max, 1, false, sliderStyle);
        Container sliderContainer = new Container(slider);

        Label value = new Label(String.valueOf(1), ls);
        Container valueContainer = new Container(value);

        mainTable.add(inputLabelContainer).spaceBottom(spaceBottom);
        mainTable.add(sliderContainer).spaceBottom(spaceBottom);
        mainTable.add(valueContainer).spaceBottom(spaceBottom);
        mainTable.row();

        valueContainer.pad(padding);
        sliderContainer.pad(padding);
        inputLabelContainer.pad(padding);

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

    private void saveValues() {

        GlobalConfig.PlayerHealth = (int) playerHealth.first.getValue();
        GlobalConfig.CactusHealth = (int) cactusHealth.first.getValue();
        GlobalConfig.CoffinHealth = (int) coffinHealth.first.getValue();
        GlobalConfig.PlayerDamageModifier = (int) playerDamage.first.getValue();
        GlobalConfig.CactusDamageModifier = (int) cactusDamage.first.getValue();
        GlobalConfig.CoffinDamageModifier = (int) coffinDamage.first.getValue();
        GlobalConfig.PlayerSpeedModifier = (int) playerSpeed.first.getValue();
        GlobalConfig.CactusSpeedModifier = (int) cactusSpeed.first.getValue();
        GlobalConfig.CoffinSpeedModifier = (int) coffinSpeed.first.getValue();
        GlobalConfig.SpawnerMaxSpawns = (int) spawnerMax.first.getValue();
        GlobalConfig.SpawnerCooldowns = (int) spawnerCooldown.first.getValue();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        mainTable.setClip(true);
        mainTable.setFillParent(true);
        mainTable.setTransform(true);
        mainTable.setBounds(viewport.getScreenX(), viewport.getScreenY(), viewport.getScreenWidth(), viewport.getScreenHeight());

        updateValue();


        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(1280, 720);
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
        UIAtlas.dispose();
    }

}
