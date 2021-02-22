package xmlClasses;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;  
  
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;  
  
//Testing Change
public class ObjectToMxl {  
	static String xmlString = new String();

public static void main(String[] args) throws Exception {
	mxlMaker();
	System.out.println("Testing merge");
	
}

public static String mxlMaker() throws Exception{  
    JAXBContext contextObj = JAXBContext.newInstance(Score_Partwise.class);  
  
    Marshaller marshallerObj = contextObj.createMarshaller();  
    marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
    
    //gives a MusicXML DTD to XML document
    
    Score_Part sp = new Score_Part("P1", "Music");
    
    Part_List pl = new Part_List(sp);  
    
    Key k = new Key(0);
    Time t = new Time(4, 4);
    Clef c = new Clef("G", 2);
    Attributes att = new Attributes(1, k, t, c);
    
    Pitch pi = new Pitch("C", 4);
    ArrayList <Note> notes = new ArrayList <Note>();
    notes.add(new Note(4, "whole", pi));
    notes.add(new Note("whole", pi));
    
    Measure m = new Measure(1, att, notes);
    ArrayList <Measure> measures = new ArrayList<Measure>();
    measures.add(m);
    Part p = new Part("P1", measures);
    
    ArrayList <Creator> creators = new ArrayList<Creator>();
    creators.add(new Creator("composer", "Aidan Mozart")); 
    creators.add(new Creator("lyricist", "Its ya boy")); 
    Identification id = new Identification(creators);
    		
    Work w = new Work("Hot cross BUNS");
    Score_Partwise spw = new Score_Partwise(3.1, pl, p, id, w);  
    marshallerObj.marshal(spw, new FileOutputStream("musicTest3.xml"));  
    
    StringWriter sw = new StringWriter(); 
    marshallerObj.marshal(spw, sw);
    xmlString = sw.toString();
    return xmlString;
	}  

	public static String getText() throws IOException {
		
  return xmlString;
	}
}  