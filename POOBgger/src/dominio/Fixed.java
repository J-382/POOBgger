package dominio;

/**
 * Fixed Object implementation
 * @author Angie Medina - Jose Perez
 * @version 1.2
 */
public abstract class Fixed extends Element {
	
	/**
	 * Indicates if the Fixed element can be occupied
	 * @return if the Fixed element can be occupied
	 */
	public boolean canBeOccupied() {
		return false;
	}
}

