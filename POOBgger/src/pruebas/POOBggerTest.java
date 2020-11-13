package pruebas;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ImageIcon;

import org.junit.Test;

import dominio.Car;
import dominio.Element;
import dominio.Log;
import dominio.POOgger;
import dominio.Turtle;

/**
 * Clase de pruebas para la clase POOBgger
 * @author Angie Medina - Jose Perez
 * @version 12/11/20
 *
 */
public class POOBggerTest {
	public HashMap<String, int[]> spritesSize;
	
	public POOBggerTest() {
		spritesSize = new HashMap<>();
		File[] files = new File("./resources/sprites/").listFiles();
		for(File file: files) {
			ImageIcon image = new ImageIcon(file.getPath());
			String key = file.getName().replaceFirst(".png", "");
			spritesSize.put(key,new int[] {image.getIconWidth(),image.getIconHeight()});
		}
	}
	
	
	@Test
	public void deberiaMoverseHaciaArriba() {
		POOgger poogger = new POOgger(672, 757, spritesSize);
		int[] beforePosition = new int[] {poogger.getPlayer().getX(), poogger.getPlayer().getY()};
		poogger.movePlayer('S');
		int[] afterPosition = new int[] {poogger.getPlayer().getX(), poogger.getPlayer().getY()};
		assertTrue(beforePosition[1] != afterPosition[1]);
	}
	
	@Test
	public void noDeberiaMoverseHaciaAbajo() {
		POOgger poogger = new POOgger(672, 757, spritesSize);
		int[] beforePosition = new int[] {poogger.getPlayer().getX(), poogger.getPlayer().getY()};
		poogger.movePlayer('W');
		int[] afterPosition = new int[] {poogger.getPlayer().getX(), poogger.getPlayer().getY()};
		System.out.println(beforePosition[1]+" "+afterPosition[1]);
		assertTrue(beforePosition[1] == afterPosition[1]);
	}
	
	@Test
	public void deberiaMoverseHaciaLaDerecha() {
		POOgger poogger = new POOgger(672, 757, spritesSize);
		int[] beforePosition = new int[] {poogger.getPlayer().getX(), poogger.getPlayer().getY()};
		poogger.movePlayer('D');
		int[] afterPosition = new int[] {poogger.getPlayer().getX(), poogger.getPlayer().getY()};
		assertTrue(beforePosition[0] != afterPosition[0]);
	}
	
	@Test
	public void deberiaMoverseHaciaLaIzquierda() {
		POOgger poogger = new POOgger(672, 757, spritesSize);
		int[] beforePosition = new int[] {poogger.getPlayer().getX(), poogger.getPlayer().getY()};
		poogger.movePlayer('A');
		int[] afterPosition = new int[] {poogger.getPlayer().getX(), poogger.getPlayer().getY()};
		assertTrue(beforePosition[0] != afterPosition[0]);
	}
	
	@Test
	public void deberiaIniciarConCincoVidas() {
		POOgger poogger = new POOgger(672, 757, spritesSize);
		assertEquals(5, poogger.getPlayer().getLives());
	}
	
	@Test
	public void noDeberiaEstarVivo() {
		POOgger poogger = new POOgger(672, 757, spritesSize);
		for (int i = 0; i < 6; i++) poogger.killPlayer(poogger.getPlayer());
		assertFalse(poogger.isPlayerAlive());
	}
	
	@Test
	public void deberiaTenerVehiculos() {
		POOgger poogger = new POOgger(672, 757, spritesSize);
		ArrayList<Element> elements = (ArrayList<Element>) poogger.getElements();
		boolean isACar = false; 
		for(Element i: elements) {
			if(i instanceof Car) {
				isACar = true;
				break;
			}
		}
		assertTrue(isACar);
	}
	
	@Test
	public void deberiaTenerTortugas() {
		POOgger poogger = new POOgger(672, 757, spritesSize);
		ArrayList<Element> elements = (ArrayList<Element>) poogger.getElements();
		boolean isATurtle = false; 
		for(Element i: elements) {
			if(i instanceof Turtle) {
				isATurtle = true;
				break;
			}
		}
		assertTrue(isATurtle);
	}
	
	@Test
	public void deberiaTenerMaderos() {
		POOgger poogger = new POOgger(672, 757, spritesSize);
		ArrayList<Element> elements = (ArrayList<Element>) poogger.getElements();
		boolean isALog = false; 
		for(Element i: elements) {
			if(i instanceof Log) {
				isALog = true;
				break;
			}
		}
		assertTrue(isALog);
	}
}