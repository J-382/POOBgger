package dominio;

import java.awt.Rectangle;

public class LogSubmergedState extends LogState{
	LogSubmergedState(Log log){
		super(log);
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(0,0,0,0);
	}
	
	@Override
	public boolean isVisible() {
		return false;
	}
}
