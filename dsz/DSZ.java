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
	
	//Global values
	public static int IDcounter = 0;
	public static Random random = new Random();
	public static int level = 0;
	public static int kills = 0;
	public static StopWatch gameTimer = new StopWatch();
	public static int state = 0; // 0=title screen 1=game 2=death 3=gameover 4=highscores
	
	//Some internal stuff
	static RenderWindow screen = new RenderWindow(new VideoMode(width,height),title);
	static EntityManager entityManager = new EntityManager();
	static TextureArray textures = new TextureArray("tilemap.png",8,2);
	static Texture zombieTexture = new Texture();
	static Texture swordTexture = new Texture();
	static Font font = new Font();
	static Background background;
	static DeathScreen death;
	static EndGame end;
	static HighScore highScore;
	
	//Starting entities
	static MapEntity worldSpawn;
	static PlayerEntity player;
	static MiniMap miniMap;
	static HealthBarEntity healthBar;
	
	static void addEntities(){
		entityManager.entityList.add(worldSpawn);
		entityManager.entityList.add(player);
		entityManager.entityList.add(miniMap);
		entityManager.entityList.add(healthBar);
	}
	
	static void setup(){
		screen.setFramerateLimit(60);
		
		//load texture for player
		Texture playerTexture = new Texture();
		Texture mapsquare = new Texture();
		Texture heartTexture = new Texture();
		Texture backgroundTexture = new Texture();
		try{
			playerTexture.loadFromFile(new File("player.png"));
			zombieTexture.loadFromFile(new File("zombie.png"));
			mapsquare.loadFromFile(new File("room.png"));
			swordTexture.loadFromFile(new File("sword.png"));
			heartTexture.loadFromFile(new File("heart.png"));
			backgroundTexture.loadFromFile(new File("background.png"));
			font.loadFromFile(new File("retganon.ttf"));
		} catch(IOException e){
			e.printStackTrace();
		}
		
		player = new PlayerEntity(playerTexture);
		try {
			worldSpawn = new MapEntity(textures,new FileReader("base.map"));
			highScore = new HighScore(new FileReader("scores.txt"));
		} catch (FileNotFoundException e1) {
			System.out.println("Can't open base.map");
			System.exit(0);
		}
		
		miniMap = new MiniMap(worldSpawn, mapsquare);
		healthBar = new HealthBarEntity(heartTexture, player);
		
		background = new Background(backgroundTexture,heartTexture);
		death = new DeathScreen();
		end = new EndGame();
		
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
			if(state == 0){
				background.update();
				
				screen.clear();
				background.draw(screen);
				screen.display();
			}
			else if(state == 1){
				entityManager.updateEntities(framecount);
				entityManager.collideEntities();
				
				screen.clear();
				entityManager.drawEntities(screen);
				screen.display();
				
				if(framecount > 60){
					framecount = 0;
				} else framecount++;
				
			}
			else if(state == 2){
				death.update();
				
				screen.clear();
				death.draw(screen);
				screen.display();
			}
			else if(state == 3){
				end.update();
				
				screen.clear();
				end.draw(screen);
				screen.display();
			}
			else if(state == 4){
				highScore.update();
				
				screen.clear();
				highScore.draw(screen);
				screen.display();
			}
		}

	}

}
