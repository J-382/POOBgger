package dominio;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

/** POOgger Playable element behavior
 * @version 2.1
 * @author Angie Medina - Jose Perez
 * */
public abstract class Playable extends Element{
	protected boolean isToxic;
	protected boolean isArmored;
	protected boolean isFast;
	protected boolean isInAir;
	protected int lives;
	protected int points;
	protected int bonus;
	protected char orientation;
	
	/**
	 * Add bonus to the playable element
	 * @param bonus integer with the bonus
	 * */
	public void addBonus(int bonus) {
		this.bonus += bonus;
	}
	
	/**
	 * Make the playable element toxic
	 * */
	public void makeToxic() {
		if(isToxic) {
			isToxic = true;
			Timer toxic = new Timer(4000,new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					isToxic = false;
				}
			});
			toxic.start();
		}
	}
	
	@Override
	public boolean isPlayable() {
		return true;
	}
	
	public char getOrientation() {
		return orientation;
	}
	
	public boolean isInAir() {
		return isInAir;
	}
}
