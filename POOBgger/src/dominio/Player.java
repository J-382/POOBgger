package dominio;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

/**
 * POOgger's player implementation
 * @version 1.9
 * @author Angie Medina - Jose Perez
 * */
public class Player extends Playable implements Pushable{
	private final int delta = 48;
	private int maxX;
	private int maxY;
	private int state;
	private int[] dimensions;
	private char orientation;
	private boolean beingCarried;
	private Carrier carrier;
	private Animator animator;
	
	public Player(int initialLives,int maxX, int maxY, int[] dimensions) {
		lives = initialLives;
		this.maxX = maxX;
		this.maxY = maxY;
		points = 0;
		x = 336;
		y = 6;//678;
		orientation = 'W';
		state = 0;
		sprite =  "Frog"+(state+1)+orientation;
		beingCarried = false;
		animator = new Animator();
		this.dimensions = dimensions; 
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
		if(canMove(dx,dy)) {
			super.move(dx, dy);
			state = (state+1)%3;
			sprite =  "Frog"+(state+1)+"W";
			if(!animator.isRunning()) {
				animator.animate(25,3,new Runnable() {public void run() {move();}});
			}
		}else {
			state = 0;
			sprite =  "Frog"+(state+1)+"W";//+orientation;
		}
	}
	
	
	public void setOrientation(char orientation) {
		if(!animator.isRunning()) {
			this.orientation = orientation;
			move();
			if(beingCarried) {
				stopBeignCarried();
			}
		}
	}
	
	public void stopBeignCarried() {
		carrier.stopCarrying(this);
		carrier = null;
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
		return revives;
	}
	
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
		state = 0;
		animator.stop();
		carrier = c;
	}
	
	@Override
	public void move(int dx,int dy) {
		if(canMove(dx,dy)) {
			super.move(dx, dy);
		}
		else {
			if(beingCarried) {
				if(x+dx<0) {
					setOrientation('D');
				}else setOrientation('A'); 
			}
		}
	}
	
	@Override 
	public String getSprite() {
		String returnImage = sprite;
		if(beingCarried) {
			returnImage = "";
		}
		return returnImage;
	}
	
	@Override
	public boolean setPosition(int x, int y) {
		boolean canMove = false;
		if(x>=0 && x<=maxX && y>=0 && y<=maxY) {
			this.x = x;
			this.y = y;
			canMove = true;
		}
		return canMove;
		
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
	
	public int[] getDimensions() {
		return dimensions;
	}

	public int getLives() {
		return lives;
	}
}
