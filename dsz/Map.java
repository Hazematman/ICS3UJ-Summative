package dsz;

import java.util.ArrayList;
import org.jsfml.graphics.*;

public class Map {
	int XSize, YSize, totalSize;
	TextureArray textures;
	ArrayList<Integer> textureMap = new ArrayList<Integer>();
	ArrayList<Integer> colMap = new ArrayList<Integer>();
	
	public Map(String textureFile,int XSize, int YSize){
		textures = new TextureArray(textureFile,XSize,YSize);
	}
	
	void loadMap(String data){
		String[] dataArray = data.split("\\s+");
		XSize = Integer.parseInt(dataArray[0]);
		YSize = Integer.parseInt(dataArray[1]);
		totalSize = XSize * YSize;
		int size = totalSize+2;
		
		for(int i=0;i<size;i++){
			textureMap.add(Integer.parseInt(dataArray[i]));
			colMap.add(Integer.parseInt(dataArray[size+i]));
		}
	}
	
	ConstTexture drawMap(int width,int height){
		Sprite currentTile = new Sprite();
		RenderTexture screen = new RenderTexture();
		int currentSlot = 0;
		screen.create(width, height);
		screen.clear();
		for(int x=0;x<XSize;x++){
			for(int y=0;y<YSize;y++){
				currentTile.setPosition(x,y);
				currentTile.setTexture(textures.tiles.get(textureMap.get(currentSlot)));
				screen.draw(currentTile);
				currentSlot++;
				
			}
		}
		screen.display();
		
		return screen.getTexture();
	}

}
