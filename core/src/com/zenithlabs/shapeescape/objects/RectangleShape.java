package com.zenithlabs.shapeescape.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.zenithlabs.shapeescape.game.Assets;

public class RectangleShape extends AbstractShape {
	
	private static final String TAG = RectangleShape.class.getName();
	
	private TextureRegion rectangle;
	private Rectangle rectBound;
	
	public RectangleShape() {
		init();
	}
	
	private void init() {
		speed = 1;
		
		dimension.set(1.618f, 1f);
		rectangle = Assets.getInstance().rectangle.rectangle;
		rectBound = new Rectangle();
		scale.set(.75f, .75f);
		updateScaledDimension();
		bound = rectBound;
	}
	
	
	@Override
	public void render(SpriteBatch batch) {
		batch.draw(rectangle, position.x, position.y,
				origin.x, origin.y, dimension.x,
				dimension.y, scale.x, scale.y, rotation, true);
	}

	@Override
	public void updateBound() {
		rectBound.set(position.x, position.y, scaledDimension.x, scaledDimension.y);
	}

}
