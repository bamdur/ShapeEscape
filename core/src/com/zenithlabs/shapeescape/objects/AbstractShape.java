package com.zenithlabs.shapeescape.objects;

import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.math.Vector2;

public abstract class AbstractShape extends AbstractGameObject {

	public Vector2 velocity;
	
	public Shape2D bound;
	
	public abstract void updateBound();
	
	@Override
	public void update(float deltaTime) {
		this.updateBound();
	}
}
