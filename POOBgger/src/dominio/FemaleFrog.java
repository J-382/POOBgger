package dominio;

public class FemaleFrog extends Mobile implements Pushable{
	private int speed;
	private int state;
	private String orientation;
	private Animator animator;
	private boolean beingCarried;
	private Carrier carrier;	
	private boolean coolDown;
	private boolean haveToFlip;
	/**
	 * Snake class constructor
	 * @param x Snake's x position
	 * @param y Snake's y position
	 * @param speed Snake's speed
	 * @param flipped if the Snake can submerge
	 * */
	public FemaleFrog(int xPos, int yPos, int speed,int[] size, String sprite, boolean flipped){
		sprite = "FFrog1D";
		this.x = xPos;
		this.y = yPos;
		this.width = size[0];
		this.height = size[1];
		this.speed = speed;
		this.isVisible = true;
		this.sprite = sprite;
		orientation = "D";
		state = 0;
		animator = new Animator();
		coolDown = false;
		haveToFlip = false;
		if(flipped) flip();
	}
	
	/**
	 * Plays female's frog  move animation;
	 * */
	private void updateSprite(){
		state = (state+1)%3;
		sprite = "FFrog"+(state+1)+orientation;
	}
	
	public void move() {
		if(carrier!=null && !carrier.getBounds().intersects(getBounds())) {
			carrier.stopCarrying(this);
			isVisible = false;
		}
		if(!animator.isRunning()) {
			if(coolDown) {
				animator.animate(1000,1,new Runnable() {public void run() {coolDown=false;}});
			}else {
				if(haveToFlip) {
					flip();
					haveToFlip = false;
				}
				animator.animate(50,3,new Runnable() {public void run() {
					updateSprite();
					x += speed/3;
					if (carrier!=null && (x+width>=carrier.getX()+carrier.getWidth() || x<=carrier.getX())) {
						haveToFlip = true;
					}}});
				coolDown = true;
			}
		}
	}
	
	/**
	 * Flip horizontally the snake's sprite
	 * */
	private void flip() {
		speed *= -1;
		orientation = orientation.equals("D")?"A":"D";
		sprite = "FFrog"+(state+1)+orientation;
	}
	
	public boolean inCollision(Element e) {
		return false;
	}

	@Override
	public void beingCarried(Carrier c) {
		beingCarried = true;
		carrier = c;
	}

	@Override
	public boolean setPosition(int x, int y) {
		return false;
	}

	@Override
	public void addPush(int push, String dir) {
	}

	@Override
	public void setVisible(boolean visible) {
	}

	@Override
	public char getDir() {
		return orientation.charAt(0);
	}

	@Override
	public boolean isBeingCarried() {
		return beingCarried;
	}
	
	@Override
	public boolean isCarriable() {
		return true;
	}
	
	@Override
	protected void stopAnimator() {
		animator.stop();
	}
	
	@Override
	protected void resumeAnimator() {
		if(animator.isPaused()) animator.resume();
	}
}
