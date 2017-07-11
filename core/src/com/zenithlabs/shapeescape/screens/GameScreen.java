package com.zenithlabs.shapeescape.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.zenithlabs.shapeescape.game.WorldController;
import com.zenithlabs.shapeescape.game.WorldRenderer;

public class GameScreen extends AbstractGameScreen {
	private static final String TAG = GameScreen.class.getName();

	private WorldController worldController;
	private WorldRenderer worldRenderer;

	private boolean paused;

	public GameScreen(Game game) {
		super(game);
	}

	@Override
	public void render(float deltaTime) {
		if (!paused) {
			worldController.update(deltaTime);
		}
		Gdx.gl.glClearColor(1, 0, 100.0f / 255.0f, 1);
		// clear the screen
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		// Render game world
		worldRenderer.render();
	}

	@Override
	public void resize(int width, int height) {
		worldRenderer.resize(width, height);
	}

	@Override
	public void pause() {
		paused = true;
	}

	@Override
	public void resume() {
		super.resume();
		// only called on android
		paused = false;
	}

	@Override
	public void show() {
		worldController = new WorldController(game);
		worldRenderer = new WorldRenderer(worldController);
		Gdx.input.setCatchBackKey(false);

	}
	
	@Override
	public void hide() {
		worldRenderer.dispose();
		Gdx.input.setCatchBackKey(false);

	}}
