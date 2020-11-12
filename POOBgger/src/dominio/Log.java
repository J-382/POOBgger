package dominio;

public class Log extends Element {
	
	protected int speed;
	
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
