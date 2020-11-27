package presentacion;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

public class SelectPanel extends JBackground{
	

	/* Frame */
	private POOggerGUI poogger;
	
	/* Botones select skin panel */
	private JButton backSelectButton;
	private JButton playButton;
	private JButton defaultButton;
	private JButton skin02Button;
	private JButton skin03Button;
	private String player1;
	private String player2;
	
	private JBackground backPanel;
	private static Font font;
	
	/* Imagenes */
	private ImageIcon defaultButtonIco = new ImageIcon("./resources/default.png");
	private ImageIcon skin02ButtonIco = new ImageIcon("./resources/skin02.png");
	private ImageIcon skin03ButtonIco = new ImageIcon("./resources/skin03.png");
	
	public SelectPanel(ImageIcon image, POOggerGUI poogger,File fontPath, JBackground backPanel) {
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
    	
    	font = font.deriveFont(40f);
    	JLabel select = new JLabel("-SELECT-");
    	select.setFont(font);
    	select.setForeground(Color.WHITE);
    	
    	JLabel separator = new JLabel("<html><font color='rgb(12,5,65)'>___________________<br>____________________<br>_____________________"
    			+ "<br>__________________________________________<br>____________________________________<br>"
    			+ "<br>__________________________________________</font></html>");
    	
		Image scale = defaultButtonIco.getImage().getScaledInstance(50,  50,  Image.SCALE_DEFAULT);
		defaultButtonIco = new ImageIcon(scale);
		
		scale = skin02ButtonIco.getImage().getScaledInstance(50,  50,  Image.SCALE_DEFAULT);
		skin02ButtonIco = new ImageIcon(scale);
		
		scale = skin03ButtonIco.getImage().getScaledInstance(50,  50,  Image.SCALE_DEFAULT);
		skin03ButtonIco = new ImageIcon(scale);
		
		defaultButton = new JButton();
		defaultButton.setIcon(defaultButtonIco);
		defaultButton.setBackground(Color.BLACK);
		defaultButton.setBounds(0, 0, 0, 10);
		
		skin02Button = new JButton();
		skin02Button.setIcon(skin02ButtonIco);
		skin02Button.setBackground(Color.BLACK);
		
		skin03Button = new JButton();
		skin03Button.setIcon(skin03ButtonIco);
		skin03Button.setBackground(Color.BLACK);
		
		backSelectButton = prepareBoton(new JButton("BACK"), Color.WHITE, 40f);
		
		playButton = prepareBoton(new JButton("PLAY"), Color.WHITE, 40f);
		
		GridBagConstraints constraints = new GridBagConstraints();
    	constraints.gridx = 0;
    	constraints.gridy = 0;
		add(logo, constraints);
		constraints.gridy = 1;
		add(separator, constraints);
		constraints.gridy = 2;
		add(select, constraints);
		constraints.gridy = 3;
		add(defaultButton, constraints);
		constraints.gridy = 4;
		add(skin02Button, constraints);
		constraints.gridy = 5;
		add(skin03Button, constraints);
		constraints.gridy = 6;
		add(playButton, constraints);
		constraints.gridy = 7;
		add(backSelectButton, constraints);
	}
	
	public void prepareAcciones() {
		defaultButton.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e) {
                if (defaultButton.isEnabled() && (player1 == null || player2 == null)) {
                	defaultButton.setEnabled(false);
                	if(player1 == null) player1 = "default";
                	else if(player2 == null)player2 = "default";
                }
       	    }
    	});
		
		skin02Button.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e) {
                if (skin02Button.isEnabled() && (player1 == null || player2 == null)) {
                	skin02Button.setEnabled(false);
                	if(player1 == null) player1 = "skin02";
                	else if(player2 == null)player2 = "skin02";
                }
       	    }
    	});
		
		skin03Button.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e) {
                if (skin03Button.isEnabled() && (player1 == null || player2 == null)) {
                	skin03Button.setEnabled(false);
                	if(player1 == null) player1 = "skin03";
                	else if(player2 == null)player2 = "skin03";
                }
       	    }
    	});
		
		playButton.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e) {
    			if(player1 == null && player2 == null) playButton.setEnabled(false);
    			
       	    }
    	});
		
		backSelectButton.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e) {
    			back();
       	    }
    	});
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
