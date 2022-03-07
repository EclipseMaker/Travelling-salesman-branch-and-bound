package Utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import Modelisation.Client;
import Modelisation.Depot;
import Modelisation.Robot;
import Modelisation.Map;


public class MapGenerator {
	public static Map generateMap(String filePath) throws IOException
	{ 
	     FileReader r = new FileReader(filePath);
	     BufferedReader reader = new BufferedReader(new FileReader(filePath));
	     
	     //Initialize Sized Map
	     String[] fieldInfo = reader.readLine().split(" ");
	     Map resultMap = new Map(Integer.parseInt(fieldInfo[0]), Integer.parseInt(fieldInfo[1]));
	     int idLocation = 0;
	    
	     //Add Clients
	     int nbClients = Integer.parseInt(reader.readLine().trim());
	     for (int cpt = 0; cpt < nbClients; cpt++)
	     {
	    	 fieldInfo = reader.readLine().split(" ");
	    	 Client c = new Client(idLocation, Integer.parseInt(fieldInfo[1]), Integer.parseInt(fieldInfo[0]),Integer.parseInt(fieldInfo[2]));
	    	 resultMap.updateMap(c);
	    	 idLocation++;
	     }
	     
	     //Add Obstacles
	     int nbObstacles = Integer.parseInt(reader.readLine().trim());
	     for (int cpt = 0; cpt < nbObstacles; cpt++)
	     {
	    	 fieldInfo = reader.readLine().split(" ");
	    	 resultMap.updateMap(Integer.parseInt(fieldInfo[0]), Integer.parseInt(fieldInfo[1]), 
	    			 			 Integer.parseInt(fieldInfo[2]), Integer.parseInt(fieldInfo[3]));
	     }
	     
	     //Add Depots
	     int nbDepots = Integer.parseInt(reader.readLine().trim());
	     for (int cpt = 0; cpt < nbDepots; cpt++)
	     {
	    	 fieldInfo = reader.readLine().split(" ");
	    	 Depot d = new Depot(idLocation, Integer.parseInt(fieldInfo[1]), Integer.parseInt(fieldInfo[0]));
	    	 resultMap.updateMap(d);
	    	 idLocation++;
	     }
	     
	     //Add Robots, each affected to a depot
	     int nbTypeRobots = Integer.parseInt(reader.readLine().trim());
	     for (int cpt = 0; cpt < nbTypeRobots; cpt++)
	     {
	    	 fieldInfo = reader.readLine().split(" ");
	    	 int capacity = Integer.parseInt(fieldInfo[0]), nbRobots = Integer.parseInt(fieldInfo[1]);
	         int idDepot = Integer.parseInt(fieldInfo[2]);
	    	 for (int cptR = 0; cptR < nbRobots; cptR++)
	    		resultMap.updateMap(new Robot(capacity), idDepot);
	     }
	     
	     //Initialize path to neighbors roads
	     resultMap.setNearestPathBetweenLocations();
	     
	     reader.close();
	     return resultMap;    
	}
	
	public static void displayMatrix(int[][] matrix) {
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
	
}
