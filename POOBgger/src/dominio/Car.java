package dominio;

public class Car extends Element{
	
	private int speed;
	
	public Car(int x, int y, int speed, String sprite){
		this.sprite = sprite;
		this.x = x;
		this.y = y;
		this.speed = speed;
	}
	
	public void move() {
		x += speed;
	}
	
	@Override
	public boolean inCollision(Element e) {
		return true;
	}
}
