package dominio;

public class Bug extends Fixed{
	
	private int x;
	private int y;
	private int points;
	public Bug(int x, int y) {
		this.x = x;
		this.y = y;
		points = 200;
	}
	
	public void desapear() {
		
	}
	
	@Override
	public boolean inCollision(Element e) {
		if(e.isPlayable()) {
			((Player)e).changePoints(points);
		}
		return false;
	}
}
