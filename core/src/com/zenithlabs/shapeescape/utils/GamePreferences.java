package com.zenithlabs.shapeescape.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;

public class GamePreferences {
	public static final String TAG = GamePreferences.class.getName();
	
	private static final GamePreferences instance = new GamePreferences();

	public int highScore;
	public int coins;
	
	public Color color;
	
	private Preferences prefs;
	
	private GamePreferences () {
		prefs = Gdx.app.getPreferences(Constants.PREFERENCES);
	}
	
	public void load() {
		highScore = prefs.getInteger("highScore", 0);
		coins = prefs.getInteger("coins", 0);
		
	}
	
	public void save() {
		prefs.putInteger("coins", coins);
		prefs.putInteger("highScore", highScore);
		prefs.flush();
	}
	
	public static GamePreferences getInstance() {
		return instance;
	}
}
