package presentacion;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class POOggerGUI extends JFrame {
	/*private static  POOggerGUI gui = null;
	
	public static POOggerGUI demePOOggerGUI() {
		if (gui == null) {
			gui = new POOggerGUI();
		}
		return gui;
	}
	
	public static void cambiePOOggerGUI(POOggerGUI p) {
		gui = p;
	}*/
	
	/* Paneles */
	private StartPanel startPanel;
	private NewGamePanel newGamePanel;
	private JBackground openGamePanel;
	private PointsPanel pointsPanel;
	private SelectPanel selectPanel;
	private HighScoresPanel highScoresPanel;
	
	private JPanel currentPanel;
	
	/* Barra menu Menu */
	private JMenuBar barraMenu;
	private JMenu opciones;
	private JMenu help;
	private JMenuItem openItem;
	private JMenuItem saveAsItem;
	private JMenuItem exitItem;
	
	/* Imagenes */
	private final ImageIcon icon = new ImageIcon("./resources/sprites/Icon.png");
	private final ImageIcon fondo = new ImageIcon("./resources/inicial.png");
	
	/* Archivos */
	private final File fontPath = new File("resources/8-BIT.TTF");
	private static Font font;
	 
	/* Game Execution  */
	private Timer execution;
			
	public POOggerGUI() {
		prepareElementos();
		prepareAcciones();
	}
	
	public void prepareElementos() {
		try {
			font = Font.createFont(Font.TRUETYPE_FONT,fontPath);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, fontPath));
		} catch(IOException | FontFormatException e) {
			
		}
		setTitle("POOgger");
		setIconImage(icon.getImage());
		setSize(new Dimension(720,768));
		setLocationRelativeTo(null);
		prepareElementosMenu();
		preparePaneles();
		
	}
	
	public void prepareElementosMenu() {
		barraMenu = new JMenuBar();
		opciones = new JMenu("Options");
		openItem = new JMenuItem("Open");
		saveAsItem = new JMenuItem("Save as");
		exitItem = new JMenuItem("Exit");
		saveAsItem.setEnabled(false);
		openItem.setEnabled(false);
		opciones.add(openItem);
		opciones.add(saveAsItem);
		opciones.addSeparator();
		opciones.add(exitItem);
		barraMenu.add(opciones);
		setJMenuBar(barraMenu);
	}
	
	public void preparePaneles() {
		startPanel = new StartPanel(fondo,this, fontPath, null);
		currentPanel = startPanel;
		startPanel.requestFocus(true);
	}
	
	public void prepareAcciones() {
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		/*Window Actions*/
    	addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
    			opcionSalga();
    		}
		});
    	
    	/*BarraMenu Actions*/
    	openItem.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			opcionAbra();
    		}
    		
    	});
    	
    	saveAsItem.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			opcionGuardeComo();
    		}
    		
    	});
		
    	exitItem.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			opcionSalga();
    		}
    	});
	}
	
	public void prepareOpenGamePanel() {
		prepareGamePanel();
		opcionAbra();
	}
	
	public void preparePointsPanel() {
		pointsPanel = new PointsPanel(fondo, this, fontPath, startPanel);
	}
	
	public void prepareHighScoresPanel() {
		highScoresPanel = new HighScoresPanel(fondo, this, fontPath, pointsPanel);
	}
	
	public void prepareNewGamePanel() {
		newGamePanel = new NewGamePanel(fondo, this, fontPath, startPanel);
	}
	
	public void prepareSelectPanel() {
		selectPanel = new SelectPanel(fondo, this, fontPath, newGamePanel);
	}
	
	public void prepareGamePanel() {
		openItem.setEnabled(true);
		saveAsItem.setEnabled(true);
		carguePanel(newGamePanel,GamePanel.demeGamePanel());
		startGamePanel();
	}
	
	public void startGamePanel() {
		execution = new Timer(10, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GamePanel.demeGamePanel().repaint();
				if (GamePanel.demeGamePanel().getGameState()[0]) {
					stopGamePanel();
				}
			}
		});
		execution.start();
		GamePanel.demeGamePanel().requestFocus();
	}
	
	public void stopGamePanel() {
		execution.stop();
		String message = "You lose :(";
		if (GamePanel.demeGamePanel().getGameState()[1])  message = "You have won :3";
		if (GamePanel.demeGamePanel().checkScoresRecords()) message += " and you have made a record!!";
		Toolkit.getDefaultToolkit().beep();
    	JOptionPane.showMessageDialog(null, message,"",JOptionPane.INFORMATION_MESSAGE);
    	System.exit(0);
	}
	
	
	private void opcionSalga() {
		int result = JOptionPane.showConfirmDialog(this, "Are you sure you want to exit?", "Exit Application",JOptionPane.YES_NO_OPTION);
		if(result == JOptionPane.YES_OPTION) {
			System.exit(0);
		}
	}
	
	public void opcionAbra() {
		GamePanel.demeGamePanel().abra();
	}
	
	public void opcionGuardeComo() {
		GamePanel.demeGamePanel().guarde();
	}

	public void carguePanel(JPanel ini, JPanel fin) {
		if(ini != null) {
			ini.setVisible(false);
			remove(ini);
		}
    	add(fin);
    	fin.setVisible(true);
    	currentPanel = fin;
    }
	
	public JButton prepareBoton(JButton boton, Color color, float sizeFont) {
		font = font.deriveFont(sizeFont);
		boton.setFont(font);
		boton.setContentAreaFilled(false);
    	boton.setBorderPainted(false);
    	boton.setForeground(color);
    	boton.setFocusPainted(false);
		return boton;
	}

	public static void main(String[] args) {
		POOggerGUI gui = new POOggerGUI();
		//POOggerGUI.demePOOggerGUI().setDefaultCloseOperation(EXIT_ON_CLOSE);
		//POOggerGUI.demePOOggerGUI().setVisible(true);
		gui.setVisible(true);
	}
}

