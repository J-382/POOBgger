package dominio;

public class Puddle extends Fixed{
	
	public Puddle(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	@Override
	public boolean inCollision(Element e) {
		if(e.isPushable()) {
			((Pushable) e).addPush(96, "D");
		}
		return false;
	}
}
