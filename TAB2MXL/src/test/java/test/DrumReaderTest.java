package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

import xmlClasses.AttributeWriter;

class DrumReaderTest {

	@Test
	void tabReaderDrumkitTest() {
		String path = System.getProperty("user.dir") + "/drumTestTab.txt";
		TabReaderV2 tabReader = new TabReaderV2(path);
		tabReader.resetMeasure();// used to get drumkit
		tabReader.readMeasure();
		String[] measure = tabReader.getMeasure();
//		System.out.println("TEST:" + measure[0]);
		DrumReader drumReader = new DrumReader(measure);

		ArrayList<String> testKit = new ArrayList<String>();
		testKit.add("P1-150");
		testKit.add("P1-147");
		testKit.add("P1-139");
		testKit.add("P1-151");
		testKit.add("P1-149");
		testKit.add("P1-136");
//		assertLinesMatch(drumReader.getDrumKit(), testKit);
		;
	}

//	@Test
//	void measureOutput() {
//		try {
//			List<String[]> notes = new ArrayList<String[]>();
//			String output = "";
//			String path = System.getProperty("user.dir") + "/drumTestTab.txt";
//			TabReaderV2 tabReader = new TabReaderV2(path);
//			tabReader.resetMeasure();// used to get drumkit
//			tabReader.readMeasure();
//			DrumReader drumReader = new DrumReader(tabReader.getMeasure());
//
//			for (int m = 0; m < 2; m++) {
//				tabReader.readMeasure();
//				drumReader.setMeasure(tabReader.getMeasure());
//				while (drumReader.hasNext()) {
//					notes = drumReader.readNote();
//					try {
//
//						while (!notes.isEmpty()) {
//							String[] notes1 = notes.get(0);
//							for (int i = 0; i < notes1.length; i++) {
//								output += notes1[i] + " ,";
//							}
//							output += "\n";
//							notes.remove(0);
//						}
//					} catch (Exception e) {
//						System.out.println(e.getMessage());
//					}
//
//				}
//			}
//			System.out.println(output);
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//		}
//	}
	
	
	@Test 
	void rowParsingTest(){
		String path = System.getProperty("user.dir") + "/drumTestTab.txt";
		try {
			File fTab = null;
			fTab=new File(path);

			TabReaderV4 TRv4 = new TabReaderV4(fTab, 6, 0);
		System.out.println("USING TABV4 NOW !!!!!");
		
		
		TRv4.readMeasure();
		DrumReader DR = new DrumReader(TRv4.getMeasure());//assumed 4/4
		
		
		TRv4.readMeasure();
		while(TRv4.hasNext()) {

            DR.setMeasure(TRv4.getMeasure());
			while(DR.hasNextRow()) {
			System.out.println("Went Through to Read Row");
			for(String[] s:DR.readNoteRow()) {
				System.out.println("Step"+s[0]+
									"Octave"+Integer.parseInt(s[1])+
									"Duration"+Integer.parseInt(s[2])+
									"Intrument"+s[3]+
									"Voice"+s[4]+
									"Type"+s[5]+
									"NoteHead"+s[6]);
				}
			}
			TRv4.readMeasure();	
		}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

//	@Test
//	void airTonightTest() {
//		try {
//			List<String[]> notes = new ArrayList<String[]>();
//			String output = "";
//			String path = System.getProperty("user.dir") + "/inTheAirTonight.txt";
//			TabReaderV2 tabReader = new TabReaderV2(path);
//			tabReader.resetMeasure();// used to get drumkit
//			tabReader.readMeasure();
//			DrumReader drumReader = new DrumReader(tabReader.getMeasure());
//
//			System.out.println("NOTES");
//
//			// goes through 3 measures
//			for (int l = 0; l < 3; l++) {
//
//				tabReader.readMeasure();
//				drumReader.setMeasure(tabReader.getMeasure());
//				while (drumReader.hasNext()) {
//					notes = drumReader.readNote();
//					try {
//						if (!notes.isEmpty()) {
//							while (!notes.isEmpty()) {
//								String[] notes1 = notes.get(0);
//								for (int i = 0; i < notes1.length; i++) {
//									output += notes1[i] + " ,";
//								}
//								output += "\n";
//								notes.remove(0);
//							}
//						}
//					} catch (Exception e) {
//						System.out.println("OUTPUT ERROR");
//						System.out.println(e.getMessage());
//					}
//
//				}
//			}
//			System.out.println(output);
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//		}
//	}
}
