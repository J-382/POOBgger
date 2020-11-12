package dominio;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

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
	
	public POOgger(int width, int height, HashMap<String,int[]> sprites) {
		screenWidth = width;
		player = new Player(5,624,678, sprites.get("Frog1W"));
		elements = new ArrayList<Element>();
		logsSpeed = new int[] {2,4,6};
		carsSpeed = new int[] {6,4,3,8,2};
		snakeSpeed = 1;
		turtleSpeed = 1;
		lizzardSpeed = 1;
		clock = new Rectangle(0,0, 300, 20);
		playerKeys = new char[] {'A','W','S','D'};
		isPlayerAlive = true;
		animator = new Animator();
		this.sprites = sprites; 
		animator.animate(100, 101, new Runnable() {public void run() {updateClock();}}, false);
	}
	
	public void updateClock() {
		clock = new Rectangle(0, 0, clock.width - 3 , clock.height);
		if (clock.width == 0) {
			player.decreasePlayerLives(336, 678);
			clock = new Rectangle(0, 0, 300, 20);
		}
		
	}
	
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
	
	public Player getPlayer() {
		return player;
	}
	
    @SuppressWarnings("exports")
	public Rectangle getClock() {
		return clock;
	}

	public boolean isPlayerAlive() {
		return isPlayerAlive;
	}
	
	public ArrayList<Element> update() {
		boolean needsClear = false;
		for(int i=0; i<elements.size(); i++) {
			Element element = elements.get(i);
			if(element.getX()>screenWidth || element.getX()<-300) {
				elements.set(i, null);
				needsClear = true;
				}
			else element.move();
			}
		if(needsClear) clearElements();
		return elements;
	}
	
	public void clearElements() {
		while(elements.remove(null)) {}
	}
	
	
	
	/*ADDs 
	 * Poner privados
	 */
	
	public void addBike() {
		Random r = new Random();
		if(r.nextBoolean()) {
			elements.add(new Bike(0,8,1,true));
		}else elements.add(new Bike(600,8,-1,false));
		
	}
	
	public void addCar() {
		String[] types = new String[] {"Red","Green","Blue","Pink","Purple"};
		Random r = new Random();
		switch (r.nextInt(5)) {
			case 0:
				elements.add(new Car(screenWidth,48*13+2,-carsSpeed[0],types[0]));
				break;
			case 1:
				elements.add(new Car(-sprites.get(types[1]+"car")[0],48*12+2,carsSpeed[1],types[1]));
				break;
			case 2:
				elements.add(new Car(screenWidth,48*11+2,-carsSpeed[2],types[2]));
				break;
			case 3:
				elements.add(new Car(-sprites.get(types[3]+"car")[0],48*10+2,carsSpeed[3],types[3]));
				break;
			case 4:
				elements.add(new Car(screenWidth,48*9+2,-carsSpeed[4],types[4]));
				break;
		}
	}
	
	public void addLizzard() {
		//elements.add(new Lizzard(0, 48*5+4, lizzardSpeed));
		elements.add(new Lizzard(0, 500, lizzardSpeed));
	}
	
	public void addLog() {
		String[] types = new String[] {"Small","Medium","Large"};
		Random r = new Random();
		switch (r.nextInt(3)) {
			case 0:
				elements.add(new SmallLog(-sprites.get(types[0]+"Log1")[0],48*6+4,logsSpeed[0],types[0]+"Log1"));
				break;
			case 1:
				elements.add(new Log(-sprites.get(types[1]+"Log")[0],48*3+4,logsSpeed[1],types[1]+"Log"));
				break;
			case 2:
				elements.add(new Log(-sprites.get(types[2]+"Log")[0],48*5+4,logsSpeed[2],types[2]+"Log"));
				break;
		}
	}
	
	public void addMotorcycle() {
		Random r = new Random();
		if(r.nextBoolean()) {
			elements.add(new Motorcycle(0,6,2,true));
		}else elements.add(new Motorcycle(350,6,-2	,false));
		
	}
	
	public void addSnake() {
		Random r = new Random();
		boolean flipped = r.nextBoolean();
		if(flipped) {
			elements.add(new Snake(678,0,-1*snakeSpeed,false));
		}else elements.add(new Snake(0,0,-1*snakeSpeed,true));
	}
	
	public void addTruck() {
		Random r = new Random();
		if(r.nextBoolean()) {
			elements.add(new Truck(0,8,1,true));
		}else elements.add(new Truck(600,8,-1,false));
		
	}
	
    public void addTurtle() {
		//elements.add(new Turtle(-sprites.get("Turtle1")[0],6,-1*turtleSpeed, true));
		elements.add(new Turtle(300, 200, -1*turtleSpeed, true));
	}
	
	public ArrayList<Element> getElements(){
		clearElements();
		return elements;
	}
	
	public boolean[] checkPlayerCollision(Player player, Element element) {
		boolean isDead = false;
		boolean areTouching = false;
		if(player.getBounds(sprites.get(player.getSprite())).intersects(element.getBounds(sprites.get(element.getSprite())))) {
			areTouching = true;
			isDead = element.inCollision(player);
		}
		return new boolean[] {isDead, areTouching};
	}
	
	public boolean checkTouch(Player player, Element element) {
		boolean areTouching = false;
		if (player.getBounds(sprites.get(player.getSprite())).intersects(element.getBounds(sprites.get(element.getSprite())))) {
			areTouching = true;
		}
		return areTouching;
	}
	public void killPlayer(Player player) {
		player.decreasePlayerLives(336,678);
	}
}