package dominio;

/**
 * Pogger's bonus bug implementation
 * @author Angie Medina - Jose Perez
 * @version 1.0
 */
public class Bug extends Fixed{
	
	private int points;
	private Animator animator;
	private int state;
	/**
	 *  
	 * @param x Bug's x position
	 * @param y Bug's y position
	 * @param width Bug's width
	 * @param height Bug's height
	 * @param time Bug's duration
	 */
	public Bug(int x, int y, int width, int height, int time) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.isVisible = true;
		points = 200;
		sprite = "Bug";
		state = 0;
		animator = new Animator();
		animator.animate(time, 2, new Runnable() {public void run() {setVisible();}});
	}
	
	/**
	 * Changes the bug visibility
	 */
	private void setVisible() {
		if(state==1) isVisible = false;
		else state+=1;
	}
	
	
	@Override
	public boolean inCollision(Element e) {
		if(e.isPlayable()) {
			//((Player)e).changePoints(points);
			isVisible = false;
		}
		return false;
	}
	
	@Override
	protected void stopAnimator() {
		animator.stop();
	}
	
	@Override
	protected void resumeAnimator() {
		animator.resume();
	}
	
	@Override
	protected boolean givesBonus() {
		return true;
	}
	
	@Override
	protected int getPoints() {
		return points;
	}
}
