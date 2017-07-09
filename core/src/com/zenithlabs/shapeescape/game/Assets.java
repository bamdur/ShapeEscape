package com.zenithlabs.shapeescape.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.utils.Disposable;
import com.zenithlabs.shapeescape.utils.Constants;

public class Assets implements Disposable, AssetErrorListener {

	public static final String TAG = Assets.class.getName();
	private static final Assets instance = new Assets();
	private AssetManager assetManager;
	
	public AssetArrow arrow;
	public AssetCircle circle;
	public AssetSquare square;
	public AssetRectangle rectangle;
	public AssetBackground background;
	public AssetFonts fonts;
	
	
	//private constructor to prevent instantion from other classes
	private Assets() {}
	
	public static Assets getInstance() {
		return instance;
	}
	
	public void init (AssetManager assetManager) {
		this.assetManager = assetManager;
		assetManager.setErrorListener(this);
		assetManager.load(Constants.TEXTURE_ATLAS_OBJECTS, TextureAtlas.class);
		//start loading assets and wait until finished
		assetManager.finishLoading();
		
		Gdx.app.debug(TAG, "# of assets loaded: " + assetManager.getAssetNames().size);
		for (String a: assetManager.getAssetNames()) {
			Gdx.app.debug(TAG, "asset: " + a);
		}
		
		TextureAtlas atlas = assetManager.get(Constants.TEXTURE_ATLAS_OBJECTS);
		
		//enable texture filtering for pixel smoothing (performance condieration?)
		for (Texture t : atlas.getTextures()) {
			t.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		}
		//create game resources
		fonts = new AssetFonts();

		
		background = new AssetBackground(atlas);
		arrow = new AssetArrow(atlas);
		square = new AssetSquare(atlas);
		rectangle = new AssetRectangle(atlas);
		circle = new AssetCircle(atlas);
		
	}
	
	@Override
	public void error(AssetDescriptor asset, Throwable throwable) {
		Gdx.app.error(TAG, "Couldn't load asset '" + asset.fileName + "'", (Exception)throwable);
		
	}

	@Override
	public void dispose() {
		assetManager.dispose();
		fonts.small.dispose();
		fonts.medium.dispose();
		fonts.large.dispose();
	}

	/*
	 * Subclasses for individual assets.
	 */
	public class AssetArrow {
		public final AtlasRegion arrow;
		public AssetArrow (TextureAtlas atlas) {
			arrow = atlas.findRegion("arrow");
		}
	}
	
	public class AssetBackground {
		public final AtlasRegion background;
		public AssetBackground (TextureAtlas atlas) {
			background = atlas.findRegion("background");
		}
	}
	
	public class AssetCircle {
		public final AtlasRegion circle;
		public AssetCircle (TextureAtlas atlas) {
			circle = atlas.findRegion("circle");
		}
	}
	
	public class AssetSquare {
		public final AtlasRegion square;
		public AssetSquare (TextureAtlas atlas) {
			square = atlas.findRegion("square");
		}
	}
	
	public class AssetRectangle {
		public final AtlasRegion rectangle;
		public AssetRectangle (TextureAtlas atlas) {
			rectangle = atlas.findRegion("rectangle");
		}
	}
	
	
	public class AssetFonts {
		public final BitmapFont small;
		public final BitmapFont medium;
		public final BitmapFont large;
		
		public final int SMALL_SIZE = 12;
		public final int MEDIUM_SIZE = 20;
		public final int LARGE_SIZE = 32;
		
		public AssetFonts() {
			FreeTypeFontGenerator gen = new FreeTypeFontGenerator(Gdx.files.internal("fonts/font.ttf"));
			FreeTypeFontParameter param = new FreeTypeFontParameter();	
			//param.color = Color.BLACK;
			//param.minFilter = TextureFilter.Linear;
			//param.magFilter = TextureFilter.Linear;
			param.flip = true;
			param.size = SMALL_SIZE;
			small = gen.generateFont(param);
			param.size = MEDIUM_SIZE;
			medium = gen.generateFont(param);
			param.size = LARGE_SIZE;
			large = gen.generateFont(param);
			
		}
	}
}


