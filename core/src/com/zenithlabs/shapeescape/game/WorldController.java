/**
 * 
 */
package com.zenithlabs.shapeescape.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.zenithlabs.shapeescape.objects.Arrow;
import com.zenithlabs.shapeescape.objects.Background;
import com.zenithlabs.shapeescape.utils.CameraHelper;

/**
 * @author Brandon Amdur
 *
 */
public class WorldController implements Controller {

	private static final String TAG = WorldController.class.getName();

	private WorldInput worldInput;
	
	public CameraHelper cameraHelper;
	
	public Background background;
	public Array<Arrow> arrows;
	
	public int score = 0;
	public int coins = 0;
	public float time = 0;
	
	public WorldController() {
		init();
	}
	
	//Public access so worldInput (input controller) can access it to reset the game
	public void init () {
		cameraHelper = new CameraHelper();
		worldInput = new WorldInput(this, cameraHelper);
		initTestObjects();
	}
	
	@Override
	public void update(float deltaTime) {
		worldInput.update(deltaTime);
		time += 60 * deltaTime;
		Gdx.app.log(TAG, "" + time);
		if (time > 60) {
			time = 0;
			score += 1 ;
			coins += 1 ;
		}
		
	}
	
	private void initTestObjects() {
		background = new Background();
		arrows = new Array<Arrow>();
		arrows.addAll(new Arrow(), new Arrow(), new Arrow());
		
		
	}
	
	public void render(SpriteBatch batch) {
		background.render(batch);
		for (Arrow arrow: arrows) {
			arrow.render(batch);
		}
	}

}
