package tab2mxl;

import config.*;
import test.*;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//ConfigReader cfg = new ConfigReader("../config.ini");

		
		//change
		ConfigReader cfg = ConfigReader.getConfig();
		System.out.println(cfg.getAttr("attr1"));
		System.out.println(cfg.getAttr("test_path")+cfg.getAttr("test_file"));
		//TabReaderV2 tb = new TabReaderV2(cfg.getAttr("hotcrossbuns_path")+cfg.getAttr("hotcrossbuns_file"));
		TabReaderV2 tb = new TabReaderV2(cfg.getAttr("test_path")+cfg.getAttr("test_file"));
		
		//tb.readMeasure();
		
		while(tb.hasNext()) {
			tb.readMeasure();
			MeasureReaderV2 ms = new MeasureReaderV2(tb.getMeasure(),4,4);
			while(ms.hasNext()) {
				ms.readNote();
			}
		}
	}

}
