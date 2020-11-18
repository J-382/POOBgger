package dominio;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * POOgger's player implementation
 * @version 2.1
 * @author Angie Medina - Jose Perez
 */
public class POOgger {
	private int screenWidth;
	private int[] logsSpeed;
	private int[] carsSpeed;
	private int snakeSpeed;
	private int turtleSpeed;
	private int lizzardSpeed;
	private Player player;
	private Rectangle clock;
    private Animator animator;
	private boolean isPlayerAlive;
	private ArrayList<Element> elements;
	private char[] playerKeys;
	private HashMap<String,int[]> sprites;
	
	/**
	 * POOgger class constructor
	 * @param width POOgger's windows width
	 * @param height POOgger's windows height
	 * @param sprites HashMap with all sprites's sizes
	 */
	public POOgger(int width, int height, HashMap<String,int[]> sprites) {
		screenWidth = width;
		player = new Player(5,624,678, sprites.get("Frog1W"));
		elements = new ArrayList<Element>();
		logsSpeed = new int[] {1,2,3};
		carsSpeed = new int[] {4,3,2,5,2};
		snakeSpeed = 1;
		turtleSpeed = 1;
		lizzardSpeed = 2;
		clock = new Rectangle(0,0, 450, 20);
		playerKeys = new char[] {'A','W','S','D'};
		isPlayerAlive = true;
		animator = new Animator();
		this.sprites = sprites;
		elements.add(new Beaver(0,48*3,screenWidth,240));
		animator.animate(100, 101, new Runnable() {public void run() {updateClock();}}, false);
	}
	
	/**
	 * Updates clock size
	 */
	private void updateClock() {
		clock = new Rectangle(0, 0, clock.width - 3 , clock.height);
		if (clock.width == 0) {
			player.decreasePlayerLives(336, 678);
			clock = new Rectangle(0, 0, 300, 20);
		}
		
	}
	
	/**
	 * Move the player, if possible, in the given direction
	 * @param dir direction
	 */
	public void movePlayer(char dir) {
		boolean isValid = false;
		for(char i: playerKeys) {
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
			Element element = elements.get(i);
			if(element.getX()>screenWidth+200 || element.getX()<-500) {
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
	}
	
	/**
	 * Add a new bike to POOgger's elements
	 */
	private void addBike() {
		elements.add(new Bike(screenWidth,48*11+8,-carsSpeed[2],false));
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
				elements.add(new Car(screenWidth,48*12+2,-carsSpeed[0],types[0]));
				break;
			case 1:
				elements.add(new Car(-sprites.get(types[1]+"Car")[0],48*13+2,carsSpeed[1],types[1]));
				break;
			case 2:
				elements.add(new Car(screenWidth,48*11+2,-carsSpeed[2],types[2]));
				break;
			case 3:
				elements.add(new Car(-sprites.get(types[3]+"Car")[0],48*10+2,carsSpeed[3],types[3]));
				break;
			case 4:
				elements.add(new Car(screenWidth,48*9+2,-carsSpeed[4],types[4]));
				break;
		}
	}
	
	/** Add a new Lizzard to POOgger's elements
	 */
	private void addLizzard() {
		//elements.add(new Lizzard(0, 48*5+4, lizzardSpeed));
		elements.add(new Lizzard(-sprites.get("Alligator2")[0],48*3+4, 138, lizzardSpeed));
	}
	
	/** 
	 * Add a new log to POOgger's elements in the given lane
	 * @param lane the new log's lane
	 */
	private void addLog(int lane) {
		String[] types = new String[] {"Small","Medium","Large"};
		switch (lane) {
			case 0:
				elements.add(new SmallLog(-sprites.get(types[0]+"Log1")[0],48*6+4,logsSpeed[0],types[0]+"Log1"));
				break;
			case 2:
				elements.add(new Log(-sprites.get(types[1]+"Log")[0],48*3+4,logsSpeed[1],types[1]+"Log"));
				break;
			case 1:
				elements.add(new Log(-sprites.get(types[2]+"Log")[0],48*5+4,logsSpeed[2],types[2]+"Log"));
				break;
		}
	}
	
	/** 
	 * Add a new motorcycle to POOgger's elements
	 */
	private void addMotorcycle() {
		elements.add(new Motorcycle(0,48*10+6,carsSpeed[3],true));
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
			elements.add(new Snake(678,0,-1*snakeSpeed,false));
		}else elements.add(new Snake(0,0,-1*snakeSpeed,true));
	}
	
	/** 
	 * Add a new Truck to POOgger's elements
	 */
	private void addTruck() {
		/**Random r = new Random();
		if(r.nextBoolean()) {
			elements.add(new Truck(0,8,1,true));
		}else*/
		elements.add(new Truck(screenWidth,48*9+2,carsSpeed[4],false));
		
	}
	
	/** Add a new turtle to POOgger's elements in the given lane
	 * @param lane the new turtle's lane
	 */
    private void addTurtle(int lane) {
    	Random r = new Random();
    	boolean doesSubmerge = r.nextBoolean();
    	if(lane==0) {
			elements.add(new Turtle(screenWidth,336,-3*turtleSpeed, doesSubmerge));
			elements.add(new Turtle(screenWidth+20+sprites.get("Turtle1")[0],336,-3*turtleSpeed, doesSubmerge));
			elements.add(new Turtle(screenWidth+40+2*sprites.get("Turtle1")[0],336,-3*turtleSpeed, doesSubmerge));
		}else {
			elements.add(new Turtle(screenWidth, 194, -2*turtleSpeed, doesSubmerge));
			elements.add(new Turtle(screenWidth+20+sprites.get("Turtle1")[0], 194, -2*turtleSpeed, doesSubmerge));
		}
		
    }
	
	/**
	 * Checks if player is collisioning with some level's elements
	 * @param element desired element to check
	 */
	private boolean checkPlayerCollisions(Player player) {
		boolean isDead = false;
		boolean touchingWater = true;
		for(int i=1; i<elements.size(); i++) {
			Element e = elements.get(i);
			if(player.getBounds(sprites.get(player.getSprite())).intersects(e.getBounds(sprites.get(e.getSprite())))) {
				isDead = e.inCollision(player);
				touchingWater = false;
			}
			if(isDead) break;
		}
		if(player.getBounds(sprites.get(player.getSprite())).intersects(((Fixed) elements.get(0)).getBounds())) {
			touchingWater = touchingWater && elements.get(0).inCollision(player);
		}else touchingWater = false;
		return isDead || touchingWater;
	}
	
	/***/
	public void killPlayer(Player player) {
		isPlayerAlive = player.decreasePlayerLives(336,678);
	}
	
	/**
	 * Adds a new element to the given lane
	 * @param lane the new element's lane
	 */
	private void addLane(int time) {
		Random r = new Random();
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
			addCar(4);
			if(r.nextBoolean()) {
				addCar(4);
			}else addTruck();
		}
	}
		
	/**
	 * Adds a new element to the given lane
	 * @param lane the new element's lane
	 */
	private void addLaneBeaver(int time) {
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
			} else addLizzard();
		}
	}
	
	public ArrayList<Element> gameLoop(int time) {
		addLaneBeaver(time);
		addLane(time);
		checkPlayerCollisions(player);
		if(time%2==0) update();
		if(checkPlayerCollisions(player)) killPlayer(player);
		return elements;
	}
}