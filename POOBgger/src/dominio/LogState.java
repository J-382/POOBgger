package dominio;

import java.awt.Rectangle;
import java.io.Serializable;

/**
 * State Pattern implementation for Log's states
 * @author Angie Medina - Jose Perez
 * @version 1.0
 */
public abstract class LogState implements Serializable{
	Log log;
	
	/**
	 * LogState class Constructor
	 * @param log context
	 */
	public LogState(Log log) {
		this.log = log;
	}
	
	/**
	 * Returns the log's bounds for it's current state 
	 * @return the log's bounds for it's current state
	 */
	public abstract Rectangle getBounds();
	
	/**
	 * Returns if the log is visible 
	 * @return log's visibility
	 */
	public boolean isVisible() {
		return true;
	}
}
