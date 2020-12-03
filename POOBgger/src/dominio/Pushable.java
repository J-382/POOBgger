package dominio;

/** POOgger Pushable element behavior
 * @version 3.0
 * @author Angie Medina - Jose Perez
 * */
public interface Pushable {
	
	/**
	 * Entangles the Pushable's movements with the Carrier's movements
	 * @param c the Carrier Element
	 * */
	public void beingCarried(Carrier c);

	/**
	 * Sets, if possible, the Pushables's position to the given coordinates
	 * @return true if sets the position, false otherwise
	 * */
	public boolean setPosition(int x, int y);
	
	/**
	 * Push the Pushable with the given params
	 * @param push push's magnitude
	 * @param dir push's direction
	 * */
	public void addPush(int push, String dir);
	
	/**
	 * Modifies the Pushable visibility
	 * @param visible Pushable's new visibility
	 */
	public void setVisible(boolean visible);
	
	/**
	 * Returns the Pushable's orientation
	 * @return the Pushable's orientation
	 */
	public char getDir();
	
	/**
	 * Returns the Pushable is being carried
	 * @return if Pushable is being carried
	 */
	public boolean isBeingCarried();
}
