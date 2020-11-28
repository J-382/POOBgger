package dominio;

import java.util.Random;

public class Thoughtless extends Player{

	public Thoughtless(int initialLives, int initX, int initY, int[] size) {
		super(initialLives, initX, initY, size);
	}

	
	
	@Override
	public void setOrientation(char orientation) {
		Random r = new Random();
		if(!animator.isRunning()) {
			switch(r.nextInt(4)) {
				case 0:
					super.setOrientation('W');
					break;
				case 1:
					super.setOrientation('A');
					break;
				case 2:
					super.setOrientation('S');
					break;
				case 3:
					super.setOrientation('D');
					break;
			}
		}
	}
	
	@Override
	public boolean isMachine() {
		return true;
	}
}
