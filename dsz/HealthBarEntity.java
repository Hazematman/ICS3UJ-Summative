package dsz;

import org.jsfml.graphics.ConstTexture;
import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.RenderTexture;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Text;
import org.jsfml.graphics.TextureCreationException;

public class HealthBarEntity extends Entity {
	
	PlayerEntity parent;
	Sprite healthBarSprite = new Sprite();
	
	RenderTexture hearts = new RenderTexture();
	Sprite heartsSprite = new Sprite();
	Text lifeText = new Text();
	
	public HealthBarEntity(ConstTexture heartTexture,PlayerEntity player){
		super();
		parent = player;
		
		type = "HealthBar";
		x = 450;
		y = 20;
		drawable = true;
		readyToUpdate = false;
		collides = false;
		
		collisionBox = new FloatRect[1];
		collisionBox[0] = new FloatRect(0,0,0,0);
		
		healthBarSprite.setScale(2, 2);
		healthBarSprite.setTexture(heartTexture);
		heartsSprite.setPosition(x,y);
		lifeText.setFont(DSZ.font);
		lifeText.setString("-LIFE-");
		lifeText.setPosition(135, 0);
		
		try {
			hearts.create(10*32, 70);
		} catch (TextureCreationException e) {
			e.printStackTrace();
		}
	}

	@Override
	void update(int framecount) {
		return;
	}

	@Override
	void onCollision(Entity object, FloatRect objectCollsionBox) {
		return;
	}

	@Override
	Sprite draw() {
		hearts.clear();
		hearts.draw(lifeText);
		for(int i=0;i<parent.health;i++){
			healthBarSprite.setPosition(i*32,30);
			hearts.draw(healthBarSprite);
		}
		hearts.display();
		heartsSprite.setTexture(hearts.getTexture());
		return heartsSprite;
	}

}
