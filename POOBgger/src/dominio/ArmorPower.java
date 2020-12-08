package dominio;

/**
 * Pogger's ArmorPower implementation
 * @author Angie Medina - Jose Perez
 * @version 1.0
 */
public class ArmorPower extends Power{

	private int state;
	/**
	 * ArmorPower Class constructor
	 * @param x ArmorPower's x position
	 * @param y ArmorPower's y position
	 * @param width ArmorPower's width
	 * @param height ArmorPower's height
	 */
	public ArmorPower(int x, int y, int width, int height) {
		super(x,y,width,height);
		Animator animator = new Animator();
		sprite = "Armor1";
		animator.animate(400, 18, new Runnable() {public void run() {updateSprite();}},false);
	}
	
	/**
	 * Changes the ArmorPower's Sprite
	 */
	private void updateSprite() {
		state = (state+1)%3;
		sprite = "Armor"+(state+1);
	}
	
	/**
	 * Give ArmorPower to the given player
	 * @param Playable the desired player
	 */
	public void givePower(Playable player) {
		player.makeArmored(true);
	}
}
