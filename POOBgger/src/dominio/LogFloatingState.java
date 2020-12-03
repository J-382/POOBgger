package dominio;

import java.awt.Rectangle;

/**
 * State Pattern implementation for Log's states
 * @author Angie Medina - Jose Perez
 * @version 1.0
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
