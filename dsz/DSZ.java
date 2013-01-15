package dsz;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

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
	static MapEntity worldSpawn;
	static PlayerEntity player;
	
	static void addEntities(){
		entityManager.entityList.add(worldSpawn);
		entityManager.entityList.add(player);
	}
	
	static void setup(){
		screen.setFramerateLimit(60);
		
		//load texture for player
		Texture playerTexture = new Texture();
		try{
			playerTexture.loadFromFile(new File("player.png"));
		} catch(IOException e){
			e.printStackTrace();
		}
		
		worldSpawn = new MapEntity(textures);
		player = new PlayerEntity(playerTexture);
		
		try {
			worldSpawn.map.loadMapFile(new FileReader("base.map"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		addEntities();
		
	}

	public static void main(String[] args) {
		int framecount = 0;
		setup();
		
		while(screen.isOpen()){
			for(Event event : screen.pollEvents()){
				if(event.type == Event.Type.CLOSED){
					screen.close();
				}
			}
			
			
			entityManager.updateEntities(framecount);
			entityManager.collideEntities();
			
			screen.clear();
			entityManager.drawEntities(screen);
			screen.display();
			
			if(framecount > 60){
				framecount = 0;
			} else framecount++;
			
		}

	}

}
