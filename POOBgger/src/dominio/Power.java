package dominio;

public abstract class Power extends Fixed{
	protected int duration;
	protected int state;
	
	abstract public void givePower(Playable player);
	
	public boolean inCollision(Element e) {
		isVisible = false;
		if(e.isPlayable()) {
			givePower((Playable) e);
		}
		return false;
	}
}
