package xmlClasses;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder={"pitch", "duration", "type"})
public class Note {
	private int duration;
	private String type;
	private Pitch pitch;
	
	public Note() {}  
	
	public Note(int duration, String type, Pitch pitch) {
	    this.duration = duration;  
	    this.type = type; 
	    this.pitch = pitch;
	} 
	
	@XmlElement
	public int getDuration() {  
	    return duration;  
	}  
	public void setDuration(int duration) {  
	    this.duration = duration;  
	}
	
	@XmlElement
	public String getType() {  
	    return type;  
	}  
	public void setType(String type) {  
	    this.type = type;  
	}
	
	@XmlElement
	public Pitch getPitch() {  
	    return pitch;  
	}  
	public void setPitch(Pitch pitch) {  
	    this.pitch = pitch;  
	}
}
