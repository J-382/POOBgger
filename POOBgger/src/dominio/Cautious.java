package dominio;

import java.util.Random;

/**
 * POOgger's implementation of the Cautious machine
 * @version 1.0
 * @author Angie Medina - Jose Perez
 */

public class Cautious extends Thoughtless{

	/**
	 * Cautious player class constructor
	 * @param initialLives Player's initialLives
	 * @param maxX Player's POOgger width
	 * @param maxY Player's POOgger height
	 * @param size Player's size
	 * @param name, Player's name
	 * @param hat, Player's personalization hat
	 */
	public Cautious(int initialLives, int initX, int initY, int[] size, String name, String hat) {
		super(initialLives, initX, initY, size, name, hat);
	}
	
	/**
	 * If its possible use one power
	 */
	public void usePower() {
		char power = ' ';
		if (canBeArmored) power = '1';
		else if (canBeFast) power = '2';
		else if(canBeFlying) power = '3';
		activatePower(power);
	}
}
