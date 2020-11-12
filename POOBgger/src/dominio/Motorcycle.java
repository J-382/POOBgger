package dominio;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.Timer;

public class Motorcycle extends Element{
	private Timer animator;
	private int frame;
	private String orientation;
	private int speed;
	
	public Motorcycle(int x, int y, int speed,boolean flipped){
		this.x = x;
		this.y = y;
		this.speed = speed;
		orientation = flipped?"F":"";
		frame = 0;
		System.out.println("./resources/sprites/Motorcycle"+(frame+1)+orientation+".png");
		sprite = new ImageIcon("./resources/sprites/Motorcycle"+(frame+1)+orientation+".png");
		animator = new Timer(150, new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				frame = (frame+1)%2;
				sprite = new ImageIcon("./resources/sprites/Motorcycle"+(frame+1)+orientation+".png");
			}
		});
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
			String dir = e.getX()<x?"A":"D";
			int maxPush = ((Pushable) e).calculateMaxPush(dir);
			if(maxPush!=0) {
				((Pushable) e).addPush(maxPush,dir);
				isDead = false;
			}
			System.out.println(maxPush);
		}
		return isDead;
	}
}
