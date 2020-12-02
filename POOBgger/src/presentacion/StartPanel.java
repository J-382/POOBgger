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


public class StartPanel extends JBackground{

	/* Frame */
	private POOggerGUI poogger;
	
	/* Botones start panel */
	private JButton newGameButton;
	private JButton openGameButton;
	private JButton pointsButton;
	
	private int WIDTH;
	private int HEIGHT;
	
	public StartPanel(ImageIcon image, POOggerGUI poogger,File fontPath, JBackground backPanel) {
		super(image);
		this.setLayout(new GridBagLayout());
		try {
			font = Font.createFont(Font.TRUETYPE_FONT,fontPath);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, fontPath));
		} catch(IOException | FontFormatException e) {
			
		}
		WIDTH = poogger.getWidth();
		HEIGHT = poogger.getHeight();
		
		this.poogger = poogger;
		prepareElementos();
		prepareAcciones();
		poogger.add(this);
	}
	public void prepareElementos() {
		JLabel separator = new JLabel("<html><font color='rgb(12,5,65)'><br><br>"
    			+ "<br><br><br><br><br><br><br><br><br>"
    			+ "<br><br><br><br><br><br>"
    			+ "<br><br><br></font></html>");
    	newGameButton = prepareBoton(new JButton("NEW GAME"), Color.WHITE, 50f);
    	
    	openGameButton = prepareBoton(new JButton("OPEN GAME"), Color.WHITE, 50f);
    	
    	pointsButton = prepareBoton(new JButton("POINTS"), Color.WHITE, 50f);
    	
        GridBagConstraints constraints = new GridBagConstraints();
    	constraints.gridx = 0;
    	constraints.gridy = 0;
    	add(separator, constraints);
    	constraints.gridy = 1;
    	add(newGameButton, constraints);
    	constraints.gridy = 2;
    	add(openGameButton, constraints);
    	constraints.gridy = 3;
    	add(pointsButton, constraints);
	}
	
	public void prepareAcciones() {
		newGameButton.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e) {
                prepareNewGamePanel();
       	    }
    	});
		
		openGameButton.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e) {
                prepareOpenGamePanel();
       	    }
    	});
		
		pointsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				preparePointsPanel();
			}
		});
	}

	
	public void prepareNewGamePanel() {
		this.setVisible(false);
		poogger.remove(this);
		poogger.prepareNewGamePanel();
	}
	
	public void prepareOpenGamePanel() {
		this.setVisible(false);
		poogger.remove(this);
		poogger.prepareOpenGamePanel();
	}
	
	public void preparePointsPanel() {
		this.setVisible(false);
		poogger.remove(this);
		poogger.preparePointsPanel();
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
