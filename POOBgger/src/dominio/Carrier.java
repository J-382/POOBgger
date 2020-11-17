package dominio;

import java.util.ArrayList;

/** Emulates a Carrier's behavior
 * @version 2.1
 * @author Angie Medina - Jose Perez
 * */
public abstract class Carrier extends Element {
	
	protected int speed;
	protected ArrayList<Pushable> carried;
	protected boolean carrying;
	protected int maxCarryNumber;

	@Override
	public void move() {
		x += speed;
		if (carrying) {
			for(Pushable i: carried) {
				((Element) i).move(speed,0);	
			}
		}
	}
	
	/**
	 * Entangles Pushable's movements with this Carrier
	 * @param c the Pushable element to entagle
	 * */
	protected void startCarrying(Pushable c) {
		carried.add(c);
		c.beingCarried(this);
		carrying = true;
	}
	
	/**
	 * Destangles Pushable's movements with this Carrier
	 * @param c the Pushable element to destangle
	 * */
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
}
