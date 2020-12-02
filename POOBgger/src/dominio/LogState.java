package dominio;

import java.awt.Rectangle;
import java.io.Serializable;

public abstract class LogState implements Serializable{
	Log log;
	public LogState(Log log) {
		this.log = log;
	}
	
	public abstract Rectangle getBounds();
	
	public boolean isVisible() {
		return true;
	}
}
