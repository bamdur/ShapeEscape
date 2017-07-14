package com.zenithlabs.shapeescape.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.zenithlabs.shapeescape.game.Assets;
import com.zenithlabs.shapeescape.objects.Arrow;

public class MenuArrowRain {
	private static final String TAG = MenuArrowRain.class.getName();
	
	private Array<Arrow> arrows;
	private long timeSinceLastArrow = 0;
	private long currentTime = 0;
	private float timeInterval = MathUtils.random(400, 700);

	public MenuArrowRain() {
		init();
	}
	
	private void init() {
		arrows = new Array<Arrow>();
		for (int i = 0; i < 1; i++) {
			generateArrow();
		}
		currentTime = TimeUtils.millis();

		
	}
	
	public void render(SpriteBatch batch) {
		for (Arrow arrow: arrows) {
			arrow.render(batch);
		}
		
			
	}
	
	

	public void update(float deltaTime) {
		timeSinceLastArrow = TimeUtils.millis() - currentTime;
		//Gdx.app.log("timeSinceLastArrow", ": " + timeSinceLastArrow);
		if (timeSinceLastArrow > timeInterval) {
			generateArrow();
			currentTime = TimeUtils.millis();
			timeInterval = MathUtils.random(400, 700);
		}
		for (Arrow arrow: arrows) {
			arrow.position.set(arrow.position.x, arrow.position.y - deltaTime * 5);
			if (arrow.position.y < -7) {
				arrows.removeValue(arrow, true);
			}
		}
	}
	
	private void generateArrow() {
		Arrow arrow = new Arrow();
		arrow.position.set(MathUtils.random(-2.8f, 3.2f) , 5);
		arrows.add(arrow);
	}
	
}
