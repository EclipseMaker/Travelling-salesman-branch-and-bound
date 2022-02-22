package Modelisation;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class Depot extends Area {
	private ArrayList<Robot> robots;

	//Constructors
	public Depot(int id, int x, int y)
	{
		super(id, x, y, true);
		this.robots = new ArrayList<Robot>(); 
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

	@Override
	public String toString()
	{
		return("D" + super.getId() + "-" + this.getRobots().size());
	}
}
