package dsz;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

import org.jsfml.window.*;
import org.jsfml.window.event.*;
import org.jsfml.graphics.*;

public class DSZ {
	
	//Some global constants
	public final static String title = "Dungeons, Swords & Zombies";
	public final static int width = 800, height = 600;
	public final static int tileWidth = 25, tileHeight = 15;
	public final static int mapWidth = 6, mapHeight = 4;
	static Random random = new Random();
	
	//Some internal stuff
	static RenderWindow screen = new RenderWindow(new VideoMode(width,height),title);
	static EntityManager entityManager = new EntityManager();
	static TextureArray textures = new TextureArray("tilemap.png",8,2);
	static Texture zombieTexture = new Texture();
	
	//Starting entities
	static MapEntity worldSpawn;
	static PlayerEntity player;
	//static ZombieEntity zomb;
	
	static void addEntities(){
		entityManager.entityList.add(worldSpawn);
		entityManager.entityList.add(player);
		//entityManager.entityList.add(zomb);
	}
	
	static void setup(){
		screen.setFramerateLimit(60);
		
		//load texture for player
		Texture playerTexture = new Texture();
		try{
			playerTexture.loadFromFile(new File("player.png"));
			zombieTexture.loadFromFile(new File("zombie.png"));
		} catch(IOException e){
			e.printStackTrace();
		}
		
		try {
			worldSpawn = new MapEntity(textures,new FileReader("base.map"));
		} catch (FileNotFoundException e1) {
			System.out.println("Can't open base.map");
			System.exit(0);
		}
		player = new PlayerEntity(playerTexture);
		//zomb = new ZombieEntity(zombieTexture,player,100,140);
		
		try {
			worldSpawn.currentMap.loadMapFile(new FileReader("base.map"));
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
