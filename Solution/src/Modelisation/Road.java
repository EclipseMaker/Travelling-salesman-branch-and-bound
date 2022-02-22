package Modelisation;

public class Road {
	private int x; 
	private int y; 
	private Boolean isLocation;
	
	public Boolean getIsLocation() {
		return isLocation;
	}

	public void setIsLocation(Boolean usable) {
		this.isLocation = usable;
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
 	
	public Road(int x, int y, Boolean isLocation)
	{
		this.x = x; 
		this.y = y;
		this.isLocation = isLocation;
	}
	
	public String toString()
	{
		return("-");
	}
}
