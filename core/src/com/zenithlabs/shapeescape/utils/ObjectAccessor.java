package com.zenithlabs.shapeescape.utils;

import com.zenithlabs.shapeescape.objects.AbstractGameObject;

import aurelienribon.tweenengine.TweenAccessor;

public class ObjectAccessor implements TweenAccessor<AbstractGameObject> {
	
	public static final int POSITION_X = 1;
	public static final int POSITION_Y = 2;
	public static final int POSITION_XY = 3;
	public static final int ROTATION = 4;
	public static final int ALPHA = 5;
	

	@Override
	public int getValues(AbstractGameObject target, int tweenType, float[] returnValues) {
		switch (tweenType) {
        case POSITION_X: returnValues[0] = target.position.x; return 1;
        case POSITION_Y: returnValues[0] = target.position.y; return 1;
        case POSITION_XY:
            returnValues[0] = target.position.x;
            returnValues[1] = target.position.y;
            return 2;
        default: assert false; return -1;
    }
	}

	@Override
	public void setValues(AbstractGameObject target, int tweenType, float[] newValues) {

	    switch (tweenType) {
	        case POSITION_X: target.position.set(newValues[0], target.position.y); break;
	        case POSITION_Y: target.position.set(target.position.x, newValues[1]); break;
	        case POSITION_XY:
	        	target.position.set(newValues[0], newValues[1]);
	            break;
	        default: assert false; break;
	    }
	}

}
