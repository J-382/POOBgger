package dominio;

public class SmallLog extends Log{

	private boolean isSubmerged;
	private int state;
	private Animator animator;
	public SmallLog(int x, int y, int speed, String sprite) {
		super(x, y, speed, sprite);
		state = 0;
		isSubmerged = false;
		animator = new Animator();
	}
	
	public void updateSprite() {
		state = (state+1);
		if (state == 3) isSubmerged = true;
		sprite = "SmallLog"+(state+1);
	}
	
	@Override
	public boolean inCollision(Element e) {
		boolean isDead = false;
		e.move(speed,0);
		if (isSubmerged) {
			isDead = true;
			animator.stop();
		}else animator.animate(400,4,new Runnable() {public void run() {updateSprite();}});
		return isDead;
	}
	
	public void drown() {
		
	}

}
