/**
 * 
 */
package com.zenithlabs.shapeescape.game;

import com.badlogic.gdx.utils.Disposable;

/**
 * @author Brandon Amdur
 * 
 * Interface for classes that provide rendering functionality
 *
 */
public interface Renderer extends Disposable {
	
	public void render();
	
	public void resize(int width, int height);

}
