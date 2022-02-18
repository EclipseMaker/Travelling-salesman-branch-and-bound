package Versions;

import java.io.IOException;

import Modelisation.Map;
import Utils.MapGenerator;

public class Version0 {
	public static void main(String[] args) throws IOException {
		Map m = MapGenerator.generateMap("src/Testdata/DATA_D/Test20_15_30_D.txt");
		System.out.println(m);
	}

}
