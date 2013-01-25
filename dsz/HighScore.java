package dsz;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Text;
import org.jsfml.system.Clock;
import org.jsfml.window.Keyboard;
import org.jsfml.window.Keyboard.Key;

public class HighScore {
	
	Text scoreText = new Text();
	String[] initials = new String[10];
	int[] scores = new int[10];
	int x,y;
	Clock timer = new Clock();
	
	public HighScore(FileReader file){
		String buf = "";
		int breakPosition = 0;
		int i = 0;
		try{
		BufferedReader br = new BufferedReader(file);
		while((buf=br.readLine()) != null){
			breakPosition = buf.indexOf("|");
			initials[i] = buf.substring(0, breakPosition);
			scores[i] = Integer.parseInt(buf.substring(breakPosition+1));
			i++;
		}
		br.close();
		file.close();
		} catch(IOException e){
			System.out.println("Can't open file");
			System.exit(0);
		}
		
		scoreText.setFont(DSZ.font);
		//x = DSZ.width/2;
		x = 350;
		y = 120;
		
		timer.restart();
	}
	
	void addHighScore(String initial, int score){
		for(int i=0;i<10;i++){
			if(score > scores[i]){
				int[] newScores = new int[10];
				String[] newIntials = new String[10];
				for(int j=0;j<i;j++){
					newScores[j] = scores[j];
					newIntials[j] = initials[j]; 
				}
				newScores[i] = score;
				newIntials[i] = initial;
				for(int j=i+1;j<10;j++){
					newScores[j] = scores[j-1];
					newIntials[j] = initials[j-1];
				}
				
				scores = newScores;
				initials = newIntials;
				
				try {
					saveHighScore(new FileWriter("scores.txt"));
				} catch (IOException e) {
					System.out.println("Can't open file");
					System.exit(0);
				}
				break;
			}
		}
	}
	
	void saveHighScore(FileWriter file) throws IOException{
		for(int i=0;i<10;i++){
			file.write(initials[i]+"|"+scores[i]+"\n");
		}
		file.close();
	}
	
	void update(){
		if(Keyboard.isKeyPressed(Key.SPACE) && timer.getElapsedTime().asMilliseconds() > 300){
			DSZ.state = 0;
		}
	}
	
	void draw(RenderWindow screen){
		scoreText.setPosition(x,y);
		scoreText.setString("HIGH SCORES!");
		screen.draw(scoreText);
		scoreText.move(0, 60);
		for(int i=0;i<10;i++){
			scoreText.setString(initials[i]+"\t"+scores[i]);
			screen.draw(scoreText);
			scoreText.move(0, 30);
		}
		scoreText.move(0, 30);
		scoreText.setString("Press space to exit");
		screen.draw(scoreText);
		
	}

}
