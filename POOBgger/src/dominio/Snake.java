package dominio;

/**
 * Pretends be a Frogger's snake
 * @version 2.1
 * @author Angie Medina - Jose Perez
 * */
public class Snake extends Element{
	
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
	public Snake(int xPos, int yPos, int speed, boolean flipped){
		sprite = "Snake1";
		x = xPos;
		y = yPos;
		this.speed = speed;
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
			animator.animate(200,2,new Runnable() {public void run() {updateSprite();}});
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
	
	@Override
	public boolean inCollision(Element e) {
		return true;
	}
}
