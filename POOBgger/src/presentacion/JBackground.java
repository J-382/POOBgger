package presentacion;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagLayout;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public abstract class JBackground extends JPanel{
	
	/* Frame */
	protected POOggerGUI poogger;
	
	private ImageIcon background;
	
	protected JBackground backPanel;
	protected static Font font;
	
	public JBackground(ImageIcon image) {
		background = image;
	}

	public abstract void prepareElementos();
	public abstract void prepareAcciones();
	
	public void paintComponent(Graphics g) {
		g.drawImage(background.getImage(), 0, 0, 720,768, null);
		setOpaque(false);
		super.paintComponent(g);
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
