package dominio;

/**
 * Pretends be a Frogger's car
 * @version 1.1
 * @author Angie Medina - Jose Perez
 * */
public class Car extends Element{
	
	private int speed;
	/**
	 * Car class constructor
	 * @param x Car's x position
	 * @param y Car's y position
	 * @param speed Car's speed
	 * @param sprite Car's sprite name
	 * */
	public Car(int x, int y, int speed, String sprite){
		this.sprite = sprite+"Car";
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
