package dominio;

import java.awt.Rectangle;

public class LogPreSubmergedState extends LogState{
	
	/**
	 * LogSubmergeState class Constructor
	 * @param log context
	 */
	LogPreSubmergedState(Log log){
		super(log);
	}

	public Rectangle getBounds() {
		return new Rectangle(log.getX(),log.getY(),log.getMinWidth(),log.getMinHeight());
	}
	
}