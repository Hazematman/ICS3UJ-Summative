package dsz;

import java.io.FileNotFoundException;
import java.io.FileReader;

import org.jsfml.window.*;
import org.jsfml.window.event.*;
import org.jsfml.graphics.*;

public class DSZ {
	
	static String title = "Dungeons, Swords & Zombies";
	public static int width = 800, height = 600;

	public static void main(String[] args) {
		RenderWindow screen = new RenderWindow(new VideoMode(width,height),title);
		screen.setFramerateLimit(60);
		
		TextureArray texs = new TextureArray("tiles.png",8,2);
		Map gameMap = new Map(texs);
		try {
			gameMap.loadMapFile(new FileReader("test.map"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		//Setup spriteGameMap with scaling and position
		Sprite spriteGameMap = new Sprite(gameMap.drawMap(400, 240));
		spriteGameMap.scale(2, 2);
		spriteGameMap.setPosition(0, 120);
		
		while(screen.isOpen()){
			for(Event event : screen.pollEvents()){
				if(event.type == Event.Type.CLOSED){
					screen.close();
				}
			}
			
			screen.clear();
			screen.draw(spriteGameMap);
			screen.display();
		}

	}

}
