package dominio;

import javax.swing.ImageIcon;


public class Turtle extends Element{
	
	private int speed;
	private int state;
	private boolean isSubmerge;
	private int times;
	//private Timer animator;
	private Animator animator;
	
	public Turtle(int x, int y, int speed, ImageIcon image, boolean doesSubmerge) {
		sprite = image;
		this.x = x;
		this.y = y;
		this.speed = speed;
		state = 0;
		isSubmerge = false;
		times = 3;
		if (doesSubmerge) times = 6;
		animator = new Animator();
	}
	
	
	public void updateSprite() {
		state = (state+1)%times;
		if (state == 5) isSubmerge =  true;
		else {isSubmerge = false;}
		sprite = new ImageIcon("./resources/sprites/Turtle"+(state+1) + ".png");
	}
	
	public void move() {
		x += speed;
		if (!animator.isRunning()) {
			animator.animate(400, times, new Runnable() {public void run() {updateSprite();}});
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
