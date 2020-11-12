package dominio;

import javax.swing.ImageIcon;

public class Truck extends Carrier{
	
	private Animator animator;
	private int frame;
	private String orientation;
	
	
	public Truck(int x, int y, int speed,boolean flipped){
		this.x = x;
		this.y = y;
		this.speed = speed;
		state = "";
		orientation = flipped?"F":"";
		frame = 0;
		animator = new Animator();
		sprite = new ImageIcon("./resources/sprites/Truck"+state+(frame+1)+orientation+".png");
	}
	
	public void updateSprite() {
		frame = (frame+1)%2;
		sprite = new ImageIcon("./resources/sprites/Truck"+state+(frame+1)+orientation+".png");
	}
	
	@Override
	public void move() {
		super.move();
		if(!animator.isRunning()) {
			animator.animate(5,2,new Runnable() {public void run() {updateSprite();}});
		}
	}
	
	@Override
	public boolean inCollision(Element e) {
		if(e.isPlayable()) {
			((Playable) e).makeToxic();
		}
		return super.inCollision(e);
	}
	
}