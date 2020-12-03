package dominio;

import java.awt.Rectangle;

/**
 * Log in submerged state implementation 
 * @version 1.0
 * @author Angie Medina - Jose Perez
 */
public class LogSubmergedState extends LogState{
	
	/**
	 * LogPreSubmergeState class Constructor
	 * @param log context
	 */
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
