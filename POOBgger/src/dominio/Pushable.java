package dominio;

/** POOgger Pushable element behavior
 * @version 1.3
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
	
	public void setVisible(boolean visible);
	
	public char getDir();
	
	public boolean isBeingCarried();
}
