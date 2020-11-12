package dominio;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.Timer;

public class SmallLog extends Log{

	private Timer submerged;
	private boolean isSubmerged;
	private int state;
	public SmallLog(int x, int y, int speed, ImageIcon sprite) {
		super(x, y, speed, sprite);
		state = 0;
		isSubmerged = false;
		submerged = new Timer(400, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				state = (state+1);
				if (state == 3) isSubmerged = true;
				setSprite("./resources/sprites/SmallLog" + (state+1) + ".png");
			}
		});
	}
	
	@Override
	public boolean inCollision(Element e) {
		boolean isDead = false;
		e.move(speed,0);
		if (isSubmerged) {
			isDead = true;
			submerged.stop();
		}
		else {submerged.start();}
		return isDead;
	}
	
	public void drown() {
		
	}

}
