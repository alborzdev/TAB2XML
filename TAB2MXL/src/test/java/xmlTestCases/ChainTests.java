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

public class ChainTests {
	
	private Chain chain;
	private int randomfret;
	private String TAB;
	
	@BeforeEach
    public void setUp() throws Exception {
		int TIMESIGS[]= {44};
        Random r = new Random();
        randomfret=r.nextInt(10);
        
        TAB = "e|-----------------|\n"+
        "B|-----------------|\n"+
        "G|-----------------|\n"+
        "D|-----------------|\n"+
        "A|-----------------|\n"+
        "E|-"+randomfret+"---------------|";
        chain = new Chain(TAB,"First Song", "Queen B", "Ludwig van Beethoven", TIMESIGS,"C major","Guitar","TAB", 6);
    }

    @Test
    public void testChainConstructor() {
    	assertEquals(TAB, chain.getTab());
    	assertEquals("First Song", chain.getTitle());
    }
    
    @Test
    public void testChainT2P() throws Exception {
    	chain.TABtoPART();
    	
    	assertEquals("E", chain.getTuning()[0][0]);	
    }
    
    @Test
    public void testLyricist() {
    	assertEquals(chain.getLyricist(), "Queen B");
    }
    
    @Test
    public void testComposer() {
    	assertEquals(chain.getComposer(), "Ludwig van Beethoven");
    }
    
    @Test
    public void testStaffLines() {
    	assertEquals(chain.getStaffLines(), 6);
    }
}
