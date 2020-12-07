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
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.TreeMap;

/**
 * Facade Class for the Frogger's implementation
 * @version 3.0
 * @author Angie Medina - Jose Perez
 */
@SuppressWarnings("serial")
public class POOgger implements Serializable{
	private static POOgger poogger = null;
	
	public static POOgger demePOOgger(HashMap<String, int[]> archivo, String[] player1, String[] player2, String mapType, String fileMode){
		if (poogger == null) {
			poogger = new POOgger(720,740, archivo, player1, player2, mapType, fileMode);
		}
		return poogger;
	}
	
	public static POOgger demePOOgger(HashMap<String, int[]> archivo){
		if (poogger == null) {
			poogger = new POOgger(720,740, archivo, new String[] {null, null}, new String[] {null, null}, null, null);
		}
		return poogger;
	}
	
	public static void cambiePOOgger(POOgger p) {
		poogger = p;
	}
	
	private int screenWidth;
	private int screenHeight;
	private ArrayList<Element> elements;
	private ArrayList<Element> fixeds;
	private ArrayList<Player> players;
	private char[] playerKeys = {'A','W','S','D'};
	private HashMap<String,int[]> sprites;
	private final int deadPenalization = -100;
	private boolean[] isOver;
	private TreeMap<Integer, ArrayList<String>> highScores;
	private File scoresFile;
	private Element throwable;
	private Generator levelGenerator;
	
