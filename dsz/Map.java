package dsz;

import java.io.*;
import java.util.ArrayList;
import org.jsfml.graphics.*;

/**
 * Class for handling the in game maps.
 * Used by MapEntity.
 */
public class Map {
	int XSize, YSize, totalSize;
	TextureArray textures;
	ArrayList<Integer> textureMap = new ArrayList<Integer>();
	ArrayList<Integer> colMap = new ArrayList<Integer>();
	
	Sprite currentTile = new Sprite();
	RenderTexture screen = new RenderTexture();
	
	/**
	 * Constructs an empty map, using texts as textures for the map.
	 * Use Map.loadMap or loadMapFile to set map data.
	 * @param texts A TextureArray that the map will use for textures.
	 */
	public Map(TextureArray texts){
		textures = texts;
		
		try {
			screen.create(DSZ.tileWidth*16, DSZ.tileHeight*16);
		} catch (TextureCreationException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Loads a map from a file.
	 * @param file A FileReader object that contains the map data.
	 */
	void loadMapFile(FileReader file){
		String buf, data="";
		try{
		BufferedReader br = new BufferedReader(file);
		while((buf=br.readLine()) != null){
			data+=buf;
		}
		br.close();
		file.close();
		} catch(IOException e){
			System.out.println("Can't open file");
			System.exit(0);
		}
		loadMap(data);
		
	}
	
	/**
	 * Loads map from string of text.
	 * @param data A string that contains all the map data.
	 */
	void loadMap(String data){
		String[] dataArray = data.split("\\s+");
		//get the map size variables which are the first two numbers in the file
		XSize = Integer.parseInt(dataArray[0]);
		YSize = Integer.parseInt(dataArray[1]);
		totalSize = XSize * YSize;
		int size = totalSize;
		
		for(int i=2;(i-2)<size;i++){
			//get the texture data
			textureMap.add(Integer.parseInt(dataArray[i]));
			//get the collision data
			colMap.add(Integer.parseInt(dataArray[(size)+i]));
		}
	}
	
	/**
	 * Takes the map data are returns a Texture that can be drawn from a sprite.
	 * @param width How wide in pixels you want the returned Texture to be.
	 * @param height How high in pixels you want the returned Texture to be.
	 * @return a Texture that is width x height big that can then be drawn to the screen
	 * @throws TextureCreationException 
	 */
	ConstTexture drawMap(){
		int currentSlot = 0;
		screen.clear();
		for(int x=0;x<XSize;x++){
			for(int y=0;y<YSize;y++){
				currentTile.setPosition(x*16,y*16);
				currentTile.setTexture(textures.tiles.get(textureMap.get(currentSlot)));
				screen.draw(currentTile);
				currentSlot++;
				
			}
		}
		screen.display();
		
		return screen.getTexture();
	}
	
	/**
	 * Get the textureID at (x,y)
	 * @param x coordinate
	 * @param y coordinate
	 * @return texture ID at (x,y)
	 */
	int getTextureID(int x, int y){
		return textureMap.get((x*YSize)+y);
	}
	
	/**
	 * Set the texture ID at (x,y) with value
	 * @param x coordinate
	 * @param y coordinate
	 * @param value to set to
	 */
	void setTextureID(int x, int y, int value){
		textureMap.set((x*YSize)+y, value);
	}
	
	/**
	 * Get the collision map ID at (x,y)
	 * @param x coordinate
	 * @param y coordinate
	 * @return collision map ID at (x,y)
	 */
	int getCollisionID(int x, int y){
		return colMap.get((x*YSize)+y);
	}
	
	/**
	 * Set the collision map ID at (x,y) with value
	 * @param x coordinate
	 * @param y coordinate
	 * @param value to set to
	 */
	void setCollisionID(int x, int y, int value){
		colMap.set((x*YSize)+y, value);
	}
	

}
