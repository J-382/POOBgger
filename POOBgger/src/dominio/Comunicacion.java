package dominio;

import java.io.File;
import java.util.ArrayList;

public interface Comunicacion {
	public ArrayList<String[]> gameLoop(int time);
	public boolean checkScoresRecords() throws POOggerException;
	public void resumeElements();
	public void pauseElements();
	public ArrayList<String[]> getPlayers();
	public String getHighScore();
	public String getLevel();
	public void updateClock(int playerIndex);
	public void movePlayer(char dir, int numPlayer);
	public void open(File file) throws POOggerException;
	public void save(File file) throws POOggerException;
	public boolean[] getGameState();
}
