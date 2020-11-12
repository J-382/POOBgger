package dominio;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public class Animator {
	private Timer timer;
	private int frame;
	
	public Animator() {}
	
	public boolean isRunning() {
		boolean running = false;
		if(timer!=null) {
			running = timer.isRunning();
		}
		return running;
	}
	
	public void stop() {
		if(timer!=null) {
			timer.stop();
		}
	}
	
	public void animate(int delay, int frames, Runnable run) {
		animate(delay,frames,run,true);
	}
	
	public void animate(int delay, int frames, Runnable run, boolean stopAtEnd) {
		frame = 0;
		timer = new Timer(delay, new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				frame++;
				if(frame<frames) {
					run.run();
				}else if (!stopAtEnd){
					frame = 0;
				}else {
					((Timer) e.getSource()).stop();
				}
			}
		});
		timer.start();
	}
}
