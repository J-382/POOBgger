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

public class PointsPanel extends JBackground{
	
	/*Botones points panel */
	private JButton backPointsButton;
	private JButton highScoresButton;
	
	private static Font font;
	
	
	public PointsPanel(ImageIcon image, POOggerGUI poogger,File fontPath, JBackground backPanel) {
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
	
	public void prepareElementos(){
		JLabel separator = new JLabel("<html><font color='rgb(12,5,65)'><br><br>"
    			+ "<br><br><br><br><br></font></html>");
		
    	font = font.deriveFont(25f);
    	
    	JLabel pointTable = new JLabel("       -POINT TABLE-     ");
    	pointTable.setFont(font);
    	pointTable.setForeground(Color.WHITE);
    	
    	separator.setFont(font);
    	font = font.deriveFont(20f);
    	JLabel points1 = new JLabel("10 PTS FOR EACH FORWARD STEP");
    	points1.setFont(font);
    	points1.setForeground(Color.YELLOW);
    	
    	JLabel points2 = new JLabel("50 PTS FOR EVERY FROG SAVED");
    	points2.setFont(font);
    	points2.setForeground(Color.YELLOW);
    	
    	JLabel points3 = new JLabel("100 PTS FOR EVERY UNUSED FROG");
    	points3.setFont(font);
    	points3.setForeground(Color.YELLOW);
    	
    	JLabel points4 = new JLabel("450 PTS FOR EVERY SAVED FEMALE FROG");
    	points4.setFont(font);
    	points4.setForeground(Color.YELLOW);
    	
    	JLabel points5 = new JLabel("200 PTS FOR EVERY CATCHED BUG");
    	points5.setFont(font);
    	points5.setForeground(Color.YELLOW);
    	
    	JLabel points6 = new JLabel("1000 PTS IF EVERY FROG IS SAVED");
    	points6.setFont(font);
    	points6.setForeground(Color.YELLOW);
    	
    	JLabel points7 = new JLabel("PLUS BONUS: 10 PTS X REMAINING SECOND");
    	points7.setFont(font);
    	points7.setForeground(Color.YELLOW);
    	
    	JLabel points8 = new JLabel("-100 PTS FOR EVERY DEAD FROG");
    	points8.setFont(font);
    	points8.setForeground(Color.YELLOW);
    	
    	JLabel points9 = new JLabel("  -200 PTS FOR EVERY FEMALE DEAD FROG  ");
    	points9.setFont(font);
    	points9.setForeground(Color.YELLOW);
    	
    	highScoresButton = prepareBoton(new JButton("HIGH SCORES"), Color.WHITE, 40f);
    	
    	backPointsButton = prepareBoton(new JButton("BACK"), Color.WHITE, 40f);
    	
    	GridBagConstraints constraints = new GridBagConstraints();
    	constraints.gridx = 0;
    	constraints.gridy = 0;
    	add(separator, constraints);
    	constraints.gridy = 1;
    	add(pointTable, constraints);
    	constraints.gridy = 2;
    	add(points1, constraints);
    	constraints.gridy = 3;
    	add(points2, constraints);
    	constraints.gridy = 4;
    	add(points3, constraints);
    	constraints.gridy = 5;
    	add(points4, constraints);
    	constraints.gridy = 6;
    	add(points5, constraints);
    	constraints.gridy = 7;
    	add(points6, constraints);
    	constraints.gridy = 8;
    	add(points7, constraints);
    	constraints.gridy = 9;
    	add(points8, constraints);
    	constraints.gridy = 10;
    	add(points9, constraints);
    	constraints.gridy = 11;
    	add(highScoresButton, constraints);
    	constraints.gridy = 12;
    	add(backPointsButton, constraints);
	}
	
	public void prepareAcciones() {
		highScoresButton.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e) {
                prepareHighScoresPanel();
       	    }
    	});
		
		backPointsButton.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e) {
                back();
       	    }
    	});
		
	}
	
	public void prepareHighScoresPanel() {
		this.setVisible(false);
		poogger.remove(this);
		poogger.prepareHighScoresPanel();
		
	}
}
