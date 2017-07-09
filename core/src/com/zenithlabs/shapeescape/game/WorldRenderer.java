/**
 * 
 */
package com.zenithlabs.shapeescape.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;
import com.zenithlabs.shapeescape.utils.Constants;

/**
 * @author Brandon Amdur
 *
 */
public class WorldRenderer implements Renderer {
	
	private OrthographicCamera camera;
	private OrthographicCamera cameraGUI;
	
	private SpriteBatch batch;
	
	private WorldController worldController;
	
	private static final String TAG = WorldRenderer.class.getName();
	
	public WorldRenderer (WorldController worldController) {
		this.worldController = worldController;
		init();

	}
	
	private void init() {
		batch = new SpriteBatch();
		camera = new OrthographicCamera(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
		camera.position.set(0, 0, 0);
		camera.update();
		
		cameraGUI = new OrthographicCamera(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
		cameraGUI.position.set(0, 0, 0);
		cameraGUI.setToOrtho(true);
		cameraGUI.update();
	}

	
	
	@Override
	public void render() {
		worldController.cameraHelper.applyTo(camera);
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		renderGUIScore();
		renderTestObjects();
		batch.end();
	}
	
	private void renderTestObjects() {

		worldController.render(batch);

	}
	@Override
	public void resize(int width, int height) {
		camera.viewportWidth = (Constants.VIEWPORT_HEIGHT / height) * width;
		camera.update();
		
		cameraGUI.viewportHeight = Constants.WINDOW_HEIGHT;
		cameraGUI.viewportWidth = (Constants.WINDOW_HEIGHT / (float) height) * (float) width;
		cameraGUI.position.set(cameraGUI.viewportWidth / 2, cameraGUI.viewportHeight /2, 0);
		cameraGUI.update();
	}
	
	private void renderGUIScore() {
		String score = "Score: ";
		float x = -20;
		float y = -20;
		Assets.getInstance().fonts.medium.draw(batch, score + worldController.score, x, y);
	}

	@Override
	public void dispose() {
		batch.dispose();
		
	}
	
}
