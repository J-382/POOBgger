package presentacion;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

import dominio.Element;
import dominio.POOgger;

@SuppressWarnings("serial")
public class GamePanel extends JPanel{
	private POOgger poogger;
	public GamePanel() {
		prepareElementos();
		prepareAcciones();
	}
	public boolean refresh() {
		return poogger.isPlayerAlive();
	}
	private void prepareElementos() {
		poogger = new POOgger(672,757);
		//poogger.addLog();
		poogger.addTurtle();
		//poogger.addBike();
		//poogger.addLizzard();
	}
	
	private void prepareAcciones() {
		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				poogger.movePlayer((""+e.getKeyChar()).toUpperCase().charAt(0));
			}
		});
		setFocusable(true);
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		for(Element i: poogger.update()) {
			g.drawImage(i.getSprite(),i.getX(),i.getY(),null);
		}
		drawGrid(g);
		g.drawString("TIME", 500, 500);
		g.setColor(Color.BLUE);
		g.fillRect(poogger.getClock().x, poogger.getClock().y, poogger.getClock().width, poogger.getClock().height);
		g.drawImage(poogger.getPlayer().getSprite(),poogger.getPlayer().getX(),poogger.getPlayer().getY(),null);
	}
	
	public void checkPlayersCollision() {
		for(Element element: poogger.getElements()) {
			if(poogger.checkPlayerCollision(poogger.getPlayer(),element)) {
				poogger.killPlayer(poogger.getPlayer());
			}
		}
	}
	
	public void drawGrid(Graphics g) {
		int spc = 48;
		for(int i=0;i<16;i++) {
			g.drawLine(0,i*spc,800,i*spc);
		}
		for(int i=0;i<16;i++) {
			g.drawLine(i*spc,0,i*spc,800);
		}
	}
	
	public static void main(String args[]) throws InterruptedException {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GamePanel game = new GamePanel();
		frame.add(game);
		frame.setSize(687,757);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		while(true) {
			game.checkPlayersCollision();
			game.repaint();
			Thread.sleep(10);
		}
	}
}
