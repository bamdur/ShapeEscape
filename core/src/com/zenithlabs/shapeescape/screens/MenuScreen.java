package com.zenithlabs.shapeescape.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

public class MenuScreen extends AbstractGameScreen {
	private static final String TAG = MenuScreen.class.getName();
	
	//private Background background;
	
	public MenuScreen(Game game) {
		super(game);
	}
	
	private void init() {
		
	}

	@Override
	public void render(float deltaTime) {
		Gdx.gl.glClearColor(0f, 0f, 0f, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		if (Gdx.input.isTouched()) game.setScreen(new GameScreen(game));
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

}
