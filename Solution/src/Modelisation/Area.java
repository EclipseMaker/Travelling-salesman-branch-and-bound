package Modelisation;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Area {
	private int id; 
	private int x;
	private int y; 
	private HashMap<Area, Path>Paths;
	private Boolean isLocation;
	
	//Constructors
	public Boolean getIsLocation() {
		return isLocation;
	}
	
	public HashMap<Area, Path> getPaths() {
		for(HashMap.Entry<Area, Path> entry : Paths.entrySet()) {
			Area key = entry.getKey();
			Path value = entry.getValue();
			System.out.println("Destination " + key + " Path " + value);
		}
		return Paths;
	}

	public void setPaths(HashMap<Area, Path> paths) {
		Paths = paths;
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
 	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Area(int id, int x, int y, Boolean isLocation)
	{
		this.x = x; 
		this.y = y;
		this.Paths = new HashMap<Area, Path>();
		this.isLocation = isLocation;
		this.id = id;
	}
	
	public void addPath(Path p)
	{
		if (Paths.containsKey(p.getTo()))
			return;
		Paths.put(p.getTo(), p);
		ArrayList<Area> reversedAreas = (ArrayList<Area>)p.getThroughAreas().clone();
		Collections.reverse(reversedAreas);
		Path reversedPath = new Path(p.getTo(), this, reversedAreas);
		p.getTo().Paths.put(this, reversedPath);
	}
	
	public String toString()
	{
		return("-");
	}
}
