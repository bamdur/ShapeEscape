package com.zenithlabs.shapeescape;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.zenithlabs.shapeescape.game.Assets;
import com.zenithlabs.shapeescape.game.WorldController;
import com.zenithlabs.shapeescape.game.WorldRenderer;

public class ShapeEscape implements ApplicationListener {
	private WorldController worldController;
	private WorldRenderer worldRenderer;
	private boolean paused;
	
	@Override
	public void create () {
		//Set log level to DEBUG - for publishing switch to NONE
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		//Load assets
		Assets.getInstance().init(new AssetManager());
		//Initialize controller and renderer
		worldController = new WorldController();
		worldRenderer = new WorldRenderer(worldController);
	}

	@Override
	public void render () {
		//Update game world before rendering takes place
		worldController.update(Gdx.graphics.getDeltaTime());
		//Set clear screen color
		Gdx.gl.glClearColor(1, 0, 100.0f/255.0f, 1);
		//clear the screen
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		//Render game world
		worldRenderer.render();
	}
	
	

	@Override
	public void resize(int width, int height) {
		//Resize
		worldRenderer.resize(width, height);
		
	}

	@Override
	public void pause() {
		paused = true;
	}

	@Override
	public void resume() {
		Assets.getInstance().init(new AssetManager());
		paused = false;
	}
	
	@Override
	public void dispose () {
		//Free memory
		worldRenderer.dispose();
		Assets.getInstance().dispose();
	}
}
