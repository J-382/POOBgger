package dominio;

import java.awt.Rectangle;
import java.io.Serializable;

public abstract class PlayerState implements Serializable{
	Player player;
	PlayerState(Player player){
		this.player = player;
	}
	
	public abstract Rectangle getBounds();
	public abstract void decreasePlayerlives();
}
