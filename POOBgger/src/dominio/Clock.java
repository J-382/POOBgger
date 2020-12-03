package dominio;

import java.awt.Rectangle;
import java.io.Serializable;

public class Clock implements Serializable{
	private Rectangle clock;
	private int timeLimit = 150;
	private final int height = 15;
	
	public Clock() {
		clock = new Rectangle(0, 0, 0, height);
	}
	
	public void restoreClock() {
		clock = new Rectangle(0, 0, 0, height);
	}
	
	public boolean updateClock() {
		boolean done = false;
		clock = new Rectangle(0, 0, clock.width + 1, clock.height);
		if (clock.width ==  timeLimit) done = true;
		return done;
	}
	
	public Rectangle getClock() {
		return clock;
	}
	
	public int secondsLeft() {
		return ((int)clock.getWidth() / (timeLimit/30));
	}

}
