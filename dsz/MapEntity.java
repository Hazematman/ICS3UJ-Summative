package dsz;

import java.util.ArrayList;
import org.jsfml.graphics.*;

/**
 * An entity that handles and controls a map and all its contents.
 */
public class MapEntity extends Entity {
	
	TextureArray textures;
	Map map;
	Sprite spriteMap = new Sprite();
	
	/**
	 * Constructs a MapEntity uses texs as its textures.
	 * @param texs The TextureArray that the map will use for its textures.
	 */
	public MapEntity(TextureArray texs){
		textures = texs;
		map = new Map(textures);
		
		//Set inherited variables
		type = "Map";
		x=0;
		y=120;
		
		spriteMap.setPosition(x, y);
		spriteMap.scale(2,2);
		
		drawable = true;
		collides = true;
		readyToUpdate = true;
	}
	
	int getCollisionID(int x, int y){
		return map.colMap.get((x*map.YSize)+y);
	}

	/**
	 * Sets the spriteMap to the current map data.
	 *  map.loadMap or map.loadMapFile should be called before map is updated.
	 */
	@Override
	void update(int framecount) {
		spriteMap.setTexture(map.drawMap(DSZ.tileWidth*16, DSZ.tileHeight*16));
		readyToUpdate = false;
		ArrayList<FloatRect> collisionBoxList = new ArrayList<FloatRect>();
		int currentSlot = 0;
		for(int x=0;x<map.XSize;x++){
			for(int y=0;y<map.YSize;y++){
				if(map.colMap.get(currentSlot) == 2){
					collisionBoxList.add(new FloatRect(x*32+this.x,y*32+this.y,32,32));
				}
				currentSlot++;
			}
		}
		collisionBox = (FloatRect[])collisionBoxList.toArray(new FloatRect[collisionBoxList.size()]);
	}

	@Override
	void onCollision(Entity object,FloatRect objectCollisionBox) {
		return;
	}
	
	/**
	 * Returns the sprite version of the map to draw to the screen.
	 */
	@Override
	Sprite draw() {
		return spriteMap;
	}

}
