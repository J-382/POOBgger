package dominio;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public abstract class Playable extends Element{
	protected boolean isToxic;
	protected boolean isArmored;
	protected boolean isFast;
	protected boolean canFly;
	protected int lives;
	protected int points;
	protected int bonus;
	
	
	public void addBonus(int bonus) {
		this.bonus += bonus;
	}
	
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
}
