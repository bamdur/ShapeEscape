package com.zenithlabs.shapeescape.utils;

import com.badlogic.gdx.graphics.Color;

public enum ShapeSkin {
	BLACK("Black", 0.0f, 0.0f, 0.0f),
	WHITE("White", 1.0f, 1.0f, 1.0f);
	
	private String name;
	private Color color = new Color();
	
	private ShapeSkin (String name, float r, float g, float b) {
		this.name = name;
		color.set(r, g, b, 1.0f);
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	public Color getColor() {
		return color;
	}
}
