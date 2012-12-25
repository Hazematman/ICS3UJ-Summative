package dsz;

import org.jsfml.window.*;
import org.jsfml.window.event.*;
import org.jsfml.graphics.*;

public class DSZ {
	
	static String title = "Dungeons, Swords & Zombies";

	public static void main(String[] args) {
		RenderWindow screen = new RenderWindow(new VideoMode(800,600),title);
		screen.setFramerateLimit(60);
		
		while(screen.isOpen()){
			for(Event event : screen.pollEvents()){
				if(event.type == Event.Type.CLOSED){
					screen.close();
				}
				
			}
			
			screen.clear(Color.CYAN);
			
			screen.display();
		}

	}

}
