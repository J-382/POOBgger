package dominio;

import java.awt.Rectangle;

public class CaveOccupiedState extends CaveState{

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
