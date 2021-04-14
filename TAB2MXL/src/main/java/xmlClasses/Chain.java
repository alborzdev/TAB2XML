package xmlClasses;

//############################ IMPORTS ##############################
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import test.DrumReader;
import test.MeasureReaderV3;
import test.MeasureReaderV4;
import test.MeasureReaderV5;
import test.TabReaderV2;
import test.TabReaderV4;

public class Chain {
	
//####################################################################
//########################### VARIABLES ##############################
//####################################################################

	//---User given parameters---

	/**The user's tab given in the constructor*/
	String TAB;
	
	/**The Title of the piece
	 * Located: Top Middle*/
	String TITLE;
	
	/**The Lyricist of the piece
	 * Located: Top Left*/
	String LYRICIST;
	
	/**The Composer of the piece
	 * Located: Top Right*/
	String COMPOSER;
	
	/**The time signature as a 2 digit number.
	 * First digit being the beat.
	 * Second being the beat-type*/
	int TIMESIG;
	int[] TIMESIGS;
	
	/**The key of the song*/
	String KEY;
	
	/**This String shows the user instrument selection*/
	String INSTRUMENT;
	
	/**The clef of the TAB*/
	String CLEF;
	
	//---Formatting information---
	
	/**Number of staff lines in the tab*/
	int STAFFLINES;
	
	/**HARDCODED: 2D String array - represents the tuning octaves of the staff lines*/
	String[][] TUNINGINFO;
	
	/**HARDCODED: Divisions - Divisions works with duration to decide how many notes are in a measure(Derry knows)*/
	int DIVISIONS = 4;
	
	/**HARDCODED: Line - ?????*/
	int LINE = 5;
	
	/**HARDCODED: Fifths - ?????*/
	int FIFTHS = 0;
	
	/**HARDCODED: Voice - 1*/
	int VOICE = 1;
	
	/**HARDCODED: Grace - null*/
	String GRACE = null;
	
	/**This ArrayList shows the drum kit*/
	ArrayList<String> DK;

	//---xml Object Writers---
	
	/**Builds and stores the ScorePartwise Object*/
	ScorePartwiseWriter SPW;
	
	/**Builds and stores the Part object for guitar or bass*/
	PartWriter PW = new PartWriter();
	
	/**Builds and stores the Part object for drums*/	
	DrumPartWriter DPW = new DrumPartWriter();
	
	/**This object is stored to be sent to the GUI*/
	StringWriter SW = new StringWriter(); 	
	
	/**This builds and stores the AttributeWriter*/
	AttributeWriter AW;

//####################################################################
//######################## CONSTRUCTORS ##############################
//####################################################################
	
	public Chain(	String TAB, String TITLE, String LYRICIST, String COMPOSER,
					int TIMESIG, String KEY, String INSTRUMENT, String CLEF){
		
		this.TAB=TAB;
		this.TITLE=TITLE;
		this.LYRICIST=LYRICIST;
		this.COMPOSER=COMPOSER;
		this.TIMESIG=TIMESIG;
		this.KEY=KEY;
		this.INSTRUMENT=INSTRUMENT;
		this.CLEF=CLEF;
	}
	
	public Chain(	String TAB, String TITLE, String LYRICIST, String COMPOSER,
			int[] TIMESIGS, String KEY, String INSTRUMENT, String CLEF, int STAFFLINES){

		this.TAB=TAB;
		this.TITLE=TITLE;
		this.LYRICIST=LYRICIST;
		this.COMPOSER=COMPOSER;
		this.TIMESIGS=TIMESIGS;
		this.KEY=KEY;
		this.INSTRUMENT=INSTRUMENT;
		this.CLEF=CLEF;
		this.STAFFLINES=STAFFLINES;
	}

//####################################################################
//###################### 3 STEPS TO CONVERT ##########################
//####################################################################
	
	//---STEP 1a - Parser Selection ---
	
	public void TABtoPART() throws Exception{

		if (INSTRUMENT.equals("Guitar")) { TABtoPARTstringed(); }
		else if(INSTRUMENT.equals("Bass")) { TABtoPARTstringed(); }
		
		//Drum -> Step 1c
		else { TABtoPARTdrum(); }
	}
	
