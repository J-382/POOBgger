package presentacion;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;

import dominio.Element;
import dominio.POOgger;
import dominio.POOggerException;
import dominio.Player;

@SuppressWarnings("serial")
public class GamePanel extends JPanel{
	
	private static GamePanel panel = null;
	public static GamePanel demeGamePanel() {
		if (panel == null) {
			panel = new GamePanel();
		}
		return panel;
	}
	public static void cambieGamePanel(GamePanel p) {
		panel = p;
	}
	
	private HashMap<String,Image> sprites;
	private HashMap<String,int[]> dimensions;
	private int width = 672;
	private int height = 757;
	private int lapsus = 0;
	private boolean paused;
	private Timer clockTime;
	private final File font = new File("resources/8-BIT.TTF");
	private GamePanel() {
		dimensions = prepareArchivos();
		prepareElementos();
		prepareAcciones();
	}
	
	public boolean refresh() {
		return POOgger.demePOOgger(dimensions).isPlayerAlive();
	}
	
	private void prepareElementos() {
		addFont();
		POOgger.demePOOgger(dimensions);
		clockTime = new Timer(100, new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				POOgger.demePOOgger(dimensions).updateClock();
			}
		});
		clockTime.start();;
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
					POOgger.demePOOgger(dimensions).movePlayer((""+e.getKeyChar()).toUpperCase().charAt(0));
				}
			}
		});
		
		setFocusable(true);
	}
	
	public void abra() {
		//paused = true;
		JFileChooser fileChooser = new JFileChooser();
    	fileChooser.setCurrentDirectory(new File("."));
    	try {
    		int selection = fileChooser.showOpenDialog(this);
    		File file = new File("");
    		if(selection == JFileChooser.APPROVE_OPTION) {
    			file = fileChooser.getSelectedFile();
    		}
    		POOgger.demePOOgger(dimensions).open(file);
    		//paused = false;
    	} catch(POOggerException e) {
    		raiseError(e.getMessage());
    	}
		
	}
	
	public void guarde() {
		//paused = true;
		JFileChooser fileChooser = new JFileChooser();
    	fileChooser.setCurrentDirectory(new File("."));
    	fileChooser.setFileFilter(new FileNameExtensionFilter("DAT File","dat"));
    	try {
        	int seleccion = fileChooser.showSaveDialog(this);
        	File file = new File("");
        	if(seleccion == JFileChooser.APPROVE_OPTION) {
        		file = fileChooser.getSelectedFile();
        	}
        	POOgger.demePOOgger(dimensions).save(file);
        	//paused = false;
    	}catch (POOggerException e) {
    		raiseError(e.getMessage());
    	}
	}
	
	public boolean[] getGameState() {
		return POOgger.demePOOgger(dimensions).getGameState();
	}
	
	public boolean checkScoresRecords() {
		return POOgger.demePOOgger(dimensions).checkScoresRecords();
	} 
	
	private void paintCollisions(Element i, Graphics g) {
		Color initialColor = g.getColor();
		g.setColor(Color.RED);
		Rectangle r = i.getBounds();
		g.drawRect(r.x, r.y, r.width, r.height);
		g.setColor(initialColor);		
	}
	
	@Override
	public void paint(Graphics g) {
		if(!paused) {
			super.paint(g);
			g.setFont(new Font("8-bit Operator+ SC", Font.BOLD, 18));
			g.drawImage(new ImageIcon("./resources/Fondo.png").getImage(),0,0,null);
			if(!clockTime.isRunning()) clockTime.start();
			for(Element i: POOgger.demePOOgger(dimensions).gameLoop(lapsus)) {
				paintCollisions(i,g);
				g.drawImage(sprites.get(i.getSprite()),i.getX(),i.getY(),null);
			}
			
			g.setColor(Color.WHITE);
			g.drawString("1-UP    HI-SCORE",40,23);
			g.setColor(Color.RED.darker());
			g.drawString(""+POOgger.demePOOgger(dimensions).getPoints(0),20,45);
			g.drawString(POOgger.demePOOgger(dimensions).getHighScore(),110,45);
			g.setColor(Color.YELLOW);
			g.drawString("TIME",585,33);
			g.setColor(Color.GREEN.darker());
			g.fillRect(270, 16, 306, 20);
			g.setColor(Color.BLACK);
			
			for (int i = 0; i < POOgger.demePOOgger(dimensions).getPlayers().get(0).getLives(); i++) {
				g.drawImage(sprites.get("Icon"),525 + 25*i,49,null);
			}
			g.fillRect(270, 16, POOgger.demePOOgger(dimensions).getClock().width, POOgger.demePOOgger(dimensions).getClock().height);
			for(Player player: POOgger.demePOOgger(dimensions).getPlayers()) {
				g.drawImage(sprites.get(player.getSprite()),player.getX(),player.getY(),null);
				g.drawImage(sprites.get(player.getHat()),player.getX(),player.getY(),null);
			}
			lapsus+=1;
		}
		else {
			clockTime.stop();
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
    
	public static void main(String args[])  throws InterruptedException {
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
