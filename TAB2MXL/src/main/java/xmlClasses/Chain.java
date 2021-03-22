package xmlClasses;

//Imports
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import test.DrumReader;
import test.MeasureReaderV3;
import test.TabReaderV2;
import test.TabReaderV3;
import test.TabReaderV4;

public class Chain {

	// ---VARIABLES---
	/** this File contains the user's tab to be parsed */
	String TAB;

	/** Contains the Title of the piece. Located top middle */
	String TITLE;

	/** Contains the Lyricist of the piece. Located top left */
	String LYRICIST;

	/** Contains the Title of the piece. Located top right */
	String COMPOSER;

	/** Contains the save location of the xml file. */
	String LOCATION;

	/**
	 * Contains the time signature as a 2 digit number. First digit being the beat.
	 * Second being the beat-type
	 */
	int TIMESIG;

	/** HARDCODED: C major - Contains the key of the song */
	String KEY = "CM";

	/** This PartWriter object builds and stores the Part object */
	PartWriter PW = new PartWriter();
	DrumPartWriter DPW = new DrumPartWriter();

	/**
	 * This ScorePartwiseWriter object builds and stores the ScorePartwise Object
	 */
	ScorePartwiseWriter SPW;

	/** This stores the Attributes for the Measures */
	Attributes ATT;

	/** Stores the list of exceptions during chain to give back to GUI */
	ArrayList<Exception> ERROR = new ArrayList<Exception>();

	/** HARDCODED for drums: 6 - represents number staff lines in the tab */
	int STAFFLINES = 6;

	/** HARDCODED: 2D String array - represents the tuning of the staff lines */
	String[][] TUNINGINFO = { new String[] { "", "2" }, new String[] { "", "2" }, new String[] { "", "3" },
			new String[] { "", "3" }, new String[] { "", "3" }, new String[] { "", "4" } };

	/** HARDCODED: TAB - represents the clef of the attribute */
	String CLEF;// = "TAB";

	/**
	 * HARDCODED: Divisions - Divisions works with duration to decide how many notes
	 * are in a measure(Derry knows)
	 */
	int DIVISIONS = 4;

	/** HARDCODED: Line - ????? */
	int LINE = 5;

	/** HARDCODED: Fifths - ????? */
	int FIFTHS = 0;

	/** This object is stored to send to the GUI */
	StringWriter SW = new StringWriter();

	/** This String shows the user instrument selection */
	String INSTRUMENT;

	/** HARDCODED: Voice - 1 */
	int VOICE = 1;

	/** This ArrayList shows the drum kit */
	ArrayList<String> DK;

	// ---CONSTRUCTORS---
	/**
	 * 
	 * @param TAB
	 * @param TITLE
	 * @param LYRICIST
	 * @param COMPOSER
	 * @param LOCATION
	 * @param TIMESIG
	 * @param KEY
	 */

	public Chain(String TAB, String TITLE, String LYRICIST, String COMPOSER, String LOCATION, int TIMESIG, String KEY,
			String INSTRUMENT, String CLEF) {

		// turning the string into a file so the v3 readers can have a File input type

		this.TAB = TAB;
		this.TITLE = TITLE;
		this.LYRICIST = LYRICIST;
		this.COMPOSER = COMPOSER;
		this.LOCATION = LOCATION;
		this.TIMESIG = TIMESIG;
		this.KEY = KEY;
		this.INSTRUMENT = INSTRUMENT;
		this.CLEF = CLEF;
	}

	// ---STEP 1---
	public void TABtoPART() throws Exception {

		if (INSTRUMENT.equals("Guitar")) {
			STAFFLINES = 6;
			TABtoPARTstringed();
		} else if (INSTRUMENT.equals("Bass")) {
			STAFFLINES = 4;
			TABtoPARTstringed();
		} else {
			STAFFLINES = 6;
			TABtoPARTdrum();
		}
	}

