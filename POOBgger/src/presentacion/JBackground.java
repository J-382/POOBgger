package presentacion;

import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class JBackground extends JPanel{
	private ImageIcon background;
	
	public JBackground(ImageIcon image) {
		background = image;
	}
	
	
	public void paintComponent(Graphics g) {
		g.drawImage(background.getImage(), 0, 0, 672, 757, null);
		setOpaque(false);
		super.paintComponent(g);
	}
}
