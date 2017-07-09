package com.zenithlabs.shapeescape.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.zenithlabs.shapeescape.utils.CameraHelper;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Input.Keys;

public class WorldInput extends InputAdapter {

	private static final String TAG = WorldInput.class.getName();
	private WorldController worldController;
	private CameraHelper cameraHelper;
	
	public WorldInput (WorldController worldController, CameraHelper cameraHelper) {
		this.worldController = worldController;
		this.cameraHelper = cameraHelper;

		init();
	}
	

	private void init() {
		Gdx.input.setInputProcessor(this);
	}

	public void update (float deltaTime) {
		handleDebugInput(deltaTime);
		cameraHelper.update(deltaTime);
	}
	
	public void handleDebugInput (float deltaTime) {
		if (Gdx.app.getType() != ApplicationType.Desktop) return;

		// Camera Controls (move)
		float camMoveSpeed = 5 * deltaTime;
		float camMoveSpeedAccelerationFactor = 5;
		if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) camMoveSpeed *= camMoveSpeedAccelerationFactor;
		if (Gdx.input.isKeyPressed(Keys.LEFT)) moveCamera(-camMoveSpeed, 0);
		if (Gdx.input.isKeyPressed(Keys.RIGHT)) moveCamera(camMoveSpeed, 0);
		if (Gdx.input.isKeyPressed(Keys.UP)) moveCamera(0, camMoveSpeed);
		if (Gdx.input.isKeyPressed(Keys.DOWN)) moveCamera(0, -camMoveSpeed);
		if (Gdx.input.isKeyPressed(Keys.BACKSPACE)) cameraHelper.setPosition(0, 0);

		// Camera Controls (zoom)
		float camZoomSpeed = 1 * deltaTime;
		float camZoomSpeedAccelerationFactor = 5;
		if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) camZoomSpeed *= camZoomSpeedAccelerationFactor;
		if (Gdx.input.isKeyPressed(Keys.COMMA)) cameraHelper.addZoom(camZoomSpeed);
		if (Gdx.input.isKeyPressed(Keys.PERIOD)) cameraHelper.addZoom(-camZoomSpeed);
		if (Gdx.input.isKeyPressed(Keys.SLASH)) cameraHelper.setZoom(1);
	}
	
	
	public void moveCamera (float x, float y) {
		x += cameraHelper.getPosition().x;
		y += cameraHelper.getPosition().y;
		cameraHelper.setPosition(x, y);
	}
	
	@Override
	public boolean keyUp (int keycode) {
		// Reset game world
		if (keycode == Keys.R) {
			worldController.init();
			Gdx.app.debug(TAG, "Game world resetted");
		}
		// Select next sprite
		else if (keycode == Keys.SPACE) {
		
		}
		
		return false;
	}
}
