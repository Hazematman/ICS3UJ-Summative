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
	void updateEntities(int framecount){
		for(int i=0;i<entityList.size();i++){
			if(entityList.get(i).readyToUpdate){
				entityList.get(i).update(framecount);
				if(entityList.get(i).alive == false){
					removeID((entityList.get(i).ID));
				}
			}
		}
	}
	
	/**
	 * Checks for collision between every entity that has the collide flag on
	 */
	void collideEntities(){
		for(int i=0;i<entityList.size();i++){
			ArrayList<Integer> ignoreList = new ArrayList<Integer>();
			if(entityList.get(i).collides){
				for(int j=i+1;j<entityList.size();j++){
					if(entityList.get(j).collides){
						for(FloatRect f1 : entityList.get(i).collisionBox){
							for(FloatRect f2 : entityList.get(j).collisionBox){
								if(f1.intersection(f2) != null){
									if(ignoreList.contains(j)){
										continue;
									}
									entityList.get(i).onCollision(entityList.get(j),f2);
									entityList.get(j).onCollision(entityList.get(i),f1);
									ignoreList.add(j);
								}
							}
						}
					}
				}
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
	
	void removeType(String type){
		for(int i=0;i<entityList.size();i++){
			if(entityList.get(i).type.equals(type)){
				entityList.remove(i);
				break;
			}
		}
	}
	
	void removeID(int ID){
		for(int i=0;i<entityList.size();i++){
			if(entityList.get(i).ID == ID){
				entityList.remove(i);
				break;
			}
		}
	}
}
