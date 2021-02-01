package tab2mxl;

import config.*;
import test.*;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//ConfigReader cfg = new ConfigReader("../config.ini");
		ConfigReader cfg = ConfigReader.getConfig();
		System.out.println(cfg.getAttr("attr1"));
		System.out.println(cfg.getAttr("test_path")+cfg.getAttr("test_file"));
		TabReaderV2 tb = new TabReaderV2();
		
		tb.readMeasure();
		
		MeasureReader ms = new MeasureReader(tb.getMeasure(),4,4);
		ms.readNote();
	}

}
