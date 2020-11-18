package dominio;

import java.util.ArrayList;

/**
 * POOgger's bike implementation
 * @version 2.2
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
		this.maxCarryNumber = 1;
		this.carried = new ArrayList<Pushable>();
		orientation = flipped?"F":"";
		frame = 0;
		animator = new Animator();
		sprite = "Bike"+(frame+1)+orientation;
	}
	
	@Override
	public void stopCarrying(Pushable c) {
		sprite = "Bike"+(frame+1)+orientation;
		super.stopCarrying(c);
	}
	
	/**
	 * Plays Bike's move animation
	 * */
	private void updateSprite() {
		String state = carrying?"C":"";
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
	
	@Override
	protected void startCarrying(Pushable c) {
		System.out.println(x+" "+y);
		if(c.setPosition(x, y)) {
			c.setVisible(false);
			super.startCarrying(c);
		}
	}
	
}
