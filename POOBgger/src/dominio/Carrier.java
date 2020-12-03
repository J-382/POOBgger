package dominio;

import java.util.ArrayList;

/** Emulates a Carrier's behavior
 * @version 2.1
 * @author Angie Medina - Jose Perez
 */
public class Carrier extends Mobile {
	
	protected ArrayList<Pushable> carried;
	protected boolean carrying;
	protected int maxCarryNumber;
	protected int speed;
	
	/**
	 * Entangles Pushable's movements with this Carrier
	 * @param c the Pushable element to entagle
	 */
	protected void startCarrying(Pushable c) {
		carried.add(c);
		c.beingCarried(this);
		carrying = true;
	}
	
	/**
	 * Destangles Pushable's movements with this Carrier
	 * @param c the Pushable element to destangle
	 */
	public void stopCarrying(Pushable c) {
		if(carried.contains(c)) {
			carried.remove(c);
		}
		carrying = !(carried.size()==0);
	}
	
	@Override
	public boolean inCollision(Element e) {
		if(e.isCarriable() && carried.size()<maxCarryNumber) {
			startCarrying((Pushable) e);
		}
		return false;
	}
	
	@Override
	public void move() {
		x += speed;
		if (carrying) {
			for(Pushable i: carried) {
				((Mobile) i).move(speed,0);	
			}
		}
	}
}