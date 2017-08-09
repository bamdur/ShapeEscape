package com.zenithlabs.shapeescape.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.zenithlabs.shapeescape.objects.AbstractShape;
import com.zenithlabs.shapeescape.utils.CameraHelper;
import com.zenithlabs.shapeescape.utils.ObjectAccessor;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Input.Keys;

public class WorldInput extends InputAdapter implements InputProcessor {

	private static final String TAG = WorldInput.class.getName();

	private WorldController worldController;
	private CameraHelper cameraHelper;
	
	private static float SPEED_FACTOR = .4f;

	// Touch Point vector for camera unprojection for mouse/touch
	Vector3 tp;

	public WorldInput(WorldController worldController, CameraHelper cameraHelper) {
		this.worldController = worldController;
		this.cameraHelper = cameraHelper;

		init();
	}

	private void init() {
		Gdx.input.setInputProcessor(this);
		tp = new Vector3();
	}

	public void update(float deltaTime) {
		handleDebugInput(deltaTime);
		cameraHelper.update(deltaTime);
		
		worldController.tweenManager.update(deltaTime);

	}

	

	//When screen is touched/pressed
	@Override
	public boolean touchDown(int x, int y, int pointer, int button) {
		if (button != Input.Buttons.LEFT || pointer > 0) return false;
		worldController.getCamera().unproject(tp.set(x, y, 0));
		return true;
	}
	
	//When touch is lifted
	@Override
	public boolean touchUp(int x, int y, int pointer, int button) {
		if (button != Input.Buttons.LEFT || pointer > 0) return false;
		worldController.getCamera().unproject(tp.set(x, y, 0));
		moveToTouch(tp.x, tp.y);

		
		return true;
	}

	@Override
	public boolean touchDragged(int x, int y, int pointer) {
		return false;
	}

	
	private void moveToTouch(float x, float y) {
		AbstractShape shape = worldController.currentShape;
		Vector2 shapePos = worldController.currentShape.position;
		Vector2 finalPos = new Vector2(x - shape.scaledDimension.x / 2, y - shape.scaledDimension.y /2);
		
		float length = (float) Math.hypot(Math.abs(finalPos.x - shapePos.x), Math.abs(finalPos.y - shapePos.y));
		Gdx.app.log(TAG, length + "");
		
		Tween.to(shape, ObjectAccessor.POSITION_XY, shape.speed * length * SPEED_FACTOR).
		target(finalPos.x, finalPos.y).ease(TweenEquations.easeOutQuad).start(worldController.tweenManager);
	}
	
	
	
	private void handleDebugInput(float deltaTime) {
		if (Gdx.app.getType() != ApplicationType.Desktop)
			return;

		// Camera Controls (move)
		float camMoveSpeed = 5 * deltaTime;
		float camMoveSpeedAccelerationFactor = 5;
		if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT))
			camMoveSpeed *= camMoveSpeedAccelerationFactor;
		if (Gdx.input.isKeyPressed(Keys.LEFT))
			moveCamera(-camMoveSpeed, 0);
		if (Gdx.input.isKeyPressed(Keys.RIGHT))
			moveCamera(camMoveSpeed, 0);
		if (Gdx.input.isKeyPressed(Keys.UP))
			moveCamera(0, camMoveSpeed);
		if (Gdx.input.isKeyPressed(Keys.DOWN))
			moveCamera(0, -camMoveSpeed);
		if (Gdx.input.isKeyPressed(Keys.BACKSPACE))
			cameraHelper.setPosition(0, 0);

		// Camera Controls (zoom)
		float camZoomSpeed = 1 * deltaTime;
		float camZoomSpeedAccelerationFactor = 5;
		if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT))
			camZoomSpeed *= camZoomSpeedAccelerationFactor;
		if (Gdx.input.isKeyPressed(Keys.COMMA))
			cameraHelper.addZoom(camZoomSpeed);
		if (Gdx.input.isKeyPressed(Keys.PERIOD))
			cameraHelper.addZoom(-camZoomSpeed);
		if (Gdx.input.isKeyPressed(Keys.SLASH))
			cameraHelper.setZoom(1);
	}

	public void moveCamera(float x, float y) {
		x += cameraHelper.getPosition().x;
		y += cameraHelper.getPosition().y;
		cameraHelper.setPosition(x, y);
	}

	@Override
	public boolean keyUp(int keycode) {
		// Reset game world
		if (keycode == Keys.R) {
			worldController.init();
			Gdx.app.debug(TAG, "Game world resetted");
		}
		// Select next sprite
		else if (keycode == Keys.ESCAPE) {
			worldController.backToMenu();
		}

		return false;
	}
}
