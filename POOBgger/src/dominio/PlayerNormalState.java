package dominio;

import java.awt.Rectangle;

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
}
