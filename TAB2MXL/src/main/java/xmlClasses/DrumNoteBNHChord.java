package xmlClasses;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;

public class DrumNoteBNHChord extends DrumNoteBNH {
	private String chord;
	
	public DrumNoteBNHChord(int duration, String type, Unpitched unpitched, Instrument inst, int voice, String stem, ArrayList<Beam> beam, String notehead) {
		super(duration, type, unpitched, inst, voice, stem, beam, notehead);
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
