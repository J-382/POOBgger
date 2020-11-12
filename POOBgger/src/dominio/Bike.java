package dominio;

import javax.swing.ImageIcon;

public class Bike extends Carrier{
	
	private Animator animator;
	private int frame;
	private String orientation;
	
	
	public Bike(int x, int y, int speed,boolean flipped){
		this.x = x;
		this.y = y;
		this.speed = speed;
		state = "";
		orientation = flipped?"F":"";
		frame = 0;
		animator = new Animator();
		sprite = new ImageIcon("./resources/sprites/Bike"+state+(frame+1)+orientation+".png");
	}
	
	public void updateSprite() {
		frame = (frame+1)%2;
		sprite = new ImageIcon("./resources/sprites/Bike"+state+(frame+1)+orientation+".png");
	}
	
	@Override
	public void move() {
		super.move();
		if(!animator.isRunning()) {
			animator.animate(200,2,new Runnable() {public void run() {updateSprite();}});
		}
	}
	
}
