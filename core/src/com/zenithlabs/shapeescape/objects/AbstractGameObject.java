/**
 * 
 */
package com.zenithlabs.shapeescape.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * @author Brandon Amdur
 *
 */
public abstract class AbstractGameObject {
	public Vector2 position;
	public Vector2 dimension;
	public Vector2 origin;
	public Vector2 scale;
	public float rotation;
	
	
	
	public AbstractGameObject() {
		position = new Vector2(0, 0);
		dimension = new Vector2(1, 1);
		origin = new Vector2();
		scale = new Vector2(1, 1);
		rotation = 0;
		
	}

	public void update(float deltaTime) {
		updateOrigin();
	}
	
	public abstract void render (SpriteBatch batch);
	
	public void updateOrigin() {
		this.origin.set(this.position.x + 
				(this.scale.x * (this.dimension.x /2)),
				this.position.y 
				+ (this.scale.y * (this.dimension.y /2)));
	}
}
