package dominio;

public class Barrier extends Fixed{
	private boolean killer;
	private int gridSize;
	public Barrier(int x, int y, int width, int height, int gridSize, boolean killer) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.gridSize = gridSize;
		this.killer = killer;
		this.sprite = "Rectangle";
		this.isVisible = true;
	}
	
	private int calculatePush(char dir, Element e) {
		int push = 0;
		switch (dir){
			case 'W':
				push = y+height-e.getY();
				break;
			case 'S':
				push = y-e.getY()-gridSize;
				break;
			case 'D':
				push = x-e.getX()-gridSize;
				break;
			case 'A':
				push = x+width-e.getX();
				break;
				
		}
		return push;
	}
	
	public boolean inCollision(Element e) {
		Boolean isDead = killer;
		if(!killer) {
			if(e.isPushable()) {
				if(!((Pushable) e).isBeingCarried()) {
					Pushable toPush = (Pushable) e;
					char dir = toPush.getDir();
					toPush.addPush(calculatePush(dir,e),""+dir);
				}else isDead = true;
			}
		}
		return isDead;
	}
}
