package com.zenithlabs.shapeescape.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.zenithlabs.shapeescape.game.Assets;

public class Background extends AbstractGameObject {
	private TextureRegion background;
	
	public Background() {
		init();
	}
	
	private void init() {
		background = Assets.getInstance().background.background;
		dimension.set(10, 10);
		origin.set(dimension.x/2, dimension.y/2);
		position.set(-origin.x, -origin.y);
	}
	@Override
	public void render(SpriteBatch batch) {
		batch.draw(background, position.x, position.y, origin.x, origin.y, dimension.x, dimension.y, scale.x, scale.y, rotation, false);
	}

}
