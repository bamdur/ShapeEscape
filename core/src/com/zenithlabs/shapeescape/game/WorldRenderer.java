/**
 * 
 */
package com.zenithlabs.shapeescape.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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
	
	private BoundRenderer boundRenderer;
	
	private static final String TAG = WorldRenderer.class.getName();
	
	public WorldRenderer (WorldController worldController) {
		this.worldController = worldController;
		boundRenderer = new BoundRenderer(worldController);
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
		
		//set worldControllers reference (alias) to camera
		this.worldController.setCamera(this.camera);
	}

	
	
	@Override
	public void render() {
		renderWorld(batch);
		renderGUI(batch);
		boundRenderer.render(camera);
		
	}
	
	private void renderWorld(SpriteBatch batch) {
		worldController.cameraHelper.applyTo(camera);
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		worldController.render(batch);
		batch.end();

	}
	
	private void renderGUI(SpriteBatch batch) {
		batch.setProjectionMatrix(cameraGUI.combined);
		batch.begin();
		renderGUIScore();
		renderGUIFPSCounter(batch);
		renderGUICoins();
		batch.end();
	}
	
	private void renderGUIScore() {
		String score = "Score: ";
		Assets.getInstance().fonts.medium.draw(batch, score + worldController.score, 5, 10);
	}

	private void renderGUIPause() {
		
	}
	
	private void renderGUICoins() {
		String score = "Coins: ";
		Assets.getInstance().fonts.medium.draw(batch, score + worldController.score, cameraGUI.viewportWidth - 90, 10);

	}
	
	private void renderGUIFPSCounter(SpriteBatch batch) {
		float x = cameraGUI.viewportWidth - 60;
		float y = cameraGUI.viewportHeight - 20;
		int fps = Gdx.graphics.getFramesPerSecond();
		BitmapFont fpsFont = Assets.getInstance().fonts.medium;
		
		if (fps >= 45) {
			fpsFont.setColor(Color.GREEN);
		} else if (fps >= 30) {
			fpsFont.setColor(Color.YELLOW);
		} else {
			fpsFont.setColor(Color.RED);
		}
		fpsFont.draw(batch, "FPS: " + fps, x, y);
		fpsFont.setColor(Color.WHITE);
				
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
	
	@Override
	public void dispose() {
		batch.dispose();
		
	}
	
}
