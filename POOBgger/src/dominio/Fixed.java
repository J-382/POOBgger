package dominio;

import java.awt.Rectangle;

/**
 * Fixed Object implementation
 * @author Angie Medina - Jose Perez
 * @version 1.2
 * */
public abstract class Fixed extends Element {
	protected int width;
	protected int height;
	
	public void move() {}
	
	public Rectangle getBounds() {
		return new Rectangle(x,y,width,height);
	}
}
