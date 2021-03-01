package xmlClasses;

import javax.xml.bind.annotation.XmlElement;

public class DrumNoteChord extends DrumNote{
		private String chord;
		private String notehead;
	
		public DrumNoteChord() {
			
		}
		public DrumNoteChord(int duration, String type, Unpitched unpitched, 
				Instrument inst, int voice, String stem, String notehead) {
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
		
		@XmlElement
		public String getNotehead() {  
		    return notehead;  
		}  
		public void setNotehead(String notehead) {  
		    this.notehead = notehead;  
		}
}
