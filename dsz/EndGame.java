package dsz;

import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Text;
import org.jsfml.graphics.TextStyle;
import org.jsfml.window.Keyboard;
import org.jsfml.window.Keyboard.Key;

public class EndGame {
	
	Text endGameMessage = new Text();
	int totalScore;

	public EndGame(){
		endGameMessage.setFont(DSZ.font);
		endGameMessage.setPosition(DSZ.width/3, DSZ.height/3);
		endGameMessage.setStyle(TextStyle.BOLD);
		
		DSZ.gameTimer.start();
		
	}
	
	void update(){
		totalScore = (int) ((DSZ.kills/(DSZ.gameTimer.getElapsedSeconds()/100))*100);
		endGameMessage.setString("CONGRADULATIONS YOU WON!"+"\n\n\tKills: "+DSZ.kills+"\n\n\tTime: "+(int)DSZ.gameTimer.getElapsedSeconds()+"\n\n\tScore: "+totalScore+
				"\n\nPress space bar to continue");
		
		if(Keyboard.isKeyPressed(Key.SPACE)){
			DSZ.state = 0;
			DSZ.kills = 0;
			DSZ.gameTimer.started = false;
		}
	}
	
	void draw(RenderWindow screen){
		screen.draw(endGameMessage);
	}
}
