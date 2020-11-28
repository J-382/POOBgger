package dominio;

import java.awt.Rectangle;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * POOgger's player implementation
 * @version 2.1
 * @author Angie Medina - Jose Perez
 */
public class POOgger implements Serializable{
	private static POOgger poogger = null;
	
	public static POOgger demePOOgger(HashMap<String, int[]> archivo) {
		if (poogger == null) {
			poogger = new POOgger(720,768, archivo,new char[] {'A','W','S','D'},new char[] {'A','W','S','D'});
		}
		return poogger;
	}
	
	public static void cambiePOOgger(POOgger p) {
		poogger = p;
	}
	
	private int screenWidth;
	private int screenHeight;
	private int[] logsSpeed;
	private int[] carsSpeed;
	private int snakeSpeed;
	private int turtleSpeed;
	private int lizzardSpeed;
	private boolean exist;
	private Player player;
	private Rectangle clock;
    private Animator animator;
	private boolean isPlayerAlive;
	private ArrayList<Element> elements;
	private ArrayList<Element> fixeds;
	//private ArrayList<Fixed> fixeds;
	private char[] player1Keys;
	private char[] player2Keys;
	private HashMap<String,int[]> sprites;
	private final int deadPenalization = -100;
	
	/**
	 * POOgger class constructor
	 * @param width POOgger's windows width
	 * @param height POOgger's windows height
	 * @param sprites HashMap with all sprites's sizes
	 */
	private POOgger(int width, int height, HashMap<String,int[]> sprites, char[] player1Keys, char[] player2Keys) {
		screenWidth = width;
		screenHeight = height;
		this.sprites = sprites;
		this.player1Keys = player1Keys;
		this.player2Keys = player2Keys;
		exist = false;
		isPlayerAlive = true;
		snakeSpeed = 1;
		turtleSpeed = 1;
		lizzardSpeed = 2;
		logsSpeed = new int[] {1,2,3};
		carsSpeed = new int[] {4,3,2,5,2};
		player = new Player(5,48*7,48*14, sprites.get("Frog1W"));
		elements = new ArrayList<Element>();
		fixeds = new ArrayList<Element>();
		clock = new Rectangle(0,0, 0, 20);
		addFixedElements();
		//addSnake();
	}
	
	private void restoreClock() {
		clock = new Rectangle(0, 0, 0, clock.height);
	}
	
	/**
	 * Updates clock size
	 **/
	public void updateClock() {
		clock = new Rectangle(0, 0, clock.width + 1, clock.height);
		if (clock.width == 306) {
			player.decreasePlayerLives();
			restoreClock();
		}
	}
	
	
	/**
	 * Move the player, if possible, in the given direction
	 * @param dir direction
	 */
	public void movePlayer(char dir) {
		boolean isValid = false;
		for(char i: player1Keys) {
			if(i==dir) {
				isValid = true;
				break;
			}
		}
		if (isValid) {
			player.setOrientation(dir);
		}
	}
	
	/**
	 * Returns the POOgger's actual player
	 */
	public Player getPlayer() {
		return player;
	}
	
	/**
	 * Returns the player's points
	 */
	public int getPoints() {
		return player.getPoints();
	}
	
	/**
	 * Returns the highest score saved
	 */
	public int getHighScore() {
		return 0;
	}
	
	/**
	 * Returns the clock sprite
	 * @return clock's sprite
	 */
	public Rectangle getClock() {
		return clock;
	}

	/**
	 * Returns if player is alive
	 * @return true if player is alive, false otherwise
	 */
	public boolean isPlayerAlive() {
		return isPlayerAlive;
	}
	
	/**
	 * Move all POOgger's elements
	 * @return An ArrayList with the moved elements
	 */
	private ArrayList<Element> update() {
		boolean needsClear = false;
		for(int i=0; i<elements.size(); i++) {
			Mobile element = (Mobile) elements.get(i);
			if(element.getX()>screenWidth+200 || element.getX()<-500 || !element.isVisible()) {
				elements.set(i, null);
				needsClear = true;
				}
			else element.move();
			}
		if(needsClear) clearElements();
		return elements;
	}
	
