package xmlClasses;

import javax.xml.bind.annotation.XmlElement;

public class DrumNoteChord extends DrumNote{
		private String chord;
	
		public DrumNoteChord(int duration, String type, Unpitched unpitched, Instrument inst, int voice, String stem) {
			super(duration, type, unpitched, inst, voice, stem);
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
