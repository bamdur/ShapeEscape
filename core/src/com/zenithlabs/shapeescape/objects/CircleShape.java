package com.zenithlabs.shapeescape.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.zenithlabs.shapeescape.game.Assets;

public class CircleShape extends AbstractShape {
	private TextureRegion circle;
	private Circle circBound;
	
	public CircleShape() {
		init();
	}
	
	private void init() {
		dimension.set(.75f, .75f);
		circle = Assets.getInstance().circle.circle;
		//scale.set(.5f, .5f);
		circBound = new Circle();
		
		bound = circBound;
	}
	
	@Override
	public void render(SpriteBatch batch) {
		batch.draw(circle, position.x, position.y,
				origin.x, origin.y, dimension.x, dimension.y, 
				scale.x, scale.y, rotation);
	}
	
	@Override 
	public void updateBound() {
		circBound.set(this.origin.x, this.origin.y, this.scale.x * this.dimension.x/2);
	}
	
	public void update(float deltaTime) {
		updateOrigin();
		updateBound();
		
	}
}
