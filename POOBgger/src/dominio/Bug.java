package dominio;

public class Bug extends Fixed{
	
	private int points;
	public Bug(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.isVisible = true;
		points = 200;
	}
	
	@Override
	public boolean inCollision(Element e) {
		if(e.isPlayable()) {
			((Player)e).increasePoints(points);
		}
		return false;
	}
}
