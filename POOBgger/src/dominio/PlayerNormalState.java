package dominio;

import java.awt.Rectangle;

/**
 * Player in normal state implementation 
 * @version 1.0
 * @author Angie Medina - Jose Perez
 */
public class PlayerNormalState extends PlayerState{
	/**
	 * PlayerNormalState class constructor
	 * @param player context
	 */
	public PlayerNormalState(Player player) {
		super(player);
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(player.getX(),player.getY(),player.getWidth(),player.getHeight());
	}

	@Override
	public void decreasePlayerlives() {
	}
	
	@Override
	public String getState() {
		return "";
	}
}
