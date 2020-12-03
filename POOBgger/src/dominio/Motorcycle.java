package dominio;

/**
 * POOgger's motorcycle implementation
 * @version 2.1
 * @author Angie Medina - Jose Perez
 * */
public class Motorcycle extends Mobile{
	private Animator animator;
	private int frame;
	private String orientation;
	/**
	 * Motorcycle class constructor
	 * @param x Motorcycle's x position
	 * @param y Motorcycle's y position
	 * @param size Motorcycle's dimensions
	 * @param sprite Motorcycle's sprite
	 * @param speed Motorcycle's speed
	 * @param flipped if the Motorcycle are flipped horizontally
	 * */
	public Motorcycle(int x, int y, int speed, int[] size, String sprite, boolean flipped){
		this.x = x;
		this.y = y;
		this.width = size[0];
		this.height = size[1];
		this.speed = speed;
		this.isVisible = true;
		orientation = flipped?"F":"";
		frame = 0;
		this.sprite = sprite+orientation;
		animator = new Animator();
		animator.animate(150, 2, new Runnable() {public void run() {updateSprite();}},false);
	}
	
	/**
	 * Plays Motorcycle's move animation
	 * */
	private void updateSprite() {
		frame = (frame+1)%2;
		sprite = "Motorcycle"+(frame+1)+orientation;
	}
	
	
	@Override
	public boolean inCollision(Element e) {
		boolean isDead = true;
		if(e.isPushable()) {
			((Pushable) e).addPush(-96,"W");
			isDead = false;
		}
		return isDead;
	}
	@Override
	protected void stopAnimator() {
		animator.stop();
	}
	
	@Override
	protected void resumeAnimator() {
		animator.resume();
	}
}
