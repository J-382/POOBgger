package dominio;

import java.awt.Rectangle;
import java.util.ArrayList;

/**
 * Pretends be a Frogger's turtle
 * @version 2.1
 * @author Angie Medina - Jose Perez
 * */
public class Turtle extends Carrier{
	
	private boolean isSubmerge;
	private boolean doesSubmerge;
	private int frame;
	private Animator animator;
	private TurtleState state;
	
	/**
	 * Turtle class constructor
	 * @param x Turtle's x position
	 * @param y Turtle's y position
	 * @param speed Turtle's speed
	 * @param size Turtle's dimension
	 * @param sprite Turtle's sprite
	 * @param doesSubmerge if the turtle can submerge
	 * */
	public Turtle(int x, int y, int speed, int[] size, String sprite,boolean doesSubmerge) {
		this.x = x;
		this.y = y;
		this.width = size[0];
		this.height = size[1];
		this.speed = speed;
		this.carried = new ArrayList<Pushable>();
		this.maxCarryNumber = 1;
		this.isVisible = true;
		this.doesSubmerge = doesSubmerge;
		state = new TurtleFloatingState(this);
		frame = 0;
		isSubmerge = false;
		this.sprite = sprite;
		animator = new Animator();
	}
	
	/**
	 * Plays turtle's submerge animation
	 * */
	private void submerge() {
		frame = (frame+1)%7;
		isSubmerge = frame>=3 && frame<5;
		state = isSubmerge?new TurtleSubmergedState(this):new TurtleFloatingState(this);
		sprite = "Turtle"+"S"+(frame+1);
	}
	
	/**
	 * Plays turtle's move animation
	 * */
	private void updateSprite() {
		frame = (frame+1)%2;
		sprite = "Turtle"+(frame+1);
		if(frame==0 && doesSubmerge) {
			animator.stop();
			animator.animate(100*(4-speed), 8, new Runnable() {public void run() {submerge();}});
		}
	}
	
	public void move() {
		super.move();
		if (!animator.isRunning()) {
			animator.animate(100*(4-speed), 3, new Runnable() {public void run() {updateSprite();}});
		}
	}
	
	@Override
	public Rectangle getBounds() {
		return state.getBounds();
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
	public Power destroy() {
		isVisible = false;
		return new ArmorPower(x,y,width,height);
	}
}
