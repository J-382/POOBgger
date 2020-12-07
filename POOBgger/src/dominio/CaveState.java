package dominio;

import java.awt.Rectangle;
import java.io.Serializable;

public abstract class CaveState implements Serializable{
	protected Cave cave;
	protected String sprite;
	
	/**
	 * CaveState class Constructor
	 * @param cave context
	 */
	public CaveState(Cave cave, String sprite) {
		this.cave = cave;
		this.sprite = sprite;
	}
	
	public abstract boolean isOccupied();
	/**
	 * Return the current sprite for the cave state
	 */
	public String getSprite() {
		return sprite;
	}
	
	/**
	 * Returns the cave's bounds for it's current state 
	 * @return the cave's bounds for it's current state
	 */
	public abstract Rectangle getBounds();
	
}
