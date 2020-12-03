package dominio;

/**
 * Mobile Object implementation
 * @author Angie Medina - Jose Perez
 * @version 1.2
 */
public abstract class Mobile extends Element{
	protected int speed;
	
	/**
	 * Default Mobile's movement
	 */
	public void move() {
		x += speed;
	}
	
	/**
	 * Moves the element 
	 * @param dx delta x
	 * @param dy delta y
	 */
	public void move(int dx, int dy) {
		x += dx;
		y += dy;
	}
}
