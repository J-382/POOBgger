package pruebas;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.Timer;

import org.junit.Test;

import dominio.POOgger;

/**
 * Clase de pruebas para la clase POOBgger
 * @author Angie Medina - Jose Perez
 * @version 12/11/20
 *
 */
public class POOggerTest {
	public HashMap<String, int[]> sprites;

	public POOggerTest() {
		sprites = new HashMap<>();
		File[] files = new File("./resources/sprites/").listFiles();
		for(File file: files) {
			ImageIcon image = new ImageIcon(file.getPath());
			String key = file.getName().replaceFirst(".png", "");
			sprites.put(key,new int[] {image.getIconWidth(),image.getIconHeight()});
		}
	}
	
	/*
	@Test
	public void deberiaMoverseHaciaArriba() {
		int xPos = POOgger.demePOOgger(sprites).getPlayer().getX(), yPos = POOgger.demePOOgger(sprites).getPlayer().getY();
		int[] beforePosition = new int[] {xPos, yPos};
		POOgger.demePOOgger(sprites).movePlayer('S');
		xPos = POOgger.demePOOgger(sprites).getPlayer().getX(); yPos = POOgger.demePOOgger(sprites).getPlayer().getY();
		int[] afterPosition = new int[] {xPos, yPos};
		assertTrue(beforePosition[1] != afterPosition[1]);
	}
	
	@Test
	public void noDeberiaMoverseHaciaAbajo() {
		int xPos = POOgger.demePOOgger(sprites).getPlayer().getX(), yPos = POOgger.demePOOgger(sprites).getPlayer().getY();
		int[] beforePosition = new int[] {xPos, yPos};
		POOgger.demePOOgger(sprites).movePlayer('W');
		xPos = POOgger.demePOOgger(sprites).getPlayer().getX(); yPos = POOgger.demePOOgger(sprites).getPlayer().getY();
		int[] afterPosition = new int[] {xPos, yPos};
		assertTrue(beforePosition[1] == afterPosition[1]);
	}
	
	@Test
	public void deberiaMoverseHaciaLaDerecha() {
		int xPos = POOgger.demePOOgger(sprites).getPlayer().getX(), yPos = POOgger.demePOOgger(sprites).getPlayer().getY();
		int[] beforePosition = new int[] {xPos, yPos};
		POOgger.demePOOgger(sprites).movePlayer('D');
		xPos = POOgger.demePOOgger(sprites).getPlayer().getX(); yPos = POOgger.demePOOgger(sprites).getPlayer().getY();
		int[] afterPosition = new int[] {xPos, yPos};
		assertTrue(beforePosition[0] != afterPosition[0]);
	}
	
	@Test
	public void deberiaMoverseHaciaLaIzquierda() {
		int xPos = POOgger.demePOOgger(sprites).getPlayer().getX(), yPos = POOgger.demePOOgger(sprites).getPlayer().getY();
		int[] beforePosition = new int[] {xPos, yPos};
		POOgger.demePOOgger(sprites).movePlayer('A');
		xPos = POOgger.demePOOgger(sprites).getPlayer().getX(); yPos = POOgger.demePOOgger(sprites).getPlayer().getY();
		int[] afterPosition = new int[] {xPos, yPos};
		assertTrue(beforePosition[0] != afterPosition[0]);
	}
	
	@Test
	public void deberiaIniciarConCincoVidas() {
		assertEquals(5, POOgger.demePOOgger(sprites).getPlayer().getLives());
	}
	
	@Test
	public void noDeberiaEstarVivo() {
		POOgger.demePOOgger(sprites);
		for (int i = 0; i < 6; i++) POOgger.demePOOgger(sprites).killPlayer(POOgger.demePOOgger(sprites).getPlayer());
		assertFalse(POOgger.demePOOgger(sprites).isPlayerAlive());
	}*/
}