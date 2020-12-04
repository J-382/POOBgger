package dominio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * POOgger's level generator implementation
 * @author Angie Medina - Jose Perez
 * @version 1.0
 */
public class Generator implements Serializable{

	private int screenHeight;
	private int screenWidth;
	private HashMap<String, Integer> speeds;
	private ArrayList<int[]> speedsAtLevel;
	private HashMap<String, int[]> sizes;
	private ArrayList<Element> mobiles;
	private ArrayList<Element> fixeds;
	private ArrayList<int[]> times;
	private int level;
	private String type;
	private Animator cooldownTimer;
	private boolean inCooldown;
	private Element throwable;
	
	private int gridSize;
	
	/**
	 * Lizard class constructor
	 * @param sizes, elements sizes
	 * @param screenWidth, the screen's width
	 * @param screenHeight, the screen's height
	 * @param gridSize, size of the grid
	 * @param level, initial level
	 * @param type, the map's type
	 */
	public Generator(HashMap<String, int[]> sizes, int screenWidth, int screenHeight, int gridSize, int level, String type) {
		mobiles = new ArrayList<Element>();
		fixeds = new ArrayList<Element>();
		this.sizes = sizes;
		this.gridSize = gridSize;
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		this.level = level;
		this.type = type;
		inCooldown = true;
		cooldownTimer = new Animator();
		prepareTimes();
		prepareSpeedsAtLevel();
		prepareSpeeds();
		cooldownTimer.animate(40000, 1, new Runnable() {public void run() {inCooldown = false;}});
	}
	
	/**
	 * Fills the speed's container with the element's speeds
	 */
	private void prepareSpeeds() {
		speeds = new HashMap<String, Integer>();
		int lvl = level%5;
		/*Street*/
		speeds.put("RedCar", speedsAtLevel.get(lvl)[0]);
		speeds.put("GreenCar", speedsAtLevel.get(lvl)[1]);
		speeds.put("BlueCar", speedsAtLevel.get(lvl)[2]);
		speeds.put("PinkCar", speedsAtLevel.get(lvl)[3]);
		speeds.put("PurpleCar", speedsAtLevel.get(lvl)[4]);
		speeds.put("Motorcycle", speedsAtLevel.get(lvl)[5]);
		speeds.put("Truck", speedsAtLevel.get(lvl)[6]);
		speeds.put("Bike", speedsAtLevel.get(lvl)[7]);
		/*Beaver*/
		speeds.put("Lizard", speedsAtLevel.get(lvl)[8]);
		speeds.put("SmallLog", speedsAtLevel.get(lvl)[9]);
		speeds.put("MediumLog", speedsAtLevel.get(lvl)[10]);
		speeds.put("LargeLog", speedsAtLevel.get(lvl)[11]);
		speeds.put("FastTurtle", speedsAtLevel.get(lvl)[12]);
		speeds.put("Turtle", speedsAtLevel.get(lvl)[13]);
		/*Both*/
		speeds.put("Eagle", speedsAtLevel.get(lvl)[14]);
		speeds.put("Snake", speedsAtLevel.get(lvl)[15]);
	}
	
	/**
	 * Returns the current level
	 * @return the current level
	 */
	public int getLevel() {
		return level+1;
	}
	
	/**
	 * Increase the current level by one
	 */
	public void levelUp() {
		level++;
		prepareSpeeds();
	}
	
	/**
	 * Prepare the appearance time of the elements
	 */
	private void prepareTimes() {
		times = new ArrayList<int[]>();
		times.add(new int[] {700,300,600,300,700,300,400,300,400,300});
		times.add(new int[] {500,150,500,150,350,200,400,300,400,300});
		times.add(new int[] {700,300,600,300,700,300,400,300,400,300});
		times.add(new int[] {500,150,500,150,350,200,400,300,400,300});
		times.add(new int[] {250,100,300,150,200,100,200,150,200,150});
	}
	
	
	/**
	 *  Prepare the speed of the elements
	 */
	private void prepareSpeedsAtLevel() {
		speedsAtLevel = new ArrayList<int[]>();
		speedsAtLevel.add(new int[] {2,1,1,3,2,3,2,1,2,1,2,3,2,1,1,1});
		speedsAtLevel.add(new int[] {2,1,1,3,2,3,2,1,2,1,2,3,2,1,1,1});
		speedsAtLevel.add(new int[] {3,2,2,4,3,4,3,2,3,2,3,4,3,2,2,2});
		speedsAtLevel.add(new int[] {3,2,2,4,3,4,3,2,3,2,3,4,3,2,2,2});
		speedsAtLevel.add(new int[] {4,2,2,6,4,6,4,2,4,2,4,6,4,2,2,2});
	}
	
