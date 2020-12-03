package dominio;

import java.util.Random;

/**
 * POOgger's implementation of the Thoughtless machine
 * @version 1.0
 * @author Angie Medina - Jose Perez
 */
public class Thoughtless extends Player{
	/**
	 * Thoughtless player class constructor
	 * @param initialLives Player's initialLives
	 * @param maxX Player's POOgger width
	 * @param maxY Player's POOgger height
	 * @param size Player's size
	 * @param name, Player's name
	 * @param hat, Player's personalization hat
	 */
	public Thoughtless(int initialLives, int initX, int initY, int[] size, String name, String hat) {
		super(initialLives, initX, initY, size, name, hat);
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
