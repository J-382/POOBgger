package presentacion;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
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
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;

import dominio.POOgger;
import dominio.POOggerException;

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
	private static String[] player1 = {"deafult", "default"};
	private static String[] player2 = {null, null};
	private static String mapType = "Sunny";
	private static String mode = "./resources/HighScoresPlayer.txt";
	private boolean paused;
	private Timer clockTime;
	private HashMap<Integer, Character> player1Keys = new HashMap<Integer, Character>(){{
		put(68, 'D'); put(65, 'A'); put(87, 'W'); put(83, 'S'); put(85,'1'); put(73,'2'); put(79,'3'); 
		}};
	private HashMap<Integer, Character> player2Keys = new HashMap<Integer, Character>(){{
		put(39, 'D'); put(37, 'A'); put(38, 'W'); put(40, 'S'); put(83, 'S'); put(49,'1'); put(50,'2'); put(51,'3'); 
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
		for (int i=0; i<POOgger.demePOOgger(dimensions).getPlayers().size();i++) {
			POOgger.demePOOgger(dimensions).updateClock(i);
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
							POOgger.demePOOgger(dimensions).movePlayer(player1Keys.get(e.getKeyCode()), 0);
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
		boolean checkFile = false;
		try {
			checkFile =  POOgger.demePOOgger(dimensions).checkScoresRecords();
		} catch (POOggerException e) {
			raiseError(e.getMessage());
		}
		return checkFile;
	} 
		
	private void paintCollisions(String[] i, Graphics g) {
		Color initialColor = g.getColor();
		g.setColor(Color.RED);
		g.drawRect(Integer.parseInt(i[1]), Integer.parseInt(i[2]), Integer.parseInt(i[3]), Integer.parseInt(i[4]));
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
			for(String[] i: POOgger.demePOOgger(dimensions).gameLoop(lapsus)) {
				paintCollisions(i,g);
				g.drawImage(sprites.get(i[0]),Integer.parseInt(i[1]),Integer.parseInt(i[2]),null);
			}
				
			int[] players = {430, 10};
			int cont = 1;
			/*for(Player player: POOgger.demePOOgger(dimensions).getPlayers()) {*/
			ArrayList<String[]> listPlayers = POOgger.demePOOgger(dimensions).getPlayers();
			g.setColor(Color.GREEN.darker());
			g.fillRect(players[0], players[1], 150, 15);
			g.setColor(Color.BLACK);
			g.fillRect(players[0], players[1], Integer.parseInt(listPlayers.get(0)[4]), Integer.parseInt(listPlayers.get(0)[5]));
			players[0] += 190;
			players[1] += 20;
			for (int i = 0; i < Integer.parseInt(listPlayers.get(0)[3]); i++) {
				g.drawImage(sprites.get("Icon"), players[0] - 25*i, players[1],null);
			}
			players[1] += 15;
			if(Boolean.parseBoolean(listPlayers.get(0)[6])) g.drawImage(sprites.get("ArmorIcon"),players[0] - 8, players[1],null);
			if(Boolean.parseBoolean(listPlayers.get(0)[7])) g.drawImage(sprites.get("FlyIcon"),players[0] - 8 - 25*1, players[1],null);
			if(Boolean.parseBoolean(listPlayers.get(0)[8])) g.drawImage(sprites.get("SpeedIcon"),players[0] - 8 - 25*2, players[1],null);
			players[0] -= 50;
			players[1] += 35;
			g.setColor(Color.RED.darker());
			g.drawString(listPlayers.get(0)[10], players[0], players[1]+6);
			players[0] += 12;
			players[1] -= 55;
			g.setColor(Color.YELLOW);
			g.drawString("TIME",players[0], players[1]);
			players[0] += 65;
			players[1] += 22;
			g.setColor(Color.WHITE);
			g.drawString(cont + "-UP",players[0], players[1]);
			g.drawImage(sprites.get(listPlayers.get(0)[0]),Integer.parseInt(listPlayers.get(0)[1]),Integer.parseInt(listPlayers.get(0)[2]),null);
			g.drawImage(sprites.get(listPlayers.get(0)[9]),Integer.parseInt(listPlayers.get(0)[1]),Integer.parseInt(listPlayers.get(0)[2]),null);
			cont++;
			
			players[0] = 40;
			players[1] = 25;
			
			if (POOgger.demePOOgger(dimensions).getPlayers().size() > 1) {
				g.setColor(Color.YELLOW);
				g.drawString("TIME", 74, 25);
				g.setColor(Color.GREEN.darker());
				g.fillRect(130, 10, Integer.parseInt(listPlayers.get(1)[4]), Integer.parseInt(listPlayers.get(1)[5]));
				g.setColor(Color.BLACK);
				players[0] += 190;
				players[1] += 20;
				for (int i = 0; i < Integer.parseInt(listPlayers.get(1)[3]); i++) {
					g.drawImage(sprites.get("Icon"), 75 + 25*i, 30,null);
				}
				players[1] += 15;
				
				if(Boolean.parseBoolean(listPlayers.get(1)[6])) g.drawImage(sprites.get("ArmorIcon"), 70, 40,null);
				if(Boolean.parseBoolean(listPlayers.get(1)[7])) g.drawImage(sprites.get("FlyIcon"), 70 + 25*1, 40,null);
				if(Boolean.parseBoolean(listPlayers.get(1)[8])) g.drawImage(sprites.get("SpeedIcon"), 70 + 25*2, 40,null);
				players[0] -= 50;
				players[1] += 35;
				g.setColor(Color.RED.darker());
				g.drawString(listPlayers.get(1)[10], 75, 80);
				players[0] += 65;
				players[1] += 22;
				g.setColor(Color.WHITE);
				g.drawString(cont + "-UP", 15, 47);
				g.drawImage(sprites.get(listPlayers.get(1)[0]),Integer.parseInt(listPlayers.get(1)[1]),Integer.parseInt(listPlayers.get(1)[2]),null);
				g.drawImage(sprites.get(listPlayers.get(1)[9]),Integer.parseInt(listPlayers.get(1)[1]),Integer.parseInt(listPlayers.get(1)[2]),null);
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
	
	private void drawGrid(Graphics g) {
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
	
    private void addFont() {
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
    
    public static void main(String[] Args) throws InterruptedException {
    	JFrame frame = new JFrame();
    	frame.add(GamePanel.demeGamePanel());
    	frame.setVisible(true);
    	frame.setSize(48*16, 48*16);
    	frame.setLocationRelativeTo(null);
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	while(true) {
    		GamePanel.demeGamePanel().repaint();
    		Thread.sleep(10);
    	}
    	
    }
}
