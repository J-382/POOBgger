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
	public SmallLog(int x, int y, int speed,int[] size, String sprite) {
		super(x, y, speed, size, sprite);
		this.isVisible = true;
		state = 0;
		isSubmerged = false;
		animator = new Animator();
	}
	
	/**
	 * Plays small log's plunge animation
	 * */
	private void updateSprite() {
		state = (state+1);
		sprite = "SmallLog"+(state+1);
		if (state == 4) {
			isSubmerged = true;
		}
	}
	
	public boolean inCollision(Element e) {
		boolean isDead = false;
		if(isSubmerged) isDead = true;
		super.inCollision(e);
		if (isSubmerged) {
			animator.stop();
		}else {
			if(!animator.isRunning()) {
				animator.animate(400,5,new Runnable() {public void run() {updateSprite();}});
			}
		}
		return isDead;
	}
}
