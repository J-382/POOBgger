package dominio;

import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;
import java.util.TreeMap;

/**
 * POOgger's player implementation
 * @version 2.3
 * @author Angie Medina - Jose Perez
 */
public class POOgger implements Serializable{
	private static POOgger poogger = null;
	
	public static POOgger demePOOgger(HashMap<String, int[]> archivo) {
		if (poogger == null) {
			poogger = new POOgger(720,720, archivo,new char[] {'A','W','S','D'},new char[] {'A','W','S','D'});
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
	private Rectangle clock;
    private Animator animator;
	private boolean isPlayerAlive;
	private ArrayList<Element> elements;
	private ArrayList<Element> fixeds;
	private ArrayList<Player> players;
	private char[] player1Keys;
	private char[] player2Keys;
	private HashMap<String,int[]> sprites;
	private final int deadPenalization = -100;
	private boolean[] isOver;
	private int level;
	private int timeLimit;
	private TreeMap<Integer, ArrayList<String>> highScores;
	private File testFile;
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
		isOver = new boolean[]{false, false, false};
		level = 5;
		timeLimit = 300;
		exist = false;
		isPlayerAlive = true;
		snakeSpeed = 1;
		turtleSpeed = 1;
		lizzardSpeed = 2;
		logsSpeed = new int[] {1,2,3};
		carsSpeed = new int[] {4,3,2,5,2};
		players = new ArrayList<Player>();
		players.add(new Player(5,48*7,48*14, sprites.get("Frog1W")));
		//players.add(new Thoughtless(5,48*7,48*14, sprites.get("Frog1W")));
		elements = new ArrayList<Element>();
		fixeds = new ArrayList<Element>();
		clock = new Rectangle(0,0, 0, 20);
		addFixedElements();
		addSnake();
		//addEagle(players.get(0));
		testFile = new File("./resources/HighScoresJvsJ.txt");
		try {
			highScores = readHighScoreFile(testFile);
		} catch (POOggerException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Restores the clock size, givin 30 seconds
	 */
	private void restoreClock() {
		clock = new Rectangle(0, 0, 0, clock.height);
	}
	
	/**
	 * Updates clock size
	 **/
	public void updateClock() {
		clock = new Rectangle(0, 0, clock.width + 1, clock.height);
		if (clock.width == timeLimit) {
			for(Player player: players) {
				killPlayer(player);
			}
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
			players.get(0).setOrientation(dir);
		}
	}
	
	public void moveMachinePlayers() {
		for(Player player: players) {
			if(player.isMachine()) player.setOrientation('W');
		}
	}
	
	/**
	 * Returns the POOgger's actual players
	 */
	public ArrayList<Player> getPlayers() {
		return players;
	}
	
	/**
	 * Returns the player's points
	 * @param player, the player's number
	 */
	public String getPoints(int player) {
		int ultiScore = players.get(player).getPoints();
		return formatScore(ultiScore);
	}
	
	/**
	 * Returns the highest score saved
	 */
	public String getHighScore() {
		int ultiScore = 0;
		for (Integer scores : highScores.keySet()) ultiScore = scores;
		return formatScore(ultiScore);
	}
	
	/**
	 * Returns the clock sprite
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
	 * Move or eliminates the POOgger's elements
	 */
	private ArrayList<Element> update() {
		moveMachinePlayers();
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
	
	/**
	 * Add a new eagle to POOgger's elements
	 * @param player, player the eagle will chase
	 */
	private void addEagle(Player player) {
		elements.add(new Eagle(1, sprites.get("Eagle1"), "Eagle1", player));
	}
	
	/**
	 * Add one element to POOgger's elements
	 * @param element, the element to add
	 */
	public void addElement(Element element) {
		elements.add(element);
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
				elements.add(new SmallLog(-sprites.get(types[0]+"Log1")[0],48*6,logsSpeed[0],sprites.get(types[0]+"Log1"),new int[] {48,48},types[0]+"Log1"));
				break;
			case 2:
				elements.add(new Log(-sprites.get(types[1]+"Log")[0],48*3,logsSpeed[1],sprites.get(types[1]+"Log"),types[1]+"Log"));
				break;
			case 1:
				Random r = new Random();
				Log log = new Log(-sprites.get(types[2]+"Log")[0],48*5,logsSpeed[2],sprites.get(types[2]+"Log"),types[2]+"Log");
				elements.add(log);
				if(r.nextBoolean()) {
					Snake snake = new Snake(-sprites.get(types[2]+"Log")[0], 48*5, 1 ,sprites.get("Snake1"), "Snake1", false);
					elements.add(snake);
					log.inCollision(snake);
					
				}
				
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
			elements.add(new Snake(-sprites.get("Snake1")[0],48*8,snakeSpeed,sprites.get("Snake1"),"Snake1",false));
		}else elements.add(new Snake(screenWidth,48*8,snakeSpeed,sprites.get("Snake1"),"Snake1",true));
	}
	
	/**
	 * 
	 * @param player
	 */
	private void addThunder(Player player) {
		//elements.add(new Thunder(player));
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
	 * @param player, the player disired player to check
	 */
	private boolean checkPlayerCollisions(Player player) {
		boolean[] mobileCollisions = checkMobileElements(player);
		return mobileCollisions[0] || checkFixedElements(player,mobileCollisions[1]);
		
	}
	
	/**
	 * Check if the player is colisioning with some level's mobile elements
	 * @param player, the player disired player to check
	 * @return if the player is dead and is is touching the water
	 */
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
		return new boolean[] {isDead,touchingWater};
	}
	
	/**
	 * Check if the player is colisioning with some level's fixed element
	 * @param player, the player disired player to check
	 * @param touchingWater
	 * @return
	 */
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
					if(!e.isOccupied()) {
						e.inCollision(player);
						if(e.isOccupied()) {
							player.changePoints(e.getPoints());
							player.increaseCavesReach();
							checkCavesState(player);
							restoreClock();
							resetPlayer(player);
							touchingWater = false;
							break;
						}
					}
				}
			}
			else {
				if(player.getBounds().intersects(f.getBounds())) {
					isDead = f.inCollision(player);		
				}
			}
			if(!f.isVisible()) {
				fixeds.set(i, null);
			}if(isDead) break;
		}
		clearElements();	
		return isDead || touchingWater;
	}
	
	
	/**
	 * Check the cave's state to see if some player have won
	 * @param player, the player who collision with the cave
	 */
	public void checkCavesState(Player player) {
		int cont = 0;
		for (int i = 1; i < fixeds.size(); i++) {
			if (((Fixed)fixeds.get(i)).canBeOccupied()) {
				if (((Cave)fixeds.get(i)).isOccupied()) cont++;
			}
		}
		if (cont == 5) {
			if (player.getLives() == player.getInitialLives()) player.changePoints(1000);
			else player.changePoints(player.getLives()*100);
			player.changePoints(timeLimit - (int)clock.getWidth());
			if (player.getCavesReach() >= 3) {
				player.increaseRoudsWon();
			}
			nextLevel();
		}
	}
	
	/**
	 * Creates another level if possible (max level 5)
	 */
	public void nextLevel() {
		String winner;
		if (level == 5) {
			for (int i = 1; i <= players.size(); i++) {
				if (players.get(i).getRoundsWon() >= 3) {
					winner = "" + i;
				}
			}
			isOver[0] = true;
			isOver[1] = true;
		}
	}
	
	/**
	 * Returns the state of the game
	 * @return isOver, first position if the game is over
	 * 				   second position if the game finish with some winner
	 * 				   third position if some player made a record
	 */
	public boolean[] getGameState() {
		if (!isPlayerAlive) {
			isOver[0] = true;
			isOver[1] = false;
		}
		return isOver;
	}
	
	/**
	 * Returns if some player have made a record
	 */
	public boolean checkScoresRecords() {
		boolean record = false;
		int minScore = -10000;
		for (int score : highScores.keySet()) {
			minScore = score;
			break;
		}
		for (Player player : players) {
			if (!player.isMachine() && player.getPoints() >= minScore) {
				record = true;
				ArrayList<String> names = new ArrayList<>();
				names.add("player");
				if (highScores.containsKey(player.getPoints())) { 
					names.addAll(highScores.get(player.getPoints()));
				}
				highScores.put(player.getPoints(), names);
			}
		}
		try {
			writeHighScoreFile(testFile);
		} catch (POOggerException e) {
			e.printStackTrace();
		}
		isOver[2] = record;
		return record;
	}
	
	/**
	 * Kill one player
	 * @param player, the player killed
	 */
	public void killPlayer(Player player) {
		player.changePoints(deadPenalization);
		restoreClock();
		isPlayerAlive = player.decreasePlayerLives();
		resetPlayer(player);
	}
	
	/**
	 * Reset the player, to its initial position
	 * @param player, the player desire to reset
	 */
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
			
			//addEagle(players.get(r.nextInt(players.size())));
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
		addBeaverLane(time);
		//addLane(time);
		if(time%2==0) update();
		for(Player player: players) {
			if(checkPlayerCollisions(player)) {
				killPlayer(player);
				if (!player.isAlive()) {
					isOver[0] = true;
					isOver[1] = false;
				}
			}
		}
		ArrayList<Element> allElements = new ArrayList<Element>();
		allElements.addAll(fixeds);
		allElements.addAll(elements);
		return allElements;
	}
	
	private void addFixedElements() {
		/*Barriers*/
		fixeds.add(new Beaver(0,48*2,screenWidth,48*6));
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
		/**fixeds.add(new SpeedPower(48*3,48*13,48,48));
		fixeds.add(new FlyPower(48*3,48*10,48,48));
		fixeds.add(new ArmorPower(48*3,48*14,48,48));**/
		/*Puddles*/
		//fixeds.add(new Puddle(48*7,48*8,48,48));
		fixeds.add(new Thunder(48*3, 48, screenHeight-48, players.get(0)));
		fixeds.add(new Bug(48*4,48*12,48,48,1500));
		//fixeds.add(new Thunder(48*3, 48, screenHeight-48, players.get(0)));
	}
	
	/**
	 * Open, if its possible, the specified file
	 * @param file, the file to open
	 * @throws POOggerException     - TIPO_ERRONEO When the file is not a .txt
	 * 								- ERROR_ABRIR  When some error occurs when tries to open the file
	 * 								- CLASE_NO_ENCONTRADA When the saved class is not found 
	 */
	public void open(File file) throws POOggerException{
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
	 * Read the specified file
	 * @param file, the file to read
	 * @throws POOggerException	    - TIPO_ERRONEO When the file is not a .txt
	 * 								- ERROR_IMPORTAR  When some error occurs when tries to read the file
	 */
	private TreeMap<Integer, ArrayList<String>> readHighScoreFile(File file) throws POOggerException{
		TreeMap<Integer, ArrayList<String>> scores = new TreeMap<>();
		try {
			if (!file.getCanonicalPath().endsWith(".txt")) {
				throw new POOggerException(POOggerException.TIPO_ERRONEO_TXT);
			}
			BufferedReader bIn = new BufferedReader(new FileReader(file));
			String line = bIn.readLine();
			while(line != null) {
				line = line.trim();
				if (line.equals("")) break;
				String[] elements = line.split(" ");
				String name = elements[0];
				int score = Integer.parseInt(elements[1]);
				ArrayList<String> names = new ArrayList<>();
				names.add(name);
				if (scores.containsKey(score)) names.addAll(scores.get(score));
				scores.put(score, names);
				line = bIn.readLine();
			}
			bIn.close();
			} catch(IOException e) {
			throw new POOggerException(POOggerException.ERROR_IMPORTAR);
		}
		return scores;
	}
	
	/**
	 * Save, if its possible, in the specified file
	 * @param file, the file where to save
	 * @throws POOggerException     - TIPO_ERRONEO When the file is not a .dat 
	 * 								- ERROR_SALVAR When some error occurs when tries to save in the file
	 */
	public void save(File file) throws POOggerException{
		try {
			if (!file.getCanonicalPath().endsWith(".dat")) {
				throw new POOggerException(POOggerException.TIPO_ERRONEO_DAT);
			}
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
			out.writeObject(poogger);
			out.close();
		} catch(IOException e) {
			throw new POOggerException(POOggerException.ERROR_SALVAR);
		}
	}
	
	/**
	 * Export to one file the saved high scores
	 * @param file, the file where to save the high score
	 * @throws POOggerException     - TIPO_ERRONEO   When the file is not a .txt
	 * 								- ERROR_EXPORTAR When some error occurs when tries to export 
	 * */
	private void writeHighScoreFile(File file) throws POOggerException {
		try {
			if (!file.getCanonicalPath().endsWith(".txt")) {
				throw new POOggerException(POOggerException.TIPO_ERRONEO_TXT);
			}
			PrintWriter pw = new PrintWriter(new FileOutputStream(file));
			int[] keyArray = new int[highScores.keySet().size()];
			int  cont = 0;
			for (int key : highScores.keySet()) {
				keyArray[cont] = key;
				cont++;
			}
			cont = 0;
			boolean max = false;
			Arrays.sort(keyArray);
			for(int i = keyArray.length - 1; i >= 0; i--) {
				for(String nickName : highScores.get(keyArray[i])) {
					if (max) break;
					pw.println(nickName + " " + formatScore(keyArray[i]));
					if (++cont == 5) {
						max = true;
						break;
					}
				}
			}
			pw.close();
		}catch(IOException e){
			throw new POOggerException(POOggerException.ERROR_EXPORTAR);
		}
	}
	
	/**
	 * Format the score, add zeros to the left side of the score if its small
	 * @param ultiScore, the score desire to format
	 * @return the format score
	 */
	private String formatScore(int ultiScore) {
		String sign = ultiScore < 0 ? "-" : "";
		ultiScore = Math.abs(ultiScore);
		String format = "" + ultiScore;
		if (ultiScore < 10) format = "0"+format;
		if (ultiScore < 100)format = "0"+format;
		if (ultiScore < 1000)format = "0"+format;
		if (ultiScore < 10000)format = "0"+format;
		return sign + format;
	}
}