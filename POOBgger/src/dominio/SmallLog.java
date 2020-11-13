package dominio;

/**
 * POOgger's small log implementation
 * @version 1.3
 * @author Angie Medina - Jose Perez
 * */
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
	
	/**
	 * Plays small log's plunge animation
	 * */
	public void updateSprite() {
		state = (state+1);
		sprite = "SmallLog"+(state+1);
		if (state == 4) {
			isSubmerged = true;
		}
	}
	
	@Override
	public boolean inCollision(Element e) {
		boolean isDead = false;
		e.move(speed,0);
		if (isSubmerged) {
			isDead = true;
			animator.stop();
		}else {
			if(!animator.isRunning()) {
				animator.animate(400,5,new Runnable() {public void run() {updateSprite();}});
			}
		}
		return isDead;
	}
	
	public void drown() {
	}

}
