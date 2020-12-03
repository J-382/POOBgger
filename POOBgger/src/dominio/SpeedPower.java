package dominio;

public class SpeedPower extends Power{
	
	public SpeedPower(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.sprite = "Speed1";
		this.state = 0;
		this.isVisible = true;
		Animator animator = new Animator();
		animator.animate(100, 18, new Runnable() {public void run() {updateSprite();}},false);
	}
	
	private void updateSprite() {
		state = (state+1)%16;
		sprite = "Speed"+(state+1);
	}
	
	public void givePower(Playable player) {
		player.makeFast(true);		
	}
}
