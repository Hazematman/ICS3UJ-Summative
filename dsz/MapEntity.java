package dsz;

import org.jsfml.graphics.Sprite;

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

	/**
	 * Sets the spriteMap to the current map data.
	 *  map.loadMap or map.loadMapFile should be called before map is updated.
	 */
	@Override
	void update() {
		spriteMap.setTexture(map.drawMap(DSZ.tileWidth*16, DSZ.tileHeight*16));
		readyToUpdate = false;
	}

	@Override
	void onCollision() {
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