	/** 
	 * Add a new car to Generator's elements in the given lane
	 * @param lane the new car's lane
	 */
	private void addCar(int lane) {
		String[] types = new String[] {"Red","Green","Blue","Pink","Purple"};
		switch (lane) {
			case 1:
				mobiles.add(new Car(screenWidth,gridSize*12,-speeds.get(types[0]+"Car"),sizes.get(types[0]+"Car"),types[0]));
				break;
			case 0:
				mobiles.add(new Car(-sizes.get(types[1]+"Car")[0],gridSize*13,speeds.get(types[1]+"Car"),sizes.get(types[1]+"Car"),types[1]));
				break;
			case 2:
				mobiles.add(new Car(screenWidth,gridSize*11,-speeds.get(types[2]+"Car"),sizes.get(types[2]+"Car"),types[2]));
				break;
			case 3:
				mobiles.add(new Car(-sizes.get(types[3]+"Car")[0],gridSize*10,speeds.get(types[3]+"Car"),sizes.get(types[3]+"Car"),types[3]));
				break;
			case 4:
				mobiles.add(new Car(screenWidth,gridSize*9,-speeds.get(types[4]+"Car"),sizes.get(types[4]+"Car"),types[4]));
				break;
		}
	}
	
	/**
	 * Add a new eagle to Generator's mobiles
	 * @param player, player the eagle will chase
	 */
	private void addEagle(Player player) {
		throwable = new Eagle(gridSize*7,gridSize,speeds.get("Eagle"), sizes.get("Eagle1"), "Eagle1", player);
		mobiles.add(throwable);
	}
	
	/** Add a new Lizzard to Generator's mobiles
	 */
	private void addLizard() {
		//mobiles.add(new Lizzard(0, gridSize*5+4, lizzardSpeed));
		mobiles.add(new Lizard(-sizes.get("Lizard1")[0],gridSize*3, sizes.get("Lizard1"), "Lizard1", speeds.get("Lizard")));
	}
	
	/** 
	 * Add a new log to Generator's mobiles in the given lane
	 * @param lane the new log's lane
	 */
	private void addLog(int lane) {
		Random r = new Random();
		String[] types = new String[] {"Small","Medium","Large"};
		switch (lane) {
			case 0:
				SmallLog small = new SmallLog(-sizes.get(types[0]+"Log1")[0],gridSize*6,speeds.get(types[0]+"Log"),sizes.get(types[0]+"Log1"),new int[] {gridSize,gridSize},types[0]+"Log1");
				FemaleFrog fFrog = new FemaleFrog(-sizes.get(types[0]+"Log1")[0], gridSize*6, 48,new int[] {48,48}, "FFrog", false); 
				mobiles.add(small);
				if(r.nextInt(10)==3) {
					small.inCollision(fFrog);
					mobiles.add(fFrog);
				}
				
				break;
			case 2:
				mobiles.add(new Log(-sizes.get(types[1]+"Log")[0],gridSize*3,speeds.get(types[1]+"Log"),sizes.get(types[1]+"Log"),types[1]+"Log"));
				break;
			case 1:
				
				Log log = new Log(-sizes.get(types[2]+"Log")[0],gridSize*5,speeds.get(types[2]+"Log"),sizes.get(types[2]+"Log"),types[2]+"Log");
				mobiles.add(log);
				if(r.nextBoolean() && r.nextBoolean() && level>3) {
					Snake snake = new Snake(-sizes.get(types[2]+"Log")[0], gridSize*5, speeds.get("Snake") ,sizes.get("Snake1"), "Snake1", false);
					mobiles.add(snake);
					log.inCollision(snake);
					
				}
				break;
		}
	}
	
