package dominio;

import java.awt.Rectangle;

public class TurtleFloatingState extends TurtleState {
	
	/**
	 * TurtleFloatingState class constructor
	 * @param turtle context
	 */
	TurtleFloatingState(Turtle turtle){
		super(turtle);
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(turtle.getX(),turtle.getY(),turtle.getWidth(),turtle.getHeight());
	}
}
