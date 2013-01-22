package dsz;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.ConstTexture;
import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.RenderTexture;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.graphics.TextureCreationException;

public class MiniMap extends Entity{
	
	MapEntity map;
	Sprite miniMapSprite = new Sprite();
	Texture mapSquare = new Texture();
	
	RenderTexture minmap = new RenderTexture();
	Sprite currentTile = new Sprite();
	
	public MiniMap(MapEntity map,ConstTexture mapSquare){
		this.map = map;
		this.mapSquare = (Texture) mapSquare;
		
		type = "MiniMap";
		x = 32;
		y = 34;
		drawable = true;
		readyToUpdate = false;
		collides = false;
		
		miniMapSprite.setPosition(x,y);
		currentTile.setTexture(mapSquare);
		try {
			minmap.create(DSZ.mapWidth*32, DSZ.mapHeight*16);
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
		minmap.clear();
		for(int x=0;x<DSZ.mapWidth;x++){
			for(int y=0;y<DSZ.mapHeight;y++){
				if(map.mapListInt[x][y] == 1){
					if(map.currentX == x && map.currentY == y){
						currentTile.setColor(Color.RED);
					}
					currentTile.setPosition(x*32, y*16);
					minmap.draw(currentTile);
					currentTile.setColor(Color.WHITE);
				}
			}
		}
		minmap.display();
		miniMapSprite.setTexture(minmap.getTexture());
		return miniMapSprite;
	}

}