	/**
	 * Eliminates null elements
	 */
	private void clearElements() {
		while(elements.remove(null)) {}
		while(fixeds.remove(null)) {}
	}
	
	private void addEagle() {
		elements.add(new Eagle(1, sprites.get("Eagle1"), "Eagle1", player));
	}
	
	private void addThunder() {
		elements.add(new Thunder(player));
	}
	/**
	 * Add a new bike to POOgger's elements
	 */
	private void addBike() {
		elements.add(new Bike(screenWidth,48*11,-carsSpeed[2], sprites.get("Bike1"),"Bike1" ,false));
		/*Random r = new Random();
		if(r.nextBoolean()) {
			elements.add(new Bike(0,8,1,true));
		}*/		
	}
	
	/** 
	 * Add a new car to POOgger's elements in the given lane
	 * @param lane the new car's lane
	 */
	private void addCar(int lane) {
		String[] types = new String[] {"Red","Green","Blue","Pink","Purple"};
		switch (lane) {
			case 0:
				elements.add(new Car(screenWidth,48*12,-carsSpeed[0],sprites.get(types[0]+"Car"),types[0]));
				break;
			case 1:
				elements.add(new Car(-sprites.get(types[1]+"Car")[0],48*13,carsSpeed[1],sprites.get(types[1]+"Car"),types[1]));
				break;
			case 2:
				elements.add(new Car(screenWidth,48*11,-carsSpeed[2],sprites.get(types[2]+"Car"),types[2]));
				break;
			case 3:
				elements.add(new Car(-sprites.get(types[3]+"Car")[0],48*10,carsSpeed[3],sprites.get(types[3]+"Car"),types[3]));
				break;
			case 4:
				elements.add(new Car(screenWidth,48*9,-carsSpeed[4],sprites.get(types[4]+"Car"),types[4]));
				break;
		}
	}
	
	/** Add a new Lizzard to POOgger's elements
	 */
	private void addLizard() {
		//elements.add(new Lizzard(0, 48*5+4, lizzardSpeed));
		elements.add(new Lizard(-sprites.get("Lizard1")[0],48*3, sprites.get("Lizard1"), "Lizard1", lizzardSpeed));
	}
	
	/** 
	 * Add a new log to POOgger's elements in the given lane
	 * @param lane the new log's lane
	 */
	private void addLog(int lane) {
		String[] types = new String[] {"Small","Medium","Large"};
		switch (lane) {
			case 0:
				elements.add(new SmallLog(-sprites.get(types[0]+"Log1")[0],48*6,logsSpeed[0],sprites.get(types[0]+"Log1"),types[0]+"Log1"));
				break;
			case 2:
				elements.add(new Log(-sprites.get(types[1]+"Log")[0],48*3,logsSpeed[1],sprites.get(types[1]+"Log"),types[1]+"Log"));
				break;
			case 1:
				elements.add(new Log(-sprites.get(types[2]+"Log")[0],48*5,logsSpeed[2],sprites.get(types[2]+"Log"),types[2]+"Log"));
				break;
		}
	}
	
	/** 
	 * Add a new motorcycle to POOgger's elements
	 */
	private void addMotorcycle() {
		elements.add(new Motorcycle(-sprites.get("Motorcycle1")[0],48*10,carsSpeed[3],sprites.get("Motorcycle1"),"Motorcycle1",true));
		/*
		Random r = new Random();
		if(r.nextBoolean()) {
			
		}else elements.add(new Motorcycle(350,6,-2	,false));
		*/
	}

	/** 
	 * Add a new snake to POOgger's elements
	 */
	private void addSnake() {
		Random r = new Random();
		boolean flipped = r.nextBoolean();
		if(flipped) {
			elements.add(new Snake(screenWidth,48*8,snakeSpeed,sprites.get("Snake1"),"Snake1",false));
		}else elements.add(new Snake(0,48*8,-1*snakeSpeed,sprites.get("Snake1"),"Snake1",true));
	}
	
