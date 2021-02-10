package xmlClasses;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "note")
@XmlType(propOrder={"pitch", "duration", "type", "accidental"})
public class AlteredNote extends Note{
	private String accidental;
	
	public AlteredNote() {}  
	
	public AlteredNote(int duration, String type, Pitch pitch, String accidental) {
	    super(duration, type, pitch);
	    this.accidental = accidental;
	    
	} 
	
	@XmlElement
	public String getAccidental() {  
	    return accidental;  
	}  
	public void setPitch(String accidental) {  
	    this.accidental = accidental;  
	}
}
