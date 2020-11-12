package dominio;

public interface Pushable {
	public void beingCarried(Carrier c);
	public boolean setPosition(int x, int y);
	public void addPush(int push, String dir);
	public int calculateMaxPush(String dir);
}
