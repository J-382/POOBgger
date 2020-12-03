package dominio;

import java.util.ArrayList;

/**
 * POOgger's bike implementation
 * @version 2.2
 * @author Angie Medina - Jose Perez
 */
public class Bike extends Carrier{
	
	private Animator animator;
	private int frame;
	private String orientation;
	
	/**
	 * Bike class constructor
	 * @param x Bike's x position
	 * @param y Bike's y position
	 * @param speed Bike's speed
	 * @param size Bike's dimensions
	 * @param sprite Bike's sprite name
	 * @param flipped if the bike is flipped horizontally
	 * */
	public Bike(int x, int y, int speed, int[] size, String sprite, boolean flipped){
		this.x = x;
		this.y = y;
		this.speed = speed;
		this.width = size[0];
		this.height = size[1];
		this.maxCarryNumber = 1;
		this.carried = new ArrayList<Pushable>();
		this.isVisible = true;
		orientation = flipped?"F":"";
		this.sprite = sprite+orientation;
		frame = 0;
		animator = new Animator();
		animator.animate(200,2,new Runnable() {public void run() {updateSprite();}},false);
	}
	
	/**
	 * Plays the bike's move animation
	 * */
	private void updateSprite() {
		String state = carrying?"C":"";
		frame = (frame+1)%2;
		sprite = "Bike"+state+(frame+1)+orientation;
	}
	
	@Override
	protected void startCarrying(Pushable c) {
		if(c.setPosition(x, y)) {
			c.setVisible(false);
			super.startCarrying(c);
		}
	}
	
	@Override
	protected void stopAnimator() {
		animator.stop();
	}
	
	@Override
	protected void resumeAnimator() {
		animator.resume();
	}
	
	@Override
	public boolean inCollision(Element e) {
		boolean isDead = true;
		if(e.isPlayable()) isDead = super.inCollision(e);
		return isDead;
	}
	
	@Override
	public void stopCarrying(Pushable c) {
		sprite = "Bike"+(frame+1)+orientation;
		super.stopCarrying(c);
	}
}
