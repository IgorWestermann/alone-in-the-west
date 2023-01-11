/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.MyGdxGame;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class GameOver implements Screen {

    private BitmapFont dark;
    private BitmapFont light;
    private FitViewport viewport;
    private Stage stage;
    private TextureAtlas atlas;
    private Skin UIAtlas;
    private Table mainTable;
    private final MyGdxGame game;
    private Label.LabelStyle ls;
    private TextField.TextFieldStyle tfs;
    private TextButton.TextButtonStyle textButtonStyle;
    private final int score;
    private TextField textInput;

    public GameOver(MyGdxGame game, int score) {
        this.game = game;
        this.score = score;
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

        TextureRegionDrawable back = (TextureRegionDrawable) UIAtlas.getDrawable("placa_2");
        TextureRegionDrawable cursor = (TextureRegionDrawable) UIAtlas.getDrawable("skull_knob");

        tfs = new TextField.TextFieldStyle(dark, Color.BLACK, cursor, cursor, back);
        ls = new Label.LabelStyle(dark, Color.BLACK);

        mainTable = new Table();
        stage.addActor(mainTable);

        mainTable.setClip(true);
        mainTable.setFillParent(true);
        mainTable.debug();
        mainTable.center();
        mainTable.align(1);
        mainTable.setBackground(new TextureRegionDrawable(new Texture("UI/background.png")));

        //mainTable.padLeft(Gdx.graphics.getWidth() / 3);
        this.textButtonStyle = new TextButton.TextButtonStyle();
        this.textButtonStyle.up = UIAtlas.getDrawable("placa_3");
        this.textButtonStyle.font = light;
        this.textButtonStyle.fontColor = Color.WHITE;

        addLabel("Game Over", 20).setScale(3);
        addLabel("Sua pontuacao final foi de: ", 10);
        addLabel(String.valueOf(score), 10);

        addLabel("Salve seu nome no registro de pontuacoes ", 20);

        textInput = addTextFieldInput("Seu nome...", 20);

        addTextButton("Continuar").addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                saveToCSVFile();
                dispose();
                game.setScreen(new MenuScreen((MyGdxGame) game));
            }
        });;

    }

    private Label addLabel(String name, int pad) {

        Label label = new Label(name, ls);

        Container labelContainer = new Container(label);
        mainTable.add(labelContainer);

        labelContainer.pad(pad);

        mainTable.row();

        return label;
    }

    private TextField addTextFieldInput(String name, int padding) {

        Label inputLabel = new Label(name, ls);
        Container inputLabelContainer = new Container(inputLabel);
        inputLabelContainer.pad(padding);

        TextField tf = new TextField("", tfs);

        Container textFieldContainer = new Container(tf);
        textFieldContainer.pad(20);

        mainTable.add(inputLabelContainer).spaceBottom(2);
        mainTable.row();
        mainTable.add(textFieldContainer).spaceBottom(2);
        mainTable.row();

        return tf;
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

        Container container = new Container(button);
        container.pad(5);

        mainTable.add(container);

        return button;
    }

    private void saveToCSVFile() {
        FileHandle handle;
        File source;
        PrintWriter printWriter = null;
        FileWriter fr = null;

        handle = Gdx.files.internal("score_register.csv");
        source = handle.file();
        try {
            fr = new FileWriter(source, true);
            printWriter = new PrintWriter(fr);

            printWriter.println(textInput.getText() + " , " + score);

        } catch (FileNotFoundException ex) {
            System.out.println("Arquivo n�o encontrado, registro n sera salvo");
        } catch (IOException ex) {
            System.out.println("Arquivo n�o encontrado, registro n sera salvo");
        } finally {
            if (fr != null) {
                try {
                    fr.close();
                } catch (IOException ex) {
                    System.out.println("Namoral , quem lan�a esses exception � chat�o");
                }
            }
            if (printWriter != null) {
                printWriter.close();
            }

        }

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        mainTable.setClip(true);
        mainTable.setFillParent(true);
        mainTable.setTransform(true);
        mainTable.setBounds(viewport.getScreenX(), viewport.getScreenY(), viewport.getScreenWidth(), viewport.getScreenHeight());
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
