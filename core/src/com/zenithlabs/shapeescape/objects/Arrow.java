package com.zenithlabs.shapeescape.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.zenithlabs.shapeescape.game.Assets;

public class Arrow extends AbstractGameObject {

	private TextureRegion arrow;
	
	public Arrow() {
		init();
	}
	
	private void init() {
		dimension.set(3.47f, 1);
		arrow = Assets.getInstance().arrow.arrow;
		scale.set(.5f, .5f); 
		rotation = 90;
	}
	@Override
	public void render(SpriteBatch batch) {
		batch.draw(arrow, position.x, position.y, origin.x, origin.y, dimension.x, dimension.y, scale.x, scale.y, rotation, true);
	}

}
