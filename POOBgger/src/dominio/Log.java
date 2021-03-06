package dominio;

import java.util.ArrayList;

/**
 * Pretends be a Frogger's log
 * @version 3.0
 * @author Angie Medina - Jose Perez
 */
public class Log extends Carrier {
	/**
	 * Log class constructor
	 * @param x Log's x position
	 * @param y Log's y position
	 * @param speed Log's speed
	 * @param size Log's dimension
	 * @param sprite Log's sprite name
	 */
	public Log(int x, int y, int speed, int size[], String sprite) {
		this.sprite = sprite;
		this.x = x;
		this.y = y;
		this.width = size[0];
		this.height = size[1];
		this.speed = speed;
		this.carried = new ArrayList<Pushable>();
		this.maxCarryNumber = Integer.MAX_VALUE;
		this.isVisible = true;
	}
	
	/**
	 * Returns the current log's width
	 */
	public int getMinWidth() {
		return width;
	}
	
	/**
	 * Returns the current log's height
	 */
	public int getMinHeight() {
		return height;
	}
}
