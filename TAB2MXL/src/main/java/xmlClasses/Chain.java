package xmlClasses;

import java.io.File;
import java.io.FileOutputStream;
import java.io.StringWriter;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import test.MeasureReaderV2;
import test.TabReaderV2;

public class Chain {
	File TAB;
	String TITLE;
	String NAME;
	String LYRICIST;
	String COMPOSER;
	String LOCATION;
	PartWriter PW = new PartWriter();
	
	public Chain(File TAB, String TITLE, String NAME, String LYRICIST, String COMPOSER,String LOCATION){
		this.TAB=TAB;
		this.TITLE=TITLE;
		this.NAME=NAME;
		this.LYRICIST=LYRICIST;
		this.COMPOSER=COMPOSER;
		this.LOCATION=LOCATION;
		TABtoPART();
		try {MARSHtoXML();} catch (Exception e) {e.printStackTrace();}
	}
	
	private void TABtoPART(){
		
		TabReaderV2 TRv2 = new TabReaderV2(TAB.toString());
		
		TRv2.readMeasure();
		while(TRv2.hasNext()) {
			MeasureReaderV2 MRv2 = new MeasureReaderV2(TRv2.getMeasure(), 4, 4);
			PW.nextMeasure();
			while(MRv2.hasNext()) {
				boolean firstNoteAdded = false;
				for(String[] s:MRv2.readNote()) {
					if(firstNoteAdded) {
						System.out.println(Integer.parseInt(s[0])+" "+s[1]+" "+s[2]+" "+Integer.parseInt(s[3]));
						PW.nextChordNote( Integer.parseInt(s[0]) , s[1], s[2], Integer.parseInt(s[3]) );
					}else {
						System.out.println(Integer.parseInt(s[0])+" "+s[1]+" "+s[2]+" "+Integer.parseInt(s[3]));
						PW.nextNote( Integer.parseInt(s[0]) , s[1], s[2], Integer.parseInt(s[3]) );
						firstNoteAdded = true;
					}
				}
			}
			TRv2.readMeasure();
		}
			
	}
	static String xmlString ;
	
	private void MARSHtoXML() throws Exception{  
	    JAXBContext contextObj = JAXBContext.newInstance(Score_Partwise.class);  
	  
	    Marshaller marshallerObj = contextObj.createMarshaller();  
	    marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	    
	    //gives a MusicXML DTD to XML document
	    marshallerObj.setProperty("com.sun.xml.internal.bind.xmlHeaders", "\n<!DOCTYPE score-partwise PUBLIC\n"
	    		+ " \"-//Recordare//DTD MusicXML 3.1 Partwise//EN\"\n"
	    		+ " \"http://www.musicxml.org/dtds/partwise.dtd\">");
	    
	    Score_Part sp = new Score_Part("P1", "Music");
	    
	    Part_List pl = new Part_List(sp);  
	    
	    ArrayList <Creator> creators = new ArrayList<Creator>();
	    creators.add(new Creator("composer", "Aidan Mozart")); 
	    creators.add(new Creator("lyricist", "Its ya boy")); 
	    Identification id = new Identification(creators);
	    		
	    Work w = new Work("Hot cross BUNS");
	    Score_Partwise spw = new Score_Partwise(3.1, pl, PW.getPart(), id, w);  
	    //marshallerObj.marshal(spw, new FileOutputStream(LOCATION+"ChainTest.xml"));
	    
	    StringWriter sw = new StringWriter(); 
	    marshallerObj.marshal(spw, sw);
	    System.out.println(sw.toString());
	    xmlString = sw.toString();
	
	}
	public String getText() {
	
	    return xmlString;
	}
}
