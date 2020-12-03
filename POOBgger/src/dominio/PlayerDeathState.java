package dominio;

import java.awt.Rectangle;

public class PlayerDeathState extends PlayerState{
	
	/**
	 * PlayerDeathState class constructor
	 * @param player context
	 */
	public PlayerDeathState(Player player) {
		super(player);
		Animator changeState = new Animator();
		changeState.animate(2000, 1, new Runnable() {public void run() {player.changeState(new PlayerNormalState(player));}});
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(0,0,0,0);
	}

	@Override
	public void decreasePlayerlives() {
	}
}