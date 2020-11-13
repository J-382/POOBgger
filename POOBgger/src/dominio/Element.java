package dominio;
import java.awt.Rectangle;

/** POOgger basic element behavior
 * @version 1.5
 * @author Angie Medina - Jose Perez
 * */
public abstract class Element {
	protected int x;
	protected int y;
	protected String sprite;
	
	/**
	 * Element automatic move
	 * */
	abstract void move();
	
	/**
	 * Returns a string with the Element's sprite name
	 * @return Element's sprite name
	 * */
	public String getSprite() {
		return sprite;
	}
	
	/**
	 * Returns Element's x position
	 * @return Element's x position
	 * */
	public int getX() {
		return x;
	}
	
	/**
	 * Returns Element's y position
	 * @return Element's y position
	 * */
	public int getY() {
		return y;
	}
	
	/**
	 * Moves the element 
	 * @param dx delta x
	 * @param dy delta y
	 * */
	public void move(int dx, int dy) {
		x += dx;
		y += dy;
	}
	
	/**
	 * returns if the given element can be destroyed upon collision
	 * @param e desired element to check
	 * @Returns true if the elements are collisioning false otherwise
	 * */
	public boolean inCollision(Element e) {
		return false;
	};
	
	/**
	 * Returns the collision box for the element
	 * @param size Element's size
	 * @return A Rectangle for the element's collision box 
	 * */
	public Rectangle getBounds(int[] size) {
		if(size==null) size = new int[] {0,0};
		return new Rectangle(x,y,size[0],size[1]);
	}
	
	/**
	 * Returns if the element is pushable
	 * @Returns True if the element is pushable false otherwise
	 * */
	public boolean isPushable() {
		return false;
	}
	
	/**
	 * Returns if the element is carriable
	 * @Returns True if the element is carriable false otherwise
	 * */
	public boolean isCarriable() {
		return false;
	}
	
	/**
	 * Returns if the element is playable
	 * @Returns True if the element is playable false otherwise
	 * */
	public boolean isPlayable() {
		return false;
	}
	
}
