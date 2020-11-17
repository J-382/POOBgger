package dominio;

import java.util.ArrayList;

/**
 * Pretends be a Frogger's lizzard
 * @version 2.1
 * @author Angie Medina - Jose Perez
 * */
public class Lizzard extends Carrier{

	private boolean isOpen;
	private int state;
	private Animator animator;
	private int length;
	
	
	/**
	 * Lizzard class constructor
	 * @param x Lizzard's x position
	 * @param y Lizzard's y position
	 * @param speed Lizzard's speed
	 * */
	public Lizzard(int x, int y, int length,int speed) {
		this.x = x;
		this.y = y;
		this.speed = speed;
		this.length = length;
		this.carried = new ArrayList<Pushable>();
		this.maxCarryNumber = Integer.MAX_VALUE;
		isOpen = true;
		state = 0;
		sprite = "Alligator1";
		animator = new Animator();
	}
	
	/**
	 * Plays Lizzard's move animation
	 * */
	private void updateSprite() {
		state =  (state + 1)%2;
		y+=state==0?15:-15;
		isOpen = state == 1;
		sprite = "Alligator"+(state + 1);
	}
	
	@Override
	public void move() {
		super.move();
		if (!animator.isRunning()) {
			animator.animate(1000, 2, new Runnable() {public void run() {updateSprite();}});
		}
	}
	
	@Override
	public boolean inCollision(Element e) {
		super.inCollision(e);
		boolean isDead = false;
		if (isOpen) {
			if (x+2*length/3 <= e.getX()) {
				System.out.println("Dead by alligator");
				isDead = true;
			} 
		}
		return isDead;
	}
}
