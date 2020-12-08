package dominio;

import java.awt.Rectangle;
import java.io.Serializable;

/**
 * State Pattern implementation for Cave's states
 * @author Angie Medina - Jose Perez
 * @version 1.0
 */
public abstract class CaveState implements Serializable{
	protected Cave cave;
	protected String sprite;
	
	/**
	 * CaveState class Constructor
	 * @param cave context
	 * @para sprite, sprote for the cave state
	 */
	public CaveState(Cave cave, String sprite) {
		this.cave = cave;
		this.sprite = sprite;
	}
	
	/**
	 * Returns if the cave is occupied
	 */
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
