package com.zenithlabs.shapeescape.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.zenithlabs.shapeescape.menu.MenuScreenRenderer;

public class MenuScreen extends AbstractGameScreen {
	private static final String TAG = MenuScreen.class.getName();
	
	private MenuScreenRenderer renderer;
	//private Background background;
	
	public MenuScreen(Game game) {
		super(game);
		init();
	}
	
	private void init() {
		renderer = new MenuScreenRenderer();
	}

	@Override
	public void render(float deltaTime) {
		renderer.update(deltaTime);
		Gdx.gl.glClearColor(0f, 0f, 1f, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		renderer.render();
		if (Gdx.input.isTouched()) game.setScreen(new GameScreen(game));
	}

	@Override
	public void resize(int width, int height) {
		renderer.resize(width, height);
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
