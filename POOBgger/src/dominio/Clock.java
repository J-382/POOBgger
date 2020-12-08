package dominio;

import java.awt.Rectangle;
import java.io.Serializable;

/**
 * Pogger's player's clock implementation
 * @author Angie Medina  - Jose Perez
 * @version 1.0
 */
public class Clock implements Serializable{
	private Rectangle clock;
	private int timeLimit = 150;
	private final int height = 15;
	
	/**
	 * Clock Class constructor
	 */
	public Clock() {
		clock = new Rectangle(0, 0, 0, height);
	}
	
	/**
	 * Restores the clock size
	 */
	public void restoreClock() {
		clock = new Rectangle(0, 0, 0, height);
	}
	
	/**
	 * Reduce the clock size and returns if the clock have done a cycle
	 * @return if the clock have done a cycle
	 */
	public boolean updateClock() {
		boolean done = false;
		clock = new Rectangle(0, 0, clock.width + 1, clock.height);
		if (clock.width ==  timeLimit) done = true;
		return done;
	}
	
	/**
	 * Returns a clock's rectangle representation
	 * @return a clock's rectangle representation
	 */
	public Rectangle getClock() {
		return clock;
	}
	
	/**
	 * Calculates the clock's seconds left
	 */
	public int secondsLeft() {
		return ((int)clock.getWidth() / (timeLimit/30));
	}

}
