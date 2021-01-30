package tab2mxl;

import config.*;
import test.*;

public class Main {

	public static void main(String[] args) {
		System.out.println("test ConfigReader");

		//ConfigReader cfg = new ConfigReader("../config.ini");
		
		ConfigReader cfg = ConfigReader.getConfig();
<<<<<<< HEAD
		System.out.println(":D");
		System.out.println("Aidan messed up this time lol");
		System.out.println(cfg.getAttr("attr1"));
=======

		System.out.println(cfg.getAttr("attr1")); 
>>>>>>> branch 'master' of https://github.com/alborzdev/2311-Project
		System.out.println(cfg.getAttr("test_path")+cfg.getAttr("test_file"));
		TabReader tb = new TabReader();
		for(int i = 0; i<7; i++) {
			tb.readMeasure();
		}

	}

}