	private void TABtoPARTstringed() throws Exception {

		File fTAB = null;

		try {
			String path = System.getProperty("user.dir") + "/autosaveTab.txt";
			FileWriter myWriter = new FileWriter(path);
			myWriter.write(TAB);
			myWriter.close();
			fTAB = new File(path);
			System.out.println("Successfully wrote to the file.");
		} catch (IOException e) {
			System.out.println("An error occured in the Chain string to file maker.");
			e.printStackTrace();
			ERROR.add(e);
		}

		TabReaderV4 TRv4 = new TabReaderV4(fTAB, STAFFLINES);
		TRv4.readMeasure();

		// Making the Attributes
		// Sheet music lines
		int SMSL = STAFFLINES;
		if (!CLEF.equals("TAB")) {
			SMSL = 5;
		}

		AttributeWriter AW = new AttributeWriter(FIFTHS, DIVISIONS, TIMESIG / 10, TIMESIG % 10, CLEF, LINE, SMSL);

		String[] tuning = TRv4.getTuning();
		for (int i = 0; i < STAFFLINES; i++) {
			TUNINGINFO[5 - i][0] = tuning[i].toUpperCase();
		}

		AW.setTuning(TUNINGINFO);// get tuning data using TRv4.getTuning()
		ATT = AW.getAttributes();

		while (TRv4.hasNext()) {
			MeasureReaderV3 MRv3 = new MeasureReaderV3(TRv4.getMeasure(), TRv4.getTuning(), TIMESIG / 10, TIMESIG % 10);// 6
																														// -
																														// num
																														// of
																														// string,
																														// 4
																														// 4
																														// -
																														// time
																														// signature

			PW.nextMeasure(ATT);
			ATT = null;
			while (MRv3.hasNext()) {
				MRv3.readNotes();
				boolean firstNoteAdded = false;
				for (String[] s : MRv3.getNotes()) {
					System.out.println("Alter" + s[4] + "Accidental" + s[5]);

					if (firstNoteAdded) {
						if (s[4].equals("")) {
							PW.nextChordNote(Integer.parseInt(s[0]), s[1], s[2], Integer.parseInt(s[3]),
									Integer.parseInt(s[6]), Integer.parseInt(s[7]), VOICE);
						} else {
							PW.nextAlteredChordNote(Integer.parseInt(s[0]), s[1], s[2], Integer.parseInt(s[3]),
									Integer.parseInt(s[4]), Integer.parseInt(s[6]), Integer.parseInt(s[7]), VOICE);
						}
					} else {
						if (s[4].equals("")) {
							PW.nextNote(Integer.parseInt(s[0]), s[1], s[2], Integer.parseInt(s[3]),
									Integer.parseInt(s[6]), Integer.parseInt(s[7]), VOICE);
						} else {
							PW.nextAlteredNote(Integer.parseInt(s[0]), s[1], s[2], Integer.parseInt(s[3]),
									Integer.parseInt(s[4]), Integer.parseInt(s[6]), Integer.parseInt(s[7]), VOICE);
						}
						firstNoteAdded = true;
					}

				}
			}
			TRv4.readMeasure();

		}

	}

