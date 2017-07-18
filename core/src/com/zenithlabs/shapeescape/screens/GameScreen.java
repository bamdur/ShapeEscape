package com.zenithlabs.shapeescape.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.zenithlabs.shapeescape.game.WorldController;
import com.zenithlabs.shapeescape.game.WorldRenderer;
import com.zenithlabs.shapeescape.utils.Constants;

public class GameScreen extends AbstractGameScreen {
	private static final String TAG = GameScreen.class.getName();

	private WorldController worldController;
	private WorldRenderer worldRenderer;

	private boolean paused;

	private Stage stage;
	private Skin skinShapeEscape;
	private Image imgImpaled;
	private Button btnMenuPlay;
	private Button btnBackToMenu;
	
	private boolean stageCreated;
	
	public GameScreen(Game game) {
		super(game);
		stageCreated = false;
	}

	@Override
	public void render(float deltaTime) {
		if (!paused) {
			worldController.update(deltaTime);
		}
		Gdx.gl.glClearColor(1, 0, 100.0f / 255.0f, 1);
		// clear the screen
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		// Render game world
		
		worldRenderer.render();

		if (worldController.gameOver) {
			if (!stageCreated) {
				rebuildStage();
				stageCreated = true;
				Gdx.input.setInputProcessor(stage);
			}
			stage.act();
			stage.draw();
		}
	}

	private void rebuildStage() {
		skinShapeEscape = new Skin(Gdx.files.internal(Constants.SKIN_MENU_UI)
				, new TextureAtlas(Constants.TEXTURE_ATLAS_MENU));
		
		//build layers
		Table layerObjects = buildObjectsLayer();
		Table layerButtons = buildControlsLayer();
		//assemble stage
		stage.clear();
		Stack stack = new Stack();
		stage.addActor(stack);
		stack.setFillParent(true);
		stack.setSize(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
		stack.add(layerButtons);
		stack.add(layerObjects);
	}
	
	private Table buildObjectsLayer() {
		Table layer = new Table();
		layer.top().padTop(60);
		imgImpaled = new Image(skinShapeEscape, "Impaled");
		imgImpaled.setColor(Color.WHITE);
		layer.add(imgImpaled);
		return layer;
	}

	private Table buildControlsLayer() {
		Table layer = new Table();
		layer.center().bottom().padBottom(60);
		
		//Play Button
		btnMenuPlay = new Button(skinShapeEscape, "PlayButton");

		layer.add(btnMenuPlay).maxSize(150, 75);
		btnMenuPlay.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				onPlayClicked();
			}
		});
		layer.row();

		//Shop Button
		btnBackToMenu = new Button(skinShapeEscape, "ShopButton");
		layer.add(btnBackToMenu).maxSize(150, 75);
		
		btnBackToMenu.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				onMenuClicked();
			}
			
		});
		
		return layer;
	}
	
	private void onPlayClicked() {
		game.setScreen(new GameScreen(game));
	}
	
	private void onMenuClicked() {
		game.setScreen(new MenuScreen(game));
	}
	
	@Override
	public void resize(int width, int height) {
		worldRenderer.resize(width, height);
	}

	@Override
	public void pause() {
		paused = true;
	}

	@Override
	public void resume() {
		super.resume();
		// only called on android
		paused = false;
	}

	@Override
	public void show() {
		worldController = new WorldController(game);
		worldRenderer = new WorldRenderer(worldController);
		stage = new Stage();
		Gdx.input.setCatchBackKey(false);

	}
	
	@Override
	public void hide() {
		worldRenderer.dispose();
		stage.dispose();
		Gdx.input.setCatchBackKey(false);

	}}
