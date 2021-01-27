package tab2mxl;

import config.*;
import test.*;

public class Main {

	public static void main(String[] args) {
		//helooo
		// TODO Auto-generated method stub
		System.out.println("My line is even better");
		System.out.println("testing push verison 2");
		System.out.println("testing push");
		System.out.println("test ConfigReader");
		System.out.println("newer thing");
		//ConfigReader cfg = new ConfigReader("../config.ini");
		ConfigReader cfg = ConfigReader.getConfig();
		System.out.println("Aidan messed up this time lol");
		System.out.println(cfg.getAttr("attr1"));
		System.out.println(cfg.getAttr("test_path")+cfg.getAttr("test_file"));
		TabReader tb = new TabReader();
		for(int i = 0; i<7; i++) {
			tb.readMeasure();
		}

	}

}
