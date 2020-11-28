package dominio;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

/**
 * POOgger's motorcycle implementation
 * @version 2.1
 * @author Angie Medina - Jose Perez
 * */
public class Motorcycle extends Mobile{
	private Timer animator;
	private int frame;
	private String orientation;
	private int speed;
	
	/**
	 * Motorcycle class constructor
	 * @param x Motorcycle's x position
	 * @param y Motorcycle's y position
	 * @param speed Motorcycle's speed
	 * @param flipped if the Motorcycle are flipped horizontally
	 * */
	public Motorcycle(int x, int y, int speed, int[] size, String sprite, boolean flipped){
		this.x = x;
		this.y = y;
		this.width = size[0];
		this.height = size[1];
		this.speed = speed;
		this.isVisible = true;
		orientation = flipped?"F":"";
		frame = 0;
		this.sprite = sprite+orientation;
		animator = new Timer(150, new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				updateSprite();
			}
		});
	}
	
	/**
	 * Plays Motorcycle's move animation
	 * */
	private void updateSprite() {
		frame = (frame+1)%2;
		sprite = "Motorcycle"+(frame+1)+orientation;
	}
	
	@Override
	public void move() {
		x += speed;
		if(!animator.isRunning()) {
			animator.start();
		}
	}
	
	@Override
	public boolean inCollision(Element e) {
		boolean isDead = true;
		if(e.isPushable()) {
			((Pushable) e).addPush(-96,"W");
			isDead = false;
		}
		return isDead;
	}
}
