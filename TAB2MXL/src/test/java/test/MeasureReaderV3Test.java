package test;


import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import org.junit.jupiter.api.Test;

import xmlClasses.AttributeWriter;

class MeasureReaderV3Test {

	@Test
	void basicTabTestCase() {
		String path = System.getProperty("user.dir") + "/src/main/java/testTab.txt";
		String output = "";
		File fTAB = new File(path);
		TabReaderV4 TRv4;
		int STAFFLINES = 6;
		try {
			TRv4 = new TabReaderV4(fTAB, STAFFLINES);
			TRv4.readMeasure();
		
			
	
			while(TRv4.hasNext()) {
				MeasureReaderV3 MRv3 = new MeasureReaderV3(TRv4.getMeasure(), TRv4.getTuning(), 4, 4);//6 - num of string, 4 4 - time signature		
				while(MRv3.hasNext()) {
					MRv3.readNotes();
					for(String[] s:MRv3.getNotes()) {
						for(int i = 0; i < s.length; i++) {
							output += s[i] + ", ";
						}
						output += "\n";
					}
						
				}	
				TRv4.readMeasure();
			}
			System.out.println(output);
		}catch(Exception e) {
			fail();
		}
	}

}
