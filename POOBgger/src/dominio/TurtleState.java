package dominio;

import java.awt.Rectangle;
import java.io.Serializable;

/**
 * State Pattern implementation for Trutle's states
 * @version 1.0
 * @author Angie Medina - Jose Perez 
 *
 */
public abstract class TurtleState implements Serializable{
	Turtle turtle;
	
	/**
	 * TurtleState class constructor
	 * @param turtle context
	 */
	TurtleState(Turtle turtle){
		this.turtle = turtle;
	}

	/**
	 * Returns the turtle's bounds for it's current state
	 */
	public abstract Rectangle getBounds();
}
