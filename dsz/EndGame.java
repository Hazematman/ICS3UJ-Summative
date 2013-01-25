package dsz;

import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Text;
import org.jsfml.graphics.TextStyle;
import org.jsfml.system.Clock;
import org.jsfml.window.Keyboard;
import org.jsfml.window.Keyboard.Key;

public class EndGame {
	
	Text endGameMessage = new Text();
	int totalScore;
	char[] intials = new char[3];
	int currentIntial = 0;
	Clock timer = new Clock();

	public EndGame(){
		endGameMessage.setFont(DSZ.font);
		endGameMessage.setPosition(DSZ.width/3, DSZ.height/3);
		endGameMessage.setStyle(TextStyle.BOLD);
		
		DSZ.gameTimer.start();
		timer.restart();
		intials[0] = 65;
		intials[1] = 65;
		intials[2] = 65;
		
	}
	
	void update(){
		totalScore = (int) ((DSZ.kills/(DSZ.gameTimer.getElapsedSeconds()/100))*100);
		endGameMessage.setString("CONGRADULATIONS YOU WON!"+"\n\n\tKills: "+DSZ.kills+"\n\n\tTime: "+(int)DSZ.gameTimer.getElapsedSeconds()+"\n\n\tScore: "+totalScore+
				"\n\n\tIntials: "+intials[0]+intials[1]+intials[2]+"\n\nPress space bar to continue");
		if(Keyboard.isKeyPressed(Key.SPACE) && timer.getElapsedTime().asMilliseconds() >= 300){
			DSZ.state = 4;
			DSZ.kills = 0;
			DSZ.gameTimer.started = false;
			DSZ.highScore.addHighScore(""+intials[0]+intials[1]+intials[2], totalScore); //I have to add the "" or it complains that its not a string
		}
		if(Keyboard.isKeyPressed(Key.UP) && timer.getElapsedTime().asMilliseconds() >= 300){
			intials[currentIntial]++;
			timer.restart();
		}
		if(Keyboard.isKeyPressed(Key.DOWN) && timer.getElapsedTime().asMilliseconds() >= 300){
			intials[currentIntial]--;
			timer.restart();
		}
		if(Keyboard.isKeyPressed(Key.RIGHT) && timer.getElapsedTime().asMilliseconds() >= 300){
			currentIntial++;
			timer.restart();
		}
		if(Keyboard.isKeyPressed(Key.LEFT) && timer.getElapsedTime().asMilliseconds() >= 300){
			currentIntial--;
			timer.restart();
		}
		
		if(currentIntial > 2){
			currentIntial = 0;
		} else if(currentIntial < 0){
			currentIntial = 2;
		}
		
		if(intials[currentIntial] < 65){
			intials[currentIntial] = 90;
		} else if(intials[currentIntial] > 90){
			intials[currentIntial] = 65;
		}
	}
	
	void draw(RenderWindow screen){
		screen.draw(endGameMessage);
	}
}
