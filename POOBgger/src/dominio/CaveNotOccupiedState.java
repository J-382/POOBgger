package dominio;
import java.awt.Rectangle;

public class CaveNotOccupiedState extends CaveState{

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
