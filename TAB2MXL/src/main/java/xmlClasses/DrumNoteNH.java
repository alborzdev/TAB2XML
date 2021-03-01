package xmlClasses;

import javax.xml.bind.annotation.XmlElement;

public class DrumNoteNH extends DrumNote{
	private String notehead;
	
	public DrumNoteNH() {
		
	}
	public DrumNoteNH(int duration, String type, Unpitched unpitched, 
			Instrument inst, int voice, String stem, String notehead) {
		
		super(duration, type, unpitched, inst, voice, stem);
		this.notehead = notehead;
	}
	
	@XmlElement
	public String getNotehead() {  
	    return notehead;  
	}  
	public void setNotehead(String notehead) {  
	    this.notehead = notehead;  
	}
}