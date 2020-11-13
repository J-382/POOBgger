package dominio;

public class Turtle extends Element{
	
	private int speed;
	private boolean isSubmerge;
	private boolean doesSubmerge;
	private int frame;
	//private Timer animator;
	private Animator animator;
	
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
	
	public void submerge() {
		frame = (frame+1)%7;
		isSubmerge = frame>=3 && frame<5;
		sprite = "Turtle"+"S"+(frame+1);
	}
	
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
