package presentacion;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.Timer;

public class NewGamePanel extends JBackground{

	/* Frame */
	private POOggerGUI poogger;
	
	/* Botones new game panel */
	private JButton backNewGameButton;
	private JButton arcadeButton;
	private JButton twoPlayersButton;
	private JButton playerMachineButton;
	private JButton machinesButton;
	
	private JBackground backPanel;
	private static Font font;
	
	/* Separador */
	private static final JLabel vertical = new JLabel("<html></html>");
	
	public NewGamePanel(ImageIcon image, POOggerGUI poogger,File fontPath, JBackground backPanel) {
		super(image);
		this.setLayout(new GridBagLayout());
		try {
			font = Font.createFont(Font.TRUETYPE_FONT,fontPath);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, fontPath));
		} catch(IOException | FontFormatException e) {
			
		}
		this.poogger = poogger;
		this.backPanel = backPanel;
		prepareElementos();
		prepareAcciones();
		poogger.add(this);
	}
	
	public void prepareElementos() {
		JLabel logo = new JLabel("POOgger");
    	font = font.deriveFont(100f);
    	logo.setFont(font);
    	logo.setForeground(Color.GREEN.darker());
    	
    	arcadeButton = prepareBoton(new JButton("ARCADE"), Color.WHITE, 45f);
    	
    	twoPlayersButton = prepareBoton(new JButton("PLAYER vs PLAYER"), Color.WHITE, 45f);
    	
    	playerMachineButton = prepareBoton(new JButton("PLAYER vs MACHINE"), Color.WHITE, 45f);
    	
    	machinesButton = prepareBoton(new JButton("MACHINE vs MACHINE"), Color.WHITE, 45f);
    	
    	backNewGameButton = prepareBoton(new JButton("BACK"), Color.WHITE, 40f);
    	
    	JLabel separator = new JLabel("<html><font color='rgb(12,5,65)'>___________________<br>____________________<br>_____________________"
    			+ "<br>__________________________________________<br>____________________________________<br>"
    			+ "<br>__________________________________________<br>____________________________________<br></font></html>");
    	GridBagConstraints constraints = new GridBagConstraints();
    	constraints.gridx = 0;
    	constraints.gridy = 0;
    	add(separator, constraints);
    	constraints.gridy = 1;
    	add(logo, constraints);
    	constraints.gridy = 2;
    	add(vertical, constraints);
    	constraints.gridy = 3;
    	add(arcadeButton, constraints);
    	constraints.gridy = 4;
    	add(twoPlayersButton, constraints);
    	constraints.gridy = 5;
    	add(playerMachineButton, constraints);
    	constraints.gridy = 6;
    	add(machinesButton, constraints);
    	constraints.gridy = 7;
    	add(backNewGameButton, constraints);
	}
	
	public void prepareAcciones() {
		arcadeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				prepareGamePanel("Arcade");
			}
		});
		
		twoPlayersButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				prepareSelectSkin();
			}
		});
		
		playerMachineButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				prepareSelectSkin();
			}
		});
		
		machinesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				prepareSelectSkin();
			}
		});
		
		backNewGameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				back();
			}
		});
	}

	public void prepareGamePanel(String mode) {
		poogger.prepareGamePanel();
	}
	
	public void prepareSelectSkin() {
		this.setVisible(false);
		poogger.remove(this);
		poogger.prepareSelectPanel();
		
	}
	
	public void back() {
		poogger.carguePanel(this, backPanel);
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
	
}
