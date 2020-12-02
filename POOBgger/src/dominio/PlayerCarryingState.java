package dominio;

import java.awt.Rectangle;

public class PlayerCarryingState extends PlayerState{
	public PlayerCarryingState(Player player) {
		super(player);
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(player.getX(),player.getY(),player.getWidth(),player.getHeight());
	}

	@Override
	public void decreasePlayerlives() {
		player.changePoints(-200);
	}
}
