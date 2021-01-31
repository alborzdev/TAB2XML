package tab2mxl;

import config.*;
import test.*;

public class Main {

	public static void main(String[] args) {
		System.out.println("test ConfigReader");

		//ConfigReader cfg = new ConfigReader("../config.ini");
		
		ConfigReader cfg = ConfigReader.getConfig();
		System.out.println(cfg.getAttr("test_path")+cfg.getAttr("test_file"));
		TabReader tb = new TabReader();
		for(int i = 0; i<7; i++) {
			tb.readMeasure();
		}
	}

}
