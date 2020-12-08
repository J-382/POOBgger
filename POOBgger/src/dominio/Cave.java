package dominio;

import java.awt.Rectangle;

/**
 * Pogger's Cave implementation
 * @author Angie Medina - Jose Perez
 * @version 1.0
 */
public class Cave extends Fixed{
	
	boolean occupied;
	final int points = 50;
	private CaveState state;
	
	/**
	 * Cave Class constructor
	 * @param x Cave's x position
	 * @param y Cave's y position
	 * @param width Cave's width
	 * @param height Cave's height
	 */
	public Cave(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.isVisible = true;
		occupied = false;
		state = new CaveNotOccupiedState(this);
		this.sprite = state.getSprite();
	}
	
	/**
	 * Calculates the collision percentage with a given element 
	 * @param e the desired element
	 * @return the collision percentage
	 */
	private double collisionPercentage(Element e) {
		Rectangle player = ((Player) e).getBounds();
		Rectangle cave = new Rectangle(x,y,width,height);
		Rectangle intersection = (Rectangle) cave.createIntersection(player);
		double areaCave = cave.getWidth()*cave.getHeight();
		double areaIntersection = intersection.getWidth()*intersection.getHeight();
		return areaIntersection/areaCave*100;
	}

	/**
	 * Changes the sprite of the cave if the collision percentage with the given element is greater than 40
	 * @param e the desired element
	 */
	private void occupied(Element e) {
		state = !(collisionPercentage(e)<40) ? new CaveOccupiedState(this) : new CaveNotOccupiedState(this);
		sprite = state.getSprite();
	}
	
	/**
	 * Returns if the cave is occupied
	 * @return if the cave is occupied
	 */
	public boolean isOccupied() {
		return state.isOccupied();
	}
	
	/**
	 * Clear the cave if its occupied
	 */
	public void clear() {
		state = new CaveNotOccupiedState(this);
		sprite = state.getSprite();
	}
	
	@Override
	public boolean inCollision(Element e) {
		if(e.isPlayable() && e.isPushable() && !isOccupied()) {
			occupied(e);
		}
		return false;
	}
	
	@Override
	public Rectangle getBounds() {
		return state.getBounds();
	}
	
	@Override
	public boolean canBeOccupied() {
		return true;
	}
	
	@Override
	protected boolean givesBonus() {
		return true;
	}
	
	@Override
	protected int getPoints() {
		return points;
	}
}
