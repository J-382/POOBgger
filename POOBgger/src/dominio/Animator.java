package dominio;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;

import javax.swing.Timer;

/** Delayed Looper
 * @version 1.2
 * @author Angie Medina - Jose Perez
 */
public class Animator implements Serializable{
	private Timer timer;
	private int frame;
	private boolean isPaused;
	
	/**Animator class constructor*/
	public Animator() {}
	
	/**
	 * Returns if the animator is running
	 * @return true if the animator is running, false otherwise
	 */
	public boolean isRunning() {
		boolean running = false;
		if(timer!=null) {
			running = timer.isRunning();
		}
		return running;
	}
	
	/**
	 * Stops the animator
	 */
	public void stop() {
		if(timer!=null) {
			timer.stop();
			isPaused = true;
		}
	}
	
	/**
	 * Resumes the animator
	 */
	public void resume() {
		if (timer != null &&!timer.isRunning()) {
			timer.start();
			isPaused = false;
		}
	}
	
	/**
	 * Starts a loop with the given params, if stopAtEnd is false, the animator restarts itself
	 * @param delay time between each iteration
	 * @param frames numbers of iteration, if stopAtEnd is false, frames must be at lease 2
	 * @param run code to run in each iteration
	 */
	public void animate(int delay, int frames, Runnable run) {
		animate(delay,frames,run,true);
	}
	
	/**
	 * Returns if the animator is paused
	 * @return a boolean that indicates if the animator is paused
	 */
	public boolean isPaused() {
		return isPaused;
	}
	
	/**
	 * Starts a loop with the given params, if stopAtEnd is false, the animator restarts itself
	 * @param delay time between each iteration
	 * @param frames numbers of iteration, if stopAtEnd is false, frames must be at lease 2
	 * @param run code to run in each iteration
	 * @param stopAtEnd indicates if the loop stops at the end
	 */
	public void animate(int delay, int frames, Runnable run, boolean stopAtEnd) {
		animate(delay,frames,run,null,stopAtEnd);
	}
	
	/**
	 * Starts a loop with the given params, if stopAtEnd is false, the animator restarts itself
	 * @param delay time between each iteration
	 * @param frames numbers of iteration, if stopAtEnd is false, frames must be at lease 2
	 * @param run code to run in each iteration
	 * @param run code to run when animation end
	 * @param stopAtEnd indicates if the loop stops at the end
	 */
	public void animate(int delay, int frames, Runnable run, Runnable doWhenAnimationStop, boolean stopAtEnd) {
		frame = 0;
		isPaused = false;
		timer = new Timer(delay, new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if(frame<frames) {
					run.run();
					frame++;
				}else if (!stopAtEnd){
					frame = 0;
				}else {
					if(doWhenAnimationStop!=null) doWhenAnimationStop.run();
					((Timer) e.getSource()).stop();
				}
				
			}
		});
		timer.start();
	}
}
