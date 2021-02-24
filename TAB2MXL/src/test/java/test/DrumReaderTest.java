package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

class DrumReaderTest {

	@Test
	void basicDrumTabTest() {
		try {

			String path = System.getProperty("user.dir") + "/drumTestTab.txt";
			// creates a file object with the path
			File file = new File(path);

			// initializes new scanner of the file object
			Scanner scan = new Scanner(file);

			// String to hold lines of one measure
			String[] lines = new String[6];

			// goes through each line the file has in it
			int i = 0;
			while (scan.hasNextLine()) {

				// stores the current line in lines
				// Shifted new line '\n' to the end instead of the front -Aidan
				lines[i] = scan.nextLine();
				i++;
			}

			DrumReader drumReader = new DrumReader(lines);
			ArrayList<String> testKit = new ArrayList<String>();
			testKit.add("CC");
			testKit.add("HH");
			testKit.add("SD");
			testKit.add("HT");
			testKit.add("MT");
			testKit.add("BD");
			assertLinesMatch(drumReader.getDrumKit(), testKit);

		} catch (Exception e) {
			fail();
		}
	}

	@Test
	void DrumTabNoteTest() {
		try {
			String path = System.getProperty("user.dir") + "/drumTestTab.txt";
			TabReaderV2 tabReader = new TabReaderV2(path);
			tabReader.resetMeasure();// used to get drumkit
			tabReader.readMeasure();
			DrumReader drumReader = new DrumReader(tabReader.getMeasure());
			tabReader.readMeasure();
			drumReader.setMeasure(tabReader.getMeasure());
			List<String[]> notes = drumReader.readNote();
			ArrayList<String[]> notesTest = new ArrayList<String[]>();
			String[] notes1 = { "A", "5", "16", "CC", "1", "whole", "x" };
			assertArrayEquals(notes.get(0), notes1);
		} catch (Exception e) {
			fail();
		}
	}

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
		testKit.add("CC");
		testKit.add("HH");
		testKit.add("SD");
		testKit.add("HT");
		testKit.add("MT");
		testKit.add("BD");
		assertLinesMatch(drumReader.getDrumKit(), testKit);
		;
	}

	@Test
	void measureOutput() {
		try {
			List<String[]> notes = new ArrayList<String[]>();
			String output = "";
			String path = System.getProperty("user.dir") + "/drumTestTab.txt";
			TabReaderV2 tabReader = new TabReaderV2(path);
			tabReader.resetMeasure();// used to get drumkit
			tabReader.readMeasure();
			DrumReader drumReader = new DrumReader(tabReader.getMeasure());

			for (int m = 0; m < 2; m++) {
				tabReader.readMeasure();
				drumReader.setMeasure(tabReader.getMeasure());
				while (drumReader.hasNext()) {
					notes = drumReader.readNote();
					try {

						while (!notes.isEmpty()) {
							String[] notes1 = notes.get(0);
							for (int i = 0; i < notes1.length; i++) {
								output += notes1[i] + " ,";
							}
							output += "\n";
							notes.remove(0);
						}
					} catch (Exception e) {
						System.out.println(e.getMessage());
					}

				}
			}
			System.out.println(output);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Test
	void airTonightTest() {
		try {
			List<String[]> notes = new ArrayList<String[]>();
			String output = "";
			String path = System.getProperty("user.dir") + "/inTheAirTonight.txt";
			TabReaderV2 tabReader = new TabReaderV2(path);
			tabReader.resetMeasure();// used to get drumkit
			tabReader.readMeasure();
			DrumReader drumReader = new DrumReader(tabReader.getMeasure());

			System.out.println("NOTES");

			// goes through 3 measures
			for (int l = 0; l < 3; l++) {

				tabReader.readMeasure();
				drumReader.setMeasure(tabReader.getMeasure());
				while (drumReader.hasNext()) {
					notes = drumReader.readNote();
					try {
						if (!notes.isEmpty()) {
							while (!notes.isEmpty()) {
								String[] notes1 = notes.get(0);
								for (int i = 0; i < notes1.length; i++) {
									output += notes1[i] + " ,";
								}
								output += "\n";
								notes.remove(0);
							}
						}
					} catch (Exception e) {
						System.out.println("OUTPUT ERROR");
						System.out.println(e.getMessage());
					}

				}
			}
			System.out.println(output);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
