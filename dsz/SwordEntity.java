package dsz;

import org.jsfml.graphics.ConstTexture;
import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.Sprite;
import org.jsfml.system.Clock;

public class SwordEntity extends Entity {
	
	PlayerEntity parent;
	Sprite sword = new Sprite();
	char direction;
	boolean dead;
	Clock timer = new Clock();
	
	public SwordEntity(ConstTexture texture, PlayerEntity parentPlayer){
		super();
		parent = parentPlayer;
		sword.setTexture(texture);
		sword.setScale(2,2);
		timer.restart();
		
		//Internal values
		type = "Sword";
		collides = true;
		drawable = true;
		readyToUpdate = true;
		direction = parent.direction;
		sword.setOrigin(8, 8);
		switch(direction){
		case 'D':
			sword.setRotation(180);
			break;
		case 'R':
			sword.setRotation(90);
			break;
		case 'L':
			sword.setRotation(270);
			break;
		}
		collisionBox = new FloatRect[1];
	}

	@Override
	void update(int framecount) {
		x = parent.x+16;
		y = parent.y+16;
		switch(direction){
		case 'U':
			y -= 32;
			break;
		case 'D':
			y += 32;
			break;
		case 'R':
			x += 32;
			break;
		case 'L':
			x -= 32;
			break;
		}
		
		if(timer.getElapsedTime().asMilliseconds() >= 100){
			dead = true;
		}
		
		sword.setPosition(x, y);
		collisionBox[0] = sword.getGlobalBounds();
	}

	@Override
	void onCollision(Entity object, FloatRect objectCollsionBox) {
		return;
	}

	@Override
	Sprite draw() {
		return sword;
	}

}