	/** 
	 * Add a new motorcycle to Generator's mobiles
	 */
	private void addMotorcycle() {
		mobiles.add(new Motorcycle(-sizes.get("Motorcycle1")[0],gridSize*10,speeds.get("Motorcycle"),sizes.get("Motorcycle1"),"Motorcycle1",true));
	}

	/** 
	 * Add a new snake to Generator's mobiles
	 */
	private void addSnake(boolean flipped) {
		Random r = new Random();
		if(flipped) {
			mobiles.add(new Snake(-sizes.get("Snake1")[0],gridSize*8,speeds.get("Snake"),sizes.get("Snake1"),"Snake1",false));
		}else mobiles.add(new Snake(screenWidth,gridSize*8,speeds.get("Snake"),sizes.get("Snake1"),"Snake1",true));
	}
	
	/**
	 * Add a new Thunder to the list of elements
	 * @param player
	 */
	private void addThunder(Player player) {
		throwable = new Thunder(gridSize*3, gridSize, screenHeight , player);
		fixeds.add(throwable);
	}
	
	
	/** 
	 * Add a new Truck to Generator's mobiles
	 */
	private void addTruck() {
		mobiles.add(new Truck(screenWidth,gridSize*9,-speeds.get("Truck"),sizes.get("Truck1"),"Truck1",false));
		
	}
	
	/** Add a new turtle to Generator's mobiles in the given lane
	 * @param lane the new turtle's lane
	 */
    private void addTurtle(int lane) {
    	Random r = new Random();
    	boolean doesSubmerge = r.nextBoolean();
    	if(lane==0) {
			mobiles.add(new Turtle(screenWidth,gridSize*7,-speeds.get("FastTurtle"),sizes.get("Turtle1"),"Turtle1", doesSubmerge));
			mobiles.add(new Turtle(screenWidth+gridSize/4+sizes.get("Turtle1")[0],gridSize*7,-speeds.get("FastTurtle"),sizes.get("Turtle1"),"Turtle1", doesSubmerge));
			mobiles.add(new Turtle(screenWidth+gridSize/2+2*sizes.get("Turtle1")[0],gridSize*7,-speeds.get("FastTurtle"),sizes.get("Turtle1"),"Turtle1", doesSubmerge));
		}else {
			mobiles.add(new Turtle(screenWidth, gridSize*4, -speeds.get("Turtle"),sizes.get("Turtle1"),"Turtle1", doesSubmerge));
			mobiles.add(new Turtle(screenWidth+gridSize/4+sizes.get("Turtle1")[0], gridSize*4, -speeds.get("Turtle"),sizes.get("Turtle1"),"Turtle1", doesSubmerge));
		}
		
    }
    
    /**
	 * Add a new bike to Generator's fixed elements
	 */
    private void addThrowable(Player player) {
    	if(type.equals("Day")) {
    		addEagle(player);
    	}else addThunder(player);
    }
    
    /**
	 * Add a new bike to Generator's elements
	 */
    private void addPuddle() {
    	Random r = new Random();
    	int x = gridSize*r.nextInt(15);
    	addPuddle(x);
    }
    /**
   	 * Add a new puddle to Generator's fixed elements
   	 */
    private void addPuddle(int x) {
    	fixeds.add(new Puddle(x,gridSize*8,gridSize,gridSize));
    }
    
    /**
	 * Add a new bike to Generator's elements
	 */
	private void addBike() {
		mobiles.add(new Bike(screenWidth,gridSize*11,-speeds.get("Bike"), sizes.get("Bike1"),"Bike1" ,false));	
	}
    
