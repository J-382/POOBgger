package dominio;

/**
 * POOgger's truck implementation
 * @version 1.3
 * @author Angie Medina - Jose Perez
 * */
public class Truck extends Carrier{
	
	private Animator animator;
	private int frame;
	private String orientation;
	
	/**
	 * Truck class constructor
	 * @param x Truck's x position
	 * @param y Truck's y position
	 * @param speed Truck's speed
	 * @param flipped if the Truck are flipped horizontally
	 * */
	public Truck(int x, int y, int speed,boolean flipped){
		this.x = x;
		this.y = y;
		this.speed = speed;
		state = "";
		orientation = flipped?"F":"";
		frame = 0;
		animator = new Animator();
		sprite = "Truck"+state+(frame+1)+orientation;
	}
	
	/**
	 * Plays truck's move animation
	 * */
	public void updateSprite() {
		frame = (frame+1)%2;
		sprite = "Truck"+state+(frame+1)+orientation;
	}
	
	@Override
	public void move() {
		super.move();
		if(!animator.isRunning()) {
			animator.animate(5,2,new Runnable() {public void run() {updateSprite();}});
		}
	}
	
	@Override
	public boolean inCollision(Element e) {
		if(e.isPlayable()) {
			((Playable) e).makeToxic();
		}
		return super.inCollision(e);
	}
	
}