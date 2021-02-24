package xmlClasses;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "note")
//@XmlType(propOrder={"chord","pitch", "duration", "type"})
public class ChordNote extends Note{
	private String chord;
	
	public ChordNote() {
		
	}
	
	public ChordNote(int duration, String type, Pitch pitch, Notations nots) {
		super(duration, type, pitch, nots);
		this.chord = "";
	}
	
	@XmlElement
	public String getChord() {  
	    return this.chord;  
	}  
	public void setChord(String chord) {  
	    this.chord = chord;
	}

}
