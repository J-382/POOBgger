package dominio;

/**
 * Pretends be a Frogger's log
 * @version 1.1
 * @author Angie Medina - Jose Perez
 * */
public class Log extends Element {
	
	protected int speed;
	
	/**
	 * Log class constructor
	 * @param x Log's x position
	 * @param y Log's y position
	 * @param speed Log's speed
	 * @param sprite Log's sprite name
	 * */
	public Log(int x, int y, int speed, String sprite) {
		this.sprite = sprite;
		this.x = x;
		this.y = y;
		this.speed = speed;
	}
	
	public void move() {
		x+=speed;
	}
	
	@Override
	public boolean inCollision(Element e) {
		e.move(speed,0);
		return false;
	}
	
}
