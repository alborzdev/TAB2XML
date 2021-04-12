package xmlClasses;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;

public class DrumNoteBChord extends DrumNoteB {
	private String chord;
	
	public DrumNoteBChord(int duration, String type, Unpitched unpitched, Instrument inst, int voice, String stem, ArrayList<Beam> beam) {
		super(duration, type, unpitched, inst, voice, stem, beam);
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
