package dsz;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.ConstTexture;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Text;
import org.jsfml.graphics.TextStyle;
import org.jsfml.system.Clock;
import org.jsfml.window.Keyboard;
import org.jsfml.window.Keyboard.Key;

public class Background {
	
	Sprite backgroundSpirte = new Sprite();
	Sprite selectorSprite = new Sprite();
	Text buttons = new Text();
	int choice = 0;
	Clock timer = new Clock();
	
	public Background(ConstTexture backgroundIMG, ConstTexture selectorTexture){
		backgroundSpirte.setTexture(backgroundIMG);
		
		selectorSprite.setTexture(selectorTexture);
		selectorSprite.setPosition(650-32,480);
		
		buttons.setFont(DSZ.font);
		buttons.setString("Play\nHigh Score\nInstruction\nQuit");
		buttons.setStyle(TextStyle.BOLD);
		buttons.setColor(Color.BLACK);
		buttons.setPosition(650, 470);
		
		timer.restart();
	}
	
	void update(){
		if(timer.getElapsedTime().asMilliseconds() >= 200){
			if(Keyboard.isKeyPressed(Key.UP)){
				if(choice == 0){
					choice = 3;
				} else choice--;
				timer.restart();
			}
			if(Keyboard.isKeyPressed(Key.DOWN)){
				if(choice == 3){
					choice = 0;
				} else choice++;
				timer.restart();
			}
		}
		
		if(Keyboard.isKeyPressed(Key.RETURN)){
			switch(choice){
			case 0:
				DSZ.state = 1;
				break;
			case 3:
				DSZ.screen.close();
				break;
			}
		}
		
		selectorSprite.setPosition(650-32, 480+(choice*28));
	}
	
	void draw(RenderWindow screen){
		screen.draw(backgroundSpirte);
		screen.draw(buttons);
		screen.draw(selectorSprite);
	}

}
