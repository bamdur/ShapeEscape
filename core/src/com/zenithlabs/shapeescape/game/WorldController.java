/**
 * 
 */
package com.zenithlabs.shapeescape.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.zenithlabs.shapeescape.objects.AbstractShape;
import com.zenithlabs.shapeescape.objects.Arrow;
import com.zenithlabs.shapeescape.objects.Background;
import com.zenithlabs.shapeescape.screens.MenuScreen;
import com.zenithlabs.shapeescape.utils.CameraHelper;

/**
 * @author Brandon Amdur
 *
 */
public class WorldController implements Controller {

	private static final String TAG = WorldController.class.getName();

	private Game game;
	
	private WorldInput worldInput;
	
	public CameraHelper cameraHelper;
	
	//alias to worldRenderer camera
	private OrthographicCamera camera;
	
	public AbstractShape currentShape;
	//array containing all active shapes 
	public Array<AbstractShape> shapes;
	
	
	public Background background;
	public Array<Arrow> arrows;
	
	public int score = 0;
	public int coins = 0;
	public float time = 0;
	
	public WorldController(Game game) {
		this.game = game;
		init();
	}
	
	//Public access so worldInput (input controller) can access it to reset the game
	public void init () {
		cameraHelper = new CameraHelper();
		worldInput = new WorldInput(this, cameraHelper);
		initContainers();
		initTestObjects();
	}
	
	@Override
	public void update(float deltaTime) {
		worldInput.update(deltaTime);
		time += 60 * deltaTime;
		//Gdx.app.log(TAG, "" + time);
		if (time > 60) {
			time = 0;
			score += 1 ;
			coins += 1 ;
		}
		updateObjects(deltaTime);
	}
	
	public void render(SpriteBatch batch) {
		background.render(batch);
		for (Arrow arrow: arrows) {
			arrow.render(batch);
		}
	}
	
	private void initContainers() {
		arrows = new Array<Arrow>();
		shapes = new Array<AbstractShape>();
	}
	private void initTestObjects() {
		background = new Background();
		
		arrows.addAll(new Arrow());
		
		shapes.addAll(arrows);
		currentShape = arrows.first();
	}
	
	private void updateObjects(float deltaTime) {
		for (Arrow arrow: arrows) {
			arrow.update(deltaTime);
		}
	}
	
	
	
	public void setCamera(OrthographicCamera camera) {
		this.camera = camera;
	}

	public OrthographicCamera getCamera() {
		return this.camera;
	}
	
	public void backToMenu() {
		game.setScreen(new MenuScreen(game));
	}
}
