package com.zenithlabs.shapeescape.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.zenithlabs.shapeescape.game.Assets;

public class Arrow extends AbstractShape {

	private TextureRegion arrow;
	private Rectangle rectBound;
	
	public Arrow() {
		init();
	}
	
	private void init() {
		dimension.set(3.47f, 1);
		arrow = Assets.getInstance().arrow.arrow;
		scale.set(.5f, .5f); 
		rotation = 90;
		rectBound = new Rectangle();
		//create an alias to the new rectangle?
		//normally alias's are bad, is this a good idea?
		//bound now points to rectBound, no need to update bound anymore, unless we reinsantiate
		bound = rectBound;
	}
	@Override
	public void render(SpriteBatch batch) {
		batch.draw(arrow, position.x, position.y, origin.x, origin.y, dimension.x, dimension.y, scale.x, scale.y, rotation, true);
	}

	@Override
	public void updateBound() {
		 //rectBound.
		 //rectBound.set(this.position.x, this.position.y, this.dimension.x * scale.x, this.dimension.y * scale.y);
		 //90 degree rotation rectbound
		rectBound.set(this.position.x - (this.dimension.y * this.scale.y),
				this.position.y, this.dimension.y * this.scale.y, 
				this.dimension.x * this.scale.x);
		//dont need this cause alias
		 //bound = rectBound;
	}
	
	public void update(float deltaTime) {
		updateBound();
	}

}
