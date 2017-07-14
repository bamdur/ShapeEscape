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
import com.badlogic.gdx.utils.Array;
import com.zenithlabs.shapeescape.menu.MenuScreenRenderer;
import com.zenithlabs.shapeescape.utils.Constants;
import com.zenithlabs.shapeescape.utils.GamePreferences;

public class MenuScreen extends AbstractGameScreen {
	
	private static final String TAG = MenuScreen.class.getName();
	
	private Stage stage;
	private Skin skinShapeEscape;
	private Image imgBackground;
	private Image imgGameName;
	private Button btnMenuPlay;
	private Button btnMenuShop;
	
	private boolean renderDebug = false;
	
	
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
		
		stage.act(deltaTime);
		stage.draw();
		renderer.render();
	}

	private void rebuildStage() {
		skinShapeEscape = new Skin(Gdx.files.internal(Constants.SKIN_MENU_UI)
				, new TextureAtlas(Constants.TEXTURE_ATLAS_MENU));
		
		//build layers
		Array<Table> layers = new Array<Table>();
		Table layerBackground = buildBackgroundLayer();
		Table layerObjects = buildObjectsLayer();
		Table layerControls = buildControlsLayer();
		
		layers.addAll(layerBackground, layerObjects, layerControls);
		for (Table layer: layers) {
			if (renderDebug) {
				layer.debug();
			}
		}
		//assemble stage
		stage.clear();
		Stack stack = new Stack();
		stack.setFillParent(true);
				
		stage.addActor(stack);
		stack.setSize(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
		stack.add(layerBackground);
		stack.add(layerObjects);
		stack.add(layerControls);
		
	}
	
	private Table buildBackgroundLayer() {
		Table layer = new Table();
		
		layer.bottom();
		imgBackground = new Image(skinShapeEscape, "background");
		imgBackground.setFillParent(true);

		layer.add(imgBackground);
		return layer;
	}
	
	private Table buildObjectsLayer() {
		Table layer = new Table();
		layer.top().padTop(60);
		imgGameName = new Image(skinShapeEscape, "ShapeEscape");
		imgGameName.setColor(Color.WHITE);
		layer.add(imgGameName);
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
		btnMenuShop = new Button(skinShapeEscape, "ShopButton");
		layer.add(btnMenuShop).maxSize(150, 75);
		
		btnMenuShop.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				onShopClicked();
			}
			
		});
		
		return layer;
	}
	
	private void onPlayClicked() {
		game.setScreen(new GameScreen(game));
		renderer.dispose();
	}
	
	private void onShopClicked() {
		game.setScreen(new ShopScreen(game, renderer));
	}
	
	private void loadSettings() {
		GamePreferences prefs = GamePreferences.getInstance();
		prefs.load();
	}
	
	private void saveSettings() {
		GamePreferences prefs = GamePreferences.getInstance();
		prefs.save();
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
	}

	@Override
	public void hide() {
		stage.dispose();
	}

	@Override
	public void pause() {
		
		
	}

}
