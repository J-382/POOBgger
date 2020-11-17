package dominio;

import java.util.ArrayList;

/**
 * POOgger's truck implementation
 * @version 2.1
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
		this.maxCarryNumber = 1;
		this.carried = new ArrayList<Pushable>();
		orientation = flipped?"F":"";
		frame = 0;
		animator = new Animator();
		sprite = "Truck"+(frame+1)+orientation;
	}
	
	/**
	 * Plays truck's move animation
	 * */
	private void updateSprite() {
		String state = carrying?"C":"";
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
		boolean isDead = false;
		if(e.isPlayable()) {
			((Playable) e).makeToxic();
		}
		if(e.getX()<x) {
			isDead = true;
		}else isDead = super.inCollision(e);
		return isDead;
	}
	
}