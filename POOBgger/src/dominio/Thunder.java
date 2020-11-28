package dominio;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import java.io.File;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Thunder extends Element{

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
	
	public Thunder(Player player) {
		this.sprite = "Alligator1";
		toChase = player;
		dy = 20;
		dx = 0;
		state = 0;
		onAir = true;
		chasePoint = new int[] {toChase.getX(), toChase.getY()};
		x = chasePoint[0];
		y = 4;
		try {
			sound = AudioSystem.getClip();
			sound.open(AudioSystem.getAudioInputStream(new File("resources/Sounds/ThunderSound.wav")));
			sound.loop(0);
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		chaseMood = false;
		timerStill = new Timer(800, new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				y += 1;
				onAir = true;
				timerStill.stop();
			}
		});
		animator = new Animator();
	}
	
	private void updateFlySprite() {
		state =  (state + 1)%2;
		y+=state==0?15:-15;
		sprite = "Alligator"+(state + 1);
	}

	@Override
	void move() {
		if (x + dx == chasePoint[0] && y + dy == chasePoint[1]) {
			onAir = false;
			dx = 0;
			dy = 0;
			if (!timerStill.isRunning()) timerStill.start();
		}
		super.move(dx, dy);
		if (!animator.isRunning()) {
			animator.animate(1000, 2, new Runnable() {public void run() {updateFlySprite();}});
		}
	}
	@Override
	public boolean inCollision(Element e) {
		return true;
	}

}
