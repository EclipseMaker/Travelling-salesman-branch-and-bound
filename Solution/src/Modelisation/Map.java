package Modelisation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

public class Map {
	private int width; 
	private int height; 
	private Area[][] Board; 
	private ArrayList<Client> Clients;
	private ArrayList<Depot> Depots;
	private	int[][] incidenceMatrix;
	
	//Constructors
	public Map(int width, int height) {
		this.width = width; 
		this.height = height;
		Clients = new ArrayList<Client>();
		Depots = new ArrayList<Depot>();
		Board = new Area[width][height];
		for (int x = 0; x < width; x++)
			for(int y = 0; y < height; y++)
				Board[x][y] = new Area(-1, x, y, false);
	}

	public int[][] getIncidenceMatrix() {
		return incidenceMatrix;
	}
	
	public void displayIncidenceMatrix() {
		
		System.out.println("Matrice Incidence");
		for(int x = 0; x < incidenceMatrix.length; x++)
		{
			for (int y = 0; y < incidenceMatrix.length; y++)
			{
				if (incidenceMatrix[x][y] == Integer.MAX_VALUE)
					System.out.print("MAX" + "\t");
				else
					System.out.print(incidenceMatrix[x][y] + "\t");
			}
			System.out.println( "\t" + x);
		}
		System.out.println();
		for (int y = 0; y < incidenceMatrix.length; y++)
			System.out.print(y  + "\t");
			
	}
	public void setIncidenceMatrix(int[][] incidenceMatrix) {
		this.incidenceMatrix = incidenceMatrix;
	}
	
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Area[][] getBoard() {
		return Board;
	}

	public void setBoard(Area[][] board) {
		Board = board;
	}

	public ArrayList<Client> getClients() {
		return Clients;
	}

	public void setClients(ArrayList<Client> clients) {
		Clients = clients;
	}

	public ArrayList<Depot> getDepots() {
		return Depots;
	}

	public void setDepots(ArrayList<Depot> depots) {
		Depots = depots;
	}
	
	//Methods
	public void updateMap(Client c)
	{
		this.Board[c.getX()][c.getY()] = c;
		Clients.add(c);
	}
	
	public void updateMap(Depot d)
	{
		this.Board[d.getX()][d.getY()] = d;
		Depots.add(d);
	}
	
	public void updateMap(int y1, int x1, int y2, int x2)
	{
		int minX = Math.min(x1, x2), maxX = Math.max(x1, x2);
		int minY = Math.min(y1, y2), maxY = Math.max(y1, y2);

		for (int x = minX; x <= maxX; x++)
			for (int y = minY; y <= maxY; y++)
				Board[x][y] = null;
	}
	
	public void updateMap(Robot r, int idDepot)
	{
		Depot d = this.Depots.get(idDepot);
		d.addRobots(r); r.setDepot(d);
	}

	public void setNearestPathBetweenLocation()
	{
		int nbLocation = this.getClients().size() + this.getDepots().size();
		incidenceMatrix = new int[nbLocation][nbLocation];
		
		for (Client c : Clients)
		{
			setNearestPathToLocation(c);
			System.out.println("Pour le client " + c);
			c.getPaths();
			for(HashMap.Entry<Area, Path> entry : c.getPaths().entrySet())
			    incidenceMatrix[c.getId()][entry.getKey().getId()] =  entry.getValue().getWeight();
		}
		
		for (Depot d : Depots)
		{
			setNearestPathToLocation(d);
			System.out.println("Pour le depot " + d);
			d.getPaths();
			for(HashMap.Entry<Area, Path> entry : d.getPaths().entrySet())
			    incidenceMatrix[d.getId()][entry.getKey().getId()] = entry.getValue().getWeight();
		}
		
		for (int xy = 0; xy < nbLocation; xy++)
			incidenceMatrix[xy][xy] = Integer.MAX_VALUE;
	}

	public void setNearestPathToLocation(Area source)
	{
		HashMap<Area, ArrayList<Area>> foundPaths = new HashMap<Area, ArrayList<Area>>();
		
		ArrayList<Area> currentPathFromSource = new ArrayList<Area>();
		currentPathFromSource.add(source);
		foundPaths.put(source, currentPathFromSource);
		
		ArrayList<Area> waitingList = new ArrayList<Area>();
		waitingList.add(source);
		
		while (!waitingList.isEmpty())
		{
			Area currentArea = waitingList.remove(0);
			int currentAreaX = currentArea.getX(), currentAreaY = currentArea.getY();
			ArrayList<Area> nextAreas = new ArrayList<Area>();
			
			if (currentAreaX > 0 && Board[currentAreaX - 1][currentAreaY]!= null && !foundPaths.containsKey(Board[currentAreaX-1][currentAreaY]))
				nextAreas.add(Board[currentAreaX-1][currentAreaY]);
			if (currentAreaX < width - 1 && Board[currentAreaX + 1][currentAreaY]!= null && !foundPaths.containsKey(Board[currentAreaX+1][currentAreaY]))
				nextAreas.add(Board[currentAreaX+1][currentAreaY]);
			if (currentAreaY > 0 && Board[currentAreaX][currentAreaY-1]!= null && !foundPaths.containsKey(Board[currentAreaX][currentAreaY-1]))
				nextAreas.add(Board[currentAreaX][currentAreaY - 1]);
			if (currentAreaY < height - 1 && Board[currentAreaX][currentAreaY+1]!= null && !foundPaths.containsKey(Board[currentAreaX][currentAreaY+1]))
				nextAreas.add(Board[currentAreaX][currentAreaY+1]);
			
			for (Area next : nextAreas)
			{
				currentPathFromSource = (ArrayList<Area>)foundPaths.get(currentArea).clone();
				currentPathFromSource.add(next);
				foundPaths.put(next, currentPathFromSource);
				waitingList.add(next);
			}
		}
		
		for(Client c : Clients)
			source.addPath(new Path(source, c, foundPaths.get(c)));
		for(Depot d : Depots)
			source.addPath(new Path(source, d, foundPaths.get(d)));
	}
		
	@Override
	public String toString()
	{
		String visualMap = "";
		for (int y = 0; y < height; y++)
		{
			for (int x = 0; x < width; x++)
				visualMap += (Board[x][y] == null) ? "X" + "\t" : Board[x][y] + "\t";
			visualMap += y + "\n";
		}
		for (int  x = 0; x < width; x++)
			visualMap += x + "\t";
		
		return visualMap;
	}
}
