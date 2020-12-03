package dominio;

/**
 * POOgger's puddle implementation
 * @author Angie Medina - Jose Perez
 * @version 1.0
 */
public class Puddle extends Fixed{
	
	/**
	 * Puddle class constructor
	 * @param x, Puddle's x position
	 * @param y, Puddle's y position
	 * @param width, Puddle's width
	 * @param height, Puddle's height
	 */
	public Puddle(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.isVisible = true;
		sprite = "Puddle";
	}
	
	@Override
	public boolean inCollision(Element e) {
		if(e.isPushable() && e.isPlayable() && !((Playable) e).isInAir()) { 
			((Pushable) e).addPush(96, "D");	
		}
		return false;
	}

}
