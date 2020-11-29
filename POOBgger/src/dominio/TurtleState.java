package dominio;

import java.awt.Rectangle;

public abstract class TurtleState {
	Turtle turtle;
	
	TurtleState(Turtle turtle){
		this.turtle = turtle;
	}
	
	public abstract Rectangle getBounds();
}
