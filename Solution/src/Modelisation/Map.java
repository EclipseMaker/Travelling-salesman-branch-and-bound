package Modelisation;

import java.util.ArrayList;

public class Map {
	private int width; 
	private int height; 
	private Obstacle[][] Board;
	private ArrayList<Client> Clients;
	private ArrayList<Depot> Depots;
	
	//Constructors
	public Map(int width, int height) {
		this.width = width; 
		this.height = height;
		Board = new Obstacle[width][height];
		Clients = new ArrayList<Client>();
		Depots = new ArrayList<Depot>();
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

	public Obstacle[][] getBoard() {
		return Board;
	}

	public void setBoard(Obstacle[][] board) {
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
	
	public void updateMap(int x1, int y1, int x2, int y2)
	{
		int minX = (Math.min(x1, y1)), maxX = (Math.max(x1, y1));
		int minY = (Math.min(x2, y2)), maxY = (Math.max(x2, y2));

		for (int x = minX; x <= maxX; x++)
		{
			for (int y = minY; y <= maxY; y++)
			{
				Obstacle o = new Obstacle(x, y, false);
				Board[x][y] = o;
			}
		}
	}
	
	public void updateMap(Robot r, int idDepot)
	{
		Depot d = this.Depots.get(idDepot);
		d.addRobots(r); r.setDepot(d);
	}

	@Override
	public String toString()
	{
		String visualMap = "";
		for (int y = 0; y < height; y++)
		{
			for (int x = 0; x < width; x++)
				visualMap += (Board[x][y] == null) ? "-" + "\t" : Board[x][y] + "\t";
			visualMap += y + "\n";
		}
		for (int  x = 0; x < width; x++)
			visualMap += x + "\t";
		
		return visualMap;
	}
}
