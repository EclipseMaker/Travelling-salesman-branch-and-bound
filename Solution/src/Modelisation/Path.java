package Modelisation;

import java.util.ArrayList;
import java.util.Arrays;

public class Path {
	private Area from; 
	private Area to;
	private int[][] costMatrix;
	private int cost; 
	private ArrayList<Area> throughAreas;
	private ArrayList<Client> leftClients;
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
		leftClients.remove(a);
	}
	public Area getLastThroughArea()
	{
		return throughAreas.get(throughAreas.size() - 1);
	}
	public ArrayList<Client> getLeftClients() {
		return leftClients;
	}
	public void setLeftClients(ArrayList<Client> leftClients) {
		this.leftClients = leftClients;
	}
	public int[][] getCostMatrix() {
		return costMatrix;
	}
	public void setCostMatrix(int[][] costMatrix) {
		int newCostMatrix[][] = new int[costMatrix.length][costMatrix[0].length];
		for (int cpt = 0; cpt < costMatrix.length; cpt++)
			newCostMatrix[cpt] = Arrays.copyOf(costMatrix[cpt], costMatrix[cpt].length);
		this.costMatrix = newCostMatrix;
	}
	
	public Path clone()
	{
		Path next = new Path();
		next.from = from; next.to = to;
		next.cost = cost;
		//System.out.println("VOICI LE POIDS DAVANT " + weight);
		next.weight = weight;
		int newCostMatrix[][] = new int[costMatrix.length][costMatrix[0].length];
		for (int cpt = 0; cpt < costMatrix.length; cpt++)
			newCostMatrix[cpt] = Arrays.copyOf(costMatrix[cpt], costMatrix[cpt].length);
		next.costMatrix = newCostMatrix; 
		next.throughAreas = new ArrayList<Area>();
		for(Area a : throughAreas)
			next.throughAreas.add((Area)a.clone());
		next.leftClients = new ArrayList<Client>();
		for(Client c : leftClients)
			next.leftClients.add((Client)c.clone());
		return next;
	}
	
	/**
	 * Minimize a square matrix 
	 * Substract mininum of each column to the affiliated column. Same for the rows.
	 * @return Sum of all minimums (columns and rows)
	 */
	public int minimizeCostMatrix()
	{
		//Minimize Rows
		int sumMinRow = 0; int size = costMatrix.length;
		for (int x = 0; x < size; x++)
		{
			int minRow = Integer.MAX_VALUE;
			for (int y = 0; y < size; y++)
			{
				if (costMatrix[x][y] < minRow)
					minRow = costMatrix[x][y];
				
				if ((y == size - 1) && (minRow != Integer.MAX_VALUE) && (minRow != 0))
				{
					sumMinRow += minRow;
					for (int y2 = 0; y2 < size; y2++)
						if (costMatrix[x][y2] != Integer.MAX_VALUE)
							costMatrix[x][y2] -= minRow;
				}	
			}
		}
		
		//Minimize Columns
		int sumMinCol = 0;
		for (int y = 0; y < size; y++)
		{
			int minCol = Integer.MAX_VALUE;
			for (int x = 0; x < size; x++)
			{
				if (costMatrix[x][y] < minCol)
					minCol = costMatrix[x][y];
			
				if ((x == size - 1) && (minCol != Integer.MAX_VALUE) && (minCol != 0))
				{
					sumMinCol += minCol;
					for (int x2 = 0; x2 < size; x2++)
					{
						if (costMatrix[x2][y] != Integer.MAX_VALUE)
							costMatrix[x2][y] -= minCol;
					}
				}
			}
		}
		
		return sumMinRow + sumMinCol;
	}
	
	public int getCost() {
		return cost;
	}
	public void setCost(int cost) {
		this.cost = cost;
	}
	
	@Override
	public String toString()
	{
		String visualPath = ""; 
		System.out.println("START");
		for (Area a : throughAreas)
		{
			visualPath+="\n";
			visualPath += a.getId();
		}
	
		return visualPath;
	}
}
