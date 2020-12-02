package dominio;

import java.awt.Rectangle;

public class PlayerDeathState extends PlayerState{
	public PlayerDeathState(Player player) {
		super(player);
		Animator changeState = new Animator();
		changeState.animate(5000, 1, new Runnable() {public void run() {player.chageState(new PlayerNormalState)}});
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(0,0,0,0);
	}

	@Override
	public void decreasePlayerlives() {
	}
}