	private void TABtoPARTdrum() {
		System.out.println("DRUM DRUM DRUM");
		int gap = 0;
		File fTAB = null;

		try {
			String path = System.getProperty("user.dir") + "/testTab.txt";
			FileWriter myWriter = new FileWriter(path);
			myWriter.write(TAB);
			myWriter.close();
			fTAB = new File(path);
			System.out.println("Successfully wrote to the file.");
		} catch (IOException e) {
			System.out.println("An error occured in the Chain string to file maker.");
			e.printStackTrace();
			ERROR.add(e);
		}
		try {
			TabReaderV4 TRv4 = new TabReaderV4(fTAB, STAFFLINES, 0);
			System.out.println("USING TABV4 NOW !!!!!");

			AttributeWriter AW = new AttributeWriter(FIFTHS, DIVISIONS, TIMESIG / 10, TIMESIG % 10, "percussion", LINE,
					STAFFLINES);
			ATT = AW.getAttributes();

			TRv4.readMeasure();
			DrumReader DR = new DrumReader(TRv4.getMeasure());// assumed 4/4
			DK = DR.getDrumKit();// - needed scorepartwise
			TRv4.readMeasure();
			while (TRv4.hasNext()) {
				DPW.nextMeasure(ATT);
				ATT = null;
				DR.setMeasure(TRv4.getMeasure());
				while (DR.hasNextRow()) {

					for (String[] s : DR.readNoteRow()) {
						//Checks if there is a forward or a note
						if (s[0].equals("forward")) {
							//creates a forward with the DPW class
//							DPW.nextForward(Integer.parseInt(s[1]));
						} else {
							
							//Console Output for Testing
							System.out.println("Step" + s[0] + "Octave" + Integer.parseInt(s[1]) + "Duration"
									+ Integer.parseInt(s[2]) + "Intrument" + s[3] + "Voice" + s[4] + "Type" + s[5]
									+ "NoteHead" + s[6] + "Beam" + s[7]);

							// checks if this note has a beam
							if (!s[7].equals("")) {
								//list to hold beams of this note
								ArrayList<Beam> beams = new ArrayList<Beam>();

								// checks if this note is a sixteenth or not
								if (Integer.parseInt(s[2]) == 1) {
									// adds two beams if this note is a sixteenth
									Beam beam1 = new Beam(1, s[7]);
									Beam beam2 = new Beam(2, s[7]);
									beams.add(beam1);
									beams.add(beam2);

								} else {
									Beam beam1 = new Beam(1, s[7]);
									beams.add(beam1);
								}

								// Adds the bean note using the DPW class
								System.out.println("Beam Note Added");
								if (s[6].equals("o")) {
									DPW.nextDrumNoteB(Integer.parseInt(s[2]), s[5], s[0], Integer.parseInt(s[1]),
											Integer.parseInt(s[4]), s[3], "up", beams);
								} else {
									DPW.nextDrumNoteBNH(Integer.parseInt(s[2]), s[5], s[0], Integer.parseInt(s[1]),
											Integer.parseInt(s[4]), s[3], "up", beams, s[6]);
								}
							} else {
								// Adds regular note iwht the DPW class
								System.out.println("Non chorded note");
								if (s[6].equals("o")) {
									DPW.nextDrumNote(Integer.parseInt(s[2]), s[5], s[0], Integer.parseInt(s[1]),
											Integer.parseInt(s[4]), s[3], "up");
								} else {
									DPW.nextDrumNoteNH(Integer.parseInt(s[2]), s[5], s[0], Integer.parseInt(s[1]),
											Integer.parseInt(s[4]), s[3], "up", s[6]);
								}
							}
						}
					}
					//backup for next row
					DPW.nextBackup(16);
				}
				TRv4.readMeasure();
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	// ---STEP 2---
	public void INFOtoPARTWISE() {
		if (INSTRUMENT.equals("Guitar") || INSTRUMENT.equals("Bass")) {
			SPW = new ScorePartwiseWriter(TITLE, LYRICIST, COMPOSER, PW.getPart());
		} else {
			SPW = new ScorePartwiseWriter(TITLE, LYRICIST, COMPOSER, DPW.getDrumPart(), DK);
		}

	}

	// ---STEP 3---
	public void MARSHtoXML() throws Exception {
		JAXBContext contextObj = JAXBContext.newInstance(Score_Partwise.class);

		Marshaller marshallerObj = contextObj.createMarshaller();
		marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

		// gives a MusicXML DTD to XML document
		/*
		 * marshallerObj.setProperty("com.sun.xml.internal.bind.xmlHeaders",
		 * "\n<!DOCTYPE score-partwise PUBLIC\n" +
		 * " \"-//Recordare//DTD MusicXML 3.1 Partwise//EN\"\n" +
		 * " \"http://www.musicxml.org/dtds/partwise.dtd\">");
		 */

		// marshallerObj.marshal(spw, new FileOutputStream(LOCATION+"ChainTest.xml"));

		marshallerObj.marshal(SPW.getScore_Partwise(), SW);
		System.out.println(SW.toString());

	}

	// ---ACCESSORS---
	public String getXML() {
		return SW.toString();
	}

	public ArrayList<Exception> getError() {
		return ERROR;
	}

	// JUnit Test Methods
	public String getTab() {
		return TAB;
	}

	public String getTitle() {
		return TITLE;
	}

	public String getComposer() {
		return COMPOSER;
	}

	public String getLyricist() {
		return LYRICIST;
	}

	public int getStaffLines() {
		return STAFFLINES;
	}

	public void setInst(String inst) {
		this.INSTRUMENT = inst;
	}
}