	//---STEP 1b - Stringed Parser ---
	
	private void TABtoPARTstringed() throws Exception{
		
		//Check for Sheet Music
		int VISIBLELINES = STAFFLINES%10;
		if(!CLEF.equals("TAB")) {
			VISIBLELINES = 5;
		}
		
		//Create AttributeWriter
		AW = new AttributeWriter(	FIFTHS, DIVISIONS, TIMESIGS[0]/10,
				TIMESIGS[0]%10, CLEF, LINE, VISIBLELINES);
		
		
		//TUNING
		//----------------------------------------------------------------------
		TabReaderV4 Ttuning = new TabReaderV4(stringToFile(TAB), STAFFLINES%10);
		Ttuning.readMeasure();
		MeasureReaderV4 Mtuning = new MeasureReaderV4(Ttuning.getMeasure(), Ttuning.getTuning(), 4, 4);
		
		String[] StringTuning = Ttuning.getTuning();//string tunings
		int[] OctaveTuning = Mtuning.getTuning();//octave tunings
		
		System.out.println("!!!!!!!!!!!!!!!!");
		TUNINGINFO = new String[STAFFLINES%10][2];
		
		for(int i = 0; i < STAFFLINES%10; i++) {
			TUNINGINFO[STAFFLINES%10-1-i][0] = StringTuning[i].toUpperCase();
			TUNINGINFO[STAFFLINES%10-1-i][1] = OctaveTuning[i]+"";
			System.out.println(TUNINGINFO[STAFFLINES%10-1-i][0]+" "+TUNINGINFO[STAFFLINES%10-1-i][1]);
		}
		
		
		AW.setTuning(TUNINGINFO);
		Attributes ATT = AW.getAttributes();
		//----------------------------------------------------------------------
		
		//Create TabReader
		TabReaderV4 TRv4 = new TabReaderV4(stringToFile(TAB), STAFFLINES%10);
		
		//Creating current measure marker
		int marker = 0;
		
		//String Note Parsing
		TRv4.readMeasure();
		while(TRv4.hasNext()) {
			MeasureReaderV5 MRv5 = new MeasureReaderV5(TRv4.getMeasure(), TRv4.getTuning(), TIMESIGS[marker]/10, TIMESIGS[marker]%10);
			System.out.println(marker+" measure is "+TIMESIGS[marker]+"@@@@");
			if (marker>0) {
				if(TIMESIGS[marker]!=TIMESIGS[marker-1]) {
					
					ATT = new AttributeWriter( FIFTHS, DIVISIONS, TIMESIGS[marker]/10,
							TIMESIGS[marker]%10, null, LINE, VISIBLELINES).getAttributes();
				}
			}
			PW.nextMeasure( ATT );//adds an empty measure
			ATT=null;//removes all attributes after the first measure
			while(MRv5.hasNext()) {
				MRv5.readNotes();
				String ChordNote = null;//makes notes chorded when they are not the first one
				for(String[] s:MRv5.getNotes()) {
					PW.nextAllNote( Integer.parseInt(s[0]), //duration
									s[1],					//type
									s[2],					//step
									Integer.parseInt(s[3]),	//octave
									Integer.parseInt(s[4]),	//alter
									Integer.parseInt(s[6]),	//string
									Integer.parseInt(s[7]),	//fret
									Integer.parseInt(s[8]),	//hNum
									s[9],					//hType
									"H",					//hCharacter
									Integer.parseInt(s[10]),//sNum
									"above",				//sPlacement
									s[11],					//sType
									Integer.parseInt(s[12]),//pNum
									s[13],					//pType
									"P",					//pCharacter
									VOICE,					//voice
									ChordNote,				//chord
									s[14]					//grace
									);
					ChordNote = "";
				}
				
				MRv5.getRepeatStatus();
				
				MRv5.getRepeatCount();
			}
			
			//inside while( TRv4.hasNext() )
			TRv4.readMeasure();
			marker++;
		}
		//HARDCODED
		PW.getPart().getMeasure().get(PW.getPart().getMeasure().size()-1).setBarline(new Barline("right", "light-heavy")); 
	}
	
