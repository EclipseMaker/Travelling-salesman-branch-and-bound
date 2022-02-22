package Modelisation;

public class Robot {
	private int capacity;
	private Depot depot;
	
	//Constructors
	public Robot(int capacity)
	{
		this.capacity = capacity;
	}
	
	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	} 
	
	public Depot getDepot() {
		return depot;
	}

	public void setDepot(Depot depot) {
		this.depot = depot;
	}
}
