package test;


import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import org.junit.jupiter.api.Test;

import config.ConfigReader;
import xmlClasses.AttributeWriter;

class MeasureReaderV5Test {

	@Test
	void basicTabTestCase() {
		
		ConfigReader cfg = ConfigReader.getConfig();
		System.out.println(cfg.getAttr("attr1"));
		
		try {
			File file = new File(cfg.getAttr("test_path")+cfg.getAttr("test_file"));
			TabReaderV5 tb = new TabReaderV5(file,6);
			String output = "";
			tb.readMeasure();
			while(tb.hasNext()) {	
				MeasureReaderV5 ms = new MeasureReaderV5(tb.getMeasure(),tb.getTuning(),4,4);
				while(ms.hasNext()) {
					ms.readNotes();
					for(String[] s:ms.getNotes()) {
						for(int i = 0; i < s.length; i++) {
							output += s[i] + ", ";
						}
						output += "\n";
					}
				}
				tb.readMeasure();
				System.out.println(output);
			}
		}catch(Exception e) {
			fail();
		}
	}
	
	void failingTabTestCase() {
		
		ConfigReader cfg = ConfigReader.getConfig();
		System.out.println(cfg.getAttr("attr1"));
		
		try {
			File file = new File(cfg.getAttr("test_path")+cfg.getAttr("bad_file"));
			TabReaderV5 tb = new TabReaderV5(file,6);
			String output = "";
			tb.readMeasure();
			while(tb.hasNext()) {	
				MeasureReaderV5 ms = new MeasureReaderV5(tb.getMeasure(),tb.getTuning(),4,4);
				while(ms.hasNext()) {
					ms.readNotes();
					for(String[] s:ms.getNotes()) {
						for(int i = 0; i < s.length; i++) {
							output += s[i] + ", ";
						}
						output += "\n";
					}
				}
				tb.readMeasure();
				System.out.println(output);
			}
			
			fail();
			
		}catch(Exception e) {
			
		}
	}

}
