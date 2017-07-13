package com.zenithlabs.shapeescape.menu;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.zenithlabs.shapeescape.game.Assets;
import com.zenithlabs.shapeescape.game.Renderer;
import com.zenithlabs.shapeescape.utils.Constants;

public class MenuScreenRenderer implements Renderer {
	private OrthographicCamera camera;
	private MenuArrowRain arrowRain;
	private SpriteBatch batch;

	public MenuScreenRenderer() {
		init();
	}
	
	private void init() {
		batch = new SpriteBatch();
		camera = new OrthographicCamera(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
		camera.position.set(0, 0, 0);
		camera.update();
		
		arrowRain = new MenuArrowRain();
	}
	
	public void update(float deltaTime) {
		arrowRain.update(deltaTime);
	}
	
	@Override
	public void render() {
		batch.begin();
		batch.setProjectionMatrix(camera.combined);
		arrowRain.render(batch);
		
		//Assets.getInstance().fonts.menu.draw(batch, "Shape Escape", -4, -2);
		batch.end();
	}
	
	
	
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
