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
	Text instructions = new Text();
	int choice = 0;
	boolean showInstructions = false;
	Clock timer = new Clock();
	
	final String textInstructions = ""+
			"The objective of the game is to complete the dungeon as fast as\n" +
			"possible while killing the most amount of zombies, but be careful\n" +
			"the zombies are dangerous and will attack you! Use the arrow keys\n" +
			"to move the player and use the space bar to attack with your sword.\n" +
			"Look around for chests, you can get health and a better sword from\n" +
			"them to help you survive!\n" +
			"\n\t\t Press enter to continue";
	
	public Background(ConstTexture backgroundIMG, ConstTexture selectorTexture){
		backgroundSpirte.setTexture(backgroundIMG);
		
		selectorSprite.setTexture(selectorTexture);
		selectorSprite.setPosition(650-32,480);
		
		buttons.setFont(DSZ.font);
		buttons.setString("Play\nHigh Score\nInstruction\nQuit");
		buttons.setStyle(TextStyle.BOLD);
		buttons.setColor(Color.BLACK);
		buttons.setPosition(650, 470);
		
		instructions.setFont(DSZ.font);
		instructions.setString(textInstructions);
		instructions.setPosition(DSZ.width/10, DSZ.height/3);
		
		timer.restart();
	}
	
	void update(){
		if(timer.getElapsedTime().asMilliseconds() >= 200){
			if(!showInstructions){
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
					if(DSZ.gameTimer.started){
						DSZ.gameTimer.start();
					} else{
						DSZ.gameTimer.reset();
						DSZ.gameTimer.started = true;
					}
					break;
				case 1:
					DSZ.state = 4;
					break;
				case 2:
					showInstructions = !showInstructions;
					break;
				case 3:
					DSZ.screen.close();
					break;
				}
				timer.restart();
			}
		}
		
		selectorSprite.setPosition(650-32, 480+(choice*28));
	}
	
	void draw(RenderWindow screen){
		if(!showInstructions){
			screen.draw(backgroundSpirte);
			screen.draw(buttons);
			screen.draw(selectorSprite);
		} else screen.draw(instructions);
	}

}