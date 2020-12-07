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

	/* Botones new game panel */
	private JButton backNewGameButton;
	private JButton arcadeButton;
	private JButton twoPlayersButton;
	private JButton playerMachineButton;
	private JButton machinesButton;
	
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
	
	protected void prepareElementos() {
		JLabel separator = new JLabel("<html><font color='rgb(12,5,65)'><br><br>"
    			+ "<br><br><br><br><br><br><br><br><br>"
    			+ "<br><br><br><br><br><br>"
    			+ "<br><br><br></font></html>");
    	
    	arcadeButton = prepareBoton(new JButton("ARCADE"), Color.WHITE, 45f);
    	arcadeButton.setBounds(100, 350, 500, 100);
    	
    	twoPlayersButton = prepareBoton(new JButton("PLAYER vs PLAYER"), Color.WHITE, 45f);
    	twoPlayersButton.setBounds(90, 410, 500, 100);
    	
    	playerMachineButton = prepareBoton(new JButton("PLAYER vs MACHINE"), Color.WHITE, 45f);
    	playerMachineButton.setBounds(50, 470, 600, 100);
    	
    	machinesButton = prepareBoton(new JButton("MACHINE vs MACHINE"), Color.WHITE, 45f);
    	machinesButton.setBounds(50, 530, 600, 100);
    	
    	backNewGameButton = prepareBoton(new JButton("BACK"), Color.WHITE, 40f);
    	backNewGameButton.setBounds(100, 590, 500, 100); 
    	
    	GridBagConstraints constraints = new GridBagConstraints();
    	constraints.gridx = 0;
    	constraints.gridy = 0;
    	add(separator, constraints);
    	constraints.gridy = 1;
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
	
	protected void prepareAcciones() {
		arcadeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//prepareGamePanel();
				prepareSelectSkin(1, "Human");
			}
		});
		
		twoPlayersButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				prepareSelectSkin(2, "Humans");
			}
		});
		
		playerMachineButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				prepareSelectSkin(2, "Machine");
			}
		});
		
		machinesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				prepareSelectSkin(2, "Machines");
			}
		});
		
		backNewGameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				back();
			}
		});
	}

	private void prepareSelectSkin(int players, String mode) {
		this.setVisible(false);
		poogger.remove(this);
		poogger.prepareSelectPanel(players, mode);
		
	}
}
