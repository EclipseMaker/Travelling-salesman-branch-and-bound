package Modelisation;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Area {
	private int id; 
	private int x;
	private int y;
	private HashMap<Integer, Path>Paths;
	private Boolean isLocation;
	
	//Constructors
	public Boolean getIsLocation() {
		return isLocation;
	}
	
	public HashMap<Integer, Path> getPaths() {
		return Paths;
	}

	public void setPaths(HashMap<Integer, Path> paths) {
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
		this.Paths = new HashMap<Integer, Path>();
		this.isLocation = isLocation;
		this.id = id;
	}
	
	@Override
	public boolean equals(Object o)
	{
		return ((Area)o).id == id;
	}
	
	@Override
	public Area clone()
	{
		Area o = new Area(id, x, y, isLocation);
		((Area)o).setPaths(this.Paths);
		return o;
	}
	public String toString()
	{
		return("-");
	}
	/**
	 * Add path in Paths for currentArea and destinationPath
	 * @param p
	 */
	public void addPath(Path p)
	{
		if (Paths.containsKey(p.getTo()))
			return;
		Paths.put(p.getTo().getId(), p);
		ArrayList<Area> reversedAreas = (ArrayList<Area>)p.getThroughAreas().clone();
		Collections.reverse(reversedAreas);
		Path reversedPath = new Path(p.getTo(), this, reversedAreas);
		p.getTo().Paths.put(this.getId(), reversedPath);
	}
}
