package com.zenithlabs.shapeescape.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.zenithlabs.shapeescape.objects.AbstractShape;
import com.zenithlabs.shapeescape.utils.CameraHelper;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Input.Keys;

public class WorldInput extends InputAdapter implements InputProcessor {

	private static final String TAG = WorldInput.class.getName();

	private WorldController worldController;
	private CameraHelper cameraHelper;

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
		
		moveToTouch(tp.x, tp.y, deltaTime);

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
	

		
		return true;
	}

	@Override
	public boolean touchDragged(int x, int y, int pointer) {
		return false;
	}

	
	private void moveToTouch(float x, float y, float deltaTime) {
		AbstractShape shape = worldController.currentShape;
		Vector2 shapePos = worldController.currentShape.position;
		Vector2 finalPos = new Vector2(x - shape.scaledDimension.x / 2, y - shape.scaledDimension.y /2);
		float xDist = Math.abs(finalPos.x - shapePos.x);
		float yDist = Math.abs(finalPos.y - shapePos.y);
		float xSpeedBuffer = 1;
		float ySpeedBuffer = 1;
		if (!shapePos.epsilonEquals(finalPos, .15f)) {
			
			if (xDist > yDist) {
				xSpeedBuffer = xDist/yDist; 
			} else if (yDist > xDist) {
				ySpeedBuffer = yDist/xDist;
			}
			
			float xIncrement = deltaTime * shape.speed * xSpeedBuffer;
			float yIncrement = deltaTime * shape.speed * ySpeedBuffer;
			
			if (shapePos.x < finalPos.x) {
				if (shapePos.y < finalPos.y) {
					//Underneath to the left
					shape.position.add(xIncrement, yIncrement);
				} else if (shapePos.y > finalPos.y){
					//Above to the left
					shape.position.add(xIncrement, -yIncrement);
				}
			} else if (shapePos.x > finalPos.x){
				if (shapePos.y < finalPos.y) {
					//Underneath to the right
					shape.position.add(-xIncrement, yIncrement);

				} else if (shapePos.y > finalPos.y){
					//above to the right
					shape.position.add(-xIncrement, -yIncrement);

				}
			}
		}
		
		//shape.position.set(x - shape.scaledDimension.x / 2, y - shape.scaledDimension.y /2);

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
