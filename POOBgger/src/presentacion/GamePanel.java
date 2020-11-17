package presentacion;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import dominio.Element;
import dominio.POOgger;

@SuppressWarnings("serial")
public class GamePanel extends JPanel{
	private POOgger poogger;
	private HashMap<String,Image> sprites;
	private int width = 672;
	private int height = 757;
	
	public GamePanel() {
		prepareElementos();
		prepareAcciones();
	}
	public boolean refresh() {
		return poogger.isPlayerAlive();
	}
	private void prepareElementos() {
		poogger = new POOgger(672,757, prepareArchivos());
	}
	
	private HashMap<String,int[]> prepareArchivos() {
		sprites = new HashMap<String,Image>();
		HashMap<String,int[]> spritesSize = new HashMap<String,int[]>();
		File[] files = new File("./resources/sprites/").listFiles();
		for(File file: files) {
			ImageIcon image = new ImageIcon(file.getPath());
			String key = file.getName().replaceFirst(".png", "");
			sprites.put(key, image.getImage());
			spritesSize.put(key,new int[] {image.getIconWidth(),image.getIconHeight()});
		}
		return spritesSize;
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
		g.drawImage(new ImageIcon("./resources/Fondo.png").getImage(),0,0,null);
		for(Element i: poogger.update()) {
			g.drawImage(sprites.get(i.getSprite()),i.getX(),i.getY(),null);
		}
		//drawGrid(g);
		g.setColor(Color.BLUE);
		g.fillRect(poogger.getClock().x, poogger.getClock().y, poogger.getClock().width, poogger.getClock().height);
		g.drawImage(sprites.get(poogger.getPlayer().getSprite()),poogger.getPlayer().getX(),poogger.getPlayer().getY(),null);
	}
	
	public void checkPlayersCollision() {
		boolean touchingSomething = false;
		try {
			for(Element element: poogger.getElements()) {
				boolean[] checksCollision =  poogger.checkPlayerCollision(poogger.getPlayer(), element);
				touchingSomething = touchingSomething || checksCollision[1];
				if(checksCollision[0]) {
					poogger.killPlayer(poogger.getPlayer());
				}
			}
			if (!touchingSomething && poogger.getPlayer().getY() < 343) {
				poogger.killPlayer(poogger.getPlayer());
			}
		}catch(Exception e) {}
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
		frame.setBackground(Color.BLACK);
		frame.setLocationRelativeTo(null);
		while(true) {
			game.checkPlayersCollision();
			game.repaint();
			Thread.sleep(10);
		}
	}
}
