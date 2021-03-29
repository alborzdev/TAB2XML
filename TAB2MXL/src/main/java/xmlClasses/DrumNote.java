package xmlClasses;

import java.util.ArrayList;

import javax.xml.bind.annotation.*;

/* Basic Drum Note */
@XmlType(name = "", propOrder = {
    "unpitched",
    "duration",
    "instrument",
    "voice", 
    "type",
    "stem"
})
public class DrumNote {
	private int duration;
	private String type;
	private Unpitched unpitched;
	private Instrument inst;
	private int voice;
	private String stem;
	//private String notehead;
	public DrumNote() {
		
	}
	
	public DrumNote(int duration, String type, Unpitched unpitched, 
					Instrument inst, int voice, String stem) {		
		this.duration = duration;
		this.type = type;
		this.unpitched = unpitched;
		this.inst = inst;
		this.voice = voice;
		this.stem = stem;
	}
	
	public DrumNote(int duration, String type, int voice) {
		this.duration = duration;
		this.type = type;
		this.voice = voice;
}
	
	@XmlElement
	public Unpitched getUnpitched() {  
	    return unpitched;  
	}  
	public void setUnpitched(Unpitched unpitched) {  
	    this.unpitched = unpitched;  
	}
	
	@XmlElement(name="instrument")
	public Instrument getInstrument() {  
	    return inst;  
	}  
	public void setInstrument(Instrument inst) {  
	    this.inst = inst;  
	}


	@XmlElement
	public int getVoice() {  
	    return voice;  
	}  
	public void setVoice(int voice) {  
	    this.voice = voice;  
	}
	
	@XmlElement
	public String getStem() {  
	    return stem;  
	}  
	public void setStem(String stem) {  
	    this.stem = stem;  
	}
	
	@XmlElement
	public String getType() {
		return this.type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	@XmlElement
	public int getDuration() {  
	    return duration;  
	}  
	public void setDuration(int duration) {  
	    this.duration = duration;  
	}
	
	
	
}
