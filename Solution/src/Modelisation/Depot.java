package Modelisation;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class Depot extends Obstacle {
	private static AtomicInteger count = new AtomicInteger(-1);
	private int id;
	private ArrayList<Robot> robots;

	/*public Depot(int x, int y, ArrayList<Robot> robot)
	{
		super(x, y, true);
		this.robots = robots; 
	}*/
	
	public Depot(int x, int y)
	{
		super(x, y, true);
		this.robots = new ArrayList<Robot>(); 
		this.id = count.incrementAndGet();
	}
	
	public ArrayList<Robot> getRobots() {
		return robots;
	}

	public void setRobots(ArrayList<Robot> robots) {
		this.robots = robots;
	}
	
	public void addRobots(Robot r) {
		this.robots.add(r);
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString()
	{
		return("D" + this.getId() + "-" + this.getRobots().size());
	}
}
