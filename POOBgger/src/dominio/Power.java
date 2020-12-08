package dominio;

/**
 * POOgger's Power implementation
 * @author Angie Medina - Jose Perez
 * @version 1.0
 */
public abstract class Power extends Fixed{
	protected int duration;
	protected int state;
	
	public Power(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.state = 0;
		this.isVisible = true;
	}
	
	/**
	 * Give a specific power to the given player
	 * @param player the desired player
	 */
	abstract public void givePower(Playable player);
	
	@Override
	public boolean inCollision(Element e) {
		isVisible = false;
		if(e.isPlayable()) {
			givePower((Playable) e);
		}
		return false;
	}
}
