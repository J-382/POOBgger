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
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
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
			panel = new GamePanel(player1, player2, mapType, mode);
		}
		return panel;
	}
	
	public static GamePanel demeGamePanel(String[] player1, String[] player2, String mapType, String mode) {
		if (panel == null) {
			panel = new GamePanel(player1, player2, mapType, mode);
		}
		return panel;
	}
	
	public static void cambieGamePanel(GamePanel p) {
		panel = p;
	}
	
	private HashMap<String,Image> sprites;
	private HashMap<String,int[]> dimensions;
	private int lapsus = 0;
	private static String[] player1 = {null, null};
	private static String[] player2 = {null, null};
	private static String mapType = null;
	private static String mode = null;
	private boolean paused;
	private Timer clockTime;
	private HashMap<Integer, Character> player1Keys = new HashMap<Integer, Character>(){{
		put(68, 'D'); put(65, 'A'); put(87, 'W'); put(83, 'S');
		}};
	private HashMap<Integer, Character> player2Keys = new HashMap<Integer, Character>(){{
		put(39, 'D'); put(37, 'A'); put(38, 'W'); put(40, 'S');
		}};	
	private final File font = new File("resources/8-BIT.TTF");
	private GamePanel(String[] player1, String[] player2, String mapType, String mode) {
		GamePanel.player1 = player1;
		GamePanel.player2 = player2;
		GamePanel.mapType = mapType;
		GamePanel.mode = mode;
		dimensions = prepareArchivos();
		prepareElementos(player1, player2, mapType, mode);
		prepareAcciones();
	}
	
	public boolean refresh() {
		return POOgger.demePOOgger(dimensions, player1, player2, mapType, mode).isPlayerAlive();
	}
	
	private void prepareElementos(String[] player1, String[] player2, String mapType, String mode) {
		addFont();
		POOgger.demePOOgger(dimensions, player1, player2, mapType, mode);
		clockTime = new Timer(200, new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				reduceTime();
			}
		});
		clockTime.start();;
		lapsus = 0;
		setUIFont(new javax.swing.plaf.FontUIResource("8-bit Operator+ SC",Font.BOLD,12));
	}
	
	private void reduceTime() {
		for (Player player : POOgger.demePOOgger(dimensions).getPlayers()) {
			POOgger.demePOOgger(dimensions).updateClock(player);
		}
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
					if (!paused) {
						if(player1Keys.containsKey(e.getKeyCode())) {
							POOgger.demePOOgger(dimensions).movePlayer((""+e.getKeyChar()).toUpperCase().charAt(0), 0);
						}
						else if(player2Keys.containsKey(e.getKeyCode())) {
							POOgger.demePOOgger(dimensions).movePlayer(player2Keys.get(e.getKeyCode()), 1);
						}
						
					}
				}
			}
		});
		
		setFocusable(true);
	}
	
	public void abra() {
		JFileChooser fileChooser = new JFileChooser();
    	fileChooser.setCurrentDirectory(new File("."));
    	try {
    		int selection = fileChooser.showOpenDialog(this);
    		File file = new File("");
    		if(selection == JFileChooser.APPROVE_OPTION) {
    			file = fileChooser.getSelectedFile();
    		}
    		POOgger.demePOOgger(dimensions).open(file);
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
			POOgger.demePOOgger(dimensions).resumeElements();
			g.setFont(new Font("8-bit Operator+ SC", Font.BOLD, 18));
			g.drawImage(new ImageIcon("./resources/Fondo.png").getImage(),0,0,null);
			if(!clockTime.isRunning()) clockTime.start();
			for(Element i: POOgger.demePOOgger(dimensions).gameLoop(lapsus)) {
				paintCollisions(i,g);
				g.drawImage(sprites.get(i.getSprite()),i.getX(),i.getY(),null);
			}
				
			int[] players = {430, 10};
			int cont = 1;
			/*for(Player player: POOgger.demePOOgger(dimensions).getPlayers()) {*/
			ArrayList<Player> listPlayers = POOgger.demePOOgger(dimensions).getPlayers();
			g.setColor(Color.GREEN.darker());
			g.fillRect(players[0], players[1], 150, 15);
			g.setColor(Color.BLACK);
			g.fillRect(players[0], players[1], listPlayers.get(0).getClock().width, listPlayers.get(0).getClock().height);
			players[0] += 190;
			players[1] += 20;
			for (int i = 0; i < listPlayers.get(0).getLives(); i++) {
				g.drawImage(sprites.get("Icon"), players[0] - 25*i, players[1],null);
			}
			players[1] += 15;
			for (int i = 0; i < listPlayers.get(0).getLives(); i++) { 
				g.drawImage(sprites.get("Icon"),players[0] - 25*i, players[1],null);
			}
			players[0] -= 50;
			players[1] += 35;
			g.setColor(Color.RED.darker());
			g.drawString(""+POOgger.demePOOgger(dimensions).getPoints(listPlayers.get(0)), players[0], players[1]);
			players[0] += 12;
			players[1] -= 55;
			g.setColor(Color.YELLOW);
			g.drawString("TIME",players[0], players[1]);
			players[0] += 65;
			players[1] += 22;
			g.setColor(Color.WHITE);
			g.drawString(cont + "-UP",players[0], players[1]);
			g.drawImage(sprites.get(listPlayers.get(0).getSprite()),listPlayers.get(0).getX(),listPlayers.get(0).getY(),null);
			g.drawImage(sprites.get(listPlayers.get(0).getHat()),listPlayers.get(0).getX(),listPlayers.get(0).getY(),null);
			cont++;
			
			players[0] = 40;
			players[1] = 25;
			
			if (POOgger.demePOOgger(dimensions).getPlayers().size() > 1) {
				g.setColor(Color.YELLOW);
				g.drawString("TIME", 74, 25);
				g.setColor(Color.GREEN.darker());
				g.fillRect(130, 10, listPlayers.get(1).getClock().width, listPlayers.get(1).getClock().height);
				g.setColor(Color.BLACK);
				players[0] += 190;
				players[1] += 20;
				for (int i = 0; i < listPlayers.get(1).getLives(); i++) {
					g.drawImage(sprites.get("Icon"), 75 + 25*i, 30,null);
				}
				players[1] += 15;
				for (int i = 0; i < listPlayers.get(1).getLives(); i++) { 
					g.drawImage(sprites.get("Icon"), 75 + 25*i, 45,null);
				}
				players[0] -= 50;
				players[1] += 35;
				g.setColor(Color.RED.darker());
				g.drawString(""+POOgger.demePOOgger(dimensions).getPoints(listPlayers.get(1)), 75, 80);
				players[0] += 65;
				players[1] += 22;
				g.setColor(Color.WHITE);
				g.drawString(cont + "-UP", 15, 47);
				g.drawImage(sprites.get(listPlayers.get(1).getSprite()),listPlayers.get(1).getX(),listPlayers.get(1).getY(),null);
				g.drawImage(sprites.get(listPlayers.get(1).getHat()),listPlayers.get(1).getX(),listPlayers.get(1).getY(),null);
			}
			/*}*/
			
			
			lapsus+=1;
			
			g.setColor(Color.WHITE);
			g.drawString("HI-SCORE",310,80);
			g.drawString("LEVEL",325,40);
			g.setColor(Color.RED.darker()); 
			g.drawString(POOgger.demePOOgger(dimensions).getHighScore(),325,60);
			g.drawString(POOgger.demePOOgger(dimensions).getLevel(),350,20);
		}
		else {
			POOgger.demePOOgger(dimensions).pauseElements();
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
}
