package dominio;

import java.awt.Rectangle;

public abstract class PlayerState {
	Player player;
	PlayerState(Player player){
		this.player = player;
	}
	
	public abstract Rectangle getBounds();
	public abstract void decreasePlayerlives();
}
