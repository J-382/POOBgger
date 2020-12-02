package dominio;

public class ArmorPower extends Power{

	private int state;
	
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
	 * Change
	 */
	private void updateSprite() {
		state = (state+1)%3;
		sprite = "Armor"+(state+1);
	}
	
	public void givePower(Playable player) {
		player.makeArmored(true);
	}
}
