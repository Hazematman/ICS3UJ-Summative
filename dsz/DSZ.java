package dsz;

import java.io.FileNotFoundException;
import java.io.FileReader;

import org.jsfml.window.*;
import org.jsfml.window.event.*;
import org.jsfml.graphics.*;

public class DSZ {
	
	//Some global constants
	public final static String title = "Dungeons, Swords & Zombies";
	public final static int width = 800, height = 600;
	public final static int tileWidth = 25, tileHeight = 15;
	
	//Some internal stuff
	static RenderWindow screen = new RenderWindow(new VideoMode(width,height),title);
	static EntityManager entityManager = new EntityManager();
	static TextureArray textures = new TextureArray("tilemap.png",8,2);
	
	//Starting entities
	static MapEntity worldSpawn = new MapEntity(textures);
	
	static void addEntities(){
		entityManager.entityList.add(worldSpawn);
	}
	
	static void setup(){
		screen.setFramerateLimit(60);
		
		try {
			worldSpawn.map.loadMapFile(new FileReader("base.map"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		addEntities();
		
	}

	public static void main(String[] args) {
		setup();
		
		while(screen.isOpen()){
			for(Event event : screen.pollEvents()){
				if(event.type == Event.Type.CLOSED){
					screen.close();
				}
			}
			
			entityManager.updateEntities();
			
			screen.clear();
			entityManager.drawEntities(screen);
			screen.display();
		}

	}

}
