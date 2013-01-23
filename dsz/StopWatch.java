package dsz;

import org.jsfml.system.Clock;

public class StopWatch {
	
	boolean amIStopped = true;
	boolean started = false;
	double totalTime = 0;
	Clock timer = new Clock();
	
	void start(){
		if(amIStopped){
			timer.restart();
			amIStopped = false;
		}
	}
	
	void stop(){
		if(!amIStopped){
			totalTime += timer.getElapsedTime().asSeconds();
			amIStopped = true;
		}
	}
	
	double getElapsedSeconds(){
		if(!amIStopped){
			return totalTime + timer.getElapsedTime().asSeconds();
		} else{
			return totalTime;
		}
	}
	
	void reset(){
		amIStopped = false;
		totalTime = 0;
		timer.restart();
	}

}
