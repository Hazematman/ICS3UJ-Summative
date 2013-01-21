package dsz;

import org.jsfml.graphics.ConstTexture;
import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.IntRect;
import org.jsfml.graphics.Sprite;

public class ZombieEntity extends Entity{
	
	int xVol = 0, yVol = 0;
	int currentFrame = 0, currentSet = 0;
	int speed = 4;
	char direction = 'D';
	Entity target;
	
	Sprite zombieSprite = new Sprite();
	
	public ZombieEntity(ConstTexture master, Entity target){
		zombieSprite.setTexture(master);
		zombieSprite.scale(2,2);
		this.target = target;
		
		//Set inherited variables
		type = "Zombie";
		x = DSZ.width/2;
		y = DSZ.height/2;
		drawable = true;
		collides = true;
		readyToUpdate = true;
		collisionBox = new FloatRect[1];
		
		zombieSprite.setPosition(x, y);
		zombieSprite.setTextureRect(new IntRect(currentFrame*16,currentSet*16,16,16));
		
	}

	@Override
	void update(int framecount) {
		if(target.x > x){
			xVol = 2;
			direction = 'R';
		} else if(target.x < x){
			xVol = -2;
			direction = 'L';
		} else {
			xVol = 0;
		}
		if(target.y > y){
			yVol = 2;
			direction = 'D';
		} else if(target.y < y){
			yVol = -2;
			direction = 'U';
		} else {
			yVol = 0;
		}
		
		if(Math.abs(xVol) > 0 && Math.abs(yVol) > 0){
			xVol = xVol/2;
			yVol = yVol/2;
		}
		
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
		
		if(framecount%5 == 0){
			if(currentFrame < 2){
				currentFrame++;
			} else currentFrame = 0;
		}
		
		collisionBox[0] = zombieSprite.getGlobalBounds();
		
	}

	@Override
	void onCollision(Entity object, FloatRect objectCollsionBox) {
		if(object.type.equals("Map")){
			MapEntity map = (MapEntity)object;
			int px = (int)Math.floor((zombieSprite.getGlobalBounds().left+16)/32);
			int py = (int)Math.floor(((zombieSprite.getGlobalBounds().top-120)+16)/32);
			if(map.getCollisionID(px, py-1) >= 2){
				zombieSprite.move(0,4);
			}
			if(map.getCollisionID(px, py+1) >= 2){
				zombieSprite.move(0,-4);
			}
			if(map.getCollisionID(px-1, py) >= 2){
				zombieSprite.move(4,0);
			}
			if(map.getCollisionID(px+1, py) >= 2){
				zombieSprite.move(-4, 0);
			}
		}
		
	}

	@Override
	Sprite draw() {
		zombieSprite.setTextureRect(new IntRect(currentFrame*16,currentSet*16,16,16));
		zombieSprite.move(xVol, yVol);
		x = (int) zombieSprite.getGlobalBounds().left;
		y = (int) zombieSprite.getGlobalBounds().top;
		return zombieSprite;
	}

}
