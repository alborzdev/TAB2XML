package xmlClasses;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

public class DrumNoteBNH extends DrumNote{
	private ArrayList<Beam> beam;
	private String notehead;
	private String stem;
	
	public DrumNoteBNH() {
		
	}
	public DrumNoteBNH(int duration, String type, Unpitched unpitched, 
			Instrument inst, int voice, String stem, ArrayList<Beam> beam, String notehead) {
		
		super(duration, type, unpitched, inst, voice, stem);
		this.beam = beam;
		this.notehead = notehead;
		this.stem = "up";
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
	
	@XmlElement
	public String getStem() {
		return this.stem;
	}	
	public void setStem(String stem) {
		this.stem = stem;
	}
}
