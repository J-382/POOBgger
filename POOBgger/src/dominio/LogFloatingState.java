package dominio;

import java.awt.Rectangle;

/**
 * Log in floating state implementation 
 * @version 1.0
 * @author Angie Medina - Jose Perez
 */
public class LogFloatingState extends LogState{
	
	/**
	 * LogFloatingState class Constructor
	 * @param log context
	 */
	LogFloatingState(Log log){
		super(log);
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(log.getX(),log.getY(),log.getWidth(),log.getHeight());
	}
}
