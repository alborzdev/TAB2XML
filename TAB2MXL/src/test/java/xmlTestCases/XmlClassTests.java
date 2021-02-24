package xmlTestCases;


import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import xmlClasses.Attributes;
import xmlClasses.Clef;
import xmlClasses.Instrument;
import xmlClasses.Key;
import xmlClasses.Note;
import xmlClasses.Part;
import xmlClasses.Pitch;
import xmlClasses.ScorePartwiseWriter;
import xmlClasses.Time;

public class XmlClassTests {
	
	private Pitch p;
	private Note n;
	private Part part;
	private Part part2;
	
	@BeforeEach
    public void setUp() throws Exception {
        p = new Pitch("C", 4);
        n = new Note(4, "quarter", p);
        part = new Part();
        part2 = new Part("1");
        
    }

    @Test
    public void testChangeStep() {
        assertEquals("C",p.getStep());
        p.setStep("G");
        assertEquals("G",p.getStep());
    }

    @Test
    public void testNote() {
        assertEquals("quarter", n.getType());
        assertEquals(4,n.getDuration());
        assertEquals(p,n.getPitch());
    }
    
    @Test
    public void testNewNote() {
        assertEquals("quarter", n.getType());
        assertEquals(4,n.getDuration());
        assertEquals(p,n.getPitch());
    }
    
    @Test
    public void testPartConstructor() {
        assertNotSame(part, part2);
    }
    
    @Test
    public void testMeasure() {
    	//int number, Attributes att, ArrayList<Note> note
    	//Measure m = new Measure(1,)
    }
    
    /**
     * 
     */
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
    public void testSPW() {
    	ScorePartwiseWriter SPW = new ScorePartwiseWriter("Title", "LYRICIST" , "COMPOSER", part);
    	ScorePartwiseWriter SPW2 = new ScorePartwiseWriter("Title", "LYRICIST" , "COMPOSER", part);
    	assertNotNull(SPW2);
    }
}
