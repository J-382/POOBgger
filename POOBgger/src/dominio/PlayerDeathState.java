package dominio;

import java.awt.Rectangle;

/**
 * Player in death state implementation 
 * @version 1.0
 * @author Angie Medina - Jose Perez
 */
public class PlayerDeathState extends PlayerState{
	
	/**
	 * PlayerDeathState class constructor
	 * @param player context
	 */
	public PlayerDeathState(Player player) {
		super(player);
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(0,0,0,0);
	}

	@Override
	public void decreasePlayerlives() {
		player.dying();
	}
	
	@Override
	public String getState() {
		return "Dth";
	}
}
