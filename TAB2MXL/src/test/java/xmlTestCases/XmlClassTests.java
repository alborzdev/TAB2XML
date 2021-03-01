package xmlTestCases;


import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import xmlClasses.Attributes;
import xmlClasses.Chain;
import xmlClasses.Clef;
import xmlClasses.Instrument;
import xmlClasses.Key;
import xmlClasses.Measure;
import xmlClasses.Notations;
import xmlClasses.Note;
import xmlClasses.Part;
import xmlClasses.PartWriter;
import xmlClasses.Pitch;
import xmlClasses.ScorePartwiseWriter;
import xmlClasses.StaffDetails;
import xmlClasses.StaffTuning;
import xmlClasses.Technical;
import xmlClasses.Time;

public class XmlClassTests {
	
	private Pitch p;
	private Note n;
	private Note n2;
	private Notations nots;
	private int voice;
	private Part part;
	private Part part2;
	private Measure measure;
	private Measure measure2;
	private StaffDetails sd;
	
	private Chain chain;
	private int randomfret;
	private String TAB;
	
	@BeforeEach
    public void setUp() throws Exception {
        p = new Pitch("C", 4);
        n = new Note(4, "quarter", p);
        nots = new Notations(new Technical(1,1));
        n2 = new Note(4, "quarter", p, nots, voice);
        part = new Part();
        part2 = new Part("1");
        
        Random r = new Random();
        randomfret=r.nextInt(10);
        
        TAB = "e|-----------------|\n"+
        "B|-----------------|\n"+
        "G|-----------------|\n"+
        "D|-----------------|\n"+
        "A|-----------------|\n"+
        "E|-"+randomfret+"---------------|";
        
        chain = new Chain(TAB,"First Song", "Queen B", "Ludwig van Beethoven", "savingLocation.txt",44,"C major","Guitar","TAB");
        
    }

    @Test
    public void testChangeStep() {
        assertEquals("C",p.getStep());
        p.setStep("G");
        assertEquals("G",p.getStep());
    }

    @Test //Test to make sure note object contains correct attributes
    public void testNote() {
        assertEquals("quarter", n.getType());
        assertEquals(4,n.getDuration());
        assertEquals(p,n.getPitch());
    }
	@Test 
	/**
	 * Testing second constructor for note with Notations and technical attributes
	 */
    public void testNote2() {
        assertEquals(nots, n2.getNotations());
        assertEquals(1, n2.getNotations().getTech().getFret());
        assertEquals(1, n2.getNotations().getTech().getString());
        //changing fret value
        n2.getNotations().getTech().setFret(2); 
        //checking to see if change occurred
        assertEquals(2, n2.getNotations().getTech().getFret());
        
        //changing string value
        n2.getNotations().getTech().setString(2); 
        //checking to see if change occurred
        assertEquals(2, n2.getNotations().getTech().getString());
    }
    
    @Test
    public void testPartConstructor() {
        assertNotSame(part, part2);
    }
    
    @Test
    /**
     * Testing a creation of multiple measures, comparing them
     * as well as their attributes
     */
    public void testMeasure() {
    	measure = new Measure();
    	assertNull(measure.getNote());
    	assertEquals(0, measure.getNumber());
    	assertNull(measure.getAttributes());
    	measure.setNumber(1);
    	assertTrue(measure.getNumber() == 1);
    	assertNotNull(measure.getNumber());
    	
    	Measure measureTwo = new Measure(1, new Attributes(), new ArrayList<Note>());
    	assertNull(measureTwo.getAttributes().getClef());
    	assertNotNull(measureTwo.getNote());
    	measureTwo.getNote().add(new Note());
    	measureTwo.getNote().get(0).setDuration(1);
    	assertEquals(1, measureTwo.getNote().get(0).getDuration());
    	
    	assertNotEquals(measure, measure2);
    	
    	//int number, Attributes att, ArrayList<Note> note
    	//Measure m = new Measure(1,)
    }
    @Test
    /**
     * Testing the creation of staff details and staff tuning
     * Making sure set and get methods work properly
     */
    public void testStaffDetails() {
    	StaffTuning st = new StaffTuning();
    	assertNull(st.getTuningStep());
    	st.setTuningStep("E");
    	assertEquals("E", st.getTuningStep());
    	sd = new StaffDetails(6, new ArrayList<StaffTuning>());
    	assertEquals(6, sd.getStafflines());
    	
    	sd.getStaffTuning().add(st);
    	assertEquals("E", sd.getStaffTuning().get(0).getTuningStep());
    	
    	
    }
    @Test
    public void testInstrument() {
    	Instrument ins = new Instrument("Guitar");
    	Instrument ins2 = new Instrument("Drum");
    	Instrument ins3 = new Instrument("Bass");
    	assertEquals("Guitar",ins.getId());
    	assertEquals("Drum",ins2.getId());
    	assertEquals("Bass",ins3.getId());
    }
    
    @Test
    /**
     * Testing the score partwise writer
     * ensures that all arguments are properly set in a ScorePartwise
     */
    public void testSPW() {
    	ScorePartwiseWriter SPW = new ScorePartwiseWriter("Title", "LYRICIST" , "COMPOSER", part);
    	ScorePartwiseWriter SPW2 = new ScorePartwiseWriter("Title", "LYRICIST" , "COMPOSER", part);
    	assertNotEquals(SPW, SPW2);
    	
    	assertEquals("Title", SPW.getScore_Partwise().getWork().getWorkTitle());
    	assertEquals("LYRICIST", SPW.getScore_Partwise().getId().getCreator(1).getName());
    	assertEquals("COMPOSER", SPW.getScore_Partwise().getId().getCreator(0).getName());
    	SPW.getScore_Partwise().setPart(part2);
    	assertNotEquals(part, SPW.getScore_Partwise().getPart());
    	assertNotEquals(SPW, SPW2);
    }
    
    @Test
    /**
     * Testing the score partwise writer
     * ensures that all arguments are properly set in a ScorePartwise
     */
    public void testPW() {
    	PartWriter PW = new PartWriter();
    	PartWriter PW2 = new PartWriter();
    	assertNotEquals(PW, PW2);
    	
    	PW.getPart().setId("test");
    	assertEquals("test", PW.getPart().getID());
    	assertNotEquals(PW.getPart().getID(), PW2.getPart().getID());
    	
    	PW.nextMeasure();
    }
}
