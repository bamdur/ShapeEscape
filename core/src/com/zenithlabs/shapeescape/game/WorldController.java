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
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.zenithlabs.shapeescape.objects.AbstractShape;
import com.zenithlabs.shapeescape.objects.Arrow;
import com.zenithlabs.shapeescape.objects.Background;
import com.zenithlabs.shapeescape.objects.CircleShape;
import com.zenithlabs.shapeescape.objects.RectangleShape;
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
	
	public RectangleShape rectangle;
	public CircleShape circle;
	public Background background;
	public Array<Arrow> aliveArrows;
	public Array<Arrow> deadArrows;
	
	//Time based fields
	private long timeSinceLastArrow;
	private long currentTime;
	private float timeInterval;
	
	public int score = 0;
	public int coins = 0;
	public float time = 0;
	
	private int moveSpeed;

	public boolean gameOver;
	
	public WorldController(Game game) {
		this.game = game;
		init();
	}
	
	//Public access so worldInput (input controller) can access it to reset the game
	public void init () {
		gameOver = false;
		timeSinceLastArrow = 0;
		currentTime = 0;
		timeInterval = MathUtils.random(400, 700);
		moveSpeed = 8;
		
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
		for (Arrow arrow: aliveArrows) {
			arrow.render(batch);
		}
		//circle.render(batch);
		rectangle.render(batch);
	}
	
	private void initContainers() {
		deadArrows = new Array<Arrow>();
		aliveArrows = new Array<Arrow>();
		shapes = new Array<AbstractShape>();
	}
	private void initObjects() {
		background = new Background();
		circle = new CircleShape();
		rectangle = new RectangleShape();
		shapes.add(rectangle);
		//currentShape = arrows.first();
		currentShape = rectangle;
	}
	
	private void updateObjects(float deltaTime) {
		updateArrows(deltaTime);
		circle.update(deltaTime);
		rectangle.update(deltaTime);
	}
	
	private void updateArrows(float deltaTime) {
		timeSinceLastArrow = TimeUtils.millis() - currentTime;

		if (timeSinceLastArrow > timeInterval) {
			generateArrow();
			currentTime = TimeUtils.millis();
			timeInterval = MathUtils.random(400, 700);
		}

		for (Arrow arrow: aliveArrows) {
			arrow.position.set(arrow.position.x, arrow.position.y - deltaTime * moveSpeed);
			if (arrow.position.y < -7) {
				deadArrows.add(arrow);
				aliveArrows.removeValue(arrow, true);
				arrow.setAlive(false);
			}
			arrow.update(deltaTime);
		}
		if (!gameOver){
			checkCollisionArrowShape();
		}
	
	}
	
	private void checkCollisionArrowShape() {
		for (Arrow arrow: aliveArrows) {
		    Rectangle arrowBound = (Rectangle) arrow.bound;
		    Vector2 arrowPos = new Vector2();
		    arrowBound.getPosition(arrowPos);
		    arrowPos.add(arrowBound.width/2, 0);
			if (currentShape.bound.contains(arrowPos)) {
				Gdx.app.log(TAG, "Collision");
				moveSpeed = 8;
				gameOver = true;
			}
		}
	}
	
	private void generateArrow() {
		Arrow arrow;
		if (deadArrows.size > 0) {
			arrow = deadArrows.first();
			deadArrows.removeValue(arrow, true);
		} else {
			arrow = new Arrow();
		}
		aliveArrows.add(arrow);
		arrow.position.set(MathUtils.random(-2.8f, 3.2f) , 5);
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
