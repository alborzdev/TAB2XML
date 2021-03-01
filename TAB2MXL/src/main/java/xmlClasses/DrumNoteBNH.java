package xmlClasses;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;

public class DrumNoteBNH extends DrumNote{
	private ArrayList<Beam> beam;
	private String notehead;
	
	public DrumNoteBNH() {
		
	}
	public DrumNoteBNH(int duration, String type, Unpitched unpitched, 
			Instrument inst, int voice, String stem, ArrayList<Beam> beam, String notehead) {
		
		super(duration, type, unpitched, inst, voice, stem);
		this.beam = beam;
		this.notehead = notehead;
	}
	
	@XmlElement
	public ArrayList<Beam> getBeam() {  
	    return beam;  
	}  
	public void setBeam(ArrayList<Beam> beam) {  
	    this.beam = beam;  
	}
	
	@XmlElement
	public String getNotehead() {  
	    return notehead;  
	}  
	public void setNotehead(String notehead) {  
	    this.notehead = notehead;  
	}
}
