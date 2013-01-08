package dsz;

import org.jsfml.graphics.*;

public class PlayerEntity extends Entity {
	
	int xVol = 0, yVol = 0;
	int currentFrame = 0, currentSet = 0;
	char direction = 'D';
	boolean animate = false;
	
	Sprite playerSprite = new Sprite();
	
	public PlayerEntity(ConstTexture master){
		playerSprite.setTexture(master);
		
		
		//Set inherited variables
		type = "Mob";
		x = 0;
		y = 0;
		drawable = true;
		collides = true;
		readyToUpdate = true;
	}

	@Override
	void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	void onCollision() {
		// TODO Auto-generated method stub
		
	}

	@Override
	Sprite draw() {
		return playerSprite;
	}

}
