package com.badgame.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

public class MainGame extends ApplicationAdapter {
	SpriteBatch batch;
	//ShapeRenderer shapeRenderer;

	private Texture background, topTube, bottomTube, gameover;
	private Texture[] birds = new Texture[2];

	private int flapsState = 0, gameState = 0, numberOfTubes = 4, points = 0, scoringTube = 0;

	private float maxTubeOffSet, birdsY = 0, xDistanceBetweenTubes, defaultTopTubeY, defaultBottomTubeY;
	private float  gap = 400, yVelocity = 0, xVelocity = 4.0f;
	private float[] tubeX = new float[numberOfTubes],  tubeOffSet = new float[numberOfTubes];

	Circle birdCircle;
	Rectangle[] topTubesShapes, bottomTubeShapes;
	BitmapFont pointsFont;



	private Random random;
	@Override
	public void create () {
		batch = new SpriteBatch();
		//shapeRenderer = new ShapeRenderer();
		birdCircle = new Circle();

		points = 0;
		pointsFont = new BitmapFont();
		pointsFont.setColor(Color.RED);
		pointsFont.getData().setScale(5);

		topTubesShapes = new Rectangle[numberOfTubes];
		bottomTubeShapes = new Rectangle[numberOfTubes];

		background = new Texture("bg.png");
		birds[0] = new Texture("bird.png");
		birds[1] = new Texture("bird2.png");
		topTube  = new Texture("toptube.png");
		bottomTube = new Texture("bottomtube.png");
		gameover = new Texture("gameover.png");


		xDistanceBetweenTubes = (float) Gdx.graphics.getWidth() /2 + gap;
		maxTubeOffSet = Gdx.graphics.getHeight() - gap * 2.5f;

		random = new Random();

		setNewGame();

		defaultTopTubeY = (Gdx.graphics.getHeight() + gap) / 2;
		defaultBottomTubeY = (Gdx.graphics.getHeight() - gap) / 2 - bottomTube.getHeight();
	}

	private void setNewGame(){
		birdsY = (Gdx.graphics.getHeight() - birds[0].getHeight())/2;
		for (int i = 0 ; i < numberOfTubes ; i++){
			tubeOffSet[i] = (random.nextFloat() - 0.5f) * maxTubeOffSet;
			tubeX[i] = (float) (Gdx.graphics.getWidth() + topTube.getWidth()) + i * xDistanceBetweenTubes;

			topTubesShapes[i] = new Rectangle();
			bottomTubeShapes[i] = new Rectangle();
		}
	}

	@Override
	public void render () {
        batch.begin();
		//shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
	//	shapeRenderer.setColor(Color.BLUE);

        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		if (gameState == 1){

			if (tubeX[scoringTube] <(Gdx.graphics.getWidth() / 2.0f -
					topTube.getWidth()/2.0f - birds[0].getWidth())){
				points++;
				if (scoringTube < numberOfTubes -1 ) {


					scoringTube++;
				} else {
					scoringTube = 0;
				}

				Gdx.app.log("Score", points + "");
			}
            if (Gdx.input.justTouched()){
                if (birdsY < Gdx.graphics.getHeight() - 100 - birds[0].getHeight()){
                    yVelocity = -20;
                }
            }

			for (int i = 0 ; i < numberOfTubes ; i++){

				if (tubeX[i] < (0 - topTube.getWidth())){
					tubeOffSet[i] = (random.nextFloat() - 0.5f) * maxTubeOffSet ;
					tubeX[i] += numberOfTubes * xDistanceBetweenTubes;
				} else {
					tubeX[i] -= xVelocity;
				}

				topTubesShapes[i] = new Rectangle(tubeX[i],
						defaultTopTubeY + tubeOffSet[i],
						topTube.getWidth(), topTube.getHeight());
				bottomTubeShapes[i] = new Rectangle(tubeX[i],
						defaultBottomTubeY + tubeOffSet[i],
						bottomTube.getWidth(), bottomTube.getHeight());

			/*	shapeRenderer.rect(tubeX[i],
						defaultTopTubeY + tubeOffSet[i],
						topTube.getWidth(), topTube.getHeight() );
				shapeRenderer.rect(tubeX[i],
						defaultBottomTubeY + tubeOffSet[i],
						bottomTube.getWidth(), bottomTube.getHeight()); */

				batch.draw(topTube, tubeX[i],
						defaultTopTubeY + tubeOffSet[i]);
				batch.draw(bottomTube, tubeX[i],
						defaultBottomTubeY + tubeOffSet[i]);
			}


			if (birdsY > (Math.floor(birds[0].getHeight()/2))){
				yVelocity++;
				birdsY -= yVelocity;
			}else {
				gameState = 2;
			}
		} else if (gameState == 0){
			if (Gdx.input.justTouched()){
				gameState = 1;
			}
		} else if (gameState == 2){
			batch.draw(gameover,
					(Gdx.graphics.getWidth()- gameover.getWidth())/2,
					(Gdx.graphics.getHeight() - gameover.getHeight())/2);

			if (Gdx.input.justTouched()){
				gameState = 0;
				setNewGame();
				points =0;
				scoringTube = 0;
				yVelocity = 0;
			}
		}

		if (flapsState == 0){
			flapsState = 1;
		} else {
			flapsState = 0;
		}


		batch.draw(birds[flapsState],
				(Gdx.graphics.getWidth() - birds[flapsState].getWidth())/ 2, birdsY);

        pointsFont.draw(batch, " Score : " + points, 20, Gdx.graphics.getHeight() - 50);



		birdCircle.set(Gdx.graphics.getWidth() / 2,
				birdsY + birds[0].getHeight() / 2.0f,
				birds[0].getWidth() / 2);



	//	shapeRenderer.circle(birdCircle.x, birdCircle.y, birdCircle.radius);

		for (int i = 0 ; i < numberOfTubes ; i++){
			if (Intersector.overlaps(birdCircle, topTubesShapes[i]) ||
			Intersector.overlaps(birdCircle, bottomTubeShapes[i])){

				gameState = 2;
			}
		}

	//	shapeRenderer.end();
		batch.end();

	}
	@Override
	public void dispose () {
		batch.dispose();
		background.dispose();
	}
}
