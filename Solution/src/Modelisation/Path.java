package Modelisation;

import java.util.ArrayList;

public class Path {
	private Area from; 
	private Area to;
	private ArrayList<Area> throughAreas;
	private int weight;

	//Constructors
	Path()
	{
		throughAreas = new ArrayList<Area>();
	}
	public Path(Area from, Area to)
	{
		this.from = from; 
		this.to = to;
	}
	public Path(Area from, Area to, ArrayList<Area> throughAreas) {
		this.from = from; 
		this.to = to;
		this.throughAreas = throughAreas;
		this.weight = throughAreas.size() - 1;
	}
	
	public Area getFrom() {
		return from;
	}
	public void setFrom(Area from) {
		this.from = from;
	}
	public Area getTo() {
		return to;
	}
	public void setTo(Area to) {
		this.to = to;
	}
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	public ArrayList<Area> getThroughAreas() {
		return throughAreas;
	}
	public void setThroughAreas(ArrayList<Area> throughAreas) {
		this.throughAreas = throughAreas;
	}
	public void addThroughArea(Area a)
	{
		throughAreas.add(a);
	}
	
	@Override
	public String toString()
	{
		String visualPath = ""; 
		for (Area a : throughAreas)
			visualPath += "(" + a.getX() + a.getY() + ") ";
		return visualPath;
	}
}