	/**
	 * POOgger class constructor
	 * @param width POOgger's windows width
	 * @param height POOgger's windows height
	 * @param sprites HashMap with all sprites's sizes
	 * @throws POOggerException 
	 */
	private POOgger(int width, int height, HashMap<String,int[]> sprites, String[] player1, String[] player2, String mapType, String scoreFile) {
		screenWidth = width;
		this.sprites = sprites;
		levelGenerator = new Generator(sprites,screenWidth,screenHeight,48,0,mapType);
		isOver = new boolean[]{false, false, false};
		players = new ArrayList<Player>();
		elements = new ArrayList<Element>();
		fixeds = new ArrayList<Element>();
		fixeds.addAll(levelGenerator.addFixedElements());
		elements.addAll(levelGenerator.addMobileElements());
		ArrayList<String[]> newPlayers = new ArrayList<>();
		newPlayers.add(player1);
		if (player2[0] != null) newPlayers.add(player2);
		addPlayers(newPlayers);
		scoresFile = new File(scoreFile);
		//powerTest();
		//bonusTest();
		try {
			highScores = readHighScoreFile(scoresFile);
		} catch (POOggerException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Add players to the game
	 * @param newPlayers, list of players, each player must have {name and personalization}
	 */
	public void addPlayers(ArrayList<String[]> newPlayers){
		int lives = players.size() >= 1 || (newPlayers.size() >= 2 && newPlayers.get(1)[0] != null) ? 3 : 5;
		int initPosx = 48*8, initPosy = 48*14;
		for (String[] player : newPlayers) {
			initPosx -= 48;
			if (player[0] != null) { 
				Class c = null;
				try {
					c = Class.forName("dominio." + player[0]);
				} catch (ClassNotFoundException e1) {
					try {
						c = Class.forName("dominio.Player");
					} catch (ClassNotFoundException e) {
						//throw new POOggerException(POOggerException.CLASE_NO_ENCONTRADA);
					}
				}
				try {
					Object o = c.getDeclaredConstructor(int.class, int.class, int.class, int[].class, String.class, String.class).
							newInstance(lives, initPosx, initPosy, sprites.get("Frog1W"), player[0], player[1]);
					
					players.add((Player)o);
				} catch ( InstantiationException | IllegalAccessException | IllegalArgumentException
						| InvocationTargetException | NoSuchMethodException | SecurityException e) {
					//throw new POOggerException(POOggerException.ERROR_INFORMACION);
				}
			}
		}
	}
	
	/**
	 * Updates one player's clock
	 * @param player, the player who's clock must be update
	 */
	public void updateClock(Player player) {
		if (player.isAlive()) {
			if (player.updateClock()) {
				killPlayer(player);
			}
		}
	}
	
	/**
	 * Move the player, if possible, in the given direction
	 * @param dir, direction to move
	 * @param numPlayer, number of the player to move
	 */
	public void movePlayer(char dir, int numPlayer) {
		boolean isValid = false;
		for(char i: playerKeys) {
			if(i==dir) {
				isValid = true;
				break;
			}
		}
		if (isValid) {
			if (numPlayer < players.size() && players.get(numPlayer).isAlive()) {
				players.get(numPlayer).setOrientation(dir);
			}
		} else if (numPlayer < players.size()) players.get(numPlayer).activatePower(dir);
	}
	
	/**
	 * Move all the machine players currently alive in the game
	 */
	public void moveMachinePlayers() {
		for(Player player: players) {
			if(player.isMachine() && player.isAlive) player.setOrientation('W');
		}
	}
	
	/**
	 * Returns the POOgger's actual players
	 */
	public ArrayList<Player> getPlayers() {
		return players;
	}
	
	/**
	 * Returns the game's current level
	 */
	public String getLevel() {
		return "" + levelGenerator.getLevel();
	}
	
	/**
	 * Returns the player's points
	 * @param player, the player who wants to return its points
	 */
	public String getPoints(Player player) {
		int ultiScore = player.getPoints();
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
	 * Returns the player's clock representation
	 */
	public Rectangle getClock(Player player) {
		return player.getClock();
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
	 * Checks if player is colliding with some level's elements
	 * @param player, the player desired player to check
	 * @return if the player got killed by some element
	 */
	private boolean checkPlayerCollisions(Player player) {
		boolean[] mobileCollisions = checkMobileElements(player);
		return mobileCollisions[0] || checkFixedElements(player,mobileCollisions[1]);
		
	}
	
	/**
	 * Check if the player is colliding with some level's mobile elements
	 * @param player, the player desired player to check
	 * @return if the player is dead and if is touching the water
	 */
	private boolean[] checkMobileElements(Player player) {
		boolean isDead = false;
		boolean touchingWater = true;
		for(Element e: elements) {
			touchingWater = touchingWater && !e.touching(player)[0];
			isDead = e.touching(player)[1];
			if (e.givesBonus()) givePlayerBonus(player, e.getPoints());
			if(isDead) break;
		}
		return new boolean[] {isDead,touchingWater};
	}
	
	/**
	 * Check if the player is colliding with some level's fixed element
	 * @param player, the player desired player to check
	 * @param touchingWater, if the player was touching the water
	 * @return if the player is dead or if is touching the water
	 */
	private boolean checkFixedElements(Player player, boolean touchingWater) {
		boolean isDead = false;
		if (fixeds.get(0).touching(player)[0]) {
			touchingWater = touchingWater && fixeds.get(0).touching(player)[1]; 
		}else touchingWater = fixeds.get(0).touching(player)[0];
		for (int i = 1; i < fixeds.size(); i++) {
			Fixed e = (Fixed)fixeds.get(i);
			if (e.touching(player)[0] && e.canBeOccupied()) {
				Cave f = (Cave)e;
				if (f.isOccupied()) {
					givePlayerBonus(player, f.getPoints());
					player.increaseCavesReach();
					checkCavesState();
					resetPlayer(player);
					touchingWater = false;
					break;
				}
			}
			else {
				if(e.touching(player)[0]) {
					if (e.givesBonus()) givePlayerBonus(player, e.getPoints());
					isDead = e.touching(player)[1];
				}
			}
			if(!e.isVisible()) {
				fixeds.set(i, null);
			}if(isDead) break;
		}
		clearElements();	
		return isDead || touchingWater;
	}

	
	/**
	 * Checks if one throwable element collide with another element
	 */
	private void checkThrowableCollision(){
		if(throwable!=null) {
			for(Element e: elements) {
				if(throwable!=e && e.touching(throwable)[0]) {
					if(throwable.isDestructible() && e.touching(throwable)[1]) {
						fixeds.add(throwable.destroy());
						throwable = null;
						break;
					}else if(!throwable.isDestructible()) fixeds.add(e.destroy());
				}
			}
		}
	}
		
	
	/**
	 * Checks the cave's state to see if some player have won
	 */
	private void checkCavesState() {
		int cont = 0;
		for (int i = 0; i < fixeds.size(); i++) {
			if (((Fixed)fixeds.get(i)).canBeOccupied()) {
				if (((Cave)fixeds.get(i)).isOccupied()) cont++;
			}
		}
		if (cont == 5) {
			for(Player player : players) {
				if (player.getLives() == player.getInitialLives()) player.changePoints(1000);
				else player.changePoints(player.getLives()*100);
				player.changePoints(player.secondsLeft() * 10);
				if (player.getCavesReach() >= 3) {
					player.increaseRoudsWon();
				}
			}
			nextLevel();
		}
	}
	
	/**
	 * Creates another level if possible (max level 5)
	 */
	private void nextLevel() {
		String winner;
		
		for (Player player : players) {
			player.resetPlayer();
		}
		if (levelGenerator.getLevel() == 5) {
			for (int i = 1; i <= players.size(); i++) {
				if (players.get(i-1).getRoundsWon() >= 3) {
					winner = players.get(i-1).getName();
				}
			}
			isOver[0] = true;
			isOver[1] = true;
		}
		else {
			levelGenerator.levelUp();
			clearCaves();
		}
	}
	
	/**
	 * Clear all the caves in the game (Makes them no occupied again)
	 */
	private void clearCaves() {
		for (Element e : fixeds) {
			if (((Fixed)e).canBeOccupied()) {
				((Cave)e).clear();
			}
		}
	}
	
	/**
	 * Returns the state of the game
	 * @return isOver, first position if the game is over
	 * 				   second position if the game finish with some winner
	 * 				   third position if some player made a record
	 */
	public boolean[] getGameState() {
		boolean onePlayerAlive = false;
		for (Player player : players) {
			onePlayerAlive = onePlayerAlive || player.isAlive;
		}
		if (!onePlayerAlive) {
			isOver[0] = true;
			isOver[1] = false;
		}
		return isOver;
	}
	
	/**
	 * Returns if some player have made a record
	 * @throws POOggerException     - TIPO_ERRONEO   When the file is not a .txt
	 * 								- ERROR_EXPORTAR When some error occurs when tries to export
	 */
	public boolean checkScoresRecords() throws POOggerException {
		isOver[2] = false;
		int minScore = -10000;
		for (int score : highScores.keySet()) {
			minScore = score;
			break;
		}
		for (Player player : players) {
			if (!player.isMachine() && player.getPoints() >= minScore) {
				isOver[2] = true;
				ArrayList<String> names = new ArrayList<>();
				names.add(player.getName());
				if (highScores.containsKey(player.getPoints())) { 
					names.addAll(highScores.get(player.getPoints()));
				}
				highScores.put(player.getPoints(), names);
			}
		}
		writeHighScoreFile(scoresFile);
		return isOver[2];
	}
	
	/**
	 * Kill one player
	 * @param player, the player to kill
	 */
	public void killPlayer(Player player) {
		
		player.decreasePlayerLives(deadPenalization);
	}
	
	/**
	 * Reset the player, to its initial position
	 * @param player, the player desire to reset
	 */
	public void resetPlayer(Player player) {
		player.resetPlayer();
	}
	
	/**
	 * Gives to one player a bonus
	 * @param player, the player who's going to get the bonus
	 * @param bonusPoints, the amount of bonus
	 */
	public void givePlayerBonus(Player player, int bonusPoints) {
		player.changePoints(bonusPoints);
	}
	
	/**
	 * 
	 * @param time
	 * @return
	 */
	public ArrayList<Element> gameLoop(int time) {
		levelGenerator.addElements(time, throwable==null, players);
		elements.addAll(levelGenerator.getMobilesElements());
		fixeds.addAll(levelGenerator.getFixedsElements());
		checkThrowableCollision();
		if(time%2==0) update();
		for(Player player: players) {
			if(checkPlayerCollisions(player)  && player.isAlive()) {
				killPlayer(player);
			}
		}
		ArrayList<Element> allElements = new ArrayList<Element>();
		allElements.addAll(fixeds);
		allElements.addAll(elements);
		return allElements;
	}
	
	
	private void powerTest() {
		fixeds.add(new SpeedPower(48*2, 48*14, 48, 48));
		fixeds.add(new FlyPower(48*3, 48*13, 48, 48));
		fixeds.add(new ArmorPower(48*4, 48*12, 48, 48));
		elements.add(new Car(screenWidth,48*12,-1,sprites.get("RedCar"),"Red"));
		elements.add(new Truck(screenWidth,48*9,-1,sprites.get("Truck1"),"Truck1",false));
		elements.add(new Lizard(-sprites.get("Lizard1")[0],48*8, sprites.get("Lizard1"), "Lizard1", 1));
		
	}
	
	private void bonusTest(){
		fixeds.add(new Bug(48*5, 48*13, 48, 48, 3000));
		String[] types = new String[] {"Small","Medium","Large"};
		SmallLog small = new SmallLog(-sprites.get(types[0]+"Log1")[0],48*12,1,sprites .get(types[0]+"Log1"),new int[] {48,48},types[0]+"Log1");
		FemaleFrog fFrog = new FemaleFrog(-sprites.get(types[0]+"Log1")[0], 48*12, 48,new int[] {48,48}, "FFrog", false); 
		elements.add(small);
		small.inCollision(fFrog);
		elements.add(fFrog);
	}
	
	/**
	 * Pauses all the current elements in the game
	 */
	public void pauseElements() {
		for(Element element : elements) {
			element.stopAnimator();
		}
		for(Element element : fixeds) {
			element.stopAnimator();
		}
	}
	
	
	/**
	 * Resumes all the current elements in the game
	 */
	public void resumeElements() {
		for(Element element : elements) {
			element.resumeAnimator();
		}
		for(Element element : fixeds) {
			element.resumeAnimator();
		}
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