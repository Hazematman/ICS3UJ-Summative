package dsz;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import org.jsfml.graphics.*;

/**
 * Class that loads one image and splits it into 16x16 elements that can
 * then be drawn individually.
 */
public class TextureArray {
	Image masterImage = new Image();
	ArrayList<Texture> tiles = new ArrayList<Texture>();
	int numberOfTextures;
	
	/**
	 * Constructs a TextureArray from file that has X x Y elements/tiles
	 * @param file A string that has the file name of the master image
	 * @param numOfX number of x elements/tiles in the image
	 * @param numOfY number of y elements/tiles in the image
	 */
	public TextureArray(String file,int numOfX, int numOfY){
		//Texture currentTile = new Texture();
		try {
			masterImage.loadFromFile(new File(file));
		} catch (IOException e) {
			System.out.println("Could not open file: "+file);
			System.exit(0);
		}
		for(int x=0;x<numOfX;x++){
			for(int y=0;y<numOfY;y++){
				Texture currentTile = new Texture();
				currentTile.loadFromImage(masterImage, new IntRect(x*16,y*16,16,16));
				tiles.add(currentTile);
			}
		}
	}

}
