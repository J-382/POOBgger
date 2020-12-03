package dominio;

import java.awt.Rectangle;
import java.io.File;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Thunder extends Fixed{

	private int frame;
	private boolean falling;
	private boolean impacted;
	private int[] chasePoint;
	private Animator animator;
	private transient Clip sound;
	
	public Thunder(int width, int height, int y,Player player) {
		this.sprite = "Thunder1";
		this.isVisible = true;
		this.width = width;
		this.height = height;
		falling = false;
		this.x = player.getX()-width/3;
		this.y = -height/2-(y-player.getY());
		chasePoint = new int[] {x,player.getY()};
		animator = new Animator();
		playSound();
		animator.animate(300, 3, new Runnable() {public void run() {fall();}});
	}
	
	/**
	 * Thunder fall animator, changes the Thunder's state to falling
	 */
	private void fall() {
		if(falling) {
			animator.stop();
			animator.animate(50, 12, new Runnable() {public void run() {updateSprite();}});
		}
		else falling = true;
	}
	
	/**
	 * Plays the Thunder's sound
	 */
	private void playSound() {
		try {
			sound = AudioSystem.getClip();
			sound.open(AudioSystem.getAudioInputStream(new File("resources/Sounds/thunderSound.wav")));
			sound.loop(0);
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Changes the Thunder's sprite
	 */
	private void updateSprite() {
		frame =  (frame + 1)%12;
		sprite = "Thunder"+(frame + 1);
		impacted = frame>6;
		isVisible = !(frame>=11);
	}
	
	@Override
	public Rectangle getBounds() {
		Rectangle bound = impacted?new Rectangle(chasePoint[0],chasePoint[1],width,height):new Rectangle(0,0,0,0);
		return bound;
	}

	public boolean inCollision(Element e) {
		return true;
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
	public boolean isDestructible() {
		return false;
	}
}
