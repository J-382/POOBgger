package dominio;

import java.awt.Rectangle;

/**
 * POOgger's small log implementation
 * @version 1.3
 * @author Angie Medina - Jose Perez
 * */
public class SmallLog extends Log{

	private boolean isSubmerged;
	private int frame;
	private Animator animator;
	private int minWidth;
	private int minHeight;
	private LogState state;
	public SmallLog(int x, int y, int speed,int[] size, int[] minSize, String sprite) {
		super(x, y, speed, size, sprite);
		this.isVisible = true;
		frame = 0;
		isSubmerged = false;
		minWidth = minSize[0];
		minHeight = minSize[1];
		animator = new Animator();
		state = new LogFloatingState(this);
	}
	
	/**
	 * Plays small log's plunge animation
	 * */
	private void updateSprite() {
		frame = (frame+1);
		sprite = "SmallLog"+(frame+1);
		isSubmerged = frame == 4;
		changeState();
		
	}
	
	private void changeState() {
		if(frame==2) state = new LogPreSubmergedState(this);
		else if(frame==4) state = new LogSubmergedState(this);
	}
	
	@Override
	public boolean inCollision(Element e) {
		super.inCollision(e);
		if(!animator.isRunning()) {
			animator.animate(400,5,new Runnable() {public void run() {updateSprite();}});
		}
		return false;
	}
	
	@Override 
	public int getMinWidth() {
		return minWidth;
	}
	
	@Override
	public int getMinHeight() {
		return minHeight;
	}
	
	@Override
	public Rectangle getBounds() {
		return state.getBounds();
	}
	
	@Override
	public boolean isVisible() {
		return state.isVisible();
	}
	
}
