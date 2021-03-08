package tab2mxl;

import java.io.File;

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
		
		
		try {
			File file = new File(cfg.getAttr("hotcrossbuns_path")+cfg.getAttr("hotcrossbuns_file"));
			//File file = new File(cfg.getAttr("test_path")+cfg.getAttr("test_file"));
			TabReaderV4 tb = new TabReaderV4(file,6);
			tb.readMeasure();
			while(tb.hasNext()) {	
				MeasureReaderV4 ms = new MeasureReaderV4(tb.getMeasure(),tb.getTuning(),4,4);
				while(ms.hasNext()) {
					ms.readNotes();
					ms.getNotes();
				}
				tb.readMeasure();
				//measure break
			}
		}catch(LineErrorException e) {
			System.out.println(e.getMessage());
			System.out.println(e.getLine());
			System.out.println(e.getString());
		}catch(Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
		}
	}

}
