package dominio;

/**
 * Pretends be a Frogger's snake
 * @version 2.1
 * @author Angie Medina - Jose Perez
 * */
public class Snake extends Mobile{
	
	private int speed;
	private int state;
	private String orientation;
	private Animator animator;

	/**
	 * Snake class constructor
	 * @param x Snake's x position
	 * @param y Snake's y position
	 * @param speed Snake's speed
	 * @param flipped if the Snake can submerge
	 * */
	public Snake(int xPos, int yPos, int speed,int[] size, String sprite, boolean flipped){
		sprite = "Snake1";
		this.x = xPos;
		this.y = yPos;
		this.width = size[0];
		this.height = size[1];
		this.speed = speed;
		this.isVisible = true;
		this.sprite = sprite;
		orientation = "";
		state = 0;
		animator = new Animator();
		if(flipped) flip();
	}
	
	/**
	 * Plays snake's move animation;
	 * */
	private void updateSprite(){
		state = (state+1)%4;
		if(state==2) y+=20;
		else if(state==3) y-=20;
		sprite = "Snake"+(state+1)+orientation;
	}
	
	public void move() {
		x += speed;
		if (x>600) flip();
		if(!animator.isRunning()) {
			animator.animate(50,2,new Runnable() {public void run() {updateSprite();}});
		}
		
	}
	
	/**
	 * Flip horizontally the snake's sprite
	 * */
	public void flip() {
		speed *= -1;
		orientation = orientation.equals("F")?"":"F";
		sprite = "Snake"+(state+1)+orientation;
	}
	
	public boolean inCollision(Element e) {
		boolean isDead = true;
		if(e.isPlayable()) {
			isDead = !((Playable) e).isToxic();
			if(!isDead) isVisible = false;
		}
		return isDead;
	}
}
