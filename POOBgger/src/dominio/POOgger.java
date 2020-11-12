package dominio;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;

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
	
	public POOgger(int width, int height) {
		screenWidth = width;
		//screenHeight = height;
		player = new Player(5,624,678);
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
		animator.animate(100, 101, new Runnable() {public void run() {updateClock();}}, false);
	}
	
	public void updateClock() {
		//clock.move((int)clock.getMinX() + 100, (int)clock.getY());
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
	
	public void addSnake() {
		Random r = new Random();
		boolean flipped = r.nextBoolean();
		ImageIcon sprite = new ImageIcon("./resources/sprites/Snake1.png");
		if(flipped) {
			elements.add(new Snake(678,0,-1*snakeSpeed,sprite,false));
		}else elements.add(new Snake(0,0,-1*snakeSpeed,sprite,true));
	}
	
	public void addCar() {
		String[] types = new String[] {"Red","Green","Blue","Pink","Purple"};
		Random r = new Random();
		int x = 0;// y = 100;
		ImageIcon sprite;
		int n = r.nextInt(5);
		switch (n) {
			case 0:
				sprite = new ImageIcon("./resources/sprites/"+types[0]+"Car.png");
				elements.add(new Car(screenWidth,48*13+2,-carsSpeed[0],sprite));
				break;
			case 1:
				sprite = new ImageIcon("./resources/sprites/"+types[1]+"Car.png");
				elements.add(new Car(x-sprite.getIconWidth(),48*12+2,carsSpeed[1],sprite));
				break;
			case 2:
				sprite = new ImageIcon("./resources/sprites/"+types[2]+"Car.png");
				elements.add(new Car(screenWidth,48*11+2,-carsSpeed[2],sprite));
				break;
			case 3:
				sprite = new ImageIcon("./resources/sprites/"+types[3]+"Car.png");
				elements.add(new Car(x-sprite.getIconWidth(),48*10+2,carsSpeed[3],sprite));
				break;
			case 4:
				sprite = new ImageIcon("./resources/sprites/"+types[4]+"Car.png");
				elements.add(new Car(screenWidth,48*9+2,-carsSpeed[4],sprite));
				break;
		}
	}
	
	public void addLog() {
		String[] types = new String[] {"Small","Medium","Large"};
		Random r = new Random();
		ImageIcon sprite;
		switch (r.nextInt(3)) {
			case 0:
				sprite = new ImageIcon("./resources/sprites/"+types[0]+"Log1.png");
				elements.add(new SmallLog(-sprite.getIconWidth(),48*6+4,logsSpeed[0],sprite));
				break;
			case 1:
				sprite = new ImageIcon("./resources/sprites/"+types[1]+"Log.png");
				elements.add(new Log(-sprite.getIconWidth(),48*3+4,logsSpeed[1],sprite));
				break;
			case 2:
				sprite = new ImageIcon("./resources/sprites/"+types[2]+"Log.png");
				elements.add(new Log(-sprite.getIconWidth(),48*5+4,logsSpeed[2],sprite));
				break;
		}
	}
	
    public void addTurtle() {
		ImageIcon sprite = new ImageIcon("./resources/sprites/Turtle1.png");
		elements.add(new Turtle(screenWidth - sprite.getIconWidth(),0,-1*turtleSpeed,sprite, true));
	}

    public void addLizzard() {
		ImageIcon sprite = new ImageIcon("./resources/sprites/Lizard1.png");
		elements.add(new Lizzard(-sprite.getIconWidth(), 48*5+4, lizzardSpeed, sprite));
	}

	public void addBike() {
		Random r = new Random();
		if(r.nextBoolean()) {
			elements.add(new Bike(0,8,1,true));
		}else elements.add(new Bike(600,8,-1,false));
		
	}
	
	public void addTruck() {
		Random r = new Random();
		if(r.nextBoolean()) {
			elements.add(new Truck(0,8,1,true));
		}else elements.add(new Truck(600,8,-1,false));
		
	}
	
	public void addMotorcycle() {
		Random r = new Random();
		if(r.nextBoolean()) {
			elements.add(new Motorcycle(0,6,2,true));
		}else elements.add(new Motorcycle(350,6,-2	,false));
		
	}
	
	public ArrayList<Element> getElements(){
		clearElements();
		return elements;
	}
	
	public boolean checkPlayerCollision(Player player, Element element) {
		boolean isDead = false;
		if(player.getBounds().intersects(element.getBounds())) {
			isDead = element.inCollision(player);
		}
		return isDead;
	}
	
	public void killPlayer(Player player) {
		player.decreasePlayerLives(336,678);
	}
}