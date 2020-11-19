package presentacion;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

import dominio.Element;
import dominio.POOgger;

@SuppressWarnings("serial")
public class GamePanel extends JPanel{
	private POOgger poogger;
	private HashMap<String,Image> sprites;
	private int width = 672;
	private int height = 757;
	private int lapsus = 0;
	private boolean paused;
	private final File font = new File("resources/8-BIT.TTF");
	
	public GamePanel() {
		prepareElementos();
		prepareAcciones();
	}
	public boolean refresh() {
		return poogger.isPlayerAlive();
	}
	private void prepareElementos() {
		addFont();
		poogger = new POOgger(672,757, prepareArchivos());
		lapsus = 0;
		setUIFont(new javax.swing.plaf.FontUIResource("8-bit Operator+ SC",Font.BOLD,12));
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
				if(e.getKeyCode()==KeyEvent.VK_SPACE) {
					paused = !paused;
				}
				else {
					poogger.movePlayer((""+e.getKeyChar()).toUpperCase().charAt(0));
				}
			}
		});
		
		setFocusable(true);
	}
	
	@Override
	public void paint(Graphics g) {
		if(!paused) {
			super.paint(g);
			g.setFont(new Font("8-bit Operator+ SC", Font.BOLD, 18));
			g.drawImage(new ImageIcon("./resources/Fondo.png").getImage(),0,0,null);
			for(Element i: poogger.gameLoop(lapsus)) {
				g.drawImage(sprites.get(i.getSprite()),i.getX(),i.getY(),null);
			}
			g.setColor(Color.WHITE);
			g.drawString("1-UP    HI-SCORE",40,23);
			g.setColor(Color.RED.darker());
			g.drawString(""+poogger.getPoints(),52,45);
			g.drawString(""+poogger.getHighScore(),195,45);
			g.setColor(Color.YELLOW);
			g.drawString("TIME",585,33);
			g.setColor(Color.GREEN.darker());
			g.fillRect(270, 16, 306, 20);
			g.setColor(Color.BLACK);
			
			for (int i = 0; i < poogger.getPlayer().getLives(); i++) {
				g.drawImage(sprites.get("Icon"),525 + 25*i,49,null);
			}
			g.fillRect(270, 16, poogger.getClock().width, poogger.getClock().height);
			g.drawImage(sprites.get(poogger.getPlayer().getSprite()),poogger.getPlayer().getX(),poogger.getPlayer().getY(),null);
			lapsus+=1;
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
	
	@SuppressWarnings("rawtypes")
	public static void setUIFont (javax.swing.plaf.FontUIResource f){
		java.util.Enumeration keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
          Object key = keys.nextElement();
          Object value = UIManager.get (key);
          if (value instanceof javax.swing.plaf.FontUIResource)
        	  UIManager.put (key, f);
          }
        } 
    public void addFont() {
    	try {
    	     GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    	     ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, font));
    	} catch (Exception e) {
    	     raiseError(e.getMessage());
    	}
    }
	
    private void raiseError(String message) {
    	Toolkit.getDefaultToolkit().beep();
    	JOptionPane.showMessageDialog(null, message,"Error",JOptionPane.ERROR_MESSAGE);
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
			game.repaint();
			Thread.sleep(10);
		}
	}
}
