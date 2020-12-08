package dominio;

/**
 * Pogger's FlyPower implementation
 * @author Angie Medina - Jose Perez
 * @version 1.0
 */
public class FlyPower extends Power{
	
	/**
	 * Fly Power  Class constructor
	 * @param x FlyPower's x position
	 * @param y FlyPower's y position
	 * @param width FlyPower's width
	 * @param height FlyPower's height
	 */
	public FlyPower(int x, int y, int width, int height) {
		super(x,y,width,height);
		sprite = "Fly1";
		Animator animator = new Animator();
		animator.animate(100, 18, new Runnable() {public void run() {updateSprite();}},false);
	}
	
	/**
	 * Changes the FlyPower's Sprite
	 */
	private void updateSprite() {
		state = (state+1)%1;
		sprite = "Fly"+(state+1);
	}
	
	@Override
	public void givePower(Playable player) {
		player.makeFly(true);		
	}
}
