package pruebas;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.Rectangle;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ImageIcon;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import dominio.*;


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
	private Player player;

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
		POOgger.demePOOgger(sprites, player1, player2, "Sunny", "./resources/HighScoresPlayer.txt");
		player = POOgger.demePOOgger(sprites).getPlayer(0);
	}
	
	public void testElementCollision(Element element, Player player,boolean isMobile) {
		testElementCollision(element,player,isMobile, true);
	}
	
	public void testElementCollision(Element element, Player player,boolean isMobile, boolean destroyAtEnd) {
		POOgger.demePOOgger(sprites).testPlayer_ElementCollision(element, player ,isMobile,destroyAtEnd);
	}
	
	public void move(int player, char dir) {
		POOgger.demePOOgger(sprites).movePlayer(dir, player);
		wait(300);
	}
	
	public void killPlayer(int player) {
		POOgger.demePOOgger(sprites).killPlayer(POOgger.demePOOgger(sprites).getPlayer(player));
	}
	
	public void wait(int delay) {
		try {
			Thread.sleep(delay);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void deberiaMoverseHaciaArriba() {
		int xPos = POOgger.demePOOgger(sprites).getPlayer(0).getX(), yPos = POOgger.demePOOgger(sprites).getPlayer(0).getY();
		int step = POOgger.demePOOgger(sprites).getPlayer(0).getWidth()/3;
		int[] beforePosition = new int[] {xPos, yPos};
		POOgger.demePOOgger(sprites).movePlayer('W', 0);
		xPos = POOgger.demePOOgger(sprites).getPlayer(0).getX(); yPos = POOgger.demePOOgger(sprites).getPlayer(0).getY();
		int[] afterPosition = new int[] {xPos, yPos};
		assertTrue(afterPosition[1] == beforePosition[1] - step);
	}

	@Test
	public void noDeberiaMoverseHaciaAbajo() {
		int xPos = POOgger.demePOOgger(sprites).getPlayer(0).getX(), yPos = POOgger.demePOOgger(sprites).getPlayer(0).getY();
		int step = POOgger.demePOOgger(sprites).getPlayer(0).getWidth()/3;
		int[] beforePosition = new int[] {xPos, yPos};
		POOgger.demePOOgger(sprites).movePlayer('S', 0);
		xPos = POOgger.demePOOgger(sprites).getPlayer(0).getX(); yPos = POOgger.demePOOgger(sprites).getPlayer(0).getY();
		int[] afterPosition = new int[] {xPos, yPos};
		assertTrue(afterPosition[1] == beforePosition[1] + step);
	}

	@Test
	public void deberiaMoverseHaciaLaDerecha() {
		int xPos = POOgger.demePOOgger(sprites).getPlayer(0).getX(), yPos = POOgger.demePOOgger(sprites).getPlayer(0).getY();
		//int step = POOgger.demePOOgger(sprites).getPlayer(0).getWidth()/3;
		int[] beforePosition = new int[] {xPos, yPos};
		POOgger.demePOOgger(sprites).movePlayer('D', 0);
		xPos = POOgger.demePOOgger(sprites).getPlayer(0).getX(); yPos = POOgger.demePOOgger(sprites).getPlayer(0).getY();
		int[] afterPosition = new int[] {xPos, yPos};
		assertTrue(beforePosition[0] < afterPosition[0]);
	}
	
	@Test
	public void deberiaMoverseHaciaLaIzquierda() {
		int xPos = POOgger.demePOOgger(sprites).getPlayer(0).getX(), yPos = POOgger.demePOOgger(sprites).getPlayer(0).getY();
		int[] beforePosition = new int[] {xPos, yPos};
		POOgger.demePOOgger(sprites).movePlayer('A', 0);
		xPos = POOgger.demePOOgger(sprites).getPlayer(0).getX(); yPos = POOgger.demePOOgger(sprites).getPlayer(0).getY();
		int[] afterPosition = new int[] {xPos, yPos};
		assertTrue(beforePosition[0] > afterPosition[0]);
	}
	
	@Test
	public void deberiaIniciarConCincoVidas() {
		assertEquals(5, POOgger.demePOOgger(sprites).getPlayer(0).getLives());
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
		assertEquals(3, POOgger.demePOOgger(sprites).getPlayer(1).getLives());
	}
	
	
	@Test
	public void noDeberiaEstarVivo() {
		for (int i = 0; i < 5; i++) POOgger.demePOOgger(sprites).killPlayer(POOgger.demePOOgger(sprites).getPlayer(0));
		assertFalse(POOgger.demePOOgger(sprites).getPlayer(0).isAlive());
	}
	
	@Test
	public void deberiaObtenerPuntosNuevoSalto() {
		
		
		int beforePoints = Integer.parseInt(POOgger.demePOOgger(sprites).getPoints(player));
		POOgger.demePOOgger(sprites).movePlayer('W', 0);
		try {
			Thread.sleep(200);
		} catch(Exception e) {
			e.printStackTrace();
		}
		int afterPoints = Integer.parseInt(POOgger.demePOOgger(sprites).getPoints(player));
		assertTrue(afterPoints == beforePoints + 10);
	}
	
	@Test
	public void noDeberiaObtenerPuntosViejoSalto() {
		
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
		
		int beforePoints = Integer.parseInt(POOgger.demePOOgger(sprites).getPoints(player));
		POOgger.demePOOgger(sprites).killPlayer(POOgger.demePOOgger(sprites).getPlayer(0));
		int afterPoints = Integer.parseInt(POOgger.demePOOgger(sprites).getPoints(player));
		assertTrue(afterPoints == beforePoints - 100);
	}
	
	@Test
	public void noDeberiaDejarloPasarBarrera() {
		
		int beforeX = player.getX();
		int beforeY = player.getY();
		Barrier barrier = new Barrier(player.getX()+48,player.getY(),player.getWidth(),player.getHeight(),48,false);
		POOgger.demePOOgger(sprites).movePlayer('D', 0);
		testElementCollision(barrier,player,false);
		assertTrue(beforeX == player.getX() || beforeY == player.getY());
	}
	
	@Test
	public void deberiaMatarloBarrera() {
		
		int initialLives = player.getLives();
		Barrier barrier = new Barrier(player.getX()+48,player.getY(),player.getWidth(),player.getHeight(),48,true);
		POOgger.demePOOgger(sprites).movePlayer('D', 0);
		testElementCollision(barrier,player,false);
		assertTrue(player.getLives()<initialLives);
	}
	
	@Test
	public void deberiaMatarloRio() {
		
		int initialLives = player.getLives();
		Beaver beaver = new Beaver(player.getX(),player.getY(),player.getWidth(),player.getHeight());
		testElementCollision(beaver,player,false);
		assertTrue(player.getLives()<initialLives);
	}
	
	@Test
	public void deberiaLlevarloEnBicicleta() {
		
		int beforeX = player.getX();
		int beforeY = player.getY();
		Bike bike = new Bike(player.getX(),player.getY(),2,new int[] {48,48},"Bike1",false);
		testElementCollision(bike,player,true);
		assertTrue(beforeX != player.getX() || beforeY != player.getY());
	}
	
	@Test
	public void deberiaDarleMasPuntosBug() {
		
		int beforePoints = player.getPoints();
		Bug bug  = new Bug(player.getX(),player.getY(),48,48,10000);
		testElementCollision(bug,player,false);
		assertTrue(player.getPoints()>beforePoints);
	}
	
	@Test
	public void deberiaMatarloCarro() {
		
		int initialLives = player.getLives();
		Car car = new Car(player.getX(),player.getY(),2,new int[] {48,48},"RedCar");
		testElementCollision(car,player,true);
		assertTrue(player.getLives()<initialLives);
	}
	
	@Test
	public void deberiaAumentarCuevasAlcanzadas() {
		
		int initialCavesReached = player.getCavesReach();
		Cave cave = new Cave(player.getX(),player.getY()-player.getHeight(),48,48);
		move(0,'W');
		testElementCollision(cave,player,false);
		assertTrue(player.getCavesReach()>initialCavesReached);
	}
	
	@Test
	public void noDeberiaAumentarCuevasAlcanzadas() {
		
		int initialCavesReached = player.getCavesReach();
		Cave cave = new Cave(player.getX()+30,player.getY()-player.getHeight(),48,48);
		move(0,'W');
		testElementCollision(cave,player,false);
		assertFalse(player.getCavesReach()>initialCavesReached);
	}
	@Test
	public void deberiaEstarOcupada() {
		
		Cave cave = new Cave(player.getX(),player.getY()-player.getHeight(),48,48);
		move(0,'W');
		testElementCollision(cave,player,false,false);
		move(0,'W');
		assertTrue(cave.getBounds().equals(new Rectangle(0,0,0,0)));
	}
	
	@Test
	public void deberiaSerMasPequeno() {
		
		double beforeClockWidth = player.getClock().width;
		player.updateClock();
		double afterClockWidth = player.getClock().width;
		assertTrue(beforeClockWidth<afterClockWidth);
	}
	
	@Test
	public void deberiaDarleMasPuntos() {
		
		int beforePoints = player.getPoints();
		Cave cave = new Cave(player.getX(),player.getY()-player.getHeight(),48,48);
		FemaleFrog fFrog = new FemaleFrog(player.getX(),player.getY(),1,new int[] {48,48},"FFrog1",false);
		testElementCollision(fFrog,player,true);
		move(0,'W');
		testElementCollision(cave,player,false);
		assertTrue(player.getPoints()-10==beforePoints+400);
	}
	
	@Test
	public void deberiaQuitarleMasPuntos() {
		
		int beforePoints = player.getPoints();
		FemaleFrog fFrog = new FemaleFrog(player.getX(),player.getY(),1,new int[] {48,48},"FFrog1",false);
		testElementCollision(fFrog,player,true);
		killPlayer(0);
		assertTrue(player.getPoints()+300==beforePoints);
	}
	
	@Test
	public void deberiaMoverseConTroncoFemaleFrog() {
		SmallLog small = new SmallLog(48,48,2,new int[] {96,48},new int[] {48,48},"SmallLog");
		FemaleFrog fFrog = new FemaleFrog(48,48,1,new int[] {48,48},"FFrog1",false);
		int beforeX = fFrog.getX();
		small.inCollision(fFrog);
		small.move();
		assertTrue(fFrog.getX()!=beforeX);
	}
	
	@Test
	public void deberiaDesaparecerConTroncoFemaleFrog() {
		SmallLog small = new SmallLog(player.getX(),player.getY(),2,new int[] {96,48},new int[] {48,48},"SmallLog");
		FemaleFrog fFrog = new FemaleFrog(player.getX(),player.getY(),1,new int[] {48,48},"FFrog1",false);
		small.inCollision(fFrog);
		testElementCollision(small,player,true);
		player.move();
		wait(2000);
		fFrog.move();
		assertFalse(fFrog.isVisible());
	}
	
	@Test
	public void deberiaLlevarloEnCocodrilo() {
		
		int beforeX = player.getX();
		int beforeY = player.getY();
		Lizard lizard = new Lizard(player.getX(),player.getY(),new int[] {48*3,48},"Lizard1",2);
		testElementCollision(lizard,player,true);
		assertTrue(beforeX != player.getX() || beforeY != player.getY());
	}
	
	@Test
	public void deberiaMorirPorCocodrilo() {
		int initialLives = player.getLives();
		Lizard lizard = new Lizard(player.getX()-player.getWidth()*2,player.getY(),new int[] {48*3,48},"Lizard1",2);
		wait(1600);
		testElementCollision(lizard,player,true);
		assertTrue(initialLives>player.getLives());
	}
	
	@Test
	public void deberiaLlevarloEnTronco() {
		int beforeX = player.getX();
		int beforeY = player.getY();
		Log log = new Log(player.getX(),player.getY(),1,new int[] {48*3,48},"LargeLog");
		testElementCollision(log,player,true);
		assertTrue(beforeX != player.getX() || beforeY != player.getY());
	}
	
	@Test
	public void deberiaEmpujarloMotocicleta() {
		int beforeX = player.getX();
		int beforeY = player.getY();
		Motorcycle mc = new Motorcycle(player.getX(),player.getY(),1,new int[] {48,48},"Motorcycle",false);
		testElementCollision(mc,player,true);
		assertTrue(beforeX == player.getX() && beforeY > player.getY());
	}
	
	@Test
	public void deberiaEmpujarloCharco() {	
		int beforeX = player.getX();
		int beforeY = player.getY();
		Puddle puddle = new Puddle(player.getX(),player.getY(),48,48);
		testElementCollision(puddle,player,false);
		assertTrue(beforeX < player.getX() && beforeY == player.getY());
	}
	
	@Test
	public void deberiaTenerMenorColision() {
		SmallLog small = new SmallLog(player.getX(),player.getY(),2,new int[] {96,48},new int[] {48,48},"SmallLog");
		Rectangle beforeBounds = small.getBounds();
		testElementCollision(small,player,true);
		player.move();
		wait(1500);
		assertTrue(small.getBounds().width*small.getBounds().height<beforeBounds.width*beforeBounds.height);
	}
	
	@Test
	public void deberiaDesaparecerTronco() {
		SmallLog small = new SmallLog(player.getX(),player.getY(),2,new int[] {96,48},new int[] {48,48},"SmallLog");
		testElementCollision(small,player,true);
		player.move();
		wait(2000);
		assertFalse(small.isVisible());
	}
	
	@Test
	public void deberiaMorirPorSnake() {
		int initialLives = player.getLives();
		Snake snake = new Snake(player.getX(),player.getY(),1,new int[] {48,48},"Snake1",false);
		testElementCollision(snake,player,true);
		assertTrue(player.getLives()<initialLives);
	}
	
	@Test
	public void deberiaMoverseConTroncoSnake() {
		Snake snake = new Snake(player.getX(),player.getY(),1,new int[] {48,48},"Snake1",false);
		Log log = new Log(player.getX(),player.getY(),1,new int[] {48*4,48},"LargeLog");
		int beforeX = snake.getX();
		log.inCollision(snake);
		log.move();
		assertTrue(snake.getX()>beforeX);
	}
	
	@Test
	public void deberiaCambiarDireccionSnake() {
		Snake snake = new Snake(player.getX(),player.getY(),1,new int[] {48,48},"Snake1",false);
		char beforeDir = snake.getDir();
		snake.flip();
		assertTrue(snake.getDir()!=beforeDir);
	}

	@Test
	public void deberiaLlevarloEnCamion() {	
		int beforeX = player.getX();
		int beforeY = player.getY();
		Truck truck = new Truck(player.getX(),player.getY(),2,new int[] {48,48},"Truck",false);
		testElementCollision(truck,player,true);
		assertTrue(beforeX != player.getX() || beforeY != player.getY());
	}
	
	@Test
	public void deberiaMorirPorCamion() {	
		int initialLives = player.getLives();
		Truck truck = new Truck(player.getX()+player.getWidth()-1,player.getY(),1,new int[] {48,48},"Truck",true);
		testElementCollision(truck,player,true);
		assertTrue(player.getLives()<initialLives);
	}
	
	@Test
	public void deberiaLlevarloEnTurtle() {
		int beforeX = player.getX();
		int beforeY = player.getY();
		Turtle turtle = new Turtle(player.getX(),player.getY(),2,new int[] {48,48},"Turtle1",true);
		testElementCollision(turtle,player,true);
		assertTrue(beforeX != player.getX() || beforeY != player.getY());
	}
	
	@Test
	public void deberiaSumergirse() {
		Turtle turtle = new Turtle(player.getX(),player.getY(),2,new int[] {48,48},"Turtle1",true);
		turtle.move();
		wait(1200);
		assertTrue(turtle.getBounds().equals(new Rectangle(0,0,0,0)));
	}
	
	@Test
	public void deberiaPoderTenerArmadura() {
		ArmorPower armor = new ArmorPower(player.getX(),player.getY(),48,48);
		testElementCollision(armor,player,false);
		assertTrue(player.hasArmor());
	}
	
	@Test
	public void deberiaPoderTenerVelocidad() {
		SpeedPower speed = new SpeedPower(player.getX(),player.getY(),48,48);
		testElementCollision(speed,player,false);
		assertTrue(player.hasSpeed());
	}
	
	@Test
	public void deberiaPoderTenerAlas() {
		FlyPower wings = new FlyPower(player.getX(),player.getY(),48,48);
		testElementCollision(wings,player,false);
		assertTrue(player.hasWings());
	}
	
	@Test
	public void deberiaTenerArmadura() {
		ArmorPower armor = new ArmorPower(player.getX(),player.getY(),48,48);
		testElementCollision(armor,player,false);
		player.activatePower('1');
		assertTrue(player.isArmored());
	}
	
	@Test
	public void deberiaTenerVelocidad() {
		SpeedPower speed = new SpeedPower(player.getX(),player.getY(),48,48);
		testElementCollision(speed,player,false);
		player.activatePower('2');
		assertTrue(player.isFast());
	}
	
	@Test
	public void deberiaTenerAlas() {
		FlyPower wings = new FlyPower(player.getX(),player.getY(),48,48);
		testElementCollision(wings,player,false);
		player.activatePower('3');
		player.makeFly();
		assertTrue(player.isInAir());
	}
	
	@Test
	public void deberiaAgregarNuevosElementos() {
		ArrayList<String[]> firstElements = POOgger.demePOOgger(sprites).gameLoop(0);
		int time = 0;
		while (time<10000) {
			POOgger.demePOOgger(sprites).gameLoop(time);
			time = time+10;
		}
		ArrayList<String[]> lastElements = POOgger.demePOOgger(sprites).gameLoop(time);
		assertFalse(firstElements.equals(lastElements)); 
	}
	
	@Test
	public void noDeberiaTenerColision() {
		POOgger.demePOOgger(sprites).killPlayer(player);
		assertTrue(player.getBounds().height*player.getBounds().width==0);
	}
	
	@Test
	public void deberiaMorirPorRayo() {
		int initialLives = player.getLives();
		Thunder thunder = new Thunder(144,48,740,player);
		wait(2000);
		testElementCollision(thunder, player, false);
		System.out.println(initialLives+" "+player.getLives());
		assertTrue(player.getLives()<initialLives);
	}
	
	@Test
	public void deberiaSeguirJugador() {
		Eagle eagle = new Eagle(player.getX()+100, 0, 5, new int[] {48,48}, "Eagle", player);
		int beforeXEagle = eagle.getX(), beforeX = player.getX();
		for(int i=0;i<1000;i++) eagle.move();
		assertTrue(Math.abs(beforeX-beforeXEagle)>Math.abs(player.getX()-eagle.getX()));
	}
	
	@Test
	public void deberiaMorirPorAguila() {
		Eagle eagle = new Eagle(player.getX(), player.getY(), 5, new int[] {48,48}, "Eagle", player);
		assertTrue(eagle.inCollision(player));
	}
	
	@Test
	public void deberiaPoderPausarAnimadores() {
		int time = 0;
		while (time<1000) {
			POOgger.demePOOgger(sprites).gameLoop(time);
			time = time+10;
		}
		try {
			POOgger.demePOOgger(sprites).pauseElements();
		}catch(Exception e) {
			Assert.fail("Se lanzo una excepcion");
		}
	}
	
	@Test
	public void deberiaPoderResumirAnimadores() {
		int time = 0;
		while (time<1000) {
			POOgger.demePOOgger(sprites).gameLoop(time);
			time = time+10;
		}
		try {
			POOgger.demePOOgger(sprites).pauseElements();
			POOgger.demePOOgger(sprites).resumeElements();
		}catch(Exception e) {
			Assert.fail("Se lanzo una excepcion");
		}
	}
	
	@After
	public void tearDown(){
		POOgger.cambiePOOgger(null);
		player = null;
	}
}