package dsz;

import org.jsfml.graphics.*;
import org.jsfml.system.Clock;
import org.jsfml.window.Keyboard;
import org.jsfml.window.Keyboard.Key;

public class PlayerEntity extends Entity {
	
	int xVol = 0, yVol = 0;
	int currentFrame = 0, currentSet = 0;
	int speed = 4;
	char direction = 'D';
	int lastdir;
	boolean animate = false;
	boolean attacking = false;
	boolean readyToAttack = true;
	
	Sprite playerSprite = new Sprite();
	SwordEntity sword;
	Clock swordTimer = new Clock();
	
	public PlayerEntity(ConstTexture master){
		super();
		playerSprite.setTexture(master);
		playerSprite.scale(2,2);
		
		//Set inherited variables
		type = "Player";
		x = DSZ.width/2;
		y = DSZ.height/2;
		drawable = true;
		collides = true;
		readyToUpdate = true;
		collisionBox = new FloatRect[1];
		
		playerSprite.setPosition(x, y);
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
		
		//check for sword button
		if(Keyboard.isKeyPressed(Keyboard.Key.SPACE) && !attacking && readyToAttack){
			sword = new SwordEntity(DSZ.swordTexture,this);
			DSZ.entityManager.entityList.add(sword);
			attacking = true;
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
		
		//Animate if required and do it at a proper speed
		if(animate){
			if(framecount%5 == 0){
				if(currentFrame < 2){
					currentFrame++;
				} else currentFrame = 0;
			}
		} else currentFrame = 0;
		
		if(attacking){
			currentFrame = 3;
			currentSet = lastdir;
			if(sword.dead){
				DSZ.entityManager.removeType("Sword");
				readyToAttack = false;
				attacking = false;
				swordTimer.restart();
			}
		}
		
		if(!readyToAttack && swordTimer.getElapsedTime().asMilliseconds() >= 200){
			readyToAttack = true;
		}
		
		lastdir = currentSet;
		collisionBox[0] = playerSprite.getGlobalBounds();
	}

	@Override
	void onCollision(Entity object,FloatRect objectCollisionBox) {
		
		if(object.type.equals("Map")){
			int collisionID = 0;
			MapEntity map = (MapEntity)object;
			int px = (int)Math.floor((playerSprite.getGlobalBounds().left+16)/32);
			int py = (int)Math.floor(((playerSprite.getGlobalBounds().top-120)+16)/32);
			if(map.getCollisionID(px, py-1) >= 2){
				collisionID = map.getCollisionID(px, py-1);
				playerSprite.move(0,4);
			}
			if(map.getCollisionID(px, py+1) >= 2){
				collisionID = map.getCollisionID(px, py+1);
				playerSprite.move(0,-4);
			}
			if(map.getCollisionID(px-1, py) >= 2){
				collisionID = map.getCollisionID(px-1, py);
				playerSprite.move(4,0);
			}
			if(map.getCollisionID(px+1, py) >= 2){
				collisionID = map.getCollisionID(px+1, py);
				playerSprite.move(-4, 0);
			}
			
			//Check if the collision id was something more than a wall
			switch(collisionID){
			//check for map up change
			case 3:
				DSZ.worldSpawn.currentY--;
				DSZ.worldSpawn.updatePosition();
				playerSprite.setPosition(playerSprite.getGlobalBounds().left,((DSZ.tileHeight-2)*32)+120);
				break;
			//check for map right change
			case 4:
				DSZ.worldSpawn.currentX++;
				DSZ.worldSpawn.updatePosition();
				playerSprite.setPosition(1*32, playerSprite.getGlobalBounds().top);
				break;
			//check for map down change
			case 5:
				DSZ.worldSpawn.currentY++;
				DSZ.worldSpawn.updatePosition();
				playerSprite.setPosition(playerSprite.getGlobalBounds().left,(1*32)+120);
				break;
			//check for map left change
			case 6:
				DSZ.worldSpawn.currentX--;
				DSZ.worldSpawn.updatePosition();
				playerSprite.setPosition((DSZ.tileWidth-2)*32, playerSprite.getGlobalBounds().top);
				break;
			}
			
		}
	}

	@Override
	Sprite draw() {
		playerSprite.setTextureRect(new IntRect(currentFrame*16,currentSet*16,16,16));
		playerSprite.move(xVol, yVol);
		x = (int) playerSprite.getGlobalBounds().left;
		y = (int) playerSprite.getGlobalBounds().top;
		return playerSprite;
	}

}
