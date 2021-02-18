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
		//TabReaderV2 tb = new TabReaderV2(cfg.getAttr("test_path")+cfg.getAttr("test_file"));
		//TabReaderV2 tb = new TabReaderV2("./src/main/java/testTab.txt");
		
		//TabReaderV3 tb = new TabReaderV3();
		TabReaderV3 tb = new TabReaderV3(cfg.getAttr("hotcrossbuns_path")+cfg.getAttr("hotcrossbuns_file"),6);
		
		tb.readMeasure();
		while(tb.hasNext()) {	
			MeasureReaderV3 ms = new MeasureReaderV3(tb.getMeasure());
			while(ms.hasNext()) {
				ms.readNotes();
				ms.getNotes();
			}
			tb.readMeasure();
			//measure break
		}
	}

}
