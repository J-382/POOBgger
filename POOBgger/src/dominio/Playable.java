package dominio;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

/** POOgger Playable element behavior
 * @version 3.0
 * @author Angie Medina - Jose Perez
 * */
public abstract class Playable extends Mobile{
	protected int cavesReach;
	protected int roundsWon;
	protected boolean isAlive;
	protected boolean isToxic;
	protected boolean isArmored;
	protected boolean isFast;
	protected boolean isInAir;
	protected boolean isFlying;
	protected boolean canFly;
	protected int initX;
	protected int initY;
	protected int initLives;
	protected int lives;
	protected int points;
	protected int bonus;
	protected char orientation;
	private Timer toxic;
	private Timer fly;
	private Timer fast;
	protected PlayerState frogState;
	
	/**
	 * Add bonus to the playable element
	 * @param bonus integer with the bonus
	 * */
	public void addBonus(int bonus) {
		this.bonus += bonus;
	}
	
	public void makeToxic(boolean isToxic) {
		this.isToxic = isToxic;
	}
	
	/**
	 * Make the playable element toxic
	 * */
	public void makeToxic() {
		if(isToxic) {
			toxic = new Timer(4000,new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					isToxic = false;
				}
			});
			toxic.start();
		}
	}
	
	public void makeArmored(boolean isArmored) {
		this.isArmored = isArmored;
	}
	
	public void makeFast(boolean isFast) {
		this.isFast = isFast;
		makeFast();
	}
	
	public void makeFast() {
		if(isFast) {
			fast = new Timer(4000,new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					isFast = false;
				}
			});
			fast.start();
		}
	}
	
	public void makeFly(boolean canFly) {
		this.canFly = canFly;
	}
	
	
	/*Probablemente se deba hacer los timers como una variable privada*/
	public void makeFly() {
		if(canFly) {
			isFlying = true;
			canFly = false;
			fly = new Timer(1000,new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					isFlying = false;
				}
			});
			fly.start();
		}
	}
	
	@Override
	public boolean isPlayable() {
		return true;
	}
	
	public boolean isMachine() {
		return false;
	}
	
	public char getOrientation() {
		return orientation;
	}
	
	public boolean isInAir() {
		return isInAir;
	}
	
	public boolean canFly() {
		return canFly;
	}
	
	public boolean isToxic() {
		return isToxic;
	}
	
	public boolean isArmored() {
		return isArmored;
	}
	
	public boolean isAlive() {
		return isAlive;
	}
	
	public void changeSprite() {
	}
	
	public boolean inCollision(Element e) {
		return false;
	}
	
	protected void changeState(PlayerState playerState) {
		frogState = playerState;
	}
	
	@Override
	public Rectangle getBounds() {
		return frogState.getBounds();
	}
}
