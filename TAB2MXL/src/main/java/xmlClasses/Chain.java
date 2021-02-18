package xmlClasses;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import test.MeasureReaderV3;
import test.TabReaderV3;

public class Chain {
	File TAB;
	String TITLE;
	String NAME;
	String LYRICIST;
	String COMPOSER;
	String LOCATION;
	PartWriter PW = new PartWriter();
	ScorePartwiseWriter SPW;
	
	public Chain(File TAB, String TITLE, String NAME, String LYRICIST, String COMPOSER,String LOCATION){
		this.TAB=TAB;
		this.TITLE=TITLE;
		this.NAME=NAME;
		this.LYRICIST=LYRICIST;
		this.COMPOSER=COMPOSER;
		this.LOCATION=LOCATION;
		MethodLadder();
	}
	public Chain(String TAB, String TITLE, String NAME, String LYRICIST, String COMPOSER,String LOCATION){
		//turning the string into a file so the v3 readers can have a File input type
		try {
			String path = System.getProperty("user.dir") + "/testTab.txt";
			FileWriter myWriter = new FileWriter(path);
			myWriter.write(TAB);
			myWriter.close();
			this.TAB=new File(path);
			System.out.println("Successfully wrote to the file.");
		}catch (IOException e) {
			System.out.println("An error occured in the Chain String constructor.");
			e.printStackTrace();
		}
		
		this.TITLE=TITLE;
		this.NAME=NAME;
		this.LYRICIST=LYRICIST;
		this.COMPOSER=COMPOSER;
		this.LOCATION=LOCATION;
		MethodLadder();
	}
	
	private void MethodLadder() {
		TABtoPART();
		System.out.println("Finished TtoP");
		INFOtoPARTWISE();
		System.out.println("Finished ItoP");
		try {MARSHtoXML();} catch (Exception e) {e.printStackTrace();}
		System.out.println("Finished MtoX");
	}
	
	private void TABtoPART(){
		
		TabReaderV3 TRv3 = new TabReaderV3(TAB.toString(), 6);// 6 - num of string
		
		TRv3.readMeasure();
		while(TRv3.hasNext()) {
			MeasureReaderV3 MRv3 = new MeasureReaderV3(TRv3.getMeasure(), 6, 4, 4);//6 - num of string, 4 4 - time signature
			PW.nextMeasure();
			while(MRv3.hasNext()) {
				MRv3.readNotes();
				boolean firstNoteAdded = false;
				for(String[] s:MRv3.getNotes()) {
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
			TRv3.readMeasure();
		}
			
	}
	
	private void INFOtoPARTWISE() {
		SPW = new ScorePartwiseWriter(NAME, TITLE, LYRICIST, COMPOSER, PW.getPart());
	}
	
	private void MARSHtoXML() throws Exception{  
	    JAXBContext contextObj = JAXBContext.newInstance(Score_Partwise.class);  
	  
	    Marshaller marshallerObj = contextObj.createMarshaller();  
	    marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	    
	    //gives a MusicXML DTD to XML document
	    /*marshallerObj.setProperty("com.sun.xml.internal.bind.xmlHeaders", "\n<!DOCTYPE score-partwise PUBLIC\n"
	    		+ " \"-//Recordare//DTD MusicXML 3.1 Partwise//EN\"\n"
	    		+ " \"http://www.musicxml.org/dtds/partwise.dtd\">");*/
	    
	      
	    //marshallerObj.marshal(spw, new FileOutputStream(LOCATION+"ChainTest.xml"));
	    StringWriter sw = new StringWriter(); 
	    marshallerObj.marshal(SPW.getScore_Partwise(), sw);
	    System.out.println(sw.toString());
	
	}
}
