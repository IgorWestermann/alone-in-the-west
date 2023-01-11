package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.entities.mobs.Player;

import java.util.ArrayList;
import java.util.List;

public class Hud {
    public Stage stage;
    private Viewport viewport;
    Texture life;
    private List<Image> playerHealth;
    Table table = new Table();

    Player player;

    public Hud(Player player, SpriteBatch sb) {
        viewport = new FillViewport(400, 200, new OrthographicCamera());
        stage = new Stage(viewport, sb);
        life = new Texture(Gdx.files.internal("FX/heart.png"));
        this.player = player;
        player.getHealth();
        playerHealth = new ArrayList<>();
        for (int i = 0; i < player.getHealth(); i++) {
            playerHealth.add(new Image(life));
        }
        table.top();
        table.left();
        for (Image healthPoint : playerHealth) {
            table.add(healthPoint);
        }
        table.setFillParent(true);
        stage.addActor(table);

    }

    public void update() {
        if (this.player.isHit() && this.playerHealth.size() > 0) {
            table.removeActor(this.playerHealth.get(player.getHealth()));
        }
        stage.draw();
    }

}