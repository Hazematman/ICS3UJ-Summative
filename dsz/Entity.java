package dsz;

import org.jsfml.graphics.Sprite;

/**
 * The base entity class that every entity will be based off of.
 * This should not be used on its own.
 */
public abstract class Entity {
	String type;
	Boolean drawable, collides, readyToUpdate;
	int x,y;
	
	abstract void update();
	abstract void onCollision();
	abstract Sprite draw();
}
