package dominio;

/**
 * Pretends be a Frogger's car
 * @version 2.0
 * @author Angie Medina - Jose Perez
 * */
public class Car extends Mobile{
	
	private int speed;
	/**
	 * Car class constructor
	 * @param x Car's x position
	 * @param y Car's y position
	 * @param speed Car's speed
	 * @param size Car's dimensions
	 * @param sprite Car's sprite name
	 * */
	public Car(int x, int y, int speed,int[] size, String sprite){
		this.sprite = sprite+"Car";
		this.x = x;
		this.y = y;
		this.width = size[0];
		this.height = size[1];
		this.speed = speed;
		this.isVisible = true;
	}
	
	public void move() {
		x += speed;
	}

	public boolean inCollision(Element e) {
		boolean isDead = true;
		if(e.isPlayable()) {
			isDead = !((Playable) e).isArmored();
			if(!isDead) isVisible = false;
		}
		return isDead;
	}
	
	@Override
	public Power destroy() {
		isVisible = false;
		return new SpeedPower(x,y,width,height);
	}
}
