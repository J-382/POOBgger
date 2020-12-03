package dominio;

import java.awt.Rectangle;

/**
 * Turtle in submerge state implementation
 * @version 1.0
 * @author Angie Medina - Jose Perez
 */
public class TurtleSubmergedState extends TurtleState {
	
	/**
	 * TurtleSubmergedState class constructor
	 * @param turtle context
	 */
	TurtleSubmergedState(Turtle turtle){
		super(turtle);
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(0,0,0,0);
	}
}
