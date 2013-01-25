package dsz;

import org.jsfml.graphics.ConstTexture;
import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.Sprite;

public class ChestEntity extends Entity{
	
	Sprite chestSprite = new Sprite();
	int contents;
	
	public ChestEntity(ConstTexture img){
		super();
		chestSprite.setTexture(img);
		chestSprite.scale(2, 2);
		chestSprite.setPosition(3*32, (3*32)+120);
		
		type = "Chest";
		collides = true;
		drawable = true;
		readyToUpdate = true;
		alive = true;
		
		contents = DSZ.random.nextInt(2);
		System.out.println(contents);
		
		collisionBox = new FloatRect[1];
	}

	@Override
	void update(int framecount) {
		collisionBox[0] = chestSprite.getGlobalBounds();
	}

	@Override
	void onCollision(Entity object, FloatRect objectCollsionBox) {
		if(object.type.equals("Player")){
			PlayerEntity player = (PlayerEntity) object;
			switch(contents){
			case 0: // Give health or sword if health is full
				if(player.health<10){
					player.health = 10;
				} else {
					player.damageLVL++;
				}
				break;
			case 1: // Give sword or health if sword is max level
				if(player.damageLVL<3){
					player.damageLVL++;
				} else {
					player.health = 10;
				}
				break;
			}
			alive = false;
		}
		
	}

	@Override
	Sprite draw() {
		return chestSprite;
	}

}
