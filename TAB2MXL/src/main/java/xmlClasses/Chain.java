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
	String[][] TUNINGINFO = {
			new String[] {"","2"},
			new String[] {"","2"},
			new String[] {"","3"},
			new String[] {"","3"},
			new String[] {"","3"},
			new String[] {"","4"}
	};
	
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
			int[] TIMESIGS, String KEY, String INSTRUMENT, String CLEF){

		this.TAB=TAB;
		this.TITLE=TITLE;
		this.LYRICIST=LYRICIST;
		this.COMPOSER=COMPOSER;
		this.TIMESIGS=TIMESIGS;
		this.KEY=KEY;
		this.INSTRUMENT=INSTRUMENT;
		this.CLEF=CLEF;
	}

//####################################################################
//###################### 3 STEPS TO CONVERT ##########################
//####################################################################
	
	//---STEP 1a - Parser Selection ---
	
	public void TABtoPART() throws Exception{
		
		//Stringed -> Step 1b
		if (INSTRUMENT.equals("Guitar")) { TABtoPARTstringed(6); }
		else if(INSTRUMENT.equals("Bass")) { TABtoPARTstringed(4); }
		
		//Drum -> Step 1c
		else { TABtoPARTdrum(); }
	}
	
	//---STEP 1b - Stringed Parser ---
	
	private void TABtoPARTstringed( int STAFFLINES ) throws Exception{
		
		//Check for Sheet Music
		int VISIBLELINES = STAFFLINES;
		if(!CLEF.equals("TAB")) {
			VISIBLELINES = 5;
		}
		
		//Create AttributeWriter
		AW = new AttributeWriter(	FIFTHS, DIVISIONS, TIMESIG/10,
									TIMESIG%10, CLEF, LINE, VISIBLELINES);
		
		//Create TabReader
		TabReaderV4 TRv4 = new TabReaderV4(stringToFile(TAB), STAFFLINES);

		//Extract and pass tuning information
		String[] tuning = TRv4.getTuning();
		for(int i = 0; i < STAFFLINES; i++) {
			TUNINGINFO[5-i][0] = tuning[i].toUpperCase();
		}
		AW.setTuning(TUNINGINFO);
		Attributes ATT = AW.getAttributes();
		
		//String Note Parsing
		TRv4.readMeasure();
		while(TRv4.hasNext()) {
			MeasureReaderV4 MRv4 = new MeasureReaderV4(TRv4.getMeasure(), TRv4.getTuning(), TIMESIG/10, TIMESIG%10);
			PW.nextMeasure( ATT );//adds an empty measure
			ATT=null;//removes all attributes after the first measrue
			while(MRv4.hasNext()) {
				MRv4.readNotes();
				String ChordNote = null;//makes notes chorded when they are not the first one
				for(String[] s:MRv4.getNotes()) {
					PW.nextAllNote( Integer.parseInt(s[0]), //duration
									s[1],					//type
									s[2],					//step
									Integer.parseInt(s[3]),	//octave
									Integer.parseInt(s[4]),	//alter
									Integer.parseInt(s[6]),	//string
									Integer.parseInt(s[7]),	//fret
									VOICE,					//voice
									ChordNote,				//chord
									GRACE					//grace
									);
					ChordNote = "";
				}
			}
			
			//inside while( TRv4.hasNext() )
			TRv4.readMeasure();
		}
		//HARDCODED
		PW.getPart().getMeasure().get(PW.getPart().getMeasure().size()-1).setBarline(new Barline("right", "light-heavy")); 
	}
	
	//---STEP 1c - Drum Parser --- TO BE CLEANED
	
	private void TABtoPARTdrum(){
		System.out.println("DRUM DRUM DRUM");
		
		TabReaderV2 TRv2 = new TabReaderV2(stringToFile(TAB).toString());
		
		
		
		AttributeWriter AW = new AttributeWriter(FIFTHS, DIVISIONS, TIMESIG/10, TIMESIG%10, "percussion", LINE, STAFFLINES);
		AW.setTuning(TUNINGINFO);//use derry tuning info
		Attributes ATT = AW.getAttributes();
		
		TRv2.resetMeasure();
		TRv2.readMeasure();
		DrumReader DR = new DrumReader(TRv2.getMeasure());//assumed 4/4
		DK = DR.getDrumKit();// - needed scorepartwise
		while(TRv2.hasNext()) {
			System.out.println("I'M RIGHT HERE");
			DPW.nextMeasure(ATT);
			ATT=null;
			TRv2.readMeasure();
            DR.setMeasure(TRv2.getMeasure());
			while(DR.hasNext()) {
				
				boolean firstNoteAdded = false;
				for(String[] s:DR.readNote()) {
					System.out.println("Step"+s[0]+
										"Octave"+Integer.parseInt(s[1])+
										"Duration"+Integer.parseInt(s[2])+
										"Intrument"+s[3]+
										"Voice"+s[4]+
										"Type"+s[5]+
										"NoteHead"+s[6]);
					if(firstNoteAdded) {
						System.out.println("Chorded note");
//						if(s[6].equals("o")) {
//							System.out.println("Make a note chord without note head");
//						}
//						else {
						DPW.nextDrumNoteChord(Integer.parseInt(s[2]), s[5], s[0], Integer.parseInt(s[1]), Integer.parseInt(s[4]), s[3], "up", s[6]);
						//}
						
					}
					else {
						System.out.println("Non chorded note");
						if(s[6].equals("o")) {
							DPW.nextDrumNote(Integer.parseInt(s[2]), s[5], s[0], Integer.parseInt(s[1]), Integer.parseInt(s[4]), s[3], "up");
						}
						else {
							DPW.nextDrumNoteNH(Integer.parseInt(s[2]), s[5], s[0], Integer.parseInt(s[1]), Integer.parseInt(s[4]), s[3], "up", s[6]);
						}
						firstNoteAdded = true;
					}
					
				}
			}
			
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
			SPW = new ScorePartwiseWriter(TITLE, LYRICIST, COMPOSER, DPW.getDrumPart(), DK);
		}
		
	}
	
	//---STEP 3 - Compiling XML ---
	public void MARSHtoXML() throws Exception{  
	    
		//Marshalling
		JAXBContext contextObj = JAXBContext.newInstance(Score_Partwise.class); 
		
	    Marshaller marshallerObj = contextObj.createMarshaller();  
	    
	    marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	    marshallerObj.marshal(SPW.getScore_Partwise(), SW);
	    
	    //Print final output to console
	    System.out.println(SW.toString());
	
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
