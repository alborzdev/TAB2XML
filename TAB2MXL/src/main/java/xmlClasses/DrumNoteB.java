package xmlClasses;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

public class DrumNoteB extends DrumNote{
	private ArrayList<Beam> beam;
	
	public DrumNoteB() {
		
	}
	public DrumNoteB(int duration, String type, Unpitched unpitched, 
			Instrument inst, int voice, String stem, ArrayList<Beam> beam) {
		
		super(duration, type, unpitched, inst, voice, stem);
		this.beam = beam;
		this.setStem("up");
	}
	
	@XmlElement
	public ArrayList<Beam> getBeam() {  
	    return beam;  
	}  
	public void setBeam(ArrayList<Beam> beam) {  
	    this.beam = beam;  
	}
}