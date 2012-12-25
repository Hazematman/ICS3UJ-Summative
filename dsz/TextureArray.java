package dsz;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import org.jsfml.graphics.*;

public class TextureArray {
	Image masterImage = new Image();
	ArrayList<Texture> tiles = new ArrayList<Texture>();
	int numberOfTextures;
	
	public TextureArray(String file,int numOfX, int numOfY){
		Texture currentTile = new Texture();
		try {
			masterImage.loadFromFile(new File(file));
		} catch (IOException e) {
			System.out.println("Could not open file: "+file);
			System.exit(0);
		}
		for(int x=0;x<numOfX;x++){
			for(int y=0;y<numOfY;y++){
				currentTile.loadFromImage(masterImage, new IntRect(x*16,y*16,16,16));
				tiles.add(currentTile);
			}
		}
	}

}
