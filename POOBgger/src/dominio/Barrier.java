package dominio;

public class Barrier extends Fixed{
	private boolean killer;
	private int gridSize;
	/**
	 * Barrier class's constructor
	 * @param x x position
	 * @param y y position
	 * @param width barrier's width
	 * @param height barrier's height
	 * @param gridSize size of game's grid
	 * @param killer indicates if the barrier must kill players
	 */
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
	
	/**
	 * Calculate a push to keep the element outside the barrier
	 * @param dir Push's directions
	 * @param e Element to push
	 * @return
	 */
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
