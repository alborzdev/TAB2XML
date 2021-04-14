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

	/* Test of the initializeDrumKit method, using the prof's given
	 * example as the input
	 */
	@Test
	void tabReaderDrumkitTest() {
		String path = System.getProperty("user.dir") + "/drumTestTab.txt";
		TabReaderV2 tabReader = new TabReaderV2(path);
		tabReader.resetMeasure();// used to get drumkit
		tabReader.readMeasure();
		String[] measure = tabReader.getMeasure();
		DrumReader drumReader = new DrumReader(measure);

		//Expected instruments in the drum kit
		ArrayList<String> testKit = new ArrayList<String>();
		testKit.add("P1-I50");
		testKit.add("P1-I43");
		testKit.add("P1-I39");
		testKit.add("P1-I48");
		testKit.add("P1-I46");
		testKit.add("P1-I36");
		
		assertLinesMatch(testKit, drumReader.getDrumKit());	
	}
	
	/* Checks the DrumReader class correctly parses the first measure of the
	 * prof's given example shown below
	 * 
	 *  CC|x---------------|*
	 *  HH|--x-x-x-x-x-x-x-|*
	 *  SD|----o-------o---|*
	 *  HT|----------------|*
	 *  MT|----------------|*
	 *  BD|o-------o-------|*/
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
	
	/* Test to make sure Hi-Hat notes with an 'o' note-head are adjusted to
	 * an open Hi-Hat
	 *  CC|x---------------|*
	 *  HH|--x-x-x-o-o-----|*
	 *  SD|----------------|*
	 *  HT|----------------|*
	 *  MT|----------------|*
	 *  BD|----------------|*/
	@Test
	void openHiHatTest() {
		String path = System.getProperty("user.dir") + "/drumTestTab2.txt";
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
			String [] note3 = {"G", "5", "2", "P1-I43", "1", "eighth", "x", "continue", "up"};
			notes.add(note3);
			String [] note4 = {"G", "5", "2", "P1-I43", "1", "eighth", "x", "continue", "up"};
			notes.add(note4);
			String [] note5 = {"G", "5", "2", "P1-I43", "1", "eighth", "x", "end", "up"};
			notes.add(note5);
			String [] note6 = {"G", "5", "2", "P1-I47", "1", "eighth", "o", "begin", "up"};
			notes.add(note6);
			String [] note7 = {"G", "5", "2", "P1-I47", "1", "eighth", "o", "end", "up"};
			notes.add(note7);
			
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
	
	/* Output Test of old row parsing for prof's given example*/
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
				if(s[0].equals("forward")) {
					System.out.println("FORWARD " + s[1]  + "");
				}else {
					System.out.println("Step"+s[0]+
							"Octave"+Integer.parseInt(s[1])+
							"Duration"+Integer.parseInt(s[2])+
							"Intrument"+s[3]+
							"Voice"+s[4]+
							"Type"+s[5]+
							"NoteHead"+s[6]+
							"Beam"+s[7]);
				}

				}
			}
			TRv4.readMeasure();	
		}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
