package tab2mxl;

import config.*;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("test ConfigReader");
		//ConfigReader cfg = new ConfigReader("../config.ini");
		ConfigReader cfg = ConfigReader.getConfig();
		System.out.println(cfg.getAttr("attr1"));
		System.out.println(cfg.getAttr("test_path")+cfg.getAttr("test_file"));
	}

}
