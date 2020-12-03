
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
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

public class HighScoresPanel extends JBackground{
	
	/* Botones highScorePanel */
	private JButton backHighScoresButton;
	
	private static Font font;
	
	/* Archivos */
	private final File highScoresPlayersFile = new File("./resources/HighScoresPlayer.txt");
	private final File highScoresMachinesFile = new File("./resources/HighScoresMachine.txt");
	
	public HighScoresPanel(ImageIcon image, POOggerGUI poogger,File fontPath, JBackground backPanel) {
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
		JLabel separator = new JLabel("<html><font color='rgb(12,5,65)'><br><br>"
    			+ "<br><br><br><br><br>"
    			+ "<br><br><br><br><br>"
    			+ "<br><br><br><br></font></html>");
    	JLabel rankingPlayersText = new JLabel("SCORE PvsP RANKING");
    	font = font.deriveFont(20f);
    	rankingPlayersText.setFont(font);
    	rankingPlayersText.setForeground(Color.YELLOW);
    	
    	JLabel rankingMachinesText = new JLabel("SCORE PvsM RANKING");
    	font = font.deriveFont(20f);
    	rankingMachinesText.setFont(font);
    	rankingMachinesText.setForeground(Color.YELLOW);
    	 
    	backHighScoresButton = prepareBoton(new JButton("BACK"), Color.WHITE, 45f);
    	
    	GridBagConstraints constraints = new GridBagConstraints();
    	constraints.gridx = 0;
    	constraints.gridy = 0;
    	add(separator, constraints);
    	constraints.gridy = 1;
    	add(rankingPlayersText, constraints);
    	
    	font = font.deriveFont(20f);
    	int y = 1;
    	ArrayList<String> scores = leaArchivo(highScoresPlayersFile);
    	for(String element : scores) {
    		JLabel nickLabel = new JLabel(element.split(" ")[0] + "     " + element.split(" ")[1]);
    		nickLabel.setFont(font);
    		nickLabel.setForeground(Color.WHITE);
    		constraints.gridx = 0;
    		constraints.gridy = ++y;
    		add(nickLabel, constraints);
    	}
    	
    	
    	constraints.gridy = 7;
    	add(rankingMachinesText, constraints);
    	y = 7;
    	scores = leaArchivo(highScoresMachinesFile);
    	for(String element : scores) {
    		JLabel nickLabel = new JLabel(element.split(" ")[0] + "     " + element.split(" ")[1]);
    		nickLabel.setFont(font);
    		nickLabel.setForeground(Color.WHITE);
    		constraints.gridx = 0;
    		constraints.gridy = ++y;
    		add(nickLabel, constraints);
    	}		
    	constraints.gridy = 13;
    	add(backHighScoresButton, constraints);
	}
	
	public void prepareAcciones() {
		backHighScoresButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				back();
			}
		});
	}
	
	private ArrayList<String> leaArchivo(File loadFile) {
    	ArrayList<String> save = new ArrayList<>();
    	try {
    		Scanner scan = new Scanner(loadFile);
    		while(scan.hasNextLine()) {
    			save.add(scan.nextLine());
    		}
    		scan.close();
    	}catch(Exception e) {}	
    	return save;
    }
}
