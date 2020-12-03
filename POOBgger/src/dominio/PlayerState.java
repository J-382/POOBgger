package dominio;

import java.awt.Rectangle;

/**
 * State Pattern implementation for Player's states
 * @author Angie Medina - Jose Perez
 * @version 1.0
 */
public abstract class PlayerState {
	Player player;
	
	/**
	 * PlayerState class constructor
	 * @param player context
	 */
	PlayerState(Player player){
		this.player = player;
	}
	/**
	 * Returns the player's bounds for his current state
	 * @return the player's bounds for his current state
	 */
	public abstract Rectangle getBounds();
	
	/**
	 * Player behavior at his dead moment
	 */
	public abstract void decreasePlayerlives();
}
