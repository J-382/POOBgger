package dominio;

/**
 * POOgger's bike implementation
 * @version 1.3
 * @author Angie Medina - Jose Perez
 * */
public class Bike extends Carrier{
	
	private Animator animator;
	private int frame;
	private String orientation;
	
	/**
	 * Bike class constructor
	 * @param x Bike's x position
	 * @param y Bike's y position
	 * @param speed Bike's speed
	 * @param flipped if the bike are flipped horizontally
	 * */
	public Bike(int x, int y, int speed,boolean flipped){
		this.x = x;
		this.y = y;
		this.speed = speed;
		state = "";
		orientation = flipped?"F":"";
		frame = 0;
		animator = new Animator();
		sprite = "Bike"+state+(frame+1)+orientation;
	}
	
	/**
	 * Plays Bike's move animation
	 * */
	public void updateSprite() {
		frame = (frame+1)%2;
		sprite = "Bike"+state+(frame+1)+orientation;
	}
	
	@Override
	public void move() {
		super.move();
		if(!animator.isRunning()) {
			animator.animate(200,2,new Runnable() {public void run() {updateSprite();}});
		}
	}
	
}
