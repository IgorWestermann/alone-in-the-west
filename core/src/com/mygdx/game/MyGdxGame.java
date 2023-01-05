package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.screens.*;


//essa classe implementa os eventos que acontecem no loop do jogo
//implementa uma interface de aplication adapter 
//vai ser responsavem por gerenciar o jogo e suas telas como um todo
public class MyGdxGame extends Game {
    
        //batch e responsavel por carimbar texturas na tela
	public SpriteBatch batch;

	@Override
	public void create () {
		batch = new SpriteBatch();
                setScreen(new PlayableScreen(this));
	}

	@Override
	public void render () {
            super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
