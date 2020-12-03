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
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class NamePanel extends JBackground{

	private JTextField namePlayer1;
	private JTextField namePlayer2;
	
	private JComboBox typeMachine1;
	private JComboBox typeMachine2;
	
	private JComboBox mapType;
	
	private JButton backNameButton;
	private JButton playButton;
	
	private String[] player1;
	private String[] player2;
	private String mode;
	private String[] machineOptions = {"Thoughtless", "Reckless", "Cautions"};
	private String[] mapOptions = {"Sunny", "Stormy"};
	
	public NamePanel(ImageIcon image, POOggerGUI poogger,File fontPath, JBackground backPanel, String player1, String player2, String mode) {
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
		this.mode = mode;
		this.player1 = new String[]{null, player1};
		if (player2.equals("")) player2 = null;
		this.player2 = new String[]{null, player2};
		prepareElementos();
		prepareAcciones();
		prepareModo();
		poogger.add(this);
	}

	@Override
	public void prepareElementos() {
    	
		font = font.deriveFont(25f);
    	
		namePlayer1 = new JTextField(10);
		
		namePlayer2 = new JTextField(10);
		
		typeMachine1 = new JComboBox(machineOptions);
		
		typeMachine2 = new JComboBox(machineOptions);
		
		mapType = new JComboBox(mapOptions);
		
		playButton = prepareBoton(new JButton("PLAY"), Color.WHITE, 40f);
		backNameButton = prepareBoton(new JButton("BACK"), Color.WHITE, 40f);
	}

	public void prepareModo() {
		if (mode.equals("Human")) {
			selectOnePlayer();
		}
		else if (mode.equals("Humans")) {
			selectTwoPlayers();
		}
		else if (mode.equals("Machine")) {
			selectOneMachine();
		}
		else selectTwoMachines();
			
	}
	
	public void selectOnePlayer() {
		JLabel nameLabel1 = new JLabel("   PLAYER1'S NAME ");
		nameLabel1.setFont(font);
    	nameLabel1.setForeground(Color.WHITE);
    	
    	JLabel mapLabel = new JLabel("MAP TYPE");
		mapLabel.setFont(font);
    	mapLabel.setForeground(Color.WHITE);
    	
    	JLabel separator = new JLabel("<html><font color='rgb(12,5,65)'><br><br>"
    			+ "<br><br><br><br><br>"
    			+ "<br><br><br><br><br>"
    			+ "<br><br><br><br><br>"
    			+ "<br><br><br><br><br>"
    			+ "</font></html>");
		
    	GridBagConstraints constraints = new GridBagConstraints();
    	constraints.gridx = 0;
    	constraints.gridy = 0;
    	add(separator, constraints);
    	constraints.gridy = 1;
    	add(nameLabel1, constraints);
    	constraints.gridx = 1;
    	add(namePlayer1, constraints);
    	constraints.gridy = 2;
    	constraints.gridx = 0;
    	add(mapLabel, constraints);
    	constraints.gridx = 1;
    	add(mapType, constraints);
    	constraints.gridy = 3;
    	constraints.gridx = 0;
    	add(playButton, constraints);
    	constraints.gridy = 4;
    	add(backNameButton, constraints);
	}
	
	public void selectTwoPlayers() {
		JLabel nameLabel1 = new JLabel("   PLAYER1'S NAME ");
		nameLabel1.setFont(font);
    	nameLabel1.setForeground(Color.WHITE);
    	
    	JLabel nameLabel2 = new JLabel("   PLAYER2'S NAME ");
		nameLabel2.setFont(font);
    	nameLabel2.setForeground(Color.WHITE);
    	
    	JLabel mapLabel = new JLabel("MAP TYPE");
		mapLabel.setFont(font);
    	mapLabel.setForeground(Color.WHITE);
    	
		JLabel separator = new JLabel("<html><font color='rgb(12,5,65)'><br><br>"
    			+ "<br><br><br><br><br>"
    			+ "<br><br><br><br><br>"
    			+ "<br><br><br><br><br>"
    			+ "<br><br><br><br><br>"
    			+ "</font></html>");
		GridBagConstraints constraints = new GridBagConstraints();
    	constraints.gridx = 0;
    	constraints.gridy = 0;
    	add(separator, constraints);
    	constraints.gridy = 1;
    	add(nameLabel1, constraints);
    	constraints.gridx = 1;
    	add(namePlayer1, constraints);
    	constraints.gridy = 2;
    	constraints.gridx = 0;
    	add(nameLabel2, constraints);
    	constraints.gridx = 1;
    	add(namePlayer2, constraints);
    	constraints.gridy = 3;
    	constraints.gridx = 0;
    	add(mapLabel, constraints);
    	constraints.gridx = 1;
    	add(mapType, constraints);
    	constraints.gridy = 4;
    	constraints.gridx = 0;
    	add(playButton, constraints);
    	constraints.gridy = 5;
    	add(backNameButton, constraints);
	}
	
	public void selectOneMachine() {
		JLabel nameLabel1 = new JLabel("   PLAYER1'S NAME ");
		nameLabel1.setFont(font);
    	nameLabel1.setForeground(Color.WHITE);
    	
		JLabel machineLabel1 = new JLabel("CPU TYPE");
		machineLabel1.setFont(font);
		machineLabel1.setForeground(Color.WHITE);
		
		JLabel mapLabel = new JLabel("MAP TYPE");
		mapLabel.setFont(font);
    	mapLabel.setForeground(Color.WHITE);
    	
		JLabel separator = new JLabel("<html><font color='rgb(12,5,65)'><br><br>"
    			+ "<br><br><br><br><br>"
    			+ "<br><br><br><br><br>"
    			+ "<br><br><br><br><br>"
    			+ "<br><br><br><br><br>"
    			+ "</font></html>");
		GridBagConstraints constraints = new GridBagConstraints();
    	constraints.gridx = 0;
    	constraints.gridy = 0;
    	add(separator, constraints);
    	constraints.gridy = 1;
    	add(nameLabel1, constraints);
    	constraints.gridx = 1;
    	add(namePlayer1, constraints);
    	constraints.gridy = 2;
    	constraints.gridx = 0;
    	add(machineLabel1, constraints);
    	constraints.gridx = 1;
    	add(typeMachine2, constraints);
    	constraints.gridy = 3;
    	constraints.gridx = 0;
    	add(mapLabel, constraints);
    	constraints.gridx = 1;
    	add(mapType, constraints);
    	constraints.gridy = 4;
    	constraints.gridx = 0;
    	add(playButton, constraints);
    	constraints.gridy = 5;
    	add(backNameButton, constraints);
	}
	
	public void selectTwoMachines() {
		JLabel machineLabel1 = new JLabel("CPU TYPE");
		machineLabel1.setFont(font);
		machineLabel1.setForeground(Color.WHITE);
		
		JLabel machineLabel2 = new JLabel("CPU TYPE");
		machineLabel2.setFont(font);
		machineLabel2.setForeground(Color.WHITE);
		
		JLabel mapLabel = new JLabel("MAP TYPE");
		mapLabel.setFont(font);
    	mapLabel.setForeground(Color.WHITE);
    	
		JLabel separator = new JLabel("<html><font color='rgb(12,5,65)'><br><br>"
    			+ "<br><br><br><br><br>"
    			+ "<br><br><br><br><br>"
    			+ "<br><br><br><br><br>"
    			+ "<br><br><br><br><br>"
    			+ "</font></html>");
		GridBagConstraints constraints = new GridBagConstraints();
    	constraints.gridx = 0;
    	constraints.gridy = 0;
    	add(separator, constraints);
    	constraints.gridy = 1;
    	add(machineLabel1, constraints);
    	constraints.gridx = 1;
    	add(typeMachine1, constraints);
    	constraints.gridy = 2;
    	constraints.gridx = 0;
    	add(machineLabel2, constraints);
    	constraints.gridx = 1;
    	add(typeMachine2, constraints);
    	constraints.gridy = 3;
    	constraints.gridx = 0;
    	add(mapLabel, constraints);
    	constraints.gridx = 1;
    	add(mapType, constraints);
    	constraints.gridy = 4;
    	constraints.gridx = 0;
    	add(playButton, constraints);
    	constraints.gridy = 5;
    	add(backNameButton, constraints);
	}
	
	@Override
	public void prepareAcciones() {
		playButton.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e) {
                prepareGamePanel();
       	    }
    	});
		
		backNameButton.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e) {
                back();
       	    }
    	});
	}

	public void prepareGamePanel() {
		if (mode.equals("Human") || mode.equals("Humans") || mode.equals("Machine")) player1[0] = namePlayer1.getText();
		else player1[0] = (String)typeMachine1.getSelectedItem();
		
		if (mode.equals("Humans")) player2[0] = namePlayer2.getText();
		else if (mode.equals("Machines") || mode.equals("Machine"))player2[0] = (String)typeMachine2.getSelectedItem();
		poogger.prepareGamePanel(player1, player2, (String)mapType.getSelectedItem(), mode);
	}
}
