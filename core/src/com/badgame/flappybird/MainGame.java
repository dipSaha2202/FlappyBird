package com.badgame.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Random;

public class MainGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture background, topTube, bottomTube;
	Texture[] birds, topTubes, bottomTubes;
	int flapsState = 0, gameState = 0;
	float birdsY = 0, numberOfTubes = 4, horizontalDistanceBetwwnTubes;
	float yVelocity = 0, tubeOffSet, xVelocity = 4.0f, tubeX;
	private float gap = 400, maxTubeOffSet;
	private Random random;
	
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

		maxTubeOffSet = ((Gdx.graphics.getHeight() - gap)/2);
		random = new Random();

	}

	@Override
	public void render () {
        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		if (gameState != 0){
			tubeX -= xVelocity;
			batch.draw(topTube, tubeX
					,
					(Gdx.graphics.getHeight() + gap) / 2 + tubeOffSet);
            batch.draw(bottomTube,
					tubeX,
					(Gdx.graphics.getHeight() - gap) / 2 - bottomTube.getHeight() + tubeOffSet);

			if (Gdx.input.justTouched()){
				tubeX = (float) (Gdx.graphics.getWidth() + topTube.getWidth());
				if (birdsY < Gdx.graphics.getHeight() - 100 - birds[0].getHeight()){
					yVelocity = -20;
					tubeOffSet = (random.nextFloat() - 0.5f) * (maxTubeOffSet + gap/2) ;
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


		batch.draw(birds[flapsState],
				(Gdx.graphics.getWidth() - birds[flapsState].getWidth())/ 2,
				  birdsY);

	//	int random = new Random().nextInt(Gdx.graphics.getWidth());


		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		background.dispose();
	}
}
