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
	
	public Rectangle getBounds() {
		return new Rectangle(x,y,width,height);
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	/**
	 * Indicates if the Fixed element can be occupied
	 * @return if the Fixed element can be occupied
	 */
	public boolean canBeOccupied() {
		return false;
	}
}

