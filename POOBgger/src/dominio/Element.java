package dominio;

import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public abstract class Element {
	protected int x;
	protected int y;
	protected ImageIcon sprite;
	abstract void move();
	public Image getSprite() {
		return sprite.getImage();
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public void move(int dx, int dy) {
		x += dx;
		y += dy;
	}
	public boolean inCollision(Element e) {
		return false;
	};
	
	public Rectangle getBounds() {
		return new Rectangle(x,y,sprite.getIconWidth(),sprite.getIconHeight());
	}
	
	public boolean isPushable() {
		return false;
	}
	
	public boolean isCarriable() {
		return false;
	}
	
	public boolean isPlayable() {
		return false;
	}
	
	public void kill() {}
	
}
