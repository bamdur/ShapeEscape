/**
 * 
 */
package com.zenithlabs.shapeescape.game;

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

	private WorldInput worldInput;
	
	public CameraHelper cameraHelper;
	
	public Background background;
	public Array<Arrow> arrows;
	
	public int score = 0;
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
