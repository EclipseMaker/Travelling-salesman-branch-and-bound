package Versions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import Modelisation.Area;
import Modelisation.Map;
import Modelisation.Path;
import Utils.MapGenerator;

public class Version0 {
	public static void main(String[] args) throws IOException {
		Map m = MapGenerator.generateMap("src/Testdata/DATA_A/Test10_5_100.txt");
		//Map m = MapGenerator.generateMap("src/Testdata/DATA_E/Test20_20_E.txt");
		System.out.println(m);
		m.displayIncidenceMatrix();
		System.out.println(m.findOptimalCycle(m.getDepots().get(0)));
	}

}
