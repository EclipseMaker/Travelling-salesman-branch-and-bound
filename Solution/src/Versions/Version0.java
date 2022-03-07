package Versions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

import Modelisation.Area;
import Modelisation.Map;
import Modelisation.Path;
import Utils.MapGenerator;

public class Version0 {
	public static void main(String[] args) throws IOException {
		final long startTime = System.currentTimeMillis();
		System.out.println("lolsss");
		Map m = MapGenerator.generateMap("src/Testdata/DATA_A/Test20_15_100.txt");
		//Map m = MapGenerator.generateMap("src/Testdata/DATA_E/Test20_20_E.txt");
		System.out.println(m);
		MapGenerator.displayMatrix(m.getIncidenceMatrix());
		System.out.println(m.findOptimalCycle(m.getDepots().get(0)));
		
		final long endTime = System.currentTimeMillis();
		System.out.println("Total execution time: " + (endTime - startTime));
	}

}
 