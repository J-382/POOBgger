package dominio;

import java.awt.Rectangle;
import java.io.Serializable;

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
	 * @return
	 */
	public abstract Rectangle getBounds();
}
