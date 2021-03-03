package xmlClasses;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;

public class DrumNoteR extends DrumNote{
	private String rest;
	
	public DrumNoteR() {
		
	}
	public DrumNoteR(int duration, String type, Unpitched unpitched, 
			Instrument inst, int voice, String stem) {
		
		super(duration, type, voice);
		this.rest = "";
	}
	
	@XmlElement
	public String getRest() {  
	    return rest;  
	}  
	public void setRest(String rest) {  
	    this.rest = rest;  
	}
}