package dominio;

public class Lizzard extends Element{

	private int speed;
	private boolean isOpen;
	private int state;
	private Animator animator;
	
	public Lizzard(int x, int y, int speed) {
		this.x = x;
		this.y = y;
		this.speed = speed;
		isOpen = false;
		state = 0;
		sprite = "Lizzard1";
		animator = new Animator();
	}
	
	public void updateSprite() {
		state =  (state + 1)%2;
		if (state == 1) isOpen = true;
		sprite = "Alligator"+(state + 1);
	}
	
	@Override
	void move() {
		x += speed;
		if (!animator.isRunning()) {
			animator.animate(1000, 2, new Runnable() {public void run() {updateSprite();}});
		}
	}
	
	@Override
	public boolean inCollision(Element e) {
		boolean isDead = false;
		e.move(speed, 0);
		return isDead;
	}
}
