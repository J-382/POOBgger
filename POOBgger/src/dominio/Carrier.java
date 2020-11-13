package dominio;

/** Emulates a Carrier's behavior
 * @version 1.3
 * @author Angie Medina - Jose Perez
 * */
public abstract class Carrier extends Element {
	
	protected int speed;
	protected Pushable carried;
	protected boolean carrying;
	protected String state;

	@Override
	public void move() {
		x += speed;
		if (carrying) {
			((Element) carried).move(speed,0);
		}
	}
	
	/**
	 * Entangles Pushable's movements with this Carrier
	 * @param c the Pushable element to entagle
	 * */
	private void startCarrying(Pushable c) {
		if(c.setPosition(x, y)) {
			state = "C";
			carried = c;
			carried.beingCarried(this);
			carrying = true;
		}
	}
	
	/**
	 * Destangles Pushable's movements with this Carrier
	 * @param c the Pushable element to destangle
	 * */
	public void stopCarrying(Pushable c) {
		if(c==carried) {
			state = "";
			carried = null;
			carrying = false;
		}
	}
	
	@Override
	public boolean inCollision(Element e) {
		if(e.isCarriable() && !carrying) {
			startCarrying((Pushable) e);
		}
		return false;
	}
}
