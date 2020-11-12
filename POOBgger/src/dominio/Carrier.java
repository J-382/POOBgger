package dominio;

public abstract class Carrier extends Element {
	
	protected int speed;
	protected Pushable carried;
	protected boolean carrying;
	protected String state;

	public void move() {
		x += speed;
		if (carrying) {
			((Element) carried).move(speed,0);
		}
	}
	
	private void startCarrying(Pushable c) {
		if(c.setPosition(x, y)) {
			state = "C";
			carried = c;
			carried.beingCarried(this);
			carrying = true;
		}
	}
	
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
