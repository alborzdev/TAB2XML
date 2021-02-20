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
	
	//---VARIABLES---
	/**this File contains the user's tab to be parsed*/
	File TAB;
	
	/**Contains the Title of the piece. Located top middle*/
	String TITLE;
	
	/**Contains the Lyricist of the piece. Located top left*/
	String LYRICIST;
	
	/**Contains the Title of the piece. Located top right*/
	String COMPOSER;
	
	/**Contains the save location of the xml file.*/
	String LOCATION;
	
	/**Contains the time signature as a 2 digit number. First digit being the numerator. Second being the denominator*/
	int TIMESIG;
	
	/**HARDCODED: C major - Contains the key of the song*/
	String KEY = "CM";
	
	/**This PartWriter object builds and stores the Part object*/
	PartWriter PW = new PartWriter();
	
	/**This ScorePartwiseWriter object builds and stores the ScorePartwise Object*/
	ScorePartwiseWriter SPW;
	
	/**Stores the list of exceptions during chain to give back to GUI*/
	ArrayList<Exception> ERROR;
	
	/**HARDCODED: 6 - represents number staff lines in the tab*/
	int Stafflines = 6;
	
	/**HARDCODED: 2D String array - represents the tuning of the staff lines*/
	String[][] TUNINGINFO = {{"E","2"},{"A","2"},{"D","3"},{"G","3"},{"B","3"},{"E","4"}};
	
	
	//---CONSTRUCTORS---
	
	public Chain(	File TAB, String TITLE, String LYRICIST, String COMPOSER,
					String LOCATION, int TIMESIG, String KEY){
		this.TAB=TAB;
		this.TITLE=TITLE;
		this.LYRICIST=LYRICIST;
		this.COMPOSER=COMPOSER;
		this.LOCATION=LOCATION;
		MethodLadder();
	}
	public Chain(	String TAB, String TITLE, String NAME,
					String LYRICIST, String COMPOSER,
					String LOCATION, int TIMESIG, String KEY){
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
			ERROR.add(e);
		}
		
		this.TITLE=TITLE;
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
		
		TabReaderV3 TRv3 = new TabReaderV3(TAB.toString(), Stafflines);// 6 - num of string
		
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
		SPW = new ScorePartwiseWriter(TITLE, LYRICIST, COMPOSER, PW.getPart());
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
	
	public Exception getError() {
		return ERROR;
	}
}
