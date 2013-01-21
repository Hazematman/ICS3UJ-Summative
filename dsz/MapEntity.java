package dsz;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import org.jsfml.graphics.*;

/**
 * An entity that handles and controls a map and all its contents.
 */
public class MapEntity extends Entity {
	
	TextureArray textures;
	Map currentMap;
	int[][] mapListInt = new int[DSZ.mapWidth][DSZ.mapHeight];
	Map[][] mapList = new Map[DSZ.mapWidth][DSZ.mapHeight];;
	Sprite spriteMap = new Sprite();
	String defaultMap = "";
	int currentX, currentY;
	
	
	/**
	 * Constructs a MapEntity uses texs as its textures.
	 * @param texs The TextureArray that the map will use for its textures.
	 */
	public MapEntity(TextureArray texs, FileReader file){
		textures = texs;
		currentMap = new Map(textures);
		//Get default map data
		String buf;
		try{
		BufferedReader br = new BufferedReader(file);
		while((buf=br.readLine()) != null){
			defaultMap+=buf;
		}
		br.close();
		} catch(IOException e){
			System.out.println("Can't open file");
			System.exit(0);
		}
		System.out.println(defaultMap);
		generateLevel();
		
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
	 * Get the textureID of the current map at (x,y)
	 * @param x coordinate
	 * @param y coordinate
	 * @return texture ID at (x,y)
	 */
	int getTextureID(int x, int y){
		return currentMap.textureMap.get((x*currentMap.YSize)+y);
	}
	
	/**
	 * Get the collision map ID of the current map at (x,y)
	 * @param x coordinate
	 * @param y coordinate
	 * @return collision map ID at (x,y)
	 */
	int getCollisionID(int x, int y){
		return currentMap.colMap.get((x*currentMap.YSize)+y);
	}

	/**
	 * Sets the spriteMap to the current map data.
	 *  map.loadMap or map.loadMapFile should be called before map is updated.
	 */
	@Override
	void update(int framecount) {
		try {
			spriteMap.setTexture(currentMap.drawMap(DSZ.tileWidth*16, DSZ.tileHeight*16));
		} catch (TextureCreationException e) {
			e.printStackTrace();
		}
		readyToUpdate = false;
		ArrayList<FloatRect> collisionBoxList = new ArrayList<FloatRect>();
		int currentSlot = 0;
		for(int x=0;x<currentMap.XSize;x++){
			for(int y=0;y<currentMap.YSize;y++){
				if(currentMap.colMap.get(currentSlot) >= 2){
					collisionBoxList.add(new FloatRect(x*32+this.x,y*32+this.y,32,32));
				}
				currentSlot++;
			}
		}
		collisionBox = (FloatRect[])collisionBoxList.toArray(new FloatRect[collisionBoxList.size()]);
	}
	
	
	void generateLevel(){
		Random random = new Random();
		int tries = 0;
		int dir;
		int x = random.nextInt(DSZ.mapWidth), y = random.nextInt(DSZ.mapHeight);
		mapListInt[x][y] = 1;
		//Here it generates the integer list of maps
		while(tries < 100){
			tries++;
			dir = random.nextInt(4);
			switch(dir){
			case 0: //Place map up
				if(y-1 >= 0 && mapListInt[x][y-1] == 0){
					y--;
					mapListInt[x][y] = 1;
				}
				break;
			case 1://place map right
				if(x+1 < DSZ.mapWidth && mapListInt[x+1][y] == 0){
					x++;
					mapListInt[x][y] = 1;
				}
				break;
			case 2: //place map down
				if(y+1 < DSZ.mapHeight && mapListInt[x][y+1] == 0){
					y++;
					mapListInt[x][y] = 1;
				}
				break;
			case 3: //place map left
				if(x-1 >= 0 && mapListInt[x-1][y] == 0){
					x--;
					mapListInt[x][y] = 1;
				}
				break;
			}
		}
		
		//print map to console to make sure its good
		for(int yy=0;yy<4;yy++){
			for(int xx=0;xx<6;xx++){
				System.out.print(mapListInt[xx][yy]+" ");
				}
			System.out.print("\n");
		}
		
		for(x=0;x<DSZ.mapWidth;x++){
			for(y=0;y<DSZ.mapHeight;y++){
				mapList[x][y] = new Map(textures);
				mapList[x][y].loadMap(defaultMap);
				//Check if there is map above
				if(y-1 >= 0 && mapListInt[x][y-1] == 1){
					mapList[x][y].setTextureID(11, 0, 12);
					mapList[x][y].setTextureID(12, 0, 0);
					mapList[x][y].setTextureID(13, 0, 10);
					
					mapList[x][y].setCollisionID(11, 0, 2);
					mapList[x][y].setCollisionID(12, 0, 3);
					mapList[x][y].setCollisionID(13, 0, 2);
				}
				//Check if there is map to the right
				if(x+1 < DSZ.mapWidth && mapListInt[x+1][y] == 1){
					mapList[x][y].setTextureID(DSZ.tileWidth-1, 6, 10);
					mapList[x][y].setTextureID(DSZ.tileWidth-1, 7, 0);
					mapList[x][y].setTextureID(DSZ.tileWidth-1, 8, 14);
					
					mapList[x][y].setCollisionID(DSZ.tileWidth-1, 6, 2);
					mapList[x][y].setCollisionID(DSZ.tileWidth-1, 7, 4);
					mapList[x][y].setCollisionID(DSZ.tileWidth-1, 8, 2);
				}
				//Check if there is map below
				if(y+1 < DSZ.mapHeight && mapListInt[x][y+1] == 1){
					mapList[x][y].setTextureID(11, DSZ.tileHeight-1, 1);
					mapList[x][y].setTextureID(12, DSZ.tileHeight-1, 0);
					mapList[x][y].setTextureID(13, DSZ.tileHeight-1, 14);
					
					mapList[x][y].setCollisionID(11, DSZ.tileHeight-1, 2);
					mapList[x][y].setCollisionID(12, DSZ.tileHeight-1, 5);
					mapList[x][y].setCollisionID(13, DSZ.tileHeight-1, 2);
				}
				//check if there is map to the left
				if(x-1 >= 0 && mapListInt[x-1][y] == 1){
					mapList[x][y].setTextureID(0, 6, 12);
					mapList[x][y].setTextureID(0, 7, 0);
					mapList[x][y].setTextureID(0, 8, 1);
					
					mapList[x][y].setCollisionID(0, 6, 2);
					mapList[x][y].setCollisionID(0, 7, 6);
					mapList[x][y].setCollisionID(0, 8, 2);
				}
			}
		}
		
		currentX = random.nextInt(DSZ.mapWidth);
		currentY = random.nextInt(DSZ.mapHeight);
		while(mapListInt[currentX][currentY] == 0){
			currentX = random.nextInt(DSZ.mapWidth);
			currentY = random.nextInt(DSZ.mapHeight);
		}
		currentMap = mapList[currentX][currentY];
		System.out.println(currentX + " " + currentY);
	}
	
	void updatePosition(){
		System.out.println(currentX + " " + currentY);
		currentMap = mapList[currentX][currentY];
		try {
			spriteMap.setTexture(currentMap.drawMap(DSZ.tileWidth*16, DSZ.tileHeight*16));
		} catch (TextureCreationException e) {
			e.printStackTrace();
		}
		ArrayList<FloatRect> collisionBoxList = new ArrayList<FloatRect>();
		int currentSlot = 0;
		for(int x=0;x<currentMap.XSize;x++){
			for(int y=0;y<currentMap.YSize;y++){
				if(currentMap.colMap.get(currentSlot) >= 2){
					collisionBoxList.add(new FloatRect(x*32+this.x,y*32+this.y,32,32));
				}
				currentSlot++;
			}
		}
		collisionBox = (FloatRect[])collisionBoxList.toArray(new FloatRect[collisionBoxList.size()]);
	}

	/**
	 * Returns nothing because map doesn't do anything on collision
	 */
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
