package dsz;

import org.jsfml.graphics.*;
import org.jsfml.window.Keyboard;
import org.jsfml.window.Keyboard.Key;

public class PlayerEntity extends Entity {
	
	int xVol = 0, yVol = 0;
	int currentFrame = 0, currentSet = 0;
	int speed = 4;
	char direction = 'D';
	boolean animate = false;
	
	Sprite playerSprite = new Sprite();
	
	public PlayerEntity(ConstTexture master){
		playerSprite.setTexture(master);
		playerSprite.scale(2,2);
		
		
		//Set inherited variables
		type = "Mob";
		x = 0;
		y = 0;
		drawable = true;
		collides = true;
		readyToUpdate = true;
		
		playerSprite.setTextureRect(new IntRect(currentFrame*16,currentSet*16,16,16));
	}

	@Override
	void update(int framecount) {
		Boolean keyUp = Keyboard.isKeyPressed(Key.UP), keyDown = Keyboard.isKeyPressed(Key.DOWN),
				keyLeft = Keyboard.isKeyPressed(Key.LEFT), keyRight = Keyboard.isKeyPressed(Key.RIGHT);
		
		//Check if any of the direction keys were pressed
		if( keyUp || keyDown || keyRight || keyLeft ){
			// Check if the up or down key is down, and update accordingly
			if(keyUp || keyDown){
				if(keyUp){
					yVol = -speed;
					animate = true;
					direction = 'U';
				} else {
					yVol = speed;
					animate = true;
					direction = 'D';
				}
			} else {
				yVol = 0;
			}
			// Check if right or left key is down, and update accordingly
			if(keyRight || keyLeft){
				if(keyRight){
					xVol = speed;
					animate = true;
					direction = 'R';
				} else {
					xVol = -speed;
					animate = true;
					direction = 'L';
				}
			} else {
				xVol = 0;
			}
		} else{
			xVol = 0;
			yVol = 0;
			animate = false;
		}
		if(Math.abs(xVol) > 0 && Math.abs(yVol) > 0){
			xVol = xVol/2;
			yVol = yVol/2;
		}
		
		// Set to the right set of textures for the direction
		switch(direction){
		case 'D':
			currentSet = 0;
			break;
		case 'U':
			currentSet = 2;
			break;
		case 'L':
			currentSet = 1;
			break;
		case 'R':
			currentSet = 3;
			break;
		}
		
		//Animate if required and do it and a proper speed
		if(animate){
			if(framecount%5 == 0){
				if(currentFrame <= 2){
					currentFrame++;
				} else currentFrame = 0;
			}
		} else currentFrame = 0;
		
	}

	@Override
	void onCollision() {
		// TODO Auto-generated method stub
		
	}

	@Override
	Sprite draw() {
		playerSprite.setTextureRect(new IntRect(currentFrame*16,currentSet*16,16,16));
		playerSprite.move(xVol, yVol);
		return playerSprite;
	}

}
