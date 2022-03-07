package Modelisation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

import Utils.MapGenerator;

public class Map {
	private int width; 
	private int height; 
	
	//Areas indexed by Coords
	private Area[][] Board;
	
	//RQ : Locations = Clients and Depots
	//Locations indexed by ID 
	private HashMap<Integer, Area> Locations;
	
	//List of Locations
	private ArrayList<Client> Clients;
	private ArrayList<Depot> Depots;
	
	//Array with shortest path length between Locations
	private	int[][] incidenceMatrix;
	
	//Constructors
	public Map(int width, int height) {
		this.width = width; 
		this.height = height;
		Clients = new ArrayList<Client>();
		Depots = new ArrayList<Depot>();
		Locations = new HashMap<Integer, Area>();
		Board = new Area[width][height];
		for (int x = 0; x < width; x++)
			for(int y = 0; y < height; y++)
				Board[x][y] = new Area(-1, x, y, false);
	}

	public int[][] getIncidenceMatrix() {
		return incidenceMatrix;
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
	
	//Map Updates
	public void updateMap(Client c)
	{
		this.Board[c.getX()][c.getY()] = c;
		Clients.add(c);
		Locations.put(c.getId(), c);
	}
	
	public void updateMap(Depot d)
	{
		this.Board[d.getX()][d.getY()] = d;
		Depots.add(d);
		Locations.put(d.getId(), d);
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
	
	/**
	 * Creates matrix shortest path distance between locations (indexed by id)
	 */
	public void setNearestPathBetweenLocations()
	{
		int nbLocation = this.getClients().size() + this.getDepots().size();
		incidenceMatrix = new int[nbLocation][nbLocation];
		
		//For each location - Sets the currentLocation-othersLocations distance
		for (Client c : Clients)
		{
			setNearestPathToLocation(c);
			c.getPaths();
			for(HashMap.Entry<Integer, Path> entry : c.getPaths().entrySet())
			    incidenceMatrix[c.getId()][entry.getKey()] =  entry.getValue().getWeight();
		}
		
		for (Depot d : Depots)
		{
			setNearestPathToLocation(d);
			d.getPaths();
			for(HashMap.Entry<Integer, Path> entry : d.getPaths().entrySet())
			    incidenceMatrix[d.getId()][entry.getKey()] = entry.getValue().getWeight();
		}
		
		//Paths with same departure arrival locations set to MAX distance
		for (int xy = 0; xy < nbLocation; xy++)
			incidenceMatrix[xy][xy] = Integer.MAX_VALUE;
	}
	
	/**
	 * Set shortest path distance from source to all locations 
	 * @param source departure area
	 */
	public void setNearestPathToLocation(Area source)
	{
		//Contains path from Source to all Areas of the maps
		HashMap<Area, ArrayList<Area>> foundPaths = new HashMap<Area, ArrayList<Area>>();
		
		ArrayList<Area> currentPath = new ArrayList<Area>();
		currentPath.add(source);
		foundPaths.put(source, currentPath);
		
		ArrayList<Area> waitingList = new ArrayList<Area>();
		waitingList.add(source);
		
		//Find the shortest source-area distance for ALL areas
		while (!waitingList.isEmpty())
		{
			Area currentArea = waitingList.remove(0);
			int currentAreaX = currentArea.getX(), currentAreaY = currentArea.getY();
			
			//Find currentArea's new and accessible neighbors
			ArrayList<Area> nextAreas = new ArrayList<Area>();
			if (currentAreaX > 0 && Board[currentAreaX - 1][currentAreaY]!= null && !foundPaths.containsKey(Board[currentAreaX-1][currentAreaY]))
				nextAreas.add(Board[currentAreaX-1][currentAreaY]);
			if (currentAreaX < width - 1 && Board[currentAreaX + 1][currentAreaY]!= null && !foundPaths.containsKey(Board[currentAreaX+1][currentAreaY]))
				nextAreas.add(Board[currentAreaX+1][currentAreaY]);
			if (currentAreaY > 0 && Board[currentAreaX][currentAreaY-1]!= null && !foundPaths.containsKey(Board[currentAreaX][currentAreaY-1]))
				nextAreas.add(Board[currentAreaX][currentAreaY - 1]);
			if (currentAreaY < height - 1 && Board[currentAreaX][currentAreaY+1]!= null && !foundPaths.containsKey(Board[currentAreaX][currentAreaY+1]))
				nextAreas.add(Board[currentAreaX][currentAreaY+1]);
			
			//For each neighbors, clone the source-currentArea path & add the current neighbor 
			//The obtained path is added to foundPaths
			//The neighbor is added to waitingList
			for (Area next : nextAreas)
			{
				currentPath = (ArrayList<Area>)foundPaths.get(currentArea).clone();
				currentPath.add(next);
				foundPaths.put(next, currentPath);
				waitingList.add(next);
			}
		}
		
		//Extract the source-locations distance and update source's Paths
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
	
	public void displayMatrix(int[][] matrix) {
		System.out.println("Matrice Incidence");
		for(int x = 0; x < matrix.length; x++)
		{
			for (int y = 0; y < matrix.length; y++)
			{
				if (matrix[x][y] == Integer.MAX_VALUE)
					System.out.print("MAX" + "\t");
				else
					System.out.print(matrix[x][y] + "\t");
			}
			System.out.println( "\t" + x);
		}
		System.out.println();
		for (int y = 0; y < matrix.length; y++)
			System.out.print(y  + "\t");	
	}
	
	/**
	 * Version 0 Solve - In progress
	 * @param d departure Depot
	 * @return Path representing how to visit clients and come back to departure Depot
	 */
	public Path findOptimalCycle(Depot d)
	{
		Path optimalPath = null;
		int[][] costMatrix = incidenceMatrix;
		Path current = new Path() {
			{
				setLeftClients(Clients); 
				addThroughArea(d);
				setCostMatrix(incidenceMatrix); 
			}
		};
		
		int cost = current.minimizeCostMatrix();
		current.setCost(cost);
		
		int upperBound = Integer.MAX_VALUE;
		ArrayList<Path> waitingList = new ArrayList<Path>(); waitingList.add(current);
		ArrayList<Path> pathToNextClients;
		Path nextPath;
		//int cpt = 0;
		while(!waitingList.isEmpty())
		{
			pathToNextClients = new ArrayList<Path>();
			current = waitingList.remove(0);
			//cpt++;
			//if (cpt == 6)
				//break;
			//System.out.println();
			//System.out.println("NEXT CITY " + current.getLastThroughArea().getId());
			
			for (Area nextArea : current.getLeftClients())
			{
				//System.out.println(nextArea.getId());
				nextPath = current.clone();
				costMatrix = nextPath.getCostMatrix();
				
				for (int y = 0; y < costMatrix.length; y++) 
					costMatrix[nextArea.getId()][y] = Integer.MAX_VALUE;		
				for (int x = 0; x < costMatrix.length; x++)
						costMatrix[x][current.getLastThroughArea().getId()] = Integer.MAX_VALUE;
				costMatrix[nextArea.getId()][current.getLastThroughArea().getId()] = Integer.MAX_VALUE;
				
				cost = current.getCost(); //System.out.println("av " + cost);
				cost += nextPath.minimizeCostMatrix(); 
				//MapGenerator.displayMatrix(costMatrix);	
				int nextAreaDistance = incidenceMatrix[current.getLastThroughArea().getId()][nextArea.getId()];
				//System.out.println("DISTANCE A LA PROCHAINE ZONE " + "X : " + current.getLastThroughArea().getId() + " Y " + nextArea.getId() + " " + nextAreaDistance );
				cost += nextAreaDistance; //System.out.println("ap " + cost); System.out.println();
				
				if (cost < upperBound)
				{
					nextPath.setCost(cost); 
					nextPath.addThroughArea(nextArea);
					nextPath.setWeight(nextPath.getWeight() + nextAreaDistance);
					pathToNextClients.add(nextPath);
				}
				//else
					//System.out.println("DENIED");
			}
			if (!current.getLeftClients().isEmpty())
			{
				pathToNextClients.sort(Comparator.comparingInt(Path::getCost));
				/*for (Path p : pathToNextClients)
					System.out.println(p.getLastThroughArea().getId() + " " + p.getCost());
				System.out.println(" ");*/
				waitingList.addAll(0, pathToNextClients);
			}
			else 
			{	
				cost = current.getCost();
				cost += current.minimizeCostMatrix();
				//System.out.println("FIN" + current.getLastThroughArea().getId());
				int nextAreaDistance = incidenceMatrix[current.getLastThroughArea().getId()][d.getId()];
				cost += nextAreaDistance;
				//System.out.println("JUSQUAU DEPOT" + nextAreaDistance);
				if(cost < upperBound)
				{
					current.setCost(cost);
					current.addThroughArea(d);
					current.setWeight(current.getWeight() + nextAreaDistance);
					optimalPath = current; 
					upperBound = cost;
					//System.out.println("UPPERBOUND MAJ" + upperBound);
				}
				//else
					//System.out.println("DENIED ");
			}
		}
		System.out.println("POIDS " + optimalPath.getWeight());
		return optimalPath; 
	}
	
}
