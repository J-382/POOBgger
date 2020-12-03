package dominio;

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
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.sprite = "Armor1";
		this.state = 0;
		this.isVisible = true;
		Animator animator = new Animator();
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
