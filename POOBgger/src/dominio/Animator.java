package dominio;

/** Delayed Looper
 * @version 1.2
 * @author Angie Medina - Jose Perez
 * */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;

import javax.swing.Timer;

public class Animator implements Serializable{
	private Timer timer;
	private int frame;
	
	/**Animator class constructor*/
	public Animator() {}
	
	/**
	 * Returns if the animator is running
	 * @return true if the animator is running, false otherwise
	 * */
	public boolean isRunning() {
		boolean running = false;
		if(timer!=null) {
			running = timer.isRunning();
		}
		return running;
	}
	
	/**
	 * Stops the animator
	 * */
	public void stop() {
		if(timer!=null) {
			timer.stop();
		}
	}
	
	
	public void animate(int delay, int frames, Runnable run) {
		animate(delay,frames,run,true);
	}
	
	/**
	 * Starts a loop with the given params, if stopAtEnd is false, the animator restarts itself
	 * @param delay time between each iteration
	 * @param frames numbers of iteration, if stopAtEnd is false, frames must be at lease 2
	 * @param run code to run in each iteration
	 * @param stopAtEnd indicates if the loop stop at the end
	 * */
	public void animate(int delay, int frames, Runnable run, boolean stopAtEnd) {
		frame = 0;
		timer = new Timer(delay, new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if(frame<frames) {
					run.run();
					frame++;
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