    /**
	 * Adds a new element to the given lane
	 * @param lane the new element's lane
	 */
	private void addLane(int time) {
		Random r = new Random();
		int lvl = level%5;
		if(time%times.get(lvl)[0]==0) {
			addCar(0);
		}
		if(time%times.get(lvl)[1]==0) {
			addCar(1);
		}
		if(time%times.get(lvl)[2]==0) {
			if(r.nextBoolean()) {
				addCar(2);
			}else addBike();
		}
		if(time%times.get(lvl)[3]==0) {
			if(r.nextBoolean()) {
				addCar(3);
			}else addMotorcycle();
		}
		if(time%times.get(lvl)[4]==0) {
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
		int lvl = level%5;
		if(time%times.get(lvl)[5]==0) {
			addTurtle(0);
		}
		if(time%times.get(lvl)[6]==0) {
			addLog(0);
		}
		if(time%times.get(lvl)[7]==0) {
			addLog(1);
		}
		if(time%times.get(lvl)[8]==0) {
			addTurtle(3);
		}
		if(time%times.get(lvl)[9]==0) {
			if(r.nextBoolean()) {
				addLog(2);
			} else addLizard();
		}
	}
	
	/**
	 * Initial fixed elements
	 * @return Initial fixed elements
	 */
	public ArrayList<Element> addFixedElements() {
		Random r = new Random();
		/*Barriers*/
		fixeds.add(new Beaver(0,gridSize*2,screenWidth,gridSize*6));
		fixeds.add(new Barrier(-gridSize,gridSize*8,gridSize,gridSize*7,gridSize,false));
		fixeds.add(new Barrier(screenWidth,gridSize*8,gridSize,gridSize*7,gridSize,false));
		fixeds.add(new Barrier(0,gridSize*15,gridSize*16,gridSize,gridSize,false));
		fixeds.add(new Barrier(-gridSize,gridSize,gridSize,gridSize*7,gridSize,true));
		fixeds.add(new Barrier(screenWidth,gridSize,gridSize,gridSize*7,gridSize,true));
		/*Caves*/
		fixeds.add(new Cave(gridSize,gridSize*2,gridSize,gridSize));
		fixeds.add(new Cave(gridSize*4,gridSize*2,gridSize,gridSize));
		fixeds.add(new Cave(gridSize*7,gridSize*2,gridSize,gridSize));
		fixeds.add(new Cave(gridSize*10,gridSize*2,gridSize,gridSize));
		fixeds.add(new Cave(gridSize*13,gridSize*2,gridSize,gridSize));
		/*Puddles*/
		switch(r.nextInt(3)) {
			case 1:
				addPuddle();
				break;
			case 2:
				int posX = r.nextInt(14)*gridSize;
				addPuddle(posX);
				addPuddle((6*gridSize>=posX?1:-1)*6*gridSize+posX);
				break;
		}
		return fixeds;
	}
	
	public ArrayList<Element> addMobileElements(){
		Random r = new Random();
		if(level>2) {
			addSnake(false);
			if(r.nextBoolean()) addSnake(true);
		}
		SmallLog small = new SmallLog(-sizes.get("SmallLog1")[0],gridSize*12,speeds.get("SmallLog"),sizes.get("SmallLog1"),new int[] {gridSize,gridSize},"SmallLog1");
		FemaleFrog fFrog = new FemaleFrog(-sizes.get("SmallLog1")[0], gridSize*12, 48,new int[] {48,48}, "FFrog", false); 
		mobiles.add(small);
		small.inCollision(fFrog);
		mobiles.add(fFrog);
		return mobiles;
		
	}
	
	/**
	 * Clear the lists of elements and adds new ones in function of the ejecution clock
	 * @param time ejecution time
	 * @param addThrowable if a throwable can be added
	 * @param players list of players
	 */
	public void addElements(int time, boolean addThrowable, ArrayList<Player> players) {
		mobiles.clear();
		fixeds.clear();
		addBeaverLane(time);
		addLane(time);
		if(addThrowable && !inCooldown) {
			inCooldown = true;
			Random r = new Random();
			addThrowable(players.get(r.nextInt(players.size())));
			cooldownTimer.animate(40000, 1, new Runnable() {public void run() {inCooldown = false;}});
		}
	}
	
	/**
	 * Returns a list with fixed elements
	 * @return a list with fixed elements
	 */
	public ArrayList<Element> getFixedsElements(){
		return fixeds;
	}
	
	/**
	 * Returns a list with mobile elements
	 * @return a list with mobile elements
	 */
	public ArrayList<Element> getMobilesElements(){
		return mobiles;
	}
	
	/**
	 * Returns the throwable element
	 * @return the throwable element
	 */
	public Element getThrowableElement() {
		return throwable;
	}
	
}
