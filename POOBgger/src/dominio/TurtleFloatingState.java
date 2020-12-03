package dominio;

import java.awt.Rectangle;

/**
 * Turtle in floating state implementation
 * @version 1.0
 * @author Angie Medina - Jose Perez
 */
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
