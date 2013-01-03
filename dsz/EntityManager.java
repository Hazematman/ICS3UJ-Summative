package dsz;

import java.util.ArrayList;
import org.jsfml.graphics.*;

public class EntityManager {
	ArrayList<Entity> entityList = new ArrayList<Entity>();
	
	ConstTexture drawEntities(){
		RenderTexture screen = new RenderTexture();
		screen.create(DSZ.width, DSZ.height);
		for(int i=0;i<entityList.size();i++){
			if(entityList.get(i).drawable){
				screen.draw(entityList.get(i).draw());
			}
		}
		
		return screen.getTexture();
	}
}
