package com.zenithlabs.shapeescape.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.utils.Disposable;
import com.zenithlabs.shapeescape.objects.AbstractShape;

/**
 * Class to render the bounds of shapes to get a visual look on collision.
 * @author Brandon Amdur
 */
public class BoundRenderer implements Disposable {

	private ShapeRenderer shapeRenderer;
	private WorldController worldController;
	
	private Shape2D bound;

	public BoundRenderer(WorldController controller) {
		this.worldController = controller;
		init();
	}
	
	private void init() {
		shapeRenderer = new ShapeRenderer();
	}
	
	private void renderBounds() {
		for (AbstractShape object: worldController.shapes) {
			if (object.bound.getClass().equals(Rectangle.class)) {
				Rectangle rectBound =  (Rectangle) object.bound;
				shapeRenderer.rect(rectBound.x, rectBound.y, rectBound.width, rectBound.height);
			} else if (object.bound.getClass().equals(Circle.class)) {
				Circle circleBound = (Circle) object.bound;
				shapeRenderer.circle(circleBound.x, circleBound.y, circleBound.radius);
			}
		
		}
	}
	
	public void render(OrthographicCamera camera) {
		camera.update();
		shapeRenderer.setProjectionMatrix(camera.combined);
		
		shapeRenderer.begin(ShapeType.Line);
		shapeRenderer.setColor(Color.RED);
		
		renderBounds();
		
		shapeRenderer.end();
	}

	
	@Override
	public void dispose() {
		shapeRenderer.dispose();
	}
	
}
