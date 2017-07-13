package com.zenithlabs.shapeescape.menu;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.zenithlabs.shapeescape.game.Renderer;
import com.zenithlabs.shapeescape.utils.Constants;

public abstract class ScreenRenderer implements Renderer {
	private OrthographicCamera camera;
	
	private SpriteBatch batch;

	public ScreenRenderer () {
	}
	
	public void init() {
		batch = new SpriteBatch();
		camera = new OrthographicCamera(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
		camera.position.set(0, 0, 0);
		camera.update();
	}
	
	@Override
	public abstract void render();
	
	
	
	@Override
	public void dispose() {
		batch.dispose();
	}


	@Override
	public void resize(int width, int height) {
		camera.viewportWidth = (Constants.VIEWPORT_HEIGHT / height) * width;
		camera.update();
	}

}
