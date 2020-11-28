package dominio;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

/**
 * POOgger's motorcycle implementation
 * @version 2.1
 * @author Angie Medina - Jose Perez
 * */
public class Motorcycle extends Element{
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
	public Motorcycle(int x, int y, int speed,boolean flipped){
		this.x = x;
		this.y = y;
		this.speed = speed;
		orientation = flipped?"F":"";
		frame = 0;
		sprite = "Motorcycle"+(frame+1)+orientation;
		animator = new Timer(150, new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				
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
			isDead = false;
			((Pushable)e).addPush(-96,"W");
		}
		return isDead;
	}
}