	/** 
	 * Add a new Truck to POOgger's elements
	 */
	private void addTruck() {
		/**Random r = new Random();
		if(r.nextBoolean()) {
			elements.add(new Truck(0,8,1,true));
		}else*/
		elements.add(new Truck(screenWidth,48*9,-carsSpeed[4],sprites.get("Truck1"),"Truck1",false));
		
	}
	
	/** Add a new turtle to POOgger's elements in the given lane
	 * @param lane the new turtle's lane
	 */
    private void addTurtle(int lane) {
    	Random r = new Random();
    	boolean doesSubmerge = r.nextBoolean();
    	if(lane==0) {
			elements.add(new Turtle(screenWidth,48*7,-3*turtleSpeed,sprites.get("Turtle1"),"Turtle1", doesSubmerge));
			elements.add(new Turtle(screenWidth+20+sprites.get("Turtle1")[0],48*7,-3*turtleSpeed,sprites.get("Turtle1"),"Turtle1", doesSubmerge));
			elements.add(new Turtle(screenWidth+40+2*sprites.get("Turtle1")[0],48*7,-3*turtleSpeed,sprites.get("Turtle1"),"Turtle1", doesSubmerge));
		}else {
			elements.add(new Turtle(screenWidth, 48*4, -2*turtleSpeed,sprites.get("Turtle1"),"Turtle1", doesSubmerge));
			elements.add(new Turtle(screenWidth+20+sprites.get("Turtle1")[0], 48*4, -2*turtleSpeed,sprites.get("Turtle1"),"Turtle1", doesSubmerge));
		}
		
    }
	
	/**
	 * Checks if player is collisioning with some level's elements
	 * @param element desired element to check
	 */
	private boolean checkPlayerCollisions(Player player) {
		boolean[] mobileCollisions = checkMobileElements(player);
		return mobileCollisions[0] || checkFixedElements(player,mobileCollisions[1]);
		
	}
	
	private boolean[] checkMobileElements(Player player) {
		boolean isDead = false;
		boolean touchingWater = true;
		for(Element e: elements) {
			if(player.getBounds().intersects(e.getBounds())) {
				isDead = e.inCollision(player);
				touchingWater = false;
			}
			if(isDead) break;
		}
		if (isDead) {
			player.changePoints(deadPenalization);
			restoreClock();
		}
		return new boolean[] {isDead,touchingWater};
	}
	
	private boolean checkFixedElements(Player player, boolean touchingWater) {
		boolean isDead = false;
		if(player.getBounds().intersects(fixeds.get(0).getBounds())) {
			touchingWater = touchingWater && fixeds.get(0).inCollision(player);
		}else touchingWater = false;
		for (int i = 1; i < fixeds.size(); i++) {
			Fixed f = (Fixed)fixeds.get(i);
			if (f.canBeOccupied()) {
				Cave e = (Cave) f;
				if(player.getBounds().intersects(e.getBounds())) {
					isDead = e.inCollision(player);
					if(!isDead && e.isOccupied()) {
						player.changePoints(e.getPoints());
						restoreClock();
						resetPlayer(player);
					}
				}
			}
			else {
				if(player.getBounds().intersects()) {
					isDead = f.inCollision(player);
					if(isDead) break;
				}
			}
		}
		clearElements();	
		if (isDead) {
			player.changePoints(deadPenalization);
			restoreClock();
		}
		return isDead || touchingWater;
	}
	
	/***/
	public void killPlayer(Player player) {
		isPlayerAlive = player.decreasePlayerLives();
	}
	
	public void resetPlayer(Player player) {
		player.resetPlayer();
	}
	/**
	 * Adds a new element to the given lane
	 * @param lane the new element's lane
	 */
	private void addLane(int time) {
		Random r = new Random();
		if (!exist) {
			addEagle();
			//addThunder();
			exist = true;
		}
		if(time%250==0) {
			addCar(0);
		}
		if(time%500==0) {
			addCar(1);
		}
		if(time%300==0) {
			if(r.nextBoolean()) {
				addCar(2);
			}else addBike();
		}
		if(time%250==0) {
			if(r.nextBoolean()) {
				addCar(3);
			}else addMotorcycle();
		}
		if(time%500==0) {
			if(r.nextBoolean()) {
				addCar(4);
			}else {
				addTruck();
			}
		}
	}
		
