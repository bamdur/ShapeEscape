package com.zenithlabs.shapeescape;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.zenithlabs.shapeescape.game.Assets;
import com.zenithlabs.shapeescape.game.WorldController;
import com.zenithlabs.shapeescape.game.WorldRenderer;
import com.zenithlabs.shapeescape.objects.AbstractGameObject;
import com.zenithlabs.shapeescape.screens.MenuScreen;
import com.zenithlabs.shapeescape.utils.ObjectAccessor;

import aurelienribon.tweenengine.Tween;

public class ShapeEscape extends Game {
	
	private int score;
	private int highScore;

	@Override
	public void create () {
		//Set log level to DEBUG - for publishing switch to NONE
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		//Load assets
		Assets.getInstance().init(new AssetManager());
		Tween.registerAccessor(AbstractGameObject.class, new ObjectAccessor());
		setScreen(new MenuScreen(this));
	}
	
	
	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getHighScore() {
		return highScore;
	}

	public void setHighScore(int highScore) {
		this.highScore = highScore;
	}

}
