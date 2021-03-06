package dominio;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import java.io.File;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 * Pogger's Eagle implementation
 * @author Angie Medina - Jose Perez
 * @version 1.0
 */
public class Eagle extends Mobile{

	private int state;
	private int[] chasePoint;
	private boolean chaseMood;
	private Animator animator;
	private Player toChase;
	private Timer timerToChase;
	private Timer timerStill;
	private int dy;
	private int dx;
	private boolean onAir;
	private transient Clip sound;
	private boolean canKill;
	
	/**
	 * Eagle Class constructor
	 * @param xPos Eagle's x initial position
	 * @param yPos Eagle's y initial position
	 * @param speed Eagle's speed
	 * @param size Eagle's dimensions
	 * @param sprite Eagle's sprite name
	 * @param player Eagle's target
	 */
	public Eagle(int xPos, int yPos, int speed,int[] size, String sprite, Player player) {
		this.sprite = sprite;
		this.isVisible = true;
		this.width = size[0];
		this.height = size[1];
		toChase = player;
		x = xPos;
		y = yPos;
		dy = 1;
		dx = 0;
		state = 0;
		onAir = true;
		canKill = true;
		try {
			sound = AudioSystem.getClip();
			sound.open(AudioSystem.getAudioInputStream(new File("resources/Sounds/eagleSound.wav")));
			sound.loop(0);
		} catch(Exception e) {
			POOgger.registre(e);
		}
		chaseMood = false;
		timerToChase = new Timer(5000, new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				timerToChase.stop();
				chasePoint = new int[] {toChase.getX(), toChase.getY()};
				chaseMood = true;
			}
		});
		timerStill = new Timer(800, new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				y += 1;
				onAir = true;
				timerStill.stop();
			}
		});
		animator = new Animator();
	}
	
	/**
	 * Plays Eagle's move animation
	 * */
	private void updateFlySprite() {
		state =  (state + 1)%30;
		sprite = "Eagle"+((state>4?0:state) + 1);
	}

	@Override
	public void move() {
		if(y <= 100) {
			super.move(dx, dy);
			if (!animator.isRunning()) {
				animator.animate(100, 2, new Runnable() {public void run() {updateFlySprite();}});
			}
		}
		else {
			if (!timerToChase.isRunning() && !chaseMood) timerToChase.start();
			if (chaseMood) {
				dy = 1;
				dx = 0;
				if (y + dy == chasePoint[1] || y == chasePoint[1]) {
					onAir = false;
					dx = 0;
					dy = 0;
					if (!timerStill.isRunning()) timerStill.start();
				}
			}
			else {
				dx = toChase.getX() > x ? 1 : -1;
				dy = 0;
			}
			super.move(dx, dy);
			if (!animator.isRunning()) {
				animator.animate(50, 2, new Runnable() {public void run() {updateFlySprite();}});
			}
		}
	}
	
	@Override
	public boolean inCollision(Element e) {
		boolean isDead = false;
		if(e.isPlayable() && canKill) {
			isDead = !((Playable) e).isToxic();
			if(!isDead) isVisible = false;
			canKill = false;
		}
		return isDead;
	}

	@Override
	protected void stopAnimator() {
		animator.stop();
	}
	
	@Override
	protected void resumeAnimator() {
		animator.resume();
	}
	
	@Override
	public Power destroy() {
		isVisible = false;
		return new FlyPower(x,y,48,48);
	}
}
