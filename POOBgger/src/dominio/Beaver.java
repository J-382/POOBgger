package dominio;

/**
 * POOgger's beaver implementation
 * @author Angie Medina - Jose Perez
 * @version 1.0
 * */
public class Beaver extends Fixed{
	/**
	 * */
	public Beaver(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.isVisible = true;
	}
	
	public boolean inCollision(Element e) {
		boolean isDead = false;
		if(e.isPlayable()) {
			if(((Playable) e).canFly()) {
				System.out.println("Pito");
				((Playable) e).makeFly();
			}
			isDead = !((Playable) e).isInAir();
		}
		return isDead;
	}
}
