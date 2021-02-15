package xmlTestCases;

import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import xmlClasses.Note;
import xmlClasses.Part;
import xmlClasses.Pitch;

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
    
}
