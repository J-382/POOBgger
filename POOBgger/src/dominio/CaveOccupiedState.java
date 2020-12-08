package dominio;

import java.awt.Rectangle;

/**
 * Cave in occupied state implementation 
 * @version 1.0
 * @author Angie Medina - Jose Perez
 */
public class CaveOccupiedState extends CaveState{

	/**
	 * CaveOccupiedState class Constructor
	 * @param cave context
	 */
	public CaveOccupiedState(Cave cave) {
		super(cave, "CaveOccupied");
	}

	public boolean isOccupied() {
		return true;
	}
	
	@Override
	public Rectangle getBounds() {
		return new Rectangle(0,0,0,0);
	}

}
