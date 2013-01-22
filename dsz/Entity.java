package dsz;

import org.jsfml.graphics.*;

/**
 * The base entity class that every entity will be based off of.
 * This should not be used on its own.
 */
public abstract class Entity {
	String type;
	Boolean drawable, collides, readyToUpdate;
	FloatRect[] collisionBox;
	int x,y;
	int ID;
	
	public Entity(){
		ID = DSZ.IDcounter;
		DSZ.IDcounter++;
	}
	
	abstract void update(int framecount);
	abstract void onCollision(Entity object,FloatRect objectCollsionBox);
	abstract Sprite draw();
}