	//---STEP 1c - Drum Parser --- TO BE CLEANED
	
	private void TABtoPARTdrum() {
		System.out.println("DRUM DRUM DRUM");
		int gap = 0;
		ArrayList<String[]> bassLine = new ArrayList<String[]>();
		
		try {
			TabReaderV4 TRv4 = new TabReaderV4(stringToFile(TAB), 6, 0);
			System.out.println("USING TABV4 NOW !!!!!");
			AW = new AttributeWriter(FIFTHS, DIVISIONS, TIMESIG / 10, TIMESIG % 10, "percussion", LINE, 5);
			Attributes ATT = AW.getAttributes();

			TRv4.readMeasure();
			DrumReader DR = new DrumReader(TRv4.getMeasure());// assumed 4/4
			DK = DR.getDrumKit();// - needed scorepartwise
			TRv4.readMeasure();
			while (TRv4.hasNext()) {
				DPW.nextMeasure(ATT);
				ATT = null;
				DR.setMeasure(TRv4.getMeasure());
				System.out.println("Set New measure");
				while(DR.hasNext()) {
					boolean first = true;
					for(String [] s: DR.readNotes()) {	
						
						//saves bass notes for later
						if(!s[3].equals("P1-I36")) {
	//						Console Output for Testing
							System.out.println("Step" + s[0] + "Octave" + Integer.parseInt(s[1]) + "Duration"
									+ Integer.parseInt(s[2]) + "Intrument" + s[3] + "Voice" + s[4] + "Type" + s[5]
									+ "NoteHead" + s[6] + "Beam" + s[7]);
	
							// checks if this note has a beam
							if(first) {
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
												Integer.parseInt(s[4]), s[3], s[8], beams);
									} else {
										DPW.nextDrumNoteBNH(Integer.parseInt(s[2]), s[5], s[0], Integer.parseInt(s[1]),
												Integer.parseInt(s[4]), s[3], s[8], beams, s[6]);
									}
								} else {
									// Adds regular note iwht the DPW class
									System.out.println("Non beam note");
									if (s[6].equals("o")) {
										DPW.nextDrumNote(Integer.parseInt(s[2]), s[5], s[0], Integer.parseInt(s[1]),
												Integer.parseInt(s[4]), s[3], s[8]);
									} else {
										DPW.nextDrumNoteNH(Integer.parseInt(s[2]), s[5], s[0], Integer.parseInt(s[1]),
												Integer.parseInt(s[4]), s[3], s[8], s[6]);
									}
								}
								first = false;
							}else {
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
										DPW.nextDrumNoteBChord(Integer.parseInt(s[2]), s[5], s[0], Integer.parseInt(s[1]),
												Integer.parseInt(s[4]), s[3], s[8], beams);
									} else {
										DPW.nextDrumNoteBNHChord(Integer.parseInt(s[2]), s[5], s[0], Integer.parseInt(s[1]),
												Integer.parseInt(s[4]), s[3], s[8], beams, s[6]);
									}
								} else {
									// Adds regular note with the DPW class
									System.out.println("Non beam note");
									if (s[6].equals("o")) {
										DPW.nextDrumNoteChord(Integer.parseInt(s[2]), s[5], s[0], Integer.parseInt(s[1]),
												Integer.parseInt(s[4]), s[3], s[8]);
									} else {
										DPW.nextDrumNoteNHChord(Integer.parseInt(s[2]), s[5], s[0], Integer.parseInt(s[1]),
												Integer.parseInt(s[4]), s[3], s[8], s[6]);
									}
								}
							}
						}else {
							bassLine.add(s);
						}
					}
				}
				
				//add all bass notes at the end
				//goes back to beginning of the measure
				DPW.nextBackup(16);
				
				for(String [] s: bassLine) {
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
									Integer.parseInt(s[4]), s[3], s[8], beams);
						} else {
							DPW.nextDrumNoteBNH(Integer.parseInt(s[2]), s[5], s[0], Integer.parseInt(s[1]),
									Integer.parseInt(s[4]), s[3], s[8], beams, s[6]);
						}
					} else {
						// Adds regular note with the DPW class
						System.out.println("Non beam note");
						if (s[6].equals("o")) {
							DPW.nextDrumNote(Integer.parseInt(s[2]), s[5], s[0], Integer.parseInt(s[1]),
									Integer.parseInt(s[4]), s[3], s[8]);
						} else {
							DPW.nextDrumNoteNH(Integer.parseInt(s[2]), s[5], s[0], Integer.parseInt(s[1]),
									Integer.parseInt(s[4]), s[3], s[8], s[6]);
						}
					}
				}
				
				//clear bassLine
				bassLine.clear();
				TRv4.readMeasure();
			}
			DPW.getDrumPart().getMeasure().get(DPW.getDrumPart().getMeasure().size()-1).setBarline(new Barline("right", "light-heavy"));
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

	}
	
	//---STEP 2 - Passing Parsing ---
	public void INFOtoPARTWISE() {
		
		//Stringed
		if(INSTRUMENT.equals("Guitar")||INSTRUMENT.equals("Bass")) {
			SPW = new ScorePartwiseWriter(TITLE, LYRICIST, COMPOSER, PW.getPart());
		}
		
		//Drums
		else {
			try {
			SPW = new ScorePartwiseWriter(TITLE, LYRICIST, COMPOSER, DPW.getDrumPart(), DK);
			}catch(Exception e) {
				System.out.println(e.getMessage());
				System.out.println("SCOREPARTWISEWRITER ERROR");
			}
		}
		
	}
	
	//---STEP 3 - Compiling XML ---
	public void MARSHtoXML() throws Exception{  
	    
		//Marshalling
		try {
			
		JAXBContext contextObj = JAXBContext.newInstance(Score_Partwise.class, Instrument.class, Unpitched.class, Entry.class, DrumNoteNH.class, DrumNoteB.class, DrumNote.class, DrumNoteBNH.class, Note.class, Forward.class, Backup.class, DrumNoteNHChord.class, DrumNoteBChord.class, DrumNoteChord.class, DrumNoteBNHChord.class); 
	    Marshaller marshallerObj = contextObj.createMarshaller(); 
	    System.out.println("test 1");
	    //adapter used to control measure marshaling
	    EntryAdapter adapter = new EntryAdapter(contextObj);
	     
	    marshallerObj.setAdapter(adapter);
	    marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	    marshallerObj.setProperty("com.sun.xml.bind.xmlHeaders", "<!DOCTYPE score-partwise PUBLIC \"-//Recordare//DTD MusicXML 3.1 Partwise//EN\" \"http://www.musicxml.org/dtds/partwise.dtd\">");

	    
	    System.out.println("SPW: " + SPW.getScore_Partwise().getPart().getMeasure().get(0).getNote().get(0).getName());
	    marshallerObj.marshal(SPW.getScore_Partwise(), SW);
	    //Print final output to console
	    System.out.println(SW.toString());
	
		}catch(Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}


//####################################################################
//########################### HELPERS ################################
//####################################################################
	public static File stringToFile(String tab) {

		File f = null;
		
		try {
			String path = System.getProperty("user.dir") + "/autosaveTab.txt";
			FileWriter myWriter = new FileWriter(path);
			myWriter.write(tab);
			myWriter.close();
			f=new File(path);
			System.out.println("Successfully wrote to the file.");
		}
		
		catch (IOException e) {
			System.out.println("An error occured in the stringToFile method in chain");
			e.printStackTrace();
		}
		
		return f;
	}
	
//#################### MUTATORS and GETTERS ##########################	
	
	//---Pulling XML---
	public String getXML() { return SW.toString(); }
	
	//---JUnit Test Methods---
	public String getTab() { return TAB; }
	public String getTitle() { return TITLE; }
	public String getComposer() { return COMPOSER; }
	public String getLyricist() { return LYRICIST; }
	public String[][] getTuning() { return TUNINGINFO; }
	public int getStaffLines() { return STAFFLINES; }
	public void setInst(String inst) { this.INSTRUMENT = inst; }
	
}
