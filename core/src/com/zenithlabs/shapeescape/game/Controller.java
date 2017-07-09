package com.zenithlabs.shapeescape.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface Controller {

	public void update(float deltaTime);
	
	public void render(SpriteBatch batch);
}
