package dsz;

import java.util.ArrayList;
import org.jsfml.graphics.*;

/**
 * Class that manages all the entities that it currently contains.
 * This includes updating them, handling collisions and drawing them to the screen.
 */
public class EntityManager {
	ArrayList<Entity> entityList = new ArrayList<Entity>();
	
	/**
	 * Calls update() from every element in the entityList that is ready to update.
	 */
	void updateEntities(){
		for(int i=0;i<entityList.size();i++){
			if(entityList.get(i).readyToUpdate){
				entityList.get(i).update();
			}
		}
	}
	
	/**
	 * Calls draw() from every element in the entityList that is drawable.
	 * @param screen The RenderWindow to draw the elements to
	 */
	void drawEntities(RenderWindow screen){
		for(int i=0;i<entityList.size();i++){
			if(entityList.get(i).drawable){
				screen.draw(entityList.get(i).draw());
			}
		}
	}
}
