package dsz;

import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Text;
import org.jsfml.graphics.TextStyle;
import org.jsfml.window.Keyboard;
import org.jsfml.window.Keyboard.Key;

public class DeathScreen {
	
	Text dead = new Text();
	Text cont = new Text();
	
	public DeathScreen(){
		dead.setFont(DSZ.font);
		dead.setString("You are DEAD!");
		dead.setCharacterSize(92);
		dead.setOrigin(dead.getGlobalBounds().width/2,dead.getGlobalBounds().height/2);
		dead.setPosition(DSZ.width/2, DSZ.height/2);
		dead.setStyle(TextStyle.BOLD);
		
		cont.setFont(DSZ.font);
		cont.setString("Press space to continue");
		cont.setPosition(dead.getPosition().x+50, dead.getPosition().y+100);
	}
	
	void update(){
		if(Keyboard.isKeyPressed(Key.SPACE)){
			DSZ.state = 0;
			DSZ.kills = 0;
			DSZ.level = 0;
			DSZ.player.reset();
			DSZ.worldSpawn.reset();
		}
	}
	
	void draw(RenderWindow screen){
		screen.draw(dead);
		screen.draw(cont);
	}

}
