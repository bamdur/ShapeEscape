package com.zenithlabs.shapeescape.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.zenithlabs.shapeescape.menu.MenuScreenRenderer;
import com.zenithlabs.shapeescape.utils.Constants;

public class ShopScreen extends AbstractGameScreen {
	
	private static final String TAG = ShopScreen.class.getName();

	
	private MenuScreenRenderer renderer;
	
	private Stage stage;
	private Skin skinShapeEscape;
	private Image imgBackground;
	
	public ShopScreen(Game game, MenuScreenRenderer renderer) {
		super(game);
		this.renderer = renderer;
		init();
	}
	
	private void init() {
		
	}

	@Override
	public void render(float deltaTime) {
		renderer.update(deltaTime);
		Gdx.gl.glClearColor(0f, 0f, 1f, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		stage.act(deltaTime);
		stage.draw();
	
		renderer.render();
	}
	
	
	private void rebuildStage() {
		skinShapeEscape = new Skin(Gdx.files.internal(Constants.SKIN_MENU_UI)
				, new TextureAtlas(Constants.TEXTURE_ATLAS_MENU));
		
		//build layers
		Table layerBackground = buildBackgroundLayer();
		//assemble stage
		stage.clear();
		Stack stack = new Stack();
		stage.addActor(stack);
		stack.setFillParent(true);
		stack.setSize(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
		stack.add(layerBackground);
	}
	
	private Table buildBackgroundLayer() {
		Table layer = new Table();
		
		layer.bottom();
		imgBackground = new Image(skinShapeEscape, "background");
		imgBackground.setFillParent(true);
		
		layer.add(imgBackground);
		return layer;
	}
	
	@Override
	public void resize(int width, int height) {
		renderer.resize(width, height);
		
	}

	@Override
	public void show() {
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		rebuildStage();
		Gdx.app.log(TAG, "Stage created");
	}

	@Override
	public void hide() {
		stage.dispose();
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}
	

}
