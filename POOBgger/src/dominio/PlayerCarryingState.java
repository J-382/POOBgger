package dominio;

import java.awt.Rectangle;

public class PlayerCarryingState extends PlayerState{
	public PlayerCarryingState(Player player) {
		super(player);
	}

	@Override
	public Rectangle getBounds() {
		return player.getBounds();
	}

	@Override
	public void decreasePlayerlives() {
		player.changePoints(-200);
	}
}
