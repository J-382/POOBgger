package dominio;

public class Puddle extends Fixed{
	
	public Puddle(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.isVisible = true;
		sprite = "Puddle";
	}
	
	public boolean inCollision(Element e) {
		if(e.isPushable() && e.isPlayable() && !((Playable) e).isInAir()) { 
			((Pushable) e).addPush(96, "D");	
		}
		return false;
	}

}
