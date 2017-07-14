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
import com.badlogic.gdx.utils.TimeUtils;
import com.zenithlabs.shapeescape.objects.AbstractShape;
import com.zenithlabs.shapeescape.objects.Arrow;
import com.zenithlabs.shapeescape.objects.Background;
import com.zenithlabs.shapeescape.objects.CircleShape;
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
	
	public CircleShape circle;
	public Background background;
	public Array<Arrow> arrows;
	
	//Time based fields
	private long timeSinceLastArrow = 0;
	private long currentTime = 0;
	private float timeInterval = MathUtils.random(400, 700);
	
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
		currentTime = TimeUtils.millis();

		initContainers();
		initObjects();
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
		circle.render(batch);
	}
	
	private void initContainers() {
		arrows = new Array<Arrow>();
		shapes = new Array<AbstractShape>();
	}
	private void initObjects() {
		background = new Background();
		circle = new CircleShape();

		shapes.add(circle);
		//currentShape = arrows.first();
		currentShape = circle;
	}
	
	private void updateObjects(float deltaTime) {
		updateArrows(deltaTime);
		circle.update(deltaTime);
	}
	
	private void updateArrows(float deltaTime) {
		timeSinceLastArrow = TimeUtils.millis() - currentTime;

		if (timeSinceLastArrow > timeInterval) {
			generateArrow();
			currentTime = TimeUtils.millis();
			timeInterval = MathUtils.random(400, 700);
		}

		for (Arrow arrow: arrows) {
			arrow.position.set(arrow.position.x, arrow.position.y - deltaTime * 8);
			if (arrow.position.y < -7) {
				arrows.removeValue(arrow, false);
				arrow.setAlive(false);
			}
			arrow.update(deltaTime);

		}
	
	}
	private void generateArrow() {
		Arrow arrow = new Arrow();
		arrow.position.set(MathUtils.random(-2.8f, 3.2f) , 5);
		arrows.add(arrow);
		shapes.add(arrow);
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
