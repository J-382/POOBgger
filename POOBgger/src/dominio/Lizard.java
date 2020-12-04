package dominio;

import java.util.ArrayList;

/**
 * Pretends be a Frogger's lizard
 * @version 3.0
 * @author Angie Medina - Jose Perez
 * */
public class Lizard extends Carrier{

	private boolean isOpen;
	private int state;
	private Animator animator;
	
	
	/**
	 * Lizard class constructor
	 * @param x Lizard's x position
	 * @param y Lizard's y position
	 * @param size Lizard's dimensions
	 * @param sprite Lizard's sprite
	 * @param speed Lizzard's speed
	 * */
	public Lizard(int x, int y, int[] size, String sprite, int speed) {
		this.x = x;
		this.y = y;
		this.speed = speed;
		this.width = size[0];
		this.height = size[1];
		this.carried = new ArrayList<Pushable>();
		this.maxCarryNumber = Integer.MAX_VALUE;
		this.isVisible = true;
		isOpen = true;
		state = 0;
		this.sprite = sprite;
		animator = new Animator();
		animator.animate(1500, 2, new Runnable() {public void run() {updateSprite();}},false);
	}
	
	/**
	 * Plays Lizard's move animation
	 * */
	private void updateSprite() {
		state =  (state + 1)%2;
		isOpen = state == 1;
		sprite = "Lizard"+(state + 1);
	}
	
	@Override
	public boolean inCollision(Element e) {
		super.inCollision(e);
		System.out.println(((Playable) e).isToxic());
		boolean isDead = false;
		if (isOpen && e.isPlayable()) {
			if (x+2*width/3 <= e.getX()) {
				isDead = !((Playable) e).isToxic();
				if(!isDead) isVisible = false;
			} 
		}
		return isDead;
	}
	
	@Override
	protected void stopAnimator() {
		animator.stop();
	}
	
	@Override
	protected void resumeAnimator() {
		animator.resume();
	}
}
