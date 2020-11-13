package dominio;


/**
 * Pretends be a Frogger's turtle
 * @version 1.2
 * @author Angie Medina - Jose Perez
 * */
public class Turtle extends Element{
	
	private int speed;
	private boolean isSubmerge;
	private boolean doesSubmerge;
	private int frame;
	//private Timer animator;
	private Animator animator;
	
	/**
	 * Turtle class constructor
	 * @param x Turtle's x position
	 * @param y Turtle's y position
	 * @param speed Turtle's speed
	 * @param doesSubmerge if the turtle can submerge
	 * */
	public Turtle(int x, int y, int speed, boolean doesSubmerge) {
		this.x = x;
		this.y = y;
		this.speed = speed;
		frame = 0;
		isSubmerge = false;
		this.doesSubmerge = doesSubmerge;
		sprite = "Turtle1";
		animator = new Animator();
	}
	
	/**
	 * Plays turtle's submerge animation
	 * */
	public void submerge() {
		frame = (frame+1)%7;
		isSubmerge = frame>=3 && frame<5;
		sprite = "Turtle"+"S"+(frame+1);
	}
	
	/**
	 * Plays turtle's move animation
	 * */
	public void updateSprite() {
		frame = (frame+1)%2;
		sprite = "Turtle"+(frame+1);
		if(frame==0 && doesSubmerge) {
			animator.stop();
			animator.animate(100*(4-speed), 8, new Runnable() {public void run() {submerge();}});
		}
	}
	
	public void move() {
		x += speed;
		if (!animator.isRunning()) {
			animator.animate(100*(4-speed), 3, new Runnable() {public void run() {updateSprite();}});
		}
	}
	
	@Override
	public boolean inCollision(Element e) {
		boolean isDead = false;
		if (isSubmerge) {
			isDead = true;
		}
		else{e.move(speed, 0);}
		return isDead;
	}	
}
