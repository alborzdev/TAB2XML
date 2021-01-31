package xmlClasses;

import javax.xml.bind.annotation.XmlAttribute;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder={"beats", "beatType"})
public class Time {
	private int beats;
	private int beat_type;
	
	public Time() {}  
	
	public Time(int beats, int beat_type) {  
	    super();  
	    this.beats = beats;  
	    this.beat_type = beat_type; 
	} 
	
	@XmlElement
	public int getBeats() {  
	    return beats;  
	}  
	public void setBeats(int beats) {  
	    this.beats = beats;  
	}
	
	@XmlElement(name="beat-type")
	public int getBeatType() {  
	    return beat_type;  
	}  
	public void setBeatType(int beat_type) {  
	    this.beat_type = beat_type;  
	}
}
