package dominio;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

/**
 * POOgger's player implementation
 * @version 2.4
 * @author Angie Medina - Jose Perez
 * */
public class Player extends Playable implements Pushable{
	private final int delta = 48;
	private int maxX;
	private int maxY;
	private int state;
	private int[] dimensions;
	private int minReachY;
	private int lastMove;
	
	private boolean beingCarried;
	private Carrier carrier;
	private Animator animator;
	private boolean isVisible;
	
	/**
	 * Player class constructor
	 * @param initialLives Player's initialLives
	 * @param maxX Player's POOgger width
	 * @param maxY Player's POOgger height
	 * @param dimensions Player's size
	 */
	public Player(int initialLives,int maxX, int maxY, int[] dimensions) {
		this.dimensions = dimensions; 
		this.isVisible = true;
		this.lives = initialLives;
		this.isInAir = false;
		this.maxX = maxX;
		this.maxY = maxY;
		points = 0;
		x = 336;
		y = 678;
		lastMove = 0;
		orientation = 'W';
		state = 0;
		sprite =  "Frog"+(state+1)+orientation;
		beingCarried = false;
		animator = new Animator();
		carrier = null;
	}
	
	public void move() {
		int dx = 0, dy = 0;
		switch (""+orientation) {
			case "W" :
				dy-=delta/3;
				break;
			case "S" :
				dy+=delta/3;
				break;
			case "D" :
				dx+=delta/3;
				break;
			case "A" :
				dx-=delta/3;
				break;
		}
		if (("" + orientation).equals("W")) {
			lastMove = (lastMove + 1) % 3;
			if (lastMove == 0 && getY() < minReachY) {
				minReachY = getY();
				increasePoints(10);
			}
		}
		super.move(dx, dy);
		updateSprite();
		if(!animator.isRunning()) {
			animator.animate(25,3,new Runnable() {public void run() {move();}});
		}
	}
	
	private void updateSprite() {
		state = (state+1)%3;
		sprite =  "Frog"+(state+1)+"W";
		
	}
	
	/**
	 * Changes, if possible, the player orientation
	 * @param orientation new orientation
	 * */
	public void setOrientation(char orientation) {
		if(!animator.isRunning()) {
			this.orientation = orientation;
			move();
			if(beingCarried) {
				stopBeignCarried();
			}
		}
	}
	
	private void stopBeignCarried() {
		carrier.stopCarrying(this);
		carrier = null;
		setVisible(true);
		Timer doWhenAnimationStop = new Timer(1,new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!animator.isRunning()) {
					beingCarried = false;
					((Timer) e.getSource()).stop();
				}
			}
		});
		doWhenAnimationStop.start();
	}
	
	/**
	 * Decrease the player lives and reset his position
	 * @param initx x inital position
	 * @param initx y inital position
	 * */
	public boolean decreasePlayerLives(int initx,int inity) {
		boolean revives = false;
		lives--;
		if(lives>0) {
			revives = true;
			animator.stop();
			state = 0;
			x = initx;
			y = inity;
		}
		if(beingCarried) stopBeignCarried(); 
		return revives;
	}
	
	/**
	 * Increase the player point by some amount
	 * @param value, the amount to add to the player's pointz
	 */
	public void increasePoints(int value) {
		points += value;
	} 
	
	/**
	 * Returns player's size
	 * @return player's size
	 * */
	public int[] getDimensions() {
		return dimensions;
	}
	
	/**
	 * Returns player's lives
	 * @return player's lives
	 * */
	public int getLives() {
		return lives;
	}
	
	/**
	 * Returns the player's points
	 **/
	public int getPoints() {
		return points;
	}
	
	/**
	 * Returns if Player can move in the given params
	 * @param dx delta x
	 * @param dy delta y 
	 * */
	private boolean canMove(int dx, int dy) {
		return !(x+dx > maxX || x+dx<0 || y+dy>maxY || y+dy<0);
	}
	
	@Override
	public boolean isCarriable() {
		return !beingCarried;
	}
	
	@Override
	public boolean isPushable() {
		return true;
	}

	@Override
	public void beingCarried(Carrier c) {
		beingCarried = true;
		//animator.stop();
		carrier = c;
	}
	
	@Override
	public void move(int dx,int dy) {
		super.move(dx, dy);
	}
	
	@Override 
	public String getSprite() {
		String returnImage = isVisible?sprite:"";
		return returnImage;
	}
	
	@Override
	public boolean setPosition(int x, int y) {
		animator.stop();
		state = 0;
		this.x = x;
		this.y = y;
		return true;
		
	}

	@Override
	public void addPush(int push, String dir) {
		super.move(push, 0);
	}

	@Override
	public int calculateMaxPush(String dir) {
		int push = 0;
		if(dir.equals("A")) {
			if(x>=96) push = -96;
			else if(x>=48) push = -48;
		}
		if(dir.equals("D")) {
			if(maxX-x>=96) push = 96;
			else if(maxX-x>=48) push = 48;
		}
		return push;
	}
	
	@Override 
	public void setVisible(boolean visible) {
		isVisible = visible;
	}
}
