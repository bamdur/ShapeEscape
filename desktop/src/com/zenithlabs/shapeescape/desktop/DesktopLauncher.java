package com.zenithlabs.shapeescape.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.zenithlabs.shapeescape.ShapeEscape;
import com.zenithlabs.shapeescape.utils.Constants;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = (int) Constants.WINDOW_WIDTH;
		config.height = (int) Constants.WINDOW_HEIGHT;
		new LwjglApplication(new ShapeEscape(), config);
	}
}