	/**
	 * Adds a new element to the given lane
	 * @param lane the new element's lane
	 */
	private void addBeaverLane(int time) {
		Random r = new Random();
		if(time%200==0) {
			addTurtle(0);
		}
		if(time%500==0) {
			addLog(0);
		}
		if(time%400==0) {
			addLog(1);
		}
		if(time%200==0) {
			addTurtle(3);
		}
		if(time%300==0) {
			if(r.nextBoolean()) {
				addLog(2);
			} else addLizard();
		}
	}
	
	public ArrayList<Element> gameLoop(int time) {
		//addBeaverLane(time);
		//addLane(time);
		checkPlayerCollisions(player);
		if(time%2==0) update();
		if(checkPlayerCollisions(player)) killPlayer(player);
		ArrayList<Element> allElements = new ArrayList<Element>();
		allElements.addAll(fixeds);
		allElements.addAll(elements);
		return allElements;
	}
	
	private void addFixedElements() {
		/*Barriers*/
		fixeds.add(new Beaver(0,48*3,screenWidth,240));
		fixeds.add(new Barrier(-48,48*8,48,48*7,48,false));
		fixeds.add(new Barrier(screenWidth,48*8,48,48*7,48,false));
		fixeds.add(new Barrier(0,48*15,48*16,48,48,false));
		fixeds.add(new Barrier(-48,48,48,48*7,48,true));
		fixeds.add(new Barrier(screenWidth,48,48,48*7,48,true));
		/*Caves*/
		fixeds.add(new Cave(48,48*2,48,48));
		fixeds.add(new Cave(48*4,48*2,48,48));
		fixeds.add(new Cave(48*7,48*2,48,48));
		fixeds.add(new Cave(48*10,48*2,48,48));
		fixeds.add(new Cave(48*13,48*2,48,48));
		/*Power*/
		fixeds.add(new SpeedPower(48*3,48*13,48,48));
		fixeds.add(new FlyPower(48*3,48*10,48,48));
		fixeds.add(new ArmorPower(48*3,48*14,48,48));
	}
	
	/**
	 * Abre si es posible, el archivo especificado
	 * @param file el archivo que se desea abrir
	 * @throws POOggerException     - TIPO_ERRONEO Cuando el archivo que se quiere abrir no es .dat
	 * 								- ERROR_ABRIR Cuando se tuvo problema al intentar abrir el archivo
	 * 								- CLASE_NO_ENCONTRADA Cuando se guardo en el archivo una objeto de una clase que no se tiene en la aplicacion 
	 */
	public void abra(File file) throws POOggerException{
		try {
			if (!file.getCanonicalPath().endsWith(".dat")) {
				throw new POOggerException(POOggerException.TIPO_ERRONEO_DAT);
			}
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
			poogger = (POOgger) in.readObject();
			in.close();
		} catch(IOException e) {		
			throw new POOggerException(POOggerException.ERROR_ABRIR);
		} catch(ClassNotFoundException e) {
			throw new POOggerException(POOggerException.CLASE_NO_ENCONTRADA);
		}
	}
	
	/**
	 * Guarda, si es posible, en el archivo especificado
	 * @param file el archivo a guardar
	 * @throws POOggerException     - TIPO_ERRONEO Cuando el archivo en el que se quiere guardar no es .dat
	 * 								- ERROR_SALVAR Cuando se tuvo problema al intentar salvar el archivo 
	 */
	public void guarde(File file) throws POOggerException{
		try {
			if (!file.getCanonicalPath().endsWith(".dat")) {
				throw new POOggerException(POOggerException.TIPO_ERRONEO_DAT);
			}
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
			out.writeObject(poogger);
			out.close();
		} catch(IOException e) {
			System.out.println(e.getMessage());
			throw new POOggerException(POOggerException.ERROR_SALVAR);
		}
	}
}