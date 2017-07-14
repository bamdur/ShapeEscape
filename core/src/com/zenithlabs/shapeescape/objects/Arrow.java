package com.zenithlabs.shapeescape.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.zenithlabs.shapeescape.game.Assets;

public class Arrow extends AbstractShape {

	private TextureRegion arrow;
	private Rectangle rectBound;
	
	private boolean alive = false;
	
	public Arrow() {
		init();
	}
	
	private void init() {
		dimension.set(3.47f, 1);
		arrow = Assets.getInstance().arrow.arrow;
		//origin.set(position.x + dimension.x / 2, position.y + dimension.y / 2);
		scale.set(.4f, .4f); 
		rotation = 90;
		rectBound = new Rectangle();
		//create an alias to the new rectangle?
		//normally alias's are bad, is this a good idea?
		//bound now points to rectBound, no need to update bound anymore, unless we reinsantiate
		bound = rectBound;
		alive = true;
	}
	@Override
	public void render(SpriteBatch batch) {
		batch.draw(arrow, position.x, position.y,
				origin.x, origin.y, dimension.x,
				dimension.y, scale.x, scale.y, rotation, true);
		

	}

	@Override
	public void updateBound() {
		//90 degree rotation rectbound
		rectBound.set(this.position.x - (this.dimension.y * this.scale.y),
				this.position.y, this.dimension.y * this.scale.y, 
				this.dimension.x * this.scale.x);
		//dont need this cause alias
		 //bound = rectBound;
	}
	
	public void update(float deltaTime) {
		if (alive) {
			updateBound();
		} else {
			rectBound = null;
		}
	}

	public void setAlive(boolean isAlive) {
		this.alive = isAlive;
	}
}
