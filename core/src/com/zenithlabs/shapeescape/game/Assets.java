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
	public AssetTriangle triangle;
	public AssetSemiCircle semiCircle;
	
	public AssetBackground background;
	public AssetPlayButton playButton;
	public AssetShopButton shopButton;
	
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
		
		//MAY BE INVALID
		assetManager.load(Constants.TEXTURE_ATLAS_MENU, TextureAtlas.class);
		//start loading assets and wait until finished
		assetManager.finishLoading();
		
		Gdx.app.debug(TAG, "# of assets loaded: " + assetManager.getAssetNames().size);
		for (String a: assetManager.getAssetNames()) {
			Gdx.app.debug(TAG, "asset: " + a);
		}
		
		TextureAtlas gameAtlas = assetManager.get(Constants.TEXTURE_ATLAS_OBJECTS);
		TextureAtlas menuAtlas = assetManager.get(Constants.TEXTURE_ATLAS_MENU);

		
		//enable texture filtering for pixel smoothing (performance condieration?)
		for (Texture t : gameAtlas.getTextures()) {
			t.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		}
		//create game resources
		fonts = new AssetFonts();

		
		background = new AssetBackground(gameAtlas);
		arrow = new AssetArrow(gameAtlas);
		square = new AssetSquare(gameAtlas);
		rectangle = new AssetRectangle(gameAtlas);
		circle = new AssetCircle(gameAtlas);
		semiCircle = new AssetSemiCircle(gameAtlas);
		triangle = new AssetTriangle(gameAtlas);
		
		playButton = new AssetPlayButton(menuAtlas);
		shopButton = new AssetShopButton(menuAtlas);
		
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
	 * 
	 * Game Assets
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
	
	public class AssetTriangle {
		public final AtlasRegion triangle;
		public AssetTriangle (TextureAtlas atlas) {
			triangle = atlas.findRegion("triangle");
		}
	}
	
	public class AssetSemiCircle {
		public final AtlasRegion semiCircle;
		public AssetSemiCircle (TextureAtlas atlas) {
			semiCircle = atlas.findRegion("semicircle");
		}
	}
	
	/*
	 * Menu Assets
	 */
	
	public class AssetPlayButton {
		public final AtlasRegion playButton;
		public AssetPlayButton(TextureAtlas atlas) {
			playButton = atlas.findRegion("playButton");
		}
	}
	
	public class AssetShopButton {
		public final AtlasRegion shopButton;
		public AssetShopButton(TextureAtlas atlas) {
			shopButton = atlas.findRegion("shopButton");
		}
	}
	
	/*
	 * Font Assets
	 */
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


