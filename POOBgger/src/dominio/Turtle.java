package dominio;

public class Turtle extends Element{
	
	private int speed;
	private int state;
	private boolean isSubmerge;
	private int times;
	//private Timer animator;
	private Animator animator;
	
	public Turtle(int x, int y, int speed, boolean doesSubmerge) {
		this.x = x;
		this.y = y;
		this.speed = speed;
		state = 0;
		isSubmerge = false;
		sprite = "Turtle1";
		times = 3;
		if (doesSubmerge) times = 6;
		animator = new Animator();
	}
	
	
	public void updateSprite() {
		state = (state+1)%times;
		if (state == 5) isSubmerge =  true;
		else {isSubmerge = false;}
		sprite = "Turtle"+(state+1);
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
