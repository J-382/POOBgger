package dominio;

import java.awt.Rectangle;

public class PlayerNormalState extends PlayerState{
	public PlayerNormalState(Player player) {
		super(player);
	}

	@Override
	public Rectangle getBounds() {
		return player.getBounds();
	}

	@Override
	public void decreasePlayerlives() {
		player.decreasePlayerLives();
	}
}
