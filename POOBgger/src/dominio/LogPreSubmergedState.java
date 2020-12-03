package dominio;

import java.awt.Rectangle;

/**
 * Log in pre-submerged state implementation 
 * @version 1.0
 * @author Angie Medina - Jose Perez
 */
public class LogPreSubmergedState extends LogState{
	
	/**
	 * LogSubmergeState class Constructor
	 * @param log context
	 */
	LogPreSubmergedState(Log log){
		super(log);
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(log.getX(),log.getY(),log.getMinWidth(),log.getMinHeight());
	}
	
}