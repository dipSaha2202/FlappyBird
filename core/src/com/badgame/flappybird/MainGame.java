package com.badgame.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MainGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture background, topTube, bottomTube;
	Texture[] birds;
	int flapsState = 0, gameState = 0;
	float birdsY = 0;
	float yVelocity = 0;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		background = new Texture("bg.png");

		birds = new Texture[2];
		birds[0] = new Texture("bird.png");
		birds[1] = new Texture("bird2.png");

		topTube = new Texture("toptube.png");
		bottomTube = new Texture("bottomtube.png");

		birdsY = (Gdx.graphics.getHeight() - birds[0].getHeight())/2;

	}

	@Override
	public void render () {
		if (gameState != 0){

			if (Gdx.input.justTouched()){
				if (birdsY < Gdx.graphics.getHeight() - 100 - birds[0].getHeight()){
					yVelocity = -20;
				}

			}

			if (birdsY > (Math.floor(birds[0].getHeight()/2)) || yVelocity < 0){
				yVelocity++;
				birdsY -= yVelocity;
			}
		} else {
			if (Gdx.input.justTouched()){
				gameState = 1;
			}
		}

		if (flapsState == 0){
			flapsState = 1;
		} else {
			flapsState = 0;
		}

		batch.begin();
		batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch.draw(birds[flapsState],
				(Gdx.graphics.getWidth() - birds[flapsState].getWidth())/ 2, birdsY);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		background.dispose();
	}
}
