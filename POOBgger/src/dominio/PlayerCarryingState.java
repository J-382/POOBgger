package dominio;

import java.awt.Rectangle;

/**
 * Player in carrying state implementation 
 * @version 1.0
 * @author Angie Medina - Jose Perez
 */
public class PlayerCarryingState extends PlayerState{
	
	/**
	 * PlayerCarryingState class constructor
	 * @param player context
	 */
	public PlayerCarryingState(Player player) {
		super(player);
	}

	@Override
	public int getPointsOnDeath() {
		return super.getPointsOnDeath()-200;
	}
	
	@Override
	public int getPointsOnArriving() {
		return super.getPointsOnDeath()+450;
	}
	
	@Override
	public Rectangle getBounds() {
		return new Rectangle(player.getX(),player.getY(),player.getWidth(),player.getHeight());
	}

	@Override
	public String getState() {
		return "C";
	}
	
}
