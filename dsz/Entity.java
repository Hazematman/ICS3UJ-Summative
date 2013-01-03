package dsz;

import org.jsfml.graphics.Sprite;

public abstract class Entity {
	String type;
	Boolean drawable, collides, readyToUpdate;
	int x,y;
	
	abstract void update();
	abstract void onCollision();
	abstract Sprite draw();
}
