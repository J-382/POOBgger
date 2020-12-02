package pruebas;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ImageIcon;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dominio.POOgger;
import dominio.Player;


/**
 * Clase de pruebas para la clase POOBgger
 * @author Angie Medina - Jose Perez
 * @version 12/11/20
 *
 */
public class POOggerTest {
	public HashMap<String, int[]> sprites;
	public final String[] player1 =  {"Test1", ""};
	public final String[] player2 = {null, null};

	public POOggerTest() {
		sprites = new HashMap<>();
		File[] files = new File("./resources/sprites/").listFiles();
		for(File file: files) {
			ImageIcon image = new ImageIcon(file.getPath());
			String key = file.getName().replaceFirst(".png", "");
			sprites.put(key,new int[] {image.getIconWidth(),image.getIconHeight()});
		} 
	}
	
	@Before
	public void setUp() {
		POOgger.demePOOgger(sprites, player1, player2, "Sunny", "./resources/HighScoresJvsJ.txt");
	}
	
	@Test
	public void deberiaMoverseHaciaArriba() {
		int xPos = POOgger.demePOOgger(sprites).getPlayers().get(0).getX(), yPos = POOgger.demePOOgger(sprites).getPlayers().get(0).getY();
		int step = POOgger.demePOOgger(sprites).getPlayers().get(0).getWidth()/3;
		int[] beforePosition = new int[] {xPos, yPos};
		POOgger.demePOOgger(sprites).movePlayer('W', 0);
		xPos = POOgger.demePOOgger(sprites).getPlayers().get(0).getX(); yPos = POOgger.demePOOgger(sprites).getPlayers().get(0).getY();
		int[] afterPosition = new int[] {xPos, yPos};
		assertTrue(afterPosition[1] == beforePosition[1] - step);
	}

	@Test
	public void noDeberiaMoverseHaciaAbajo() {
		int xPos = POOgger.demePOOgger(sprites).getPlayers().get(0).getX(), yPos = POOgger.demePOOgger(sprites).getPlayers().get(0).getY();
		int step = POOgger.demePOOgger(sprites).getPlayers().get(0).getWidth()/3;
		int[] beforePosition = new int[] {xPos, yPos};
		POOgger.demePOOgger(sprites).movePlayer('S', 0);
		xPos = POOgger.demePOOgger(sprites).getPlayers().get(0).getX(); yPos = POOgger.demePOOgger(sprites).getPlayers().get(0).getY();
		int[] afterPosition = new int[] {xPos, yPos};
		assertTrue(afterPosition[1] == beforePosition[1] + step);
	}

	@Test
	public void deberiaMoverseHaciaLaDerecha() {
		int xPos = POOgger.demePOOgger(sprites).getPlayers().get(0).getX(), yPos = POOgger.demePOOgger(sprites).getPlayers().get(0).getY();
		int step = POOgger.demePOOgger(sprites).getPlayers().get(0).getWidth()/3;
		int[] beforePosition = new int[] {xPos, yPos};
		POOgger.demePOOgger(sprites).movePlayer('D', 0);
		xPos = POOgger.demePOOgger(sprites).getPlayers().get(0).getX(); yPos = POOgger.demePOOgger(sprites).getPlayers().get(0).getY();
		int[] afterPosition = new int[] {xPos, yPos};
		assertTrue(beforePosition[0] < afterPosition[0]);
	}
	
	@Test
	public void deberiaMoverseHaciaLaIzquierda() {
		int xPos = POOgger.demePOOgger(sprites).getPlayers().get(0).getX(), yPos = POOgger.demePOOgger(sprites).getPlayers().get(0).getY();
		int[] beforePosition = new int[] {xPos, yPos};
		POOgger.demePOOgger(sprites).movePlayer('A', 0);
		xPos = POOgger.demePOOgger(sprites).getPlayers().get(0).getX(); yPos = POOgger.demePOOgger(sprites).getPlayers().get(0).getY();
		int[] afterPosition = new int[] {xPos, yPos};
		assertTrue(beforePosition[0] > afterPosition[0]);
	}
	
	@Test
	public void deberiaIniciarConCincoVidas() {
		assertEquals(5, POOgger.demePOOgger(sprites).getPlayers().get(0).getLives());
	}
	
	@Test
	public void deberiaTenerUnJugador() {
		assertEquals(1, POOgger.demePOOgger(sprites).getPlayers().size());
	}
	
	@Test
	public void deberiaTenerDosJugadores() {
		ArrayList<String[]> player2 = new ArrayList<>();
		player2.add(new String[] {"Test2", "Egg"});
		POOgger.demePOOgger(sprites).addPlayers(player2);
		assertEquals(2, POOgger.demePOOgger(sprites).getPlayers().size());
	}
	
	@Test
	public void deberiaIniciarConTresVidas() {
		ArrayList<String[]> player2 = new ArrayList<>();
		player2.add(new String[] {"Test2", "Egg"});
		POOgger.demePOOgger(sprites).addPlayers(player2);
		assertEquals(3, POOgger.demePOOgger(sprites).getPlayers().get(1).getLives());
	}
	
	
	@Test
	public void noDeberiaEstarVivo() {
		for (int i = 0; i < 5; i++) POOgger.demePOOgger(sprites).killPlayer(POOgger.demePOOgger(sprites).getPlayers().get(0));
		assertFalse(POOgger.demePOOgger(sprites).getPlayers().get(0).isAlive());
	}
	
	@Test
	public void deberiaObtenerPuntosNuevoSalto() {
		Player player = POOgger.demePOOgger(sprites).getPlayers().get(0);
		
		int beforePoints = Integer.parseInt(POOgger.demePOOgger(sprites).getPoints(player));
		POOgger.demePOOgger(sprites).movePlayer('W', 0);
		try {
			Thread.sleep(150);
		} catch(Exception e) {
			e.printStackTrace();
		}
		int afterPoints = Integer.parseInt(POOgger.demePOOgger(sprites).getPoints(player));
		assertTrue(afterPoints == beforePoints + 10);
	}
	
	@Test
	public void noDeberiaObtenerPuntosViejoSalto() {
		Player player = POOgger.demePOOgger(sprites).getPlayers().get(0);
		POOgger.demePOOgger(sprites).movePlayer('W', 0);
		try {
			Thread.sleep(150);
		} catch(Exception e) {
			e.printStackTrace();
		}
		int beforePoints = Integer.parseInt(POOgger.demePOOgger(sprites).getPoints(player));
		POOgger.demePOOgger(sprites).movePlayer('S', 0);
		POOgger.demePOOgger(sprites).movePlayer('W', 0);
		try {
			Thread.sleep(150);
		} catch(Exception e) {
			e.printStackTrace();
		}
		int afterPoints = Integer.parseInt(POOgger.demePOOgger(sprites).getPoints(player));
		assertTrue(afterPoints == beforePoints);
	}
	
	@Test
	public void deberiaPerderPuntosAlMorir() {
		Player player = POOgger.demePOOgger(sprites).getPlayers().get(0);
		int beforePoints = Integer.parseInt(POOgger.demePOOgger(sprites).getPoints(player));
		POOgger.demePOOgger(sprites).killPlayer(POOgger.demePOOgger(sprites).getPlayers().get(0));
		int afterPoints = Integer.parseInt(POOgger.demePOOgger(sprites).getPoints(player));
		assertTrue(afterPoints == beforePoints - 100);
	}
	
	@After
	public void tearDown(){
		POOgger.cambiePOOgger(null);
	}
}