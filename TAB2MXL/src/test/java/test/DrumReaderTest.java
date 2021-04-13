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
		testKit.add("P1-I50");
		testKit.add("P1-I43");
		testKit.add("P1-I39");
		testKit.add("P1-I48");
		testKit.add("P1-I46");
		testKit.add("P1-I36");
		assertLinesMatch(testKit, drumReader.getDrumKit());	
	}

	@Test
	void profExample() {
		String path = System.getProperty("user.dir") + "/drumTestTab.txt";
		try {
			File fTab = null;
			fTab=new File(path);
			TabReaderV4 TRv4 = new TabReaderV4(fTab, 6, 0);
			
			
		}catch(Exception e) {
			
		}
		
	}
	
	/* Checks the DrumReader class correctly parses the first measure of the
	 * prof's given example shown below
	 * 
	 *  CC|x---------------|--------x-------|*
	 *  HH|--x-x-x-x-x-x-x-|----------------|*
	 *  SD|----o-------o---|oooo------------|*
	 *  HT|----------------|----oo----------|*
	 *  MT|----------------|------oo--------|*
	 *  BD|o-------o-------|o-------o-------|*/
	@Test
	void profExampleTest() {
		String path = System.getProperty("user.dir") + "/drumTestTab.txt";
		try {
			//Loads Drum Tab into a string array
			File fTab = null;
			fTab = new File(path);
			TabReaderV4 TRv4 = new TabReaderV4(fTab, 6, 0);
			TRv4.readMeasure();
			String [] tab = TRv4.getMeasure();
			
			DrumReader DR = new DrumReader(tab);// assumed 4/4
			TRv4.readMeasure();
			tab = TRv4.getMeasure();
			DR.setMeasure(tab);
			
			ArrayList<String[]> actualNotes = new ArrayList<String[]>();
			ArrayList<String[]> notes = new ArrayList<String[]>();

			//Expected notes in the first measure
			String [] note1 = {"A", "5", "2", "P1-I50", "1", "eighth", "x", "begin", "up"};
			notes.add(note1);
			String [] note2 = {"F", "4", "8", "P1-I36", "2", "half", "o", "", "down"};
			notes.add(note2);
			String [] note3 = {"G", "5", "2", "P1-I43", "1", "eighth", "x", "continue", "up"};
			notes.add(note3);
			String [] note4 = {"G", "5", "2", "P1-I43", "1", "eighth", "x", "continue", "up"};
			notes.add(note4);
			String [] note5 = {"C", "5", "1", "P1-I39", "1","16th", "o", "", "up"};
			notes.add(note5);
			String [] note6 = {"G", "5", "2", "P1-I43", "1", "eighth", "x", "end", "up"};
			notes.add(note6);
			String [] note7 = {"G", "5", "2", "P1-I43", "1", "eighth", "x", "begin", "up"};
			notes.add(note7);
			String [] note8 = {"F", "4", "8", "P1-I36", "2", "half", "o", "", "down"};
			notes.add(note8);
			String [] note9 = {"G", "5", "2", "P1-I43", "1", "eighth", "x", "continue", "up"};
			notes.add(note9);
			String [] note10 = {"G", "5", "2", "P1-I43", "1", "eighth", "x", "continue", "up"};
			notes.add(note10);
			String [] note11 = {"C", "5", "1", "P1-I39", "1", "16th", "o", "", "up"};
			notes.add(note11);
			String [] note12 = {"G", "5", "2", "P1-I43", "1", "eighth", "x", "end", "up"};
			notes.add(note12);

			//add all parsed notes in the first measure into a different array-list
			while(DR.hasNext()) {
				for(String [] s: DR.readNotes()) {
					actualNotes.add(s);
				}
			}
			
			//check the values of each note
			for(int i = 0; i < actualNotes.size(); i++) {
				assertArrayEquals(notes.get(i), actualNotes.get(i));
			}
			
		}catch (Exception e) {
			
		}
	}

}
