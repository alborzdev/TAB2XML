package xmlClasses;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;

public class DrumNoteNHChord extends DrumNoteNH{
private String chord;
	
	public DrumNoteNHChord(int duration, String type, Unpitched unpitched, Instrument inst, int voice, String stem, String notehead) {
		super(duration, type, unpitched, inst, voice, stem, notehead);
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
