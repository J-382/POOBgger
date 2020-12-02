package dominio;

import java.awt.Rectangle;
import java.io.Serializable;

public abstract class TurtleState implements Serializable{
	Turtle turtle;
	
	TurtleState(Turtle turtle){
		this.turtle = turtle;
	}
	
	public abstract Rectangle getBounds();
}
