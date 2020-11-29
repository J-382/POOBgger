package dominio;

import java.awt.Rectangle;

public class LogFloatingState extends LogState{
	LogFloatingState(Log log){
		super(log);
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(log.getX(),log.getY(),log.getWidth(),log.getHeight());
	}
}
