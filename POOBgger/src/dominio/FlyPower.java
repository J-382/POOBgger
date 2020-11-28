package dominio;


public class FlyPower extends Power{

	private int state;
	
	public FlyPower(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.sprite = "Fly1";
		this.state = 0;
		this.isVisible = true;
		Animator animator = new Animator();
		//animator.animate(100, 18, new Runnable() {public void run() {updateSprite();}},false);
	}
	
	private void updateSprite() {
		state = (state+1)%17;
		sprite = "Fly"+(state+1);
	}
	
	public void givePower(Playable player) {
		player.makeFly(true);		
	}
}
