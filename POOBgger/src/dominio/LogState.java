package dominio;

import java.awt.Rectangle;

public abstract class LogState {
	Log log;
	public LogState(Log log) {
		this.log = log;
	}
	
	public abstract Rectangle getBounds();
	
	public boolean isVisible() {
		return true;
	}
}
