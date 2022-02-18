package Modelisation;
import java.util.concurrent.atomic.AtomicInteger;

public class Obstacle {
	private int x; 
	private int y; 
	private Boolean usable;
	
	public Boolean getUsable() {
		return usable;
	}

	public void setUsable(Boolean usable) {
		this.usable = usable;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
 	
	public Obstacle(int x, int y, Boolean usable)
	{
		this.x = x; 
		this.y = y;
		this.usable = usable;
	}
	
	public String toString()
	{
		return("X");
	}
}
