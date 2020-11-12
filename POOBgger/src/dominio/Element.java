package dominio;
import java.awt.Rectangle;

public abstract class Element {
	protected int x;
	protected int y;
	protected String sprite;
	abstract void move();
	public String getSprite() {
		return sprite;
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
	
	public Rectangle getBounds(int[] size) {
		if(size==null) size = new int[] {0,0};
		return new Rectangle(x,y,size[0],size[1]);
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
	
}
