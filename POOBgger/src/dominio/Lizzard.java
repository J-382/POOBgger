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
		sprite = "Alligator1";
		animator = new Animator();
	}
	
	public void updateSprite() {
		state =  (state + 1)%2;
		y+=state==0?15:-15;
		if (state == 1) isOpen = true;
		else isOpen = false;
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
		if (isOpen) {
			int elementWidth = ((Player)e).getDimensions()[0];
			if (this.getX() + 2*elementWidth <= e.getX()) {
				isDead = true;
			} 
		}
		e.move(speed, 0);
		return isDead;
	}
}
