package dominio;
import java.awt.Rectangle;

/**
 * Cave in not occupied state implementation 
 * @version 1.0
 * @author Angie Medina - Jose Perez
 */
public class CaveNotOccupiedState extends CaveState{

	/**
	 * CaveNotOccupiedState class Constructor
	 * @param cave context
	 */
	public CaveNotOccupiedState(Cave cave) {
		super(cave,"Cave");
	}

	public boolean isOccupied() {
		return false;
	}
	
	@Override
	public Rectangle getBounds() {
		return new Rectangle(cave.getX(), cave.getY(), cave.getWidth(), cave.getHeight());
	}

}
