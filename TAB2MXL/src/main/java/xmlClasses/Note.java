package xmlClasses;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder={"chord","grace","pitch", "duration", "type", "notations"})
public class Note {
	private int duration;
	private String type;
	private Pitch pitch;
	private Notations nots;
	private int voice;
	private String chord;
	private String grace;
	
	public Note() {}  
	
//	/* Used for drums, this is all that drums need */
	public Note(int duration, String type) {
	    this.duration = duration;  
	    this.type = type; 
	}
	
	public Note(int duration, String type, Pitch pitch) {
	    this.duration = duration;  
	    this.type = type; 
	    this.pitch = pitch;
	}
	
	public Note(int duration, String type, Pitch pitch, Notations nots, int voice, String chord, String grace) {
	    this.duration = duration;  
	    this.type = type; 
	    this.pitch = pitch;
	    this.nots = nots;
	    this.chord = chord;
	    this.grace = grace;
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
	
	@XmlElement
	public Notations getNotations() {  
	    return nots;  
	}  
	public void setNotations(Notations nots) {  
	    this.nots = nots;  
	}
	
	@XmlElement
	public String getChord() {  
	    return this.chord;  
	}  
	public void setChord(String chord) {  
	    this.chord = chord;
	}
	
	@XmlElement
	public String getGrace() {  
	    return this.grace;  
	}  
	public void setGrace(String grace) {  
	    this.grace = grace;
	}

	
//	@XmlElement
//	public int getVoice() {  
//	    return voice;  
//	}  
//	public void setVoice(int voice) {  
//	    this.voice = voice;  
//	}
}
