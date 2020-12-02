package dominio;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

/**
 * POOgger's player implementation
 * @version 2.6
 * @author Angie Medina - Jose Perez
 * */
public class Player extends Playable implements Pushable{
	
	private final int delta;
	private int state;
	private int minReachY;
	private int lastMove;
	private Clock clock;

	protected boolean beingCarried;
	protected Carrier carrier;
	protected Animator animator;

	private String name;
	private String hat;
	
	
	/**
	 * Player class constructor
	 * @param initialLives Player's initialLives
	 * @param maxX Player's POOgger width
	 * @param maxY Player's POOgger height
	 * @param dimensions Player's size
	 */
	public Player(int initialLives,int initX, int initY, int[] size, String name, String hat) {
		this.width = size[0];
		delta = size[0];
		this.height = size[1]; 
		this.isVisible = true;
		this.lives = initialLives;
		initLives = initialLives;
		this.initX = initX;
		this.initY = initY;
		cavesReach = 0;
		roundsWon = 0;
		isAlive = true;
		this.hat = hat;
		this.name = name;
		clock = new Clock();
		animator = new Animator();
		resetPlayer();
	}
	
	public void move() {
		int delay = isFast?25:50;
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
				changePoints(10);
			}
		}
		super.move(dx, dy);
		updateSprite();
		if(!animator.isRunning()) {
			animator.animate(delay,2,new Runnable() {public void run() {move();}});
		}
		isInAir();
	}
	
	public boolean updateClock() {
		return clock.updateClock();
	}
	
	public Rectangle getClock() {
		return clock.getClock();
	}
	
	private void updateSprite() {
		state = (state+1)%3;
		sprite =  "Frog"+(state+1)+orientation;	
	}
	
	/**
	 * Changes, if possible, the player orientation
	 * @param orientation new orientation
	 * */
	public void setOrientation(char orientation) {
		if(!animator.isRunning()) {
			this.orientation = orientation;
			move();
			isFlying = false;
			if(beingCarried) {
				stopBeignCarried();
			}
		}
	}
	
	protected void stopBeignCarried() {
		if (carrier != null) {
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
	}
	
	/**
	 * Returns if the player is alive
	 */
	public boolean isAlive() {
		return isAlive;
	}
	
	/**
	 * Decrease the player lives and reset his position
	 * @param initx x inital position
	 * @param initx y inital position
	 * */
	public boolean decreasePlayerLives() {
		lives--;
		if (lives >= 1) {
			resetPlayer();
		}else isAlive = false;
		return isAlive;
	}
	
	public void resetPlayer() {
		orientation = 'W';
		sprite =  "Frog"+(state+1)+orientation;
		animator.stop();
		clock.restoreClock();
		state = 0;
		x = initX;
		y = initY;
		minReachY = y;
		lastMove = 0;
		isToxic = false;
		isArmored = false;
		isFast = false;
		canFly = false;
		isFlying = false;
		if(carrier!=null) stopBeignCarried(); 
		carrier = null;
	}
	
	/**
	 * Change the player point by some amount
	 * @param value, the amount to add to the player's pointz
	 */
	public void changePoints(int value) {
		points += value;
	} 
	
	/**
	 * Increase the caves that the player have reached
	 */
	public void increaseCavesReach() {
		cavesReach++;
	}
	
	public void increaseRoudsWon() {
		roundsWon++;
	}
	
	public int getRoundsWon() {
		return roundsWon;
	}
	
	public int getCavesReach() {
		return cavesReach;
	}
	
	/**
	 * Returns player's size
	 * @return player's size
	 * */
	public int[] getDimensions() {
		return new int[] {width,height};
	}
	
	/**
	 * Returns player's lives
	 * @return player's lives
	 * */
	public int getLives() {
		return lives;
	}
	
	/**
	 * Returns player's initial lives
	 */
	public int getInitialLives() {
		return initLives;
	}
	
	/**
	 * Returns the player's points
	 **/
	public int getPoints() {
		return points;
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
	public String getSprite() {
		String returnImage = isVisible?sprite:"";
		return returnImage;
	}
	 
	public String getHat() {
		String returnImage = isVisible?hat:"";
		return returnImage;
	}
	
	public String getName() {
		return name;
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
		if(dir.equals("W") || dir.equals("S")) super.move(0, push);
		else super.move(push, 0);
	}
	
	@Override 
	public void setVisible(boolean visible) {
		isVisible = visible;
	}

	@Override
	public char getDir() {
		return orientation;	
	}
	
	@Override
	public boolean isBeingCarried() {
		return beingCarried;
	}
	
	@Override
	public boolean isInAir() {
		isInAir = !(state==0) || isFlying;
		return isInAir;
	}
	
	@Override
	protected void stopAnimator() {
		animator.stop();
	}
	
	@Override
	protected void resumeAnimator() {
		animator.resume();
	}
}
