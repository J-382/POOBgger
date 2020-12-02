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
	private Rectangle clock;
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
	private Element throwable;
	private Generator levelGenerator;
	
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
		levelGenerator = new Generator(sprites,screenWidth,screenHeight,48,0,"Day");
		isOver = new boolean[]{false, false, false};
		level = 5;
		timeLimit = 300;
		isPlayerAlive = true;
		players = new ArrayList<Player>();
		players.add(new Player(5,48*7,48*14, sprites.get("Frog1W")));
		elements = new ArrayList<Element>();
		fixeds = new ArrayList<Element>();
		fixeds.addAll(levelGenerator.addFixedElements());
		clock = new Rectangle(0,0, 0, 20);
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
				if(element.isVisible==false) System.out.println(element.getClass());
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
				if (e.givesBonus()) givePlayerBonus(player, e.getPoints());
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
							givePlayerBonus(player, e.getPoints());
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
					if (f.givesBonus()) givePlayerBonus(player, f.getPoints());
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

	private void checkThrowableCollision(){
		if(throwable!=null) {
			for(Element e: elements) {
				if(throwable!=e && e.getBounds().intersects(throwable.getBounds())) {
					if(throwable.isDestructible() && e.inCollision(throwable)) {
						fixeds.add(throwable.destroy());
						throwable = null;
						break;
					}else if(!throwable.isDestructible()) fixeds.add(e.destroy());
				}
			}
		}
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
	 * Gives to one player a bonification
	 * @param player
	 * @param bonusPoints
	 */
	public void givePlayerBonus(Player player, int bonusPoints) {
		player.changePoints(bonusPoints);
	}
	
	public ArrayList<Element> gameLoop(int time) {
		levelGenerator.addElements(time, throwable==null, players);
		elements.addAll(levelGenerator.getMobilesElements());
		fixeds.addAll(levelGenerator.getFixedsElements());
		checkThrowableCollision();
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
	
	public void pauseElements() {
		for(Element element : elements) {
			element.stopAnimator();
		}
		for(Element element : fixeds) {
			element.stopAnimator();
		}
	}
	
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